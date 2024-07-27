package edu.neoflex.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EmailMessageTheme {
    FINISH_REGISTRATION ("Завершите оформление"),
    CREATE_DOCUMENTS    ("Перейти к оформлению документов"),
    SEND_DOCUMENTS      ("Документы созданы"),
    SEND_SES            ("Код для подписания"),
    CREDIT_ISSUED       ("Кредит оформлен"),
    STATEMENT_DENIED    ("Отказ");

    private final String emailMessageHeader;

    public String getEmailMessageHeader() {
        return emailMessageHeader;
    }
}
