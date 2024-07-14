package edu.neoflex.service.impl;

import edu.neoflex.dto.EmailMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaSender {

    @Autowired
    KafkaTemplate<String, EmailMessageDto> kafkaTemplate;

    public void sendMessage(String topic, EmailMessageDto text){
        log.info("--kafka-- sending message {} with topic {}", text, topic);

        kafkaTemplate.send(topic, text);
    }
}
