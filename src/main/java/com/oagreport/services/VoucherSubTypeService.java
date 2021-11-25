package com.oagreport.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.datatablesrepository.VoucherSubTypeRepository;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.VoucherSubType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class VoucherSubTypeService {


	
	  @Autowired VoucherSubTypeRepository voucherSubTypeRepository;
	 


    public ServiceResponse loadValues() {
      Map<String, Object> result = new HashMap<>();
      ServiceResponse response=new ServiceResponse(true,result);
      return  response;
    }

	
	  public DataTablesOutput<VoucherSubType>FindAll(DataTablesInput input){
	  DataTablesOutput<VoucherSubType> data =
	  voucherSubTypeRepository.findAll(input); return data; }
	  
	  public Iterable<VoucherSubType> FindAll(){ Iterable<VoucherSubType> data =
	  voucherSubTypeRepository.findAll(); return data; }
	 

}
