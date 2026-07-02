package com.example.order.kafka;

import com.example.order.dto.PaymentRequestEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafka;
    private final String paymentEventsTopic;

    public PaymentEventProducer(KafkaTemplate<String, Object> kafka,
                                @Value("${kafka.topic.payment-events}") String paymentEventsTopic) {
        this.kafka = kafka;
        this.paymentEventsTopic = paymentEventsTopic;
    }

    public void publishPaymentRequest(PaymentRequestEvent event, String correlationId) {
        ProducerRecord<String, Object> record = new ProducerRecord<>(paymentEventsTopic, event.getOrderId(), event);
        record.headers().add("X-Correlation-ID", correlationId.getBytes());
        kafka.send(record);
    }
}