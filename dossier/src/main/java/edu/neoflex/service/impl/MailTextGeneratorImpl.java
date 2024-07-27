package edu.neoflex.service.impl;

import edu.neoflex.dto.EmailMessageDto;
import edu.neoflex.dto.EmailMessageTheme;
import edu.neoflex.service.MailTextGenerator;
import org.springframework.stereotype.Service;

@Service
public class MailTextGeneratorImpl implements MailTextGenerator {

    private final String prefix = "Для заявления ";

    @Override
    public String generateText(EmailMessageDto dto){
        String sesCode = "";
        if(dto.getTheme().equals(EmailMessageTheme.SEND_SES)){
            sesCode = dto.getSesCode();
        }
        return prefix + dto.getStatementId() + ": " +
        switch (dto.getTheme()){
            case FINISH_REGISTRATION -> "Регистрация завершена!";
            case CREDIT_ISSUED -> "Запрос отправлен";
            case SEND_SES -> "Ваш код электронной подписи: " + sesCode;
            case SEND_DOCUMENTS -> "Документы по Вашему займу приложены к данному письму";
            case CREATE_DOCUMENTS -> "Созданы документы по Вашему займу";
            case STATEMENT_DENIED -> "Заявка отклонена.";
        };
    }
}
