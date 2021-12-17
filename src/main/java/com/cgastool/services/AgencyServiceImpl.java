package com.cgastool.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgastool.domain.Agency;
import com.cgastool.domain.AgencyDto;
import com.cgastool.repository.AgencyRepo;

import com.cgastool.repository.AgencyRepository1;

@Service
public class AgencyServiceImpl implements AgencyServce {
	
	@Autowired 
	private AgencyRepo agencyRepo;
	
	@Autowired
	private AgencyRepository1 agencyRepository1;
	
	
	@Override
	public List<Map<String, Object>> getAgencyCodeService(String ag_code) {
		 List<Map<String, Object>> agencylist= agencyRepository1.getAgencyCodeRepo(ag_code);
		return agencylist;	
	}
	
	

}
