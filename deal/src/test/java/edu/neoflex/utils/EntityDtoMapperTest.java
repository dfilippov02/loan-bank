package edu.neoflex.utils;

import edu.neoflex.domain.Client;
import edu.neoflex.domain.Statement;
import edu.neoflex.domain.enums.ApplicationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EntityDtoMapperTest {

    @InjectMocks
    EntityDtoMapper mapper;

    private UUID id = UUID.fromString("8f8daf0a-6f7f-49da-8288-21c445c86fd5");

    @Test
    public void getStatement(){
        Client client = Client.builder().build();

        Statement statement = mapper.getStatementForClient(client);

        assertEquals(statement.getStatus(), ApplicationStatus.PREAPPROVAL);
        assertEquals(statement.getClient(), client);
        assertEquals(statement.getStatusHistory().size(), 1);
    }

}