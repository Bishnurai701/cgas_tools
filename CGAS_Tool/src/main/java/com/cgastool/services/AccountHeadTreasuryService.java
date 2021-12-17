package com.oagreport.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TmsAccountHead;
import com.oagreport.repository.AccountHeadTreasuryRepository;




@Service
@Transactional
public class AccountHeadTreasuryService {


    @Autowired
    AccountHeadTreasuryRepository accountHeadRepository;


    public ServiceResponse loadValues() {

      //Object ministry=  ministryService.getMinistry();

      Map<String, Object> result = new HashMap<>();
      //result.put("ministry", ministry);



      ServiceResponse response=new ServiceResponse(true,result);
      return  response;
    }

    public DataTablesOutput<TmsAccountHead>FindAll(DataTablesInput input){
        DataTablesOutput<TmsAccountHead> data = accountHeadRepository.findAll(input);
        return  data;
    }

    public Iterable<TmsAccountHead> FindAll(){
        Iterable<TmsAccountHead> data = accountHeadRepository.getAccountHead();
        return  data;
    }

  
}

