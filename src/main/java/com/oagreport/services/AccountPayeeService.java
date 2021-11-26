package com.oagreport.services;


import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.datatablesrepository.AccountPayeeRepository;
import com.oagreport.domain.AccountPayee;
import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.ServiceResponse;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AccountPayeeService {


    final AccountPayeeRepository accountPayeeRepository;



    public AccountPayeeService(AccountPayeeRepository accountPayeeRepository) {
        this.accountPayeeRepository = accountPayeeRepository;

    }

	/*
	 * public ServiceResponse loadValues() { Object docTypes=
	 * identityTypeRepository.findAll(); Object banks= bankRepository.findAll();
	 * Object payeeTypes=payeeTypeRepository.findAllByPayeeType(); Map<String,
	 * Object> result = new HashMap<>(); result.put("docTypes", docTypes);
	 * result.put("banks", banks); result.put("payeeTypes", payeeTypes);
	 * ServiceResponse response=new ServiceResponse(true,result); return response; }
	 */

    
    
    
    
    public  Iterable<AccountPayee>  FindAll(AgencyRequestDto request){

        if(request.paymentTypeId==1) {
            Iterable<AccountPayee> data = accountPayeeRepository.finalAllPayeeACPayee(request.agencyId);
            return data;
        }
        else {
            Iterable<AccountPayee> data = accountPayeeRepository.finalAllPayeeEFTPayee(request.agencyId);
            return data;
        }
    }
    
    
    public  Iterable<AccountPayee>  GetAllPayee(AgencyRequestDto request){
        Iterable<AccountPayee> data = accountPayeeRepository.finalAllPayee(request.agencyId);
        return data;
}
    
	/*
	 * public DataTablesOutput<AccountPayee>FindAll(AgencyRequestDto request,
	 * DataTablesInput input){ DataTablesOutput<AccountPayee> data =
	 * accountPayeeRepository.findAll(input, new
	 * AccountPayeeService.AgencywithSpecification(request,input)); return data; }
	 * 
	 * 
	 * public Iterable<AccountPayee> FindAllDepositPayee(AgencyRequestDto request){
	 * 
	 * if(request.paymentTypeId==1) { Iterable<AccountPayee> data =
	 * accountPayeeRepository.finalAllDepositPayeeACPayee(request.agencyId); return
	 * data; } else { Iterable<AccountPayee> data =
	 * accountPayeeRepository.finalAllDepositPayeeEFTPayee(request.agencyId); return
	 * data; } }
	 * 
	 * 
	 * public Iterable<AccountPayee> FindAll(AgencyRequestDto request){
	 * 
	 * if(request.paymentTypeId==1) { Iterable<AccountPayee> data =
	 * accountPayeeRepository.finalAllPayeeACPayee(request.agencyId); return data; }
	 * else { Iterable<AccountPayee> data =
	 * accountPayeeRepository.finalAllPayeeEFTPayee(request.agencyId); return data;
	 * } }
	 * 
	 * public Iterable<AccountPayee> GetAllPayee(AgencyRequestDto request){
	 * Iterable<AccountPayee> data =
	 * accountPayeeRepository.finalAllPayee(request.agencyId); return data; }
	 * 
	 * public Iterable<AccountPayee> FindAllDeductionPayee(AgencyRequestDto
	 * request){
	 * 
	 * if(request.paymentTypeId==1) { Iterable<AccountPayee> data =
	 * accountPayeeRepository.findAllDeductinACPayee(request.agencyId); return data;
	 * } else { Iterable<AccountPayee> data =
	 * accountPayeeRepository.findAllDeductinEPFPayee(request.agencyId); return
	 * data; } }
	 * 
	 * @Transactional public ServiceResponse Update(AccountPayee request){
	 * 
	 * try { AccountPayee toUpdate =
	 * accountPayeeRepository.findById(request.getId()).get();
	 * 
	 * if (toUpdate == null) { ServiceResponse response = new ServiceResponse(false,
	 * "Payee is not found."); }
	 * 
	 * toUpdate.setTypeId(request.getTypeId()); toUpdate.setPan(request.getPan());
	 * toUpdate.setNdescription(request.getNdescription());
	 * toUpdate.setMobile(request.getMobile());
	 * toUpdate.setEaddress(request.getEaddress());
	 * toUpdate.setDesignation(request.getDesignation());
	 * 
	 * if (request.getIsFtAccount() == 0) { toUpdate.setEdescription(null);
	 * toUpdate.setIsFtAccount(null); toUpdate.setEdescription(null);
	 * toUpdate.setBankId(null); toUpdate.setAccountNo(null);
	 * toUpdate.setDocumentTypeId(null); toUpdate.setAccountType(null);
	 * toUpdate.setDocumentRefNo(null); } else {
	 * 
	 * toUpdate.setIsFtAccount(request.getIsFtAccount());
	 * toUpdate.setEdescription(request.getEdescription());
	 * toUpdate.setBankId(request.getBankId());
	 * toUpdate.setAccountNo(request.getAccountNo());
	 * toUpdate.setAccountType(request.getAccountType());
	 * toUpdate.setDocumentTypeId(request.getDocumentTypeId());
	 * toUpdate.setDocumentRefNo(request.getDocumentRefNo()); }
	 * 
	 * 
	 * toUpdate.setNaddress(request.getNaddress());
	 * toUpdate.setEaddress(request.getEaddress());
	 * toUpdate.setEmail(request.getEmail());
	 * 
	 * 
	 * if (request.getIsDepositPayee() == null) { toUpdate.setIsDepositPayee(0); }
	 * else { toUpdate.setIsDepositPayee(request.getIsDepositPayee()); }
	 * 
	 * 
	 * if (request.getIsDeductionPayee() == null) { toUpdate.setIsDeductionPayee(0);
	 * } else { toUpdate.setIsDeductionPayee(request.getIsDeductionPayee()); }
	 * 
	 * toUpdate.setStatus(request.getStatus());
	 * accountPayeeRepository.save(toUpdate); ServiceResponse response = new
	 * ServiceResponse(true, "Payee has updated successfully."); return response;
	 * }catch(Exception ex){ ServiceResponse response = new ServiceResponse(false,
	 * "Error during data save."); return response; } }
	 * 
	 * public ServiceResponse Create(AccountPayee request){ try{
	 * 
	 * if(request.getPan()!="" || request.getPan()!=null) { Long nCountPAN =
	 * accountPayeeRepository.findByPan(request.getAgencyId(), request.getPan(),
	 * request.getNdescription()); if (nCountPAN > 1) { ServiceResponse response =
	 * new ServiceResponse(false, "Duplicate PAN found."); return response; } }
	 * 
	 * String _code=accountPayeeRepository.getPayeeCode(request.getAgencyId());
	 * AccountPayee payee=new AccountPayee(); payee.setFiscalYearId(2078);//Need to
	 * Review payee.setAgencyId(request.getAgencyId()); payee.setCode(_code);
	 * 
	 * if(request.getTypeId()==null) { ServiceResponse response = new
	 * ServiceResponse(false, "Payee Type is required."); return response; }
	 * payee.setTypeId(request.getTypeId()); payee.setPan(request.getPan());
	 * payee.setNdescription(request.getNdescription());
	 * payee.setMobile(request.getMobile());
	 * payee.setEaddress(request.getEaddress());
	 * payee.setNaddress(request.getNaddress());
	 * payee.setTelephone(request.getTelephone());
	 * payee.setDesignation(request.getDesignation());
	 * payee.setEmail(request.getEmail());
	 * 
	 * if(request.getIsFtAccount()==null) { payee.setIsFtAccount(0);
	 * payee.setEdescription(null); payee.setBankId(null); payee.setAccountNo(null);
	 * payee.setDocumentTypeId(null); payee.setDocumentRefNo(null); } else {
	 * payee.setIsFtAccount(request.getIsFtAccount());
	 * payee.setEdescription(request.getEdescription());
	 * payee.setBankId(request.getBankId());
	 * payee.setAccountNo(request.getAccountNo());
	 * payee.setDocumentTypeId(request.getDocumentTypeId());
	 * payee.setAccountType(request.getAccountType());
	 * payee.setDocumentRefNo(request.getDocumentRefNo()); }
	 * 
	 * 
	 * 
	 * if(request.getIsDepositPayee()==null) { payee.setIsDepositPayee(0); } else {
	 * payee.setIsDepositPayee(request.getIsDepositPayee()); }
	 * 
	 * 
	 * if(request.getIsDeductionPayee()==null) { payee.setIsDeductionPayee(0); }
	 * else { payee.setIsDeductionPayee(request.getIsDeductionPayee()); }
	 * 
	 * 
	 * payee.setDesignation(request.getDesignation()); payee.setStatus(1);
	 * payee.setCreatedOn(LocalDateTime.now());
	 * payee.setCreatedBy(SecurityUtils.getUserName());
	 * 
	 * accountPayeeRepository.save(payee); Optional<AccountPayee> accountPayee=
	 * accountPayeeRepository.findById(payee.getId()); Map<String, Object> result =
	 * new HashMap<>(); result.put("accountPayee", accountPayee); ServiceResponse
	 * response = new ServiceResponse(true,
	 * "Payee has created successfully.",result); return response; }catch (Exception
	 * ex){ ServiceResponse response = new ServiceResponse(false, ex.getMessage());
	 * return response; } }
	 * 
	 * public ServiceResponse Delete(long id){ try { AccountPayee accountPayee=
	 * accountPayeeRepository.findById(id).get(); if(accountPayee!=null) { //Need to
	 * check is it used or not in transaction?
	 * //accountPayeeRepository.deleteById(id); ServiceResponse response = new
	 * ServiceResponse(true, "Payee has deleted successfully."); return response;
	 * }else{ ServiceResponse response = new ServiceResponse(false,
	 * "Payee not found!!"); return response; } }catch (DataAccessException ex){
	 * //ServiceResponse response = new ServiceResponse(false,
	 * "Unable to delete record !"); ServiceResponse response = new
	 * ServiceResponse(false, ex.getMessage()); return response; } }
	 * 
	 * public ServiceResponse getById(long id){
	 * if(accountPayeeRepository.existsById(id)) { Optional<AccountPayee>
	 * accountPayee= accountPayeeRepository.findById(id); Map<String, Object> result
	 * = new HashMap<>(); result.put("accountPayee", accountPayee); ServiceResponse
	 * response=new ServiceResponse(true,result); return response; }
	 * 
	 * ServiceResponse response=new ServiceResponse(false,"Not Found"); return
	 * response; }
	 * 
	 * private class AgencywithSpecification implements Specification<AccountPayee>
	 * { private AgencyRequestDto requestDto;
	 * 
	 * public AgencywithSpecification(AgencyRequestDto request,DataTablesInput
	 * input) { requestDto=request; }
	 * 
	 * @Override public Predicate toPredicate(Root<AccountPayee> root,
	 * CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	 * Expression<Long> agencyId = root.get("agencyId").as(Long.class);
	 * Expression<Integer> typeId = root.get("typeId").as(Integer.class); Predicate
	 * a=
	 * criteriaBuilder.and(criteriaBuilder.equal(agencyId,requestDto.getAgencyId()))
	 * ; Predicate b=criteriaBuilder.equal(typeId,1); Predicate
	 * c=criteriaBuilder.equal(typeId,3);
	 * 
	 * Predicate d = criteriaBuilder.or(b, c); return criteriaBuilder.and(a,d); } }
	 */
}
