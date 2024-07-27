package edu.neoflex.messages;

public class MailMessages {

    public static String CREDIT_CONTRACT = """
            Кредитный договор
            ФИО: %s %s %s
            Серия номер паспорта: %s %s
            Дата рождения: %s
            email: %s
            ПСК: %s
            Процентная ставка: %s%%
            Период (в мес): %s
            Сумма ежемесячного платежа: %s
            """;

    public static String PAYMENT_SCHEDULE = """
            Месяц: %s
            Сумма платежа: %s
            Сумма выплат по процентам: %s
            Сумма выплат по долгу: %s
            Оставшаяся задолженность: %s
            
            """;

    public static String APPLICATION = """
            Анкета
            ФИО: %s %s %s
            Серия номер паспорта: %s %s
            Дата рождения: %s
            """;


}
