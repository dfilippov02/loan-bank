package edu.neoflex.service.impl;

import edu.neoflex.domain.Statement;
import edu.neoflex.dto.EmailMessageDto;
import edu.neoflex.dto.EmailMessageTheme;
import edu.neoflex.repository.StatementRepository;
import edu.neoflex.service.SendDocsService;
import edu.neoflex.utils.ClientToScoringDataMapper;
import edu.neoflex.utils.CreditMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendDocsServiceImpl implements SendDocsService {

    private final KafkaSender kafkaSender;
    private final CreditMapper creditMapper = CreditMapper.INSTANCE;
    private final ClientToScoringDataMapper clientToScoringDataMapper = ClientToScoringDataMapper.INSTANCE;
    private final StatementRepository repository;

    /**
     * Формирование и отправка сообщения в кафку
     * @param theme
     * @param topic
     * @param statementId
     */
    @Override
    public void send(EmailMessageTheme theme, String topic, UUID statementId) {

        Statement statement = repository.findById(statementId).orElseThrow();

        EmailMessageDto messageDto = EmailMessageDto.builder()
                .address(statement.getClient().getEmail())
                .statementId(statement.getStatementId())
                .theme(theme).build();

        if(theme.equals(EmailMessageTheme.SEND_DOCUMENTS)){
            messageDto.setAddress(statement.getClient().getEmail());
            messageDto.setStatementId(statement.getStatementId());
            messageDto.setCreditDto(creditMapper.toDto(statement.getCredit()));
            messageDto.setScoringDataDto(clientToScoringDataMapper.map(statement.getClient(), statement));
        }

        if(theme.equals(EmailMessageTheme.SEND_SES)){
            messageDto.setSesCode(statement.getSesCode());
        }

        kafkaSender.sendMessage(topic, messageDto);
    }
}
