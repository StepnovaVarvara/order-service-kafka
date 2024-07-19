package com.example.order_service.listener;

import com.example.order_service.dto.StatusEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaStatusEventListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "${app.kafka.orderStatusTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}")
    public void listen(ConsumerRecord<String, String> kafkaMessage) throws JsonProcessingException {

        objectMapper.registerModule(new JavaTimeModule());

        log.info("Received message: {}", kafkaMessage.value());
        log.info("Key: {}; Partition: {}; Topic: {}; Timestamp: {}", kafkaMessage.key(), kafkaMessage.partition(), kafkaMessage.topic(), kafkaMessage.timestamp());

        StatusEvent statusEvent = objectMapper.readValue(kafkaMessage.value(), StatusEvent.class);

        log.info("StatusEvent from json = {}", statusEvent);
    }


//    @KafkaListener(topics = "${app.kafka.orderStatusTopic}",
//            groupId = "${app.kafka.kafkaMessageGroupId}",
//            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory")
//    public void listen(@Payload StatusEvent statusEvent,
//                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
//                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
//                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
//
//        log.info("Received message: {}", statusEvent.toString());
//        log.info("Key: {}; Partition: {}; Topic: {}; Timestamp: {}", key, partition, topic, timestamp);
//    }
}
