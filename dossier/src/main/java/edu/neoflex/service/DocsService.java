package edu.neoflex.service;

import edu.neoflex.dto.EmailMessageDto;
import org.springframework.core.io.ByteArrayResource;

public interface DocsService {

    ByteArrayResource getApplication(EmailMessageDto data);

    ByteArrayResource getCreditContract(EmailMessageDto data);

    ByteArrayResource getPaymentSchedule(EmailMessageDto data);
}
