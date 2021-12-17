package com.oagreport.domain;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity @Table(name = "IFMIS_C_DEPARTMENT")
public class Department extends BaseDto {
    @Id
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


    @Column(name = "MINISTRY_ID")
    private Long ministryId;

    public Department() {
    }
}