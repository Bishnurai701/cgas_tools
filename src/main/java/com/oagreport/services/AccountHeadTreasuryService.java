package com.oagreport.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.datatablesrepository.AccountHeadTreasuryRepository;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TmsAccountHead;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AccountHeadTreasuryService{

	
	 @Autowired AccountHeadTreasuryRepository accountHeadRepository; 
	  
	  
	  public ServiceResponse loadValues() {
	  
	  //Object ministry= ministryService.getMinistry();
	  
	  Map<String, Object> result = new HashMap<>(); 
	  //result.put("ministry",ministry);
	  
	  
	  
	  ServiceResponse response=new ServiceResponse(true,result); return response; }
	  
	  
	
	  public DataTablesOutput<TmsAccountHead>FindAll(DataTablesInput input){
	  DataTablesOutput<TmsAccountHead> data = accountHeadRepository.findAll(input);
	  return data; }
	  
	  
	  public Iterable<TmsAccountHead> FindAll(){ Iterable<TmsAccountHead> data =
	  accountHeadRepository.getAccountHead(); return data; }
	 
	  
	/*
	 * public ServiceResponse Update(TmsAccountHead request){
	 * 
	 * //final String currentUserName =
	 * SecurityContextHolder.getContext().getAuthentication().getName();
	 * 
	 * // String UserName=SecurityUtils.getUserName();
	 * 
	 * 
	 * 
	 * TmsAccountHead toUpdate =
	 * accountHeadRepository.findById(request.getId()).get();
	 * 
	 * if(toUpdate==null){ ServiceResponse response=new
	 * ServiceResponse(false,"AccountHead is not found."); }
	 * toUpdate.setEdescription(request.getEdescription());
	 * toUpdate.setNdescription(request.getNdescription());
	 * 
	 * 
	 * accountHeadRepository.save(toUpdate); ServiceResponse response=new
	 * ServiceResponse(true,"Account Head has updated sucessfully."); return
	 * response; }
	 * 
	 * @Transactional public ServiceResponse Create(TmsAccountHead request){ try {
	 * if (accountHeadRepository.existsById(request.getId())) { ServiceResponse
	 * response = new ServiceResponse(false, "Already Found"); return response; }
	 * long _id = accountHeadRepository.getMax(); request.setId(_id);
	 * request.setCreatedOn(LocalDateTime.now());
	 * 
	 * 
	 * accountHeadRepository.save(request); ServiceResponse response = new
	 * ServiceResponse(true, "AccouhtHead has created sucessfully."); return
	 * response; }catch (Exception ex){ ServiceResponse response = new
	 * ServiceResponse(false, ex.getMessage()); return response; } }
	 * 
	 * public ServiceResponse getById(long id){
	 * if(accountHeadRepository.existsById(id)) { Optional<TmsAccountHead>
	 * accountHead= accountHeadRepository.findById(id); Map<String, Object> result =
	 * new HashMap<>(); result.put("accountHead", accountHead); ServiceResponse
	 * response=new ServiceResponse(true,result); return response; }
	 * 
	 * ServiceResponse response=new ServiceResponse(false,"Already Found"); return
	 * response; }
	 */
	 
}
