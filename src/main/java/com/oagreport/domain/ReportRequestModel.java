package com.oagreport.domain;

import lombok.Data;


@Data
public class ReportRequestModel {
    private Long fiscalYearId;
    private Long ministryId;
    private Long departmentId;
    private Long treasuryOfficeId;
    private Long agencyId;
    private Long accountId;
    private Long monthId;
    private String dateFrom;
    private String dateTo;
    private Long deductionTypeId=Long.valueOf(0);
    private Long typeId= Long.valueOf(1);
    private Long refId;
    private long ledgerTypeId;
    private long payeeId;
    private long headId;
    private long activityId;
}