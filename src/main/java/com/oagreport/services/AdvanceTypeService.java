package com.oagreport.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.datatablesrepository.AdvanceTypeRepository;
import com.oagreport.domain.AdvanceType;
import com.oagreport.domain.ServiceResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AdvanceTypeService {


	
	  @Autowired AdvanceTypeRepository advanceTypeRepository;
	 


    public ServiceResponse loadValues() {

      //Object ministry=  ministryService.getMinistry();

      Map<String, Object> result = new HashMap<>();
      //result.put("ministry", ministry);



      ServiceResponse response=new ServiceResponse(true,result);
      return  response;
    }

	
	  public DataTablesOutput<AdvanceType>FindAll(DataTablesInput input){
	  DataTablesOutput<AdvanceType> data = advanceTypeRepository.findAll(input);
	  return data; }
	  
	  public Iterable<AdvanceType> FindAll(){ Iterable<AdvanceType> data =
	  advanceTypeRepository.findAll(); return data; }
	 
}

