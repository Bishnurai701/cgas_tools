package com.oagreport.domain;



import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AgencyRequestDto extends BaseDto {
    public Long agencyId;
    public Long accountId;
    public Long economicId;
    public Long activityId;
    public Long donorId;
    public Long sourceTypeId;
    public Integer fiscalYearId;
    public Integer paymentTypeId;
    public Long payeeId;
    public Long monthId;
    public Long treasuryOfficeId;
    public  Long typeId;
    public  Long id;
    public String transactionNo;
    public Integer deductionTypeId;

    @Temporal(TemporalType.DATE)
    public Date fromDate;


    @Temporal(TemporalType.DATE)
    public Date toDate;
}
