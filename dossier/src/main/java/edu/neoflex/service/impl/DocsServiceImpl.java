package edu.neoflex.service.impl;

import edu.neoflex.dto.EmailMessageDto;
import edu.neoflex.messages.MailMessages;
import edu.neoflex.service.DocsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DocsServiceImpl implements DocsService {

    /**
     * Создание документа анкеты
     * @param data
     * @return
     */
    @Override
    public ByteArrayResource getApplication(EmailMessageDto data){

        log.info("Creating application for {}", data.getStatementId());

        String app = MailMessages.APPLICATION.formatted(
                data.getScoringDataDto().getFirstName(),
                data.getScoringDataDto().getLastName(),
                data.getScoringDataDto().getMiddleName(),
                data.getScoringDataDto().getPassportSeries(),
                data.getScoringDataDto().getPassportNumber(),
                data.getScoringDataDto().getBirthdate().toString()
        );

        log.info("Application for {} was created", data.getStatementId());

        return new ByteArrayResource(app.getBytes());
    }

    /**
     * Создание крудитного договора
     * @param data
     * @return
     */
    @Override
    public ByteArrayResource getCreditContract(EmailMessageDto data){

        log.info("Creating credit contract for {}", data.getStatementId());

        String creditContract = MailMessages.CREDIT_CONTRACT.formatted(
                data.getScoringDataDto().getFirstName(),
                data.getScoringDataDto().getLastName(),
                data.getScoringDataDto().getMiddleName(),
                data.getScoringDataDto().getPassportSeries(),
                data.getScoringDataDto().getPassportNumber(),
                data.getScoringDataDto().getBirthdate().toString(),
                data.getScoringDataDto().getAmount(),
                data.getCreditDto().getPsk(),
                data.getCreditDto().getRate(),
                data.getCreditDto().getTerm(),
                data.getCreditDto().getMonthlyPayment()
        );

        log.info("Credit contract for {} was created", data.getStatementId());

        return new ByteArrayResource(creditContract.getBytes());
    }

    /**
     * Создание графика платежей
     * @param data
     * @return
     */
    @Override
    public ByteArrayResource getPaymentSchedule(EmailMessageDto data) {

        log.info("Creating payment schedule for {}", data.getStatementId());

        StringBuilder sb = new StringBuilder("График платежей \n");
        data.getCreditDto().getPaymentSchedule().forEach(payment -> sb.append(
                MailMessages.PAYMENT_SCHEDULE.formatted(
                        payment.getDate().getMonth(),
                        payment.getTotalPayment(),
                        payment.getInterestPayment(),
                        payment.getDebtPayment(),
                        payment.getRemainingDebt())));

        log.info("Payment schedule for {} was created", data.getStatementId());

        return new ByteArrayResource(sb.toString().getBytes());
    }
}
