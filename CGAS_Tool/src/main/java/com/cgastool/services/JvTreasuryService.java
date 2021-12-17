package com.oagreport.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oagreport.domain.AccountDto;
import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.ServiceResponse;





@Service
public class JvTreasuryService {
	
	@Autowired
	 private AccountHeadTreasuryService accountHeadService;
	
	@Autowired
	private PaymentTypeService paymentTypeService;
	
	@Autowired
	private VoucherTypeService voucherTypeService;
	
	@Autowired
	private AdvanceTypeService advanceTypeService;
	
	@Autowired
	private AmountTypeService amountTypeService;
	
	
	@Autowired
	private VoucherSubTypeService voucherSubTypeService;

	
	@Autowired
	private AuthorizationService authorizationService;
	
	   public ServiceResponse getAccount(AgencyRequestDto request) {
	        Object accountHeads=accountHeadService.FindAll();
	        Object paymentTypes=paymentTypeService.FindAll();
	        Object voucherTypes=voucherTypeService.FindAll();
	        Object advanceTypes=advanceTypeService.FindAll();
	        Object amountTypes=amountTypeService.FindAll();

	        Object voucherSubTypes=voucherSubTypeService.FindAll();

	       // List<AccountDto> accountItems= agencyDetailService.getAccount(request);
	        List<AccountDto> accountItems=authorizationService.GetBudgetAccount(request);

	        Map<String, Object> result = new HashMap<>();
	        result.put("accounts", accountItems);
	        result.put("accountHeads", accountHeads);
	        result.put("paymentTypes", paymentTypes);
	        result.put("voucherTypes", voucherTypes);
	        result.put("voucherSubTypes", voucherSubTypes);
	        result.put("amountTypes",amountTypes);
	        result.put("advanceTypes",advanceTypes);

	        ServiceResponse response=new ServiceResponse(true,result);
	        return  response;
	    }
	
}
