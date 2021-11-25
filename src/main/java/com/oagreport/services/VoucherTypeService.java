package com.oagreport.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.datatablesrepository.VoucherTypeRepository;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class VoucherTypeService {


	
	  @Autowired VoucherTypeRepository voucherTypeRepository;
	 


    public ServiceResponse loadValues() {

      //Object ministry=  ministryService.getMinistry();

      Map<String, Object> result = new HashMap<>();
      //result.put("ministry", ministry);



      ServiceResponse response=new ServiceResponse(true,result);
      return  response;
    }

	
	  public DataTablesOutput<VoucherType>FindAll(DataTablesInput input){
	  DataTablesOutput<VoucherType> data = voucherTypeRepository.findAll(input);
	  return data; }
	 

	
	  public Iterable<VoucherType> FindAll(){ Iterable<VoucherType> data =
	  voucherTypeRepository.findAll(); return data; }
	 

   
}
