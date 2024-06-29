package edu.neoflex.repository;

import edu.neoflex.domain.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatementRepository extends JpaRepository<Statement, UUID> {
}
