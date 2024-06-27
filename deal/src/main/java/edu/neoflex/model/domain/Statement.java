package edu.neoflex.model.domain;

import edu.neoflex.model.domain.enums.ApplicationStatus;
import edu.neoflex.model.domain.jsonb.StatusHistory;
import edu.neoflex.model.dto.LoanOfferDto;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
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
public class Statement {

    @Id
    @Column(name = "statement_id")
    private UUID statementId = UUID.randomUUID();

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "sign_date")
    private LocalDateTime signDate;

    @Column(name = "ses_code")
    private String sesCode;

    //------------------------------------------------------------------------------------------------------------------
    // ENUMS

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;


    //------------------------------------------------------------------------------------------------------------------
    // JSONB


    @Type(type = "json")
    @Column(name = "status_history", columnDefinition = "jsonb")
    private List<StatusHistory> statusHistory = new ArrayList<>();

    @Type(type = "json")
    @Column(name = "applied_offer", columnDefinition = "jsonb")
    private LoanOfferDto appliedOffer;


    //------------------------------------------------------------------------------------------------------------------
    // LINKS

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;
}
