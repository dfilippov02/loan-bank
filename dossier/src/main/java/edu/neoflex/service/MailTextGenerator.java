package edu.neoflex.service;

import edu.neoflex.dto.EmailMessageDto;

public interface MailTextGenerator {
    String generateText(EmailMessageDto dto);
}
