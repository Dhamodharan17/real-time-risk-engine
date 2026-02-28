package com.riskengine.service;

import com.riskengine.model.Rate;
import com.riskengine.model.RiskResult;
import com.riskengine.model.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class RiskEngineConsumer {

    private final KieContainer kieContainer;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public RiskEngineConsumer(KieContainer kieContainer,
                              KafkaTemplate<String, Object> kafkaTemplate) {
        this.kieContainer = kieContainer;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "user-events", groupId = "risk-engine")
    public void consume(UserEvent event) {
        // 1. Create the container for the result
        RiskResult riskResult = new RiskResult();

        KieSession session = kieContainer.newKieSession();

        // Print all rules loaded in the session before firing
        System.out.println("------ Rules in Session ------");
        session.getKieBase().getKiePackages().forEach(pkg -> {
            pkg.getRules().forEach(rule -> System.out.println("Package: " + pkg.getName() + " | Rule: " + rule.getName()));
        });
        System.out.println("------------------------------");

        try {
            // 2. Set the global BEFORE firing
            session.setGlobal("risk", riskResult);
            session.insert(event);

            int fired = session.fireAllRules();
            System.out.println("Rules fired = " + fired);

            // 3. If rules fired, riskResult is now populated by the engine
            if (fired > 0) {
                System.out.println("Result: " + riskResult.getRiskLevel());
                // kafkaTemplate.send("risk-decisions", riskResult.getUserId(), riskResult);
            }
        } finally {
            session.dispose();
        }
    }
}