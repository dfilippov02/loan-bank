package edu.neoflex.service;

import edu.neoflex.dto.EmailMessageDto;

import javax.mail.MessagingException;

public interface NotificationsMailSender {
    void sendSimpleMessage(EmailMessageDto data);

    void sendDocxMessage(EmailMessageDto data) throws MessagingException;
}
