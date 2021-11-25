package com.oagreport.domain;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    public BigDecimal accountId;
    public String code;
    public String edescription;
    public String ndescription;

    public AccountDto(BigDecimal accountId, String code, String edescription, String ndescription) {
        this.accountId = accountId;
        this.code = code;
        this.edescription = edescription;
        this.ndescription = ndescription;
    }
}
