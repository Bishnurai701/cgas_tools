package com.oagreport.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.datatablesrepository.AmountTypeRepository;
import com.oagreport.domain.AmountType;

@Service
@Transactional
public class AmountTypeService {


	
	  @Autowired AmountTypeRepository amountTypeRepository;
	 


//    public ServiceResponse loadValues() {
//
//      //Object ministry=  ministryService.getMinistry();
//
//      Map<String, Object> result = new HashMap<>();
//      //result.put("ministry", ministry);
//
//
//
//      ServiceResponse response=new ServiceResponse(true,result);
//      return  response;
//    }
//
//    public DataTablesOutput<AdvanceType>FindAll(DataTablesInput input){
//        DataTablesOutput<AdvanceType> data = advanceTypeRepository.findAll(input);
//        return  data;
//    }

	
	  public Iterable<AmountType> FindAll(){ Iterable<AmountType> data =
	  amountTypeRepository.findAll(); return data; }
	 

//    public ServiceResponse Update(AdvanceType request){
//
//        //final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        //String UserName=SecurityUtils.getUserName();
//
//
//
//        AdvanceType toUpdate = advanceTypeRepository.findById(request.getId()).get();
//
//        if(toUpdate==null){
//            ServiceResponse response=new ServiceResponse(false,"AdvanceType is not found.");
//        }
//        toUpdate.setEdescription(request.getEdescription());
//        toUpdate.setNdescription(request.getNdescription());
//
//
//        advanceTypeRepository.save(toUpdate);
//        ServiceResponse response=new ServiceResponse(true,"AdvanceType has updated sucessfully.");
//        return  response;
//    }
//
//    @Transactional
//    public ServiceResponse Create(AdvanceType request){
//        try {
//            if (advanceTypeRepository.existsById(request.getId())) {
//                ServiceResponse response = new ServiceResponse(false, "Already Found");
//                return response;
//            }
//            long _id = advanceTypeRepository.getMax();
//            request.setId(_id);
//            request.setCreatedOn(LocalDateTime.now());
//
//
//            advanceTypeRepository.save(request);
//            ServiceResponse response = new ServiceResponse(true, "AdvanceType has created sucessfully.");
//            return response;
//        }catch (Exception ex){
//            ServiceResponse response = new ServiceResponse(false, ex.getMessage());
//            return response;
//        }
//    }
//
//    public ServiceResponse getById(long id){
//        if(advanceTypeRepository.existsById(id)) {
//            Optional<AdvanceType> advanceType= advanceTypeRepository.findById(id);
//            Map<String, Object> result = new HashMap<>();
//            result.put("advanceType", advanceType);
//            ServiceResponse response=new ServiceResponse(true,result);
//            return  response;
//        }
//
//        ServiceResponse response=new ServiceResponse(false,"Already Found");
//        return response;
//    }
}