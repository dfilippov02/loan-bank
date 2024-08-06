package edu.neoflex.service;

import edu.neoflex.domain.Statement;

import java.util.List;
import java.util.UUID;

public interface StatementService {
    Statement getStatement(UUID statementId);
    List<Statement> getStatementList();
}
