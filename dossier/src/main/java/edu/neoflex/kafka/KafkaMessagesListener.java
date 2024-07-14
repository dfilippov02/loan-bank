package edu.neoflex.kafka;

import edu.neoflex.config.KafkaTopics;
import edu.neoflex.dto.EmailMessageDto;
import edu.neoflex.service.NotificationsMailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessagesListener {

    private final NotificationsMailSender notificationsMailSender;

    @KafkaListener(topics = {
            KafkaTopics.FINISH_REGISTRATION,
            KafkaTopics.CREATE_DOCUMENTS,
            KafkaTopics.SEND_SES,
            KafkaTopics.CREDIT_ISSUED,
            KafkaTopics.STATEMENT_DENIED
    }, groupId = "group1")
    void listener(EmailMessageDto data) {
        log.info("Received message [{}] in group1", data);

        notificationsMailSender.sendSimpleMessage(data);
    }

    @KafkaListener(topics = {
            KafkaTopics.SEND_DOCUMENTS,
    }, groupId = "group1")
    void sendDocsListener(EmailMessageDto data) throws MessagingException {
        log.info("Received message {} in group1", data);

        notificationsMailSender.sendDocxMessage(data);
    }
}