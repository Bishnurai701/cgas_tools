package com.oagreport.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "IFMIS_C_AGENCY")
public class Agency extends BaseDto {
    @Id
    @Column(name = "ID") //,columnDefinition = "long default 0"
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "CODE")
    private String code;

    @NotNull
    @Size(max = 250)
    @Column(name = "E_DESCRIPTION")
    private String edescription;

    @NotNull
    @Size(max = 500)
    @Column(name = "N_DESCRIPTION")
    private String ndescription;

    @NotNull
    @Size(max = 250)
    @Column(name = "E_ADDRESS",length = 255,  nullable = true)
    private String eaddress;

    @NotNull
    @Size(max = 500)
    @Column(name = "N_ADDRESS", nullable = true)
    private String naddress;


    @Column(name = "FISCALYEAR_ID", nullable = true)
    private int fiscalYearId;

    @Column(name = "TYPE", nullable = false)
    private int type;

    @NotNull(message = "Treasury OfficeId is Required")
    @Column(name = "TREASURYOFFICE_ID", nullable = true)
    private Long treasuryOfficeId;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "TREASURYOFFICE_ID")
//    private TreasuryOffice treasuryOffice;



//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "PROVINCE_ID")
//    private Province province;

    @Column(name = "DISTRICT_ID", nullable = true)
    private Long districtId;

    @Column(name = "LOCALBODY_ID")
    private Long localBodyId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MINISTRY_ID")
    private Ministry ministry;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

//    @Column(name = "DEPARTMENT_ID", nullable = true)
//    private Long departmentId;


    public Agency() {
    }
}
