package com.cgastool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor

@Entity
@Table(name="IFMIS_C_AGENCY")
public class Agency{
	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "TREASURYOFFICE_ID", nullable = true)
	private Long treasuryOfficeId;
	
	
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
	
	
	public Agency() {
	}
}
