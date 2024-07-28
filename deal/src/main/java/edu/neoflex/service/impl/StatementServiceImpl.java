package edu.neoflex.service.impl;

import edu.neoflex.domain.Statement;
import edu.neoflex.exception.AppException;
import edu.neoflex.repository.StatementRepository;
import edu.neoflex.service.StatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatementServiceImpl implements StatementService {

    private final StatementRepository repository;

    @Override
    public Statement getStatement(UUID statementId) {
        log.info("Requesting statement with id {}", statementId);
        return repository.findById(statementId).orElseThrow(() -> new AppException(new Throwable("Invalid statement id")));
    }

    @Override
    public List<Statement> getStatementList() {
        log.info("Requesting all statements");
        return repository.findAll();
    }
}
