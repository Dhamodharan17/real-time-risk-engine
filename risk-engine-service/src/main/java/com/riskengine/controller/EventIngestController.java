package com.riskengine.controller;

import com.riskengine.model.UserEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventIngestController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventIngestController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<Void> ingest(@RequestBody UserEvent event) {
        kafkaTemplate.send("user-events", event.getUserId(), event);
        return ResponseEntity.accepted().build();
    }
}