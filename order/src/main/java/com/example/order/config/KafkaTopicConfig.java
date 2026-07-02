package com.example.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.payment-events}")
    private String paymentEventsTopic;

    @Value("${kafka.topic.payment-results}")
    private String paymentResultsTopic;

    @Bean
    public NewTopic paymentEventsTopic() {
        return TopicBuilder.name(paymentEventsTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentResultsTopic() {
        return TopicBuilder.name(paymentResultsTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}