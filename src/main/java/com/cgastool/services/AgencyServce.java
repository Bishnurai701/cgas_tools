package com.cgastool.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cgastool.domain.AgencyDto;



@Component
public interface AgencyServce {
	
	List<Map<String, Object>> getAgencyCodeService(String ag_code) ;

}
