package com.oagreport.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


public interface JvVoucherDto {
    public BigDecimal getId();
    public String getJvNo();
    public String getVoucherEType();
    public String getVoucherNType();
    public Date getEDate();
    public String getNDate();
    public BigDecimal getAgencyId();
    public BigDecimal getAccountId();
    public BigDecimal getAmount();
    public BigDecimal getNarration();
    public String getStatus();
    public String getCreatedBy();
    public Date getCreatedOn();

}