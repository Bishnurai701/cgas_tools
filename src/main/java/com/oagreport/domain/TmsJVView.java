package com.oagreport.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "VW_TMS_JV")
public class TmsJVView {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "JV_NO")
    private Long jvNo;

    @Column(name = "EDATE")
    private Date edate;

    @Column(name = "NDATE")
    private String ndate;

    @Column(name = "VOUCHER_TYPE_ID")
    private Long voucherTypeId;

    @Column(name = "VOUCHER_SUBTYPE_ID")
    private Long voucherSubTypeId;

    @Column(name = "VoucherEType")
    private String voucherEType;

    @Column(name = "VoucherNType")
    private String voucherNType;

    @Column(name = "FISCALYEAR_ID")
    private Integer fiscalYearId;

    @Column(name = "FISCALYEAR")
    private String fiscalYear;

    @Column(name = "AgencyId")
    private Integer agencyId;

    @Column(name = "AccountId")
    private Integer accountId;

    @Column(name = "projectEDescription")
    private String projectEdescription;

    @Column(name = "projectNDescription")
    private String projectNdescription;

    @Column(name = "levelEDescription")
    private String levelEdescription;
//
//    @Column(name = "levelNDescription")
//    private String levelNdescription;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "Narration")
    private String narration;

    @Column(name = "STATUS", nullable = false)
    private Long status;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "STATUS_DESC", nullable = false)
    private String statusDesc;

    @Column(name = "POGEN_VOUCHER", nullable = false)
    private Integer poGenVoucher;

    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;

    @Column(name = "CREATED_ON", nullable = false)
    private Date createdOn;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "JV_ID")
//    @OrderBy("sno")
//   private List<TmsJVDetailActivityView> jvDetailActivity;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "JV_ID")
//    @OrderBy("id")
//    private List<TmsJVDetailEconomicView> jvDetailEconomic;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "JV_ID")
//    @OrderBy("sno")
//    private List<TmsJVPayeeView> payees;

    public TmsJVView() {
    }
}
