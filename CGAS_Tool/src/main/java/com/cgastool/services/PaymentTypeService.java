package com.oagreport.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.PaymentType;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.repository.PaymentTypeRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class PaymentTypeService {


    @Autowired
    PaymentTypeRepository paymentTypeRepository;


    public ServiceResponse loadValues() {

      //Object ministry=  ministryService.getMinistry();

      Map<String, Object> result = new HashMap<>();
      //result.put("ministry", ministry);



      ServiceResponse response=new ServiceResponse(true,result);
      return  response;
    }

    public DataTablesOutput<PaymentType>FindAll(DataTablesInput input){
        DataTablesOutput<PaymentType> data = paymentTypeRepository.findAll(input);
        return  data;
    }

    public Iterable<PaymentType> FindAll(){
        Iterable<PaymentType> data = paymentTypeRepository.findAll();
        return  data;
    }






}
