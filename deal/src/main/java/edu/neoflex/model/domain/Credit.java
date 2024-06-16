package edu.neoflex.model.domain;

import edu.neoflex.model.domain.enums.CreditStatus;
import edu.neoflex.model.dto.PaymentScheduleElementDto;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@TypeDef(name = "json", typeClass = JsonType.class)
public class Credit {

    @Id
    @Column(name = "credit_id")
    private UUID creditId = UUID.randomUUID();

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "term")
    private Integer term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "psk")
    private BigDecimal psk;

    private Boolean insuranceEnabled;

    private Boolean isSalaryClient;

    //------------------------------------------------------------------------------------------------------------------
    // ENUMS

    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;

    //------------------------------------------------------------------------------------------------------------------
    // JSONB

    @Type(type = "json")
    @Column(name = "payment_schedule", columnDefinition = "jsonb")
    private List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();

    //------------------------------------------------------------------------------------------------------------------
    // LINKS

    @OneToOne(mappedBy = "credit", fetch = FetchType.LAZY)
    private Statement statement;

}
