package edu.neoflex.service;

import edu.neoflex.dto.EmailMessageTheme;

import java.util.UUID;

public interface SendDocsService {

    void send(EmailMessageTheme theme, String topic, UUID statementId);
}
