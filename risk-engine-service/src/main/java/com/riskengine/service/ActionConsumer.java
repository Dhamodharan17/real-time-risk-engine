package com.riskengine.service;

import com.riskengine.model.RiskResult;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ActionConsumer {

    @KafkaListener(topics = "risk-decisions", groupId = "action-service")
    public void handle(RiskResult result) {
        if ("HIGH".equals(result.getRiskLevel())) {
            System.out.println("🚨 BLOCK USER: " + result.getUserId());
        }
    }
}