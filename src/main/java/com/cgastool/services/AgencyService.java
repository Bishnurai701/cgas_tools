package com.cgastool.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.cgastool.domain.Agency;
import com.cgastool.domain.AgencyDto;
import com.cgastool.domain.ServiceResponse;
import com.cgastool.repository.AgencyRepo;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Service
public class AgencyService {
	
	@Autowired
	private AgencyRepo agencyRepo;
	

	public String getCodeService(String code) {
		return agencyRepo.getCodeRepo(code);
	}
	
	public List<Agency> getAllAgencyService() {
		List<Agency> account= agencyRepo.findAll();
		return account;
		
	}
	
	 public ServiceResponse getById(long id){
	        if(agencyRepo.existsById(id)) {
	            Optional<Agency> account= agencyRepo.findById(id);
	            Map<String, Object> result = new HashMap<>();
	            result.put("account", account);
	            ServiceResponse response=new ServiceResponse(true,result);
	            return  response;
	        }

	        ServiceResponse response=new ServiceResponse(false,"Already Found");
	        return response;
	    }
	 
	 public List<Map<String, Object>> getIdByCodeService(String agencycode){
	          return agencyRepo.getIdByCodeRepo(agencycode);
	    }
	 
	 
	 
	 
	 
	
//	public Agency getByCode(String code) {
//		return agencyRepo.findAgencyByCode(code);
//	}
	
//	public Agency getCode(String code)
//	{  
//		  List<Agency> agency = agencyRepo.findByCode(code);
//		   return  agency.get(1);  
	      
//	List<Agency> agency = new ArrayList<Agency>();  
//	agencyRepo.findByCode(code);
//	 return agency;
	

}
