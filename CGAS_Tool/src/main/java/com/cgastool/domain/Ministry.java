package com.oagreport.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "IFMIS_C_MINISTRY")
@NamedQueries(
        {
                @NamedQuery(name=Ministry.FindByMinistryId, query="select e from Ministry e where e.id=:id")
        })
public class Ministry extends BaseDto {

    private static final String NAMESPACE = "Ministry.";
    public static final String FindByMinistryId = NAMESPACE + "FindByMinistryId";

    @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PERSON")
    // @SequenceGenerator(sequenceName = "SQ_PERSON", allocationSize = 1, name = "SQ_PERSON")
    @Column(name="ID")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name="CODE")
    private String code;


    @NotNull
    @Size(max = 250)
    @Column(name="E_DESCRIPTION")
    private String edescription;

    @NotNull
    @Size(max = 500)
    @Column(name="N_DESCRIPTION")
    private String ndescription;

    @Column(name = "FISCALYEAR_ID")
    private int fiscalYearId;

    @Column(name = "DOMAIN_ID")
    private int domainId;



    public Ministry() {
    }



}