package com.example.order_service.controller;

import com.example.order_service.dto.Order;
import com.example.order_service.dto.OrderEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order/kafka")
@RequiredArgsConstructor
public class OrderKafkaController {
    private int counter = 1;

    @Value("${app.kafka.orderTopic}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/send")
    public String getOrder(@RequestBody Order order) throws JsonProcessingException {
        OrderEvent orderEvent = new OrderEvent()
                .setProduct("Product " + counter)
                .setQuantity(counter);
        counter++;

        String orderEventJson = objectMapper.writeValueAsString(orderEvent);

        kafkaTemplate.send(topicName, orderEventJson);

        return "Message sent to kafka with OrderEvent > " + orderEvent.getProduct();
    }
}
