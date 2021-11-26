package com.oagreport.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "AC_C_PAYEE")
public class AccountPayee extends BaseDto {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "PayeeSeqGenerator")
    @SequenceGenerator(name="PayeeSeqGenerator", sequenceName = "SEQ_PAYEE_ID", allocationSize = 1)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "FISCALYEAR_ID")
    private Integer fiscalYearId;

    @Column(name = "AGENCY_ID")
    private Long agencyId;

    @Size(max = 250)
    @Column(name = "NAME")
    private String name;

    @Size(max = 250)
    @Column(name = "DESIGNATION")
    private String designation;

    @Size(max = 50)
    @Column(name = "PAN")
    private String pan;

    @Size(max = 250)
    @Column(name = "E_DESCRIPTION")
    private String edescription;


    @Size(max = 500)
    @Column(name = "N_DESCRIPTION")
    private String ndescription;


    @Size(max = 250)
    @Column(name = "E_ADDRESS")
    private String eaddress;


    @Size(max = 500)
    @Column(name = "N_ADDRESS")
    private String naddress;


    @Size(max = 250)
    @Column(name = "PARENT_RELATION")
    private String parentRelation;


    @Size(max = 500)
    @Column(name = "PARENT_NAME")
    private String parentName;


    @Size(max = 250)
    @Column(name = "GRANDPARENT_RELATION")
    private String grantParentRelation;


    @Size(max = 500)
    @Column(name = "GRANDPARENT_NAME")
    private String grantParentName;

    @Size(max = 50)
    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "IS_DEPOSIT_PAYEE")
    private Integer isDepositPayee;

    @Column(name = "IS_DEDUCT_PAYEE")
    private Integer isDeductionPayee;

    @Column(name = "IS_FT_ACCOUNT")
    private Integer isFtAccount;


    @Column(name = "ACCOUNT_NO")
    private String accountNo;


    @Column(name = "ACCOUNT_TYPE")
    private String accountType;


    @Column(name = "BANK_ID")
    private Long bankId;


    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BRANCH_ID")
    private Integer branchId;


    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "DOCUMENT_TYPE_ID")
    private Integer documentTypeId;

    @Column(name = "DOCUMENT_REF_NO")
    private String documentRefNo;


    @Size(max = 1000)
    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "TYPE_ID", nullable = false)
    private Integer typeId;

    public AccountPayee() {
    }
}
