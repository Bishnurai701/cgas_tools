package com.cgastool.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;


@Getter
@Entity
public class AgencyDto {
	@Id
	private Long ID;
	private String CODE;
	private Long FISCALYEAR_ID;
	private String E_DESCRIPTION;
	private String N_DESCRIPTION;
	private Long TREASURYOFFICE_ID;
	private Long TYPE;
	private Long PROVINCE_ID;
	private Long DISTRICT_ID;
	private Long LOCALBODY_ID;
	private Long MINISTRY_ID;
	private Long DEPARTMENT_ID;
	private String E_ADDRESS;
	private String N_ADDRESS;
	private char IS_EXPENDITURE;
	private char IS_REVENUE;
	private char IS_DEPOSIT;
	private String REMARKS;
	private int STATUS;
	private String CREATED_BY;
	private Date CREATED_ON;
	private String MINISTRY_CODE;
	private String DEPARTMENT_CODE;
	private String DISTRICT_CODE;
}
