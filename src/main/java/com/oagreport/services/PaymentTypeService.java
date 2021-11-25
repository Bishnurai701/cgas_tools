package com.oagreport.services;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oagreport.datatablesrepository.PaymentTypeRepository;
import com.oagreport.domain.PaymentType;
import com.oagreport.domain.ServiceResponse;

@Service
@Transactional
public class PaymentTypeService {


	
	  @Autowired PaymentTypeRepository paymentTypeRepository;
	 
	 

	/*
	 * public ServiceResponse loadValues() {
	 * 
	 * //Object ministry= ministryService.getMinistry();
	 * 
	 * Map<String, Object> result = new HashMap<>(); //result.put("ministry",
	 * ministry);
	 * 
	 * 
	 * 
	 * ServiceResponse response=new ServiceResponse(true,result); return response; }
	 */

	/*
	 * public DataTablesOutput<PaymentType>FindAll(DataTablesInput input){
	 * DataTablesOutput<PaymentType> data = paymentTypeRepository.findAll(input);
	 * return data; }
	 */

	
	
	  public Iterable<PaymentType> FindAll(){ Iterable<PaymentType> data =
	  paymentTypeRepository.findAll(); return data; }
	 
	 

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
	 * PaymentType toUpdate = paymentTypeRepository.findById(request.getId()).get();
	 * 
	 * if(toUpdate==null){ ServiceResponse response=new
	 * ServiceResponse(false,"AccountHead is not found."); }
	 * toUpdate.setEdescription(request.getEdescription());
	 * toUpdate.setNdescription(request.getNdescription());
	 * 
	 * 
	 * paymentTypeRepository.save(toUpdate); ServiceResponse response=new
	 * ServiceResponse(true,"Account Head has updated sucessfully."); return
	 * response; }
	 */
	/*
	 * @Transactional public ServiceResponse Create(PaymentType request){ try { if
	 * (paymentTypeRepository.existsById(request.getId())) { ServiceResponse
	 * response = new ServiceResponse(false, "Already Found"); return response; }
	 * long _id = paymentTypeRepository.getMax(); request.setId(_id);
	 * request.setCreatedOn(LocalDateTime.now());
	 * 
	 * 
	 * paymentTypeRepository.save(request); ServiceResponse response = new
	 * ServiceResponse(true, "AccouhtHead has created sucessfully."); return
	 * response; }catch (Exception ex){ ServiceResponse response = new
	 * ServiceResponse(false, ex.getMessage()); return response; } }
	 */

	/*
	 * public ServiceResponse getById(long id){
	 * if(paymentTypeRepository.existsById(id)) { Optional<PaymentType> paymentType=
	 * paymentTypeRepository.findById(id); Map<String, Object> result = new
	 * HashMap<>(); result.put("paymentType", paymentType); ServiceResponse
	 * response=new ServiceResponse(true,result); return response; }
	 * 
	 * ServiceResponse response=new ServiceResponse(false,"Already Found"); return
	 * response; }
	 */
}