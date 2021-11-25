package com.oagreport.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "AC_MISC_ACCOUNT")
public class MiscAccount implements Serializable{
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "FISCALYEAR_ID")
    private Integer fiscalYearId;

    @Column(name = "TREASURYOFFICE_ID")
    private Integer treasuryOfficeId;

    @Column(name = "ACCOUNTNO")
    private String accountNo;

    @Column(name = "E_DESCRIPTION")
    private String edescription;

    @Column(name = "N_DESCRIPTION")
    private String ndescription;


    public MiscAccount() {
    }
}