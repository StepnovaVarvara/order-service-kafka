package com.example.order_service.controller;

import com.example.order_service.dto.Order;
import com.example.order_service.dto.OrderEvent;
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

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;


    // получаем Order и отправляем сообщение в Kafka
    @PostMapping("/send")
    public String getOrder(@RequestBody Order order) {
        OrderEvent orderEvent = new OrderEvent()
                .setProduct("Product " + counter)
                .setQuantity(counter);

        counter++;

        kafkaTemplate.send(topicName, orderEvent);

        return "Message sent to kafka with OrderEvent > " + orderEvent.getProduct();
    }
}
