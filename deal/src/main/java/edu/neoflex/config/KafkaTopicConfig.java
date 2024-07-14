package edu.neoflex.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic finishRegistration() {
        return new NewTopic(KafkaTopics.FINISH_REGISTRATION, 1, (short) 1);
    }

    @Bean
    public NewTopic createDocs() {
        return new NewTopic(KafkaTopics.CREATE_DOCUMENTS, 1, (short) 1);
    }

    @Bean
    public NewTopic sendDocs() {
        return new NewTopic(KafkaTopics.SEND_DOCUMENTS, 1, (short) 1);
    }

    @Bean
    public NewTopic sendSes() {
        return new NewTopic(KafkaTopics.SEND_SES, 1, (short) 1);
    }

    @Bean
    public NewTopic creditIssued() {
        return new NewTopic(KafkaTopics.CREDIT_ISSUED, 1, (short) 1);
    }

    @Bean
    public NewTopic denied() {
        return new NewTopic(KafkaTopics.STATEMENT_DENIED, 1, (short) 1);
    }
}
