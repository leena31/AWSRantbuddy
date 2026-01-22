package com.web.rantbuddy.service;


import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class EventPublisher {

    private static final String EVENT_BUS = "rantbuddy-bus";

    private final EventBridgeClient eventBridgeClient;
    private final ObjectMapper objectMapper;

    public EventPublisher(EventBridgeClient eventBridgeClient, ObjectMapper objectMapper) {
        this.eventBridgeClient = eventBridgeClient;
        this.objectMapper = objectMapper;
    }

    public void publishRantCreatedEvent(String rantId) {

        try {
            Map<String, String> detail = new HashMap<>();
            detail.put("rantId", rantId);

            PutEventsRequestEntry entry = PutEventsRequestEntry.builder()
                    .eventBusName(EVENT_BUS)
                    .source("rantbuddy.api")
                    .detailType("RantCreated")
                    .detail(objectMapper.writeValueAsString(detail))
                    .build();

            eventBridgeClient.putEvents(
                    PutEventsRequest.builder()
                            .entries(entry)
                            .build()
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to publish RantCreated event", e);
        }
    }
}
