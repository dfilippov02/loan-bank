package edu.neoflex.domain;

import edu.neoflex.domain.enums.Gender;
import edu.neoflex.domain.enums.MaritalStatus;
import edu.neoflex.domain.jsonb.Employment;
import edu.neoflex.domain.jsonb.Passport;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@TypeDef(name = "json", typeClass = JsonType.class)
public class Client {

    @Id
    @Column(name = "client_id")
    private UUID clientId = UUID.randomUUID();

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @Column(name = "account_number")
    private UUID accountNumber;


    //------------------------------------------------------------------------------------------------------------------
    // ENUMS

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;


    //------------------------------------------------------------------------------------------------------------------
    // JSONB

    @Type(type = "json")
    @Column(name = "passport", columnDefinition = "jsonb")
    private Passport passport;

    @Type(type = "json")
    @Column(name = "employment", columnDefinition = "jsonb")
    private Employment employment;


    //------------------------------------------------------------------------------------------------------------------
    // LINKS

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY)
    private Statement statement;

}
