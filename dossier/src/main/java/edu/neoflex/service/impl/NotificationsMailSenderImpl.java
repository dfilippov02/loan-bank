package edu.neoflex.service.impl;

import edu.neoflex.dto.EmailMessageDto;
import edu.neoflex.service.DocsService;
import edu.neoflex.service.MailTextGenerator;
import edu.neoflex.service.NotificationsMailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsMailSenderImpl implements NotificationsMailSender {

    private final JavaMailSender mailSender;
    private final MailTextGenerator textGenerator;
    private final DocsService docsService;

    /**
     * Отправка простого сообщения без вложений
     * @param data
     */
    @Override
    public void sendSimpleMessage(EmailMessageDto data){
        log.info("Creating simple message for {}, theme: {}",
                data.getStatementId(),
                data.getTheme());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ntest-mail-sender@yandex.ru");
        mailMessage.setTo(data.getAddress());
        mailMessage.setSubject(data.getTheme().getEmailMessageHeader());
        mailMessage.setText(textGenerator.generateText(data));

        log.info("Sending simple message for {}, theme: {}, text: {}",
                data.getStatementId(),
                data.getTheme(),
                mailMessage.getText());

        mailSender.send(mailMessage);
    }

    /**
     * Отправка сообщения с вложениями (документами)
     * @param data
     * @throws MessagingException
     */
    @Override
    public void sendDocxMessage(EmailMessageDto data) throws MessagingException {

        log.info("Creating message with docs for {}, theme: {}",
                data.getStatementId(),
                data.getTheme());

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("ntest-mail-sender@yandex.ru");
        helper.setTo(data.getAddress());
        helper.setSubject(data.getTheme().getEmailMessageHeader());
        helper.setText(textGenerator.generateText(data));

        helper.addAttachment("График.txt", docsService.getPaymentSchedule(data));
        helper.addAttachment("Анкета.txt", docsService.getApplication(data));
        helper.addAttachment("Кредитный договор.txt", docsService.getCreditContract(data));

        log.info("Sending message with docs for {}", data.getStatementId());

        mailSender.send(mimeMessage);
    }
}
