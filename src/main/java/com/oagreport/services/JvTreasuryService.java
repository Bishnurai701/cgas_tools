package com.oagreport.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oagreport.datatablesrepository.AccountHeadMiscRepository;
import com.oagreport.datatablesrepository.JvViewTreasuryRepository;
import com.oagreport.datatablesrepository.LedgerTypeRepository;
import com.oagreport.domain.AccountDto;
import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.MiscAccount;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TmsJVView;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oracle.jdbc.OracleTypes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
        import org.springframework.data.jdbc.support.oracle.SqlReturnStruct;
        import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
        import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
        import org.springframework.data.jpa.domain.Specification;
        import org.springframework.jdbc.core.JdbcTemplate;
        import org.springframework.jdbc.core.SqlOutParameter;
        import org.springframework.jdbc.core.SqlParameter;
        import org.springframework.jdbc.core.simple.SimpleJdbcCall;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;
 
        import javax.annotation.PostConstruct;
        import javax.persistence.EntityManager;
        import javax.persistence.ParameterMode;
        import javax.persistence.PersistenceContext;
        import javax.persistence.StoredProcedureQuery;
        import javax.persistence.criteria.*;
        import javax.sql.DataSource;
        import java.math.BigDecimal;
        import java.sql.Types;
        import java.time.LocalDateTime;
        import java.util.*;
        import java.util.stream.Collectors;

@Service
@Transactional
public class JvTreasuryService {
	
	@Autowired
    private PaymentTypeService paymentTypeService;

	final AccountPayeeService payeeService;
	final JvViewTreasuryRepository jvViewRepository;
	final LedgerTypeRepository ledgerTypeRepository;
    final VoucherTypeService voucherTypeService;
    final VoucherSubTypeService voucherSubTypeService;
    final AccountHeadTreasuryService accountHeadService;

    final AmountTypeService amountTypeService;
    final AdvanceTypeService advanceTypeService;


    final  AuthorizationService authorizationService;
    private final DataSource dataSource;
 



    final ModelMapper modelMapper;


    @PersistenceContext
    private EntityManager em;

    public JvTreasuryService(LedgerTypeRepository ledgerTypeRepository,AccountPayeeService payeeService,PaymentTypeService paymentTypeService, DataSource dataSource, VoucherTypeService voucherTypeService, VoucherSubTypeService voucherSubTypeService, AccountHeadTreasuryService accountHeadService, ModelMapper modelMapper, AmountTypeService amountTypeService,AdvanceTypeService advanceTypeService, AuthorizationService authorizationService, JvViewTreasuryRepository jvViewRepository) {
        this.ledgerTypeRepository = ledgerTypeRepository;
    	this.payeeService =payeeService;
    	this.paymentTypeService = paymentTypeService;
		this.jvViewRepository = jvViewRepository;
        this.dataSource = dataSource;
        this.voucherTypeService = voucherTypeService;
        this.voucherSubTypeService = voucherSubTypeService;
        this.accountHeadService = accountHeadService;
        this.modelMapper = modelMapper;
        this.amountTypeService = amountTypeService;
        this.advanceTypeService = advanceTypeService;
        this.authorizationService = authorizationService;

   
    }
    
    




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
		  result.put("voucherTypes",voucherTypes); result.put("voucherSubTypes",
		  voucherSubTypes); result.put("amountTypes",amountTypes);
		  result.put("advanceTypes",advanceTypes);
		 
		 
		 

        ServiceResponse response=new ServiceResponse(true,result);
        return  response;
    }
	
	
	
	/*
	 * public ServiceResponse getActivities(AgencyRequestDto request) {
	 * List<ActivitiesDto> activities= agencyDetailService.getActivities(request);
	 * Map<String, Object> result = new HashMap<>(); result.put("activities",
	 * activities); ServiceResponse response=new ServiceResponse(true,result);
	 * return response; }
	 * 
	 * public ServiceResponse getEconomics(AgencyRequestDto request) {
	 * List<EconomicsDto> economics= agencyDetailService.getEconomics(request);
	 * Map<String, Object> result = new HashMap<>(); result.put("economics",
	 * economics); ServiceResponse response=new ServiceResponse(true,result); return
	 * response; }
	 */
	
	  public DataTablesOutput<TmsJVView>FindAll(AgencyRequestDto request,
	  DataTablesInput input){ DataTablesOutput<TmsJVView> data =
	  jvViewRepository.findAll(input, new
	  AgencywithFiscalYearAccountSpecification(request,input)); return data; }
	  
	  
	  public Iterable<TmsJVView> FindAll(){ Iterable<TmsJVView> data =
	  jvViewRepository.findAll(); return data; }
	  
	  
	  
	    public ServiceResponse getPayee(AgencyRequestDto request) {
	        Object payees=payeeService.FindAll(request);
	        Object ledgerTypes=ledgerTypeRepository.findAllByStatus();
	        Map<String, Object> result = new HashMap<>();
	        result.put("payees", payees);
	        result.put("ledgerTypes", ledgerTypes);

	        ServiceResponse response=new ServiceResponse(true,result);
	        return  response;
	    }

	    public ServiceResponse getAllPayee(AgencyRequestDto request) {
	        Object payees=payeeService.GetAllPayee(request);
	        Map<String, Object> result = new HashMap<>();
	        result.put("payees", payees);
	        ServiceResponse response=new ServiceResponse(true,result);
	        return  response;
	    }
	  
		
		/*
		 * @Transactional public ServiceResponse Update(TmsJvInfo request){ TmsJV
		 * toUpdate = jvRepository.findById(request.getId()).get();
		 * if(toUpdate.getStatus()==1) {
		 * 
		 * request.setVoucherTypeId(3);//Adjustment Voucher
		 * 
		 * if(request.getVoucherSubTypeId() !=33) { request.setVoucherNo(""); }
		 * 
		 * Integer nCount=0; if(request.getPoGenVoucher()==1) { for ( TmsJvDetailInfo
		 * detail: request.getJvDetails()) { if (detail.getHeadId() == 9) { nCount = 1;
		 * } } if(nCount==0) { ServiceResponse response = new ServiceResponse(false,
		 * "भुक्तानी आदेश बनाउने Dr/Cr Budget छैन"); return response; } }
		 * 
		 * 
		 * // jvRepository.deleteById(toUpdate.getId()); //String nCode =
		 * request.getCode(); // TmsJvInfo info=AddPerform(request); //try{
		 * //info.setCode(nCode); //info.setJvNo(nCode); // jvRepository.save(info);
		 * //return new ServiceResponse(true,
		 * "Adjustment Voucher has updated successfully."); // } catch (Exception ex) {
		 * // ServiceResponse response = new ServiceResponse(false, ex.getMessage()); //
		 * return response; // }
		 * 
		 * 
		 * try { TmsJvInfo info1 = AddPerform(request); GsonBuilder builder=new
		 * GsonBuilder().serializeNulls(); Gson gson = builder.create(); String
		 * strRequest = gson.toJson(info1); String nuserId =
		 * SecurityUtils.getUserName();
		 * 
		 * StoredProcedureQuery query =
		 * em.createStoredProcedureQuery("Adjustment.EditJV")
		 * .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
		 * .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
		 * .registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
		 * .registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
		 * .setParameter(1, strRequest) .setParameter(2, nuserId);
		 * 
		 * query.execute(); Integer nStatus = (Integer)
		 * query.getOutputParameterValue(3); String nMessage = (String)
		 * query.getOutputParameterValue(4);
		 * 
		 * if (nStatus == 0) return new ServiceResponse(true, nMessage); else return new
		 * ServiceResponse(false, nMessage);
		 * 
		 * } catch (Exception ex) { ServiceResponse response = new
		 * ServiceResponse(false, "Error during process. Contact to Support."); return
		 * response; } } ServiceResponse response = new ServiceResponse(false,
		 * "Edit operation is not allowed."); return response; }
		 * 
		 * 
		 * 
		 * private TmsJvInfo AddPerform(TmsJvInfo request) { TmsJvInfo info=new
		 * TmsJvInfo();
		 * 
		 * info.setId(request.getId()); info.setFiscalYearId(request.getFiscalYearId());
		 * info.setAgencyId(request.getAgencyId());
		 * info.setAccountId(request.getAccountId());
		 * info.setVoucherTypeId(request.getVoucherTypeId());
		 * info.setVoucherSubTypeId(request.getVoucherSubTypeId());
		 * info.setVoucherNo(request.getVoucherNo());
		 * info.setPoGenVoucher(request.getPoGenVoucher());
		 * info.setEdate(request.getEdate()); info.setNdate(request.getNdate());
		 * info.setRemarks(request.getRemarks()); info.setStatus(1);
		 * info.setCreatedBy(SecurityUtils.getUserName());
		 * info.setCreatedOn(LocalDateTime.now()); //
		 * info.setCode(jvRepository.getCode(request.getAgencyId(),request.getAccountId(
		 * ))); // info.setJvNo(info.getCode()); info.setRemarks(request.getRemarks());
		 * info.setNarration(request.getNarration());
		 * info.setTotalcramount(request.getTotalcramount()); Long i = 0L;
		 * 
		 * List<TmsJvDetailInfo> _details=new ArrayList<>();
		 * 
		 * for ( TmsJvDetailInfo detail: request.getJvDetails()) { i=i+1;
		 * TmsJvDetailInfo _detail=new TmsJvDetailInfo(); if(detail.getHeadId()==31){
		 * detail.setActivityId(null); detail.setComponentId(null);
		 * detail.setDonorGrpId(null); detail.setDonorId(null);
		 * detail.setEconomicId(null); detail.setSourceTypeId(null); }
		 * _detail.setSno(i); _detail.setTxnType(detail.getTxnType());
		 * _detail.setHeadId(detail.getHeadId());
		 * _detail.setEconomicId(detail.getEconomicId());
		 * _detail.setComponentId(detail.getComponentId());
		 * _detail.setActivityId(detail.getActivityId());
		 * _detail.setDonorGrpId(detail.getDonorGrpId());
		 * _detail.setDonorId(detail.getDonorId());
		 * _detail.setSourceTypeId(detail.getSourceTypeId());
		 * _detail.setDrAmount(detail.getDrAmount());
		 * _detail.setCrAmount(detail.getCrAmount());
		 * _detail.setStatus(info.getStatus());
		 * _detail.setCreatedBy(info.getCreatedBy());
		 * _detail.setCreatedOn(info.getCreatedOn());
		 * 
		 * _detail.setVoucherNo(info.getVoucherNo()); _details.add(_detail);
		 * 
		 * } info.setJvDetails(_details); return info; }
		 * 
		 * private TmsJvInfo AdjustPerform(TmsJvInfo request) { TmsJvInfo info=new
		 * TmsJvInfo(); info.setId(request.getId());
		 * info.setRemarks(request.getRemarks()); info.setStatus(request.getStatus());
		 * info.setNarration(request.getNarration()); Long i = 0L;
		 * 
		 * List<TmsJvDetailInfo> _details=new ArrayList<>();
		 * 
		 * for ( TmsJvDetailInfo detail: request.getJvDetails()) { i=i+1;
		 * TmsJvDetailInfo _detail=new TmsJvDetailInfo(); if(detail.getHeadId()==31){
		 * detail.setActivityId(null); detail.setComponentId(null);
		 * detail.setDonorGrpId(null); detail.setDonorId(null);
		 * detail.setEconomicId(null); detail.setSourceTypeId(null); }
		 * _detail.setId(detail.getId()); _detail.setSno(detail.getSno());
		 * _detail.setTxnType(detail.getTxnType());
		 * _detail.setHeadId(detail.getHeadId());
		 * _detail.setEconomicId(detail.getEconomicId());
		 * _detail.setComponentId(detail.getComponentId());
		 * _detail.setActivityId(detail.getActivityId());
		 * _detail.setDonorGrpId(detail.getDonorGrpId());
		 * _detail.setDonorId(detail.getDonorId());
		 * _detail.setSourceTypeId(detail.getSourceTypeId());
		 * _detail.setDrAmount(detail.getDrAmount());
		 * _detail.setCrAmount(detail.getCrAmount()); _details.add(_detail);
		 * 
		 * } info.setJvDetails(_details); return info; }
		 * 
		 * @Transactional public ServiceResponse Create(TmsJvInfo request){
		 * request.setVoucherTypeId(3);//Adjustment Voucher
		 * 
		 * if(request.getVoucherSubTypeId()==32){ //Direct Payment for ( TmsJvDetailInfo
		 * detail: request.getJvDetails()) { if (detail.getSourceTypeId() == 3 ||
		 * detail.getSourceTypeId() == 5 || detail.getSourceTypeId() == 7) {
		 * 
		 * }else{ ServiceResponse response = new ServiceResponse(false,
		 * "You can not add this source in Direct Payment"); return response; } } }
		 * 
		 * if(request.getVoucherSubTypeId() !=33) { request.setVoucherNo(""); }
		 * 
		 * Integer nCount=0; if(request.getPoGenVoucher()==1) { for ( TmsJvDetailInfo
		 * detail: request.getJvDetails()) { if (detail.getHeadId() == 9) { nCount = 1;
		 * } } if(nCount==0) { ServiceResponse response = new ServiceResponse(false,
		 * "भुक्तानी आदेश बनाउने Dr/Cr Budget छैन"); return response; } }
		 * 
		 * try { //TmsJV info=AddPerform(request);
		 * 
		 * try { TmsJvInfo info = AddPerform(request); GsonBuilder builder=new
		 * GsonBuilder().serializeNulls(); Gson gson = builder.create(); String
		 * strRequest = gson.toJson(info); String nuserId = SecurityUtils.getUserName();
		 * 
		 * StoredProcedureQuery query =
		 * em.createStoredProcedureQuery("Adjustment.CreateJv")
		 * .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
		 * .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
		 * .registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
		 * .registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
		 * .setParameter(1, strRequest) .setParameter(2, nuserId);
		 * 
		 * query.execute(); Integer nStatus = (Integer)
		 * query.getOutputParameterValue(3); String nMessage = (String)
		 * query.getOutputParameterValue(4);
		 * 
		 * if (nStatus == 0) return new ServiceResponse(true, nMessage); else return new
		 * ServiceResponse(false, nMessage);
		 * 
		 * } catch (Exception ex) { ServiceResponse response = new
		 * ServiceResponse(false, "Error during process. Contact to Support."); return
		 * response; }
		 * 
		 * 
		 * 
		 * 
		 * // jvRepository.save(info); // return new ServiceResponse(true,
		 * "Adjustment Journal Voucher has created successfully."); }catch
		 * (DataAccessException ex){ ServiceResponse response = new
		 * ServiceResponse(false, ex.getCause().getLocalizedMessage()); return response;
		 * } catch (RuntimeException ex){ ServiceResponse response = new
		 * ServiceResponse(false, ex.getMessage()); return response; } catch (Exception
		 * ex){ ServiceResponse response = new ServiceResponse(false, ex.getMessage());
		 * return response; } }
		 * 
		 * @Transactional public ServiceResponse Adjustment(TmsJvInfo request){
		 * if(SecurityUtils.hasRole(RoleNames.FCGO_ADMIN)) {
		 * 
		 * 
		 * try { TmsJvInfo info = AdjustPerform(request); GsonBuilder builder=new
		 * GsonBuilder().serializeNulls(); Gson gson = builder.create(); String
		 * strRequest = gson.toJson(info); String nuserId = SecurityUtils.getUserName();
		 * 
		 * StoredProcedureQuery query =
		 * em.createStoredProcedureQuery("Adjustment.JVAdjustment")
		 * .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
		 * .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
		 * .registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
		 * .registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
		 * .setParameter(1, strRequest) .setParameter(2, nuserId);
		 * 
		 * query.execute(); Integer nStatus = (Integer)
		 * query.getOutputParameterValue(3); String nMessage = (String)
		 * query.getOutputParameterValue(4);
		 * 
		 * if (nStatus == 0) return new ServiceResponse(true, nMessage); else return new
		 * ServiceResponse(false, nMessage);
		 * 
		 * } catch (Exception ex) { ServiceResponse response = new
		 * ServiceResponse(false, "Error during process. Contact to Support."); return
		 * response; } }else{ ServiceResponse response = new ServiceResponse(false,
		 * "No Permission to adjust Voucher."); return response; }
		 * 
		 * }
		 * 
		 * 
		 * public ServiceResponse GenerateJvByBill(JvRequestDto request){ try { //
		 * if(request.getEdate().after(new Date())) { // ServiceResponse response = new
		 * ServiceResponse(false, "Select Valid Transaction Date."); // return response;
		 * // } boolean isSuccess=false;
		 * 
		 * request.setCreatedBy(SecurityUtils.getUserName());
		 * 
		 * JvRequestStructMapper requestMapper = modelMapper.map(request,
		 * JvRequestStructMapper.class); List<JvIdDto>
		 * jvIdDto=Arrays.asList(modelMapper.map(request.getIds(),JvIdDto[].class));
		 * TypeResult type=GenerateBillJV(requestMapper,jvIdDto);
		 * if(type.getStatus()==1) isSuccess=true;
		 * 
		 * ServiceResponse response= new ServiceResponse(isSuccess, type.getMessage());
		 * return response;
		 * 
		 * }catch (Exception ex){ ServiceResponse response = new ServiceResponse(false,
		 * "Unable to generate JV Voucher."); return response; } }
		 * 
		 * 
		 * @Transactional public ServiceResponse VerifyNoPoJV(JvRequestDto request){
		 * if(SecurityUtils.hasRole(RoleNames.AGENCY_ADMIN)) {
		 * 
		 * try { boolean isSuccess = false;
		 * request.setCreatedBy(SecurityUtils.getUserName()); List<JvIdDto>
		 * _Id=request.getIds();
		 * 
		 * TmsJV jv= jvRepository.findById(_Id.get(0).getId()).get(); jv.setStatus(2);
		 * jvRepository.save(jv);
		 * 
		 * ServiceResponse response = new ServiceResponse(true,
		 * "Journal Voucher has approved successfully."); return response;
		 * 
		 * } catch (Exception ex) { ServiceResponse response = new
		 * ServiceResponse(false, "Unable to generate JV Voucher."); return response; }
		 * 
		 * }else{ ServiceResponse response = new ServiceResponse(false,
		 * "Only Admin Role can verify the Voucher."); return response; } }
		 * 
		 * @Transactional public ServiceResponse GenerateJvByNikasa(JvRequestDto
		 * request){ try { boolean isSuccess=false;
		 * 
		 * request.setCreatedBy(SecurityUtils.getUserName());
		 * 
		 * JvRequestStructMapper requestMapper = modelMapper.map(request,
		 * JvRequestStructMapper.class); List<JvIdDto>
		 * jvIdDto=Arrays.asList(modelMapper.map(request.getIds(),JvIdDto[].class));
		 * TypeResult type=GenerateNikasaJV(requestMapper,jvIdDto);
		 * if(type.getStatus()==1) isSuccess=true;
		 * 
		 * ServiceResponse response= new ServiceResponse(isSuccess, type.getMessage());
		 * return response;
		 * 
		 * }catch (Exception ex){ ServiceResponse response = new ServiceResponse(false,
		 * "Unable to generate JV Voucher."); return response; } }
		 * 
		 * @Transactional public ServiceResponse
		 * generateReversePaymentOrder(JvRequestDto request){ try { boolean
		 * isSuccess=false;
		 * 
		 * request.setCreatedBy(SecurityUtils.getUserName());
		 * 
		 * JvRequestStructMapper requestMapper = modelMapper.map(request,
		 * JvRequestStructMapper.class); List<JvIdDto>
		 * jvIdDto=Arrays.asList(modelMapper.map(request.getIds(),JvIdDto[].class));
		 * TypeResult type=GenerateTSANikasaJV(requestMapper,jvIdDto);
		 * if(type.getStatus()==1) isSuccess=true;
		 * 
		 * ServiceResponse response= new ServiceResponse(isSuccess,
		 * "Nikasa JV Voucher has generated successfully."); return response;
		 * 
		 * }catch (Exception ex){ ServiceResponse response = new ServiceResponse(false,
		 * "Unable to generate JV Voucher."); return response; } }
		 * 
		 * @Transactional public ServiceResponse GenerateTSANikasaVoucher(JvRequestDto
		 * request){ try { boolean isSuccess=false;
		 * 
		 * request.setCreatedBy(SecurityUtils.getUserName());
		 * 
		 * JvRequestStructMapper requestMapper = modelMapper.map(request,
		 * JvRequestStructMapper.class); List<JvIdDto>
		 * jvIdDto=Arrays.asList(modelMapper.map(request.getIds(),JvIdDto[].class));
		 * TypeResult type=GenerateTSANikasaJV(requestMapper,jvIdDto);
		 * if(type.getStatus()==1) isSuccess=true;
		 * 
		 * ServiceResponse response= new ServiceResponse(isSuccess,
		 * "Nikasa JV Voucher has generated successfully."); return response;
		 * 
		 * }catch (Exception ex){ ServiceResponse response = new ServiceResponse(false,
		 * "Unable to generate JV Voucher."); return response; } }
		 * 
		 * @Transactional public ServiceResponse GenerateRefundVoucher(JvRequestDto
		 * request){ try { boolean isSuccess=false;
		 * 
		 * request.setCreatedBy(SecurityUtils.getUserName());
		 * 
		 * JvRequestStructMapper requestMapper = modelMapper.map(request,
		 * JvRequestStructMapper.class); List<JvIdDto>
		 * jvIdDto=Arrays.asList(modelMapper.map(request.getIds(),JvIdDto[].class));
		 * TypeResult type=GenerateEFTRefundJV(requestMapper,jvIdDto);
		 * if(type.getStatus()==1) isSuccess=true;
		 * 
		 * ServiceResponse response= new ServiceResponse(isSuccess, type.getMessage());
		 * return response;
		 * 
		 * }catch (Exception ex){ ServiceResponse response = new ServiceResponse(false,
		 * "Unable to generate JV Voucher."); return response; } }
		 * 
		 * @Transactional public ServiceResponse GeneratePreviousPeski(JvRequestDto
		 * request){ try { boolean isSuccess=false;
		 * 
		 * request.setCreatedBy(SecurityUtils.getUserName());
		 * 
		 * JvRequestStructMapper requestMapper = modelMapper.map(request,
		 * JvRequestStructMapper.class); List<JvIdDto>
		 * jvIdDto=Arrays.asList(modelMapper.map(request.getIds(),JvIdDto[].class));
		 * TypeResult type=GeneratePreviousPeskiJV(requestMapper,jvIdDto);
		 * if(type.getStatus()==1) isSuccess=true;
		 * 
		 * ServiceResponse response= new ServiceResponse(isSuccess, type.getMessage());
		 * return response;
		 * 
		 * }catch (Exception ex){ ServiceResponse response = new ServiceResponse(false,
		 * "Unable to generate JV Voucher."); return response; } }
		 * 
		 * 
		 * @Transactional public ServiceResponse GeneratePaymentOrder(JvRequestDto
		 * request){ try {
		 * 
		 * boolean isSuccess=false; // request.ndate="1";
		 * request.setCreatedBy(SecurityUtils.getUserName()); JvRequestStructMapper
		 * requestMapper = modelMapper.map(request, JvRequestStructMapper.class);
		 * List<JvIdDto>
		 * jvIdDto=Arrays.asList(modelMapper.map(request.getIds(),JvIdDto[].class));
		 * TypeResult type=GeneratePaymentOrder(requestMapper,jvIdDto);
		 * if(type.getStatus()==1) isSuccess=true;
		 * 
		 * ServiceResponse response= new ServiceResponse(isSuccess, type.getMessage());
		 * return response;
		 * 
		 * }catch (Exception ex){ ServiceResponse response = new ServiceResponse(false,
		 * ex.getMessage()); return response; } }
		 * 
		 * 
		 * 
		 * 
		 * @Transactional public ServiceResponse GenerateJvBySalary(JvRequestDto
		 * request){ try { boolean isSuccess=false;
		 * request.setCreatedBy(SecurityUtils.getUserName());
		 * 
		 * JvRequestStructMapper requestMapper = modelMapper.map(request,
		 * JvRequestStructMapper.class); List<JvIdDto>
		 * jvIdDto=Arrays.asList(modelMapper.map(request.getIds(),JvIdDto[].class));
		 * TypeResult type=GenerateSalaryJV(requestMapper,jvIdDto);
		 * if(type.getStatus()==1) isSuccess=true;
		 * 
		 * ServiceResponse response= new ServiceResponse(isSuccess, type.getMessage());
		 * return response;
		 * 
		 * }catch (Exception ex){ ServiceResponse response = new ServiceResponse(false,
		 * ex.getMessage()); return response; } }
		 * 
		 * public ServiceResponse getById(long id){ if(jvRepository.existsById(id)) {
		 * TmsJV jv= jvRepository.findById(id).get(); List<TmsJVDetail>
		 * details=jv.getJvDetails();
		 * 
		 * jv.setJvDetails(null);
		 * 
		 * Gson gson = new Gson(); String jsonString = gson.toJson(jv); TmsJvRes
		 * response = gson.fromJson(jsonString, TmsJvRes.class);
		 * 
		 * 
		 * 
		 * 
		 * List<TmsJvDetailRes> listDetails = new ArrayList<TmsJvDetailRes>();
		 * 
		 * details.forEach(i -> { TmsJvDetailRes res = new TmsJvDetailRes();
		 * res.setActivityId(i.getActivityId()); res.setComponentId(i.getComponentId());
		 * res.setCrAmount(i.getCrAmount()); res.setDonorGrpId(i.getDonorGrpId());
		 * res.setDonorId(i.getDonorId()); res.setDrAmount(i.getDrAmount());
		 * res.setActivityId(i.getActivityId()); res.setEconomicId(i.getEconomicId());
		 * res.setSourceTypeId(i.getSourceTypeId()); res.setSno(i.getSno());
		 * res.setHeadId(i.getHeadId()); res.setTxnType(i.getTxnType());
		 * res.setId(i.getId()); listDetails.add(res); });
		 * 
		 * 
		 * response.setJvDetails(listDetails);
		 * 
		 * Map<String, Object> result = new HashMap<>(); result.put("jv", response);
		 * ServiceResponse responseResult=new ServiceResponse(true,result); return
		 * responseResult;
		 * 
		 * }
		 * 
		 * ServiceResponse response=new ServiceResponse(false,"Not Found"); return
		 * response; }
		 * 
		 * public ServiceResponse print(long id){
		 * if(jvViewPrintTreasuryRepository.existsById(id)) { Optional<TmsJVViewPrint>
		 * jv= jvViewPrintTreasuryRepository.findById(id); Map<String, Object> result =
		 * new HashMap<>(); result.put("jv", jv); ServiceResponse response=new
		 * ServiceResponse(true,result); return response; }
		 * 
		 * ServiceResponse response=new ServiceResponse(false,"Not Found"); return
		 * response;
		 * 
		 * }
		 * 
		 * 
		 * public TmsJV get(long id){ if(jvRepository.existsById(id)) { Optional<TmsJV>
		 * jv= jvRepository.findById(id); return jv.get(); } return null; }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * public ServiceResponse getAdvances(AgencyRequestDto request) { List<Object[]>
		 * advances=
		 * jvRepository.getAdvances(request.agencyId,request.accountId,request.payeeId);
		 * List<AdvanceDto> activitiesItems = advances.stream().map( o -> new
		 * AdvanceDto( (BigDecimal) o[0], (BigDecimal) o[1], (BigDecimal) o[2],
		 * (BigDecimal) o[3], (String) o[4], (BigDecimal) o[5], (BigDecimal) o[6],
		 * (String) o[7], (BigDecimal) o[8], (String) o[9], (BigDecimal) o[10], (String)
		 * o[11], (BigDecimal) o[12], (String)o[13], (BigDecimal) o[14], (String) o[15],
		 * (String) o[16], (BigDecimal) o[17] )) .collect(Collectors.toList());
		 * 
		 * Map<String, Object> result = new HashMap<>(); result.put("advances",
		 * activitiesItems); ServiceResponse response=new ServiceResponse(true,result);
		 * return response;
		 * 
		 * }
		 * 
		 * public ServiceResponse getAdvancePayees(AgencyRequestDto request) {
		 * List<Object[]> advances=
		 * jvRepository.getAdvancePayee(request.fiscalYearId,request.agencyId,request.
		 * accountId); List<PayeeDto> activitiesItems = advances.stream().map( o -> new
		 * PayeeDto( (BigDecimal) o[0], (String) o[1], (String) o[2] ))
		 * .collect(Collectors.toList()); Map<String, Object> result = new HashMap<>();
		 * result.put("advancePayees", activitiesItems); ServiceResponse response=new
		 * ServiceResponse(true,result); return response; }
		 * 
		 * @Transactional public ServiceResponse deleteAll(JvRequestDto request){ try {
		 * boolean isSuccess=false; request.setCreatedBy(SecurityUtils.getUserName());
		 * 
		 * JvRequestStructMapper requestMapper = modelMapper.map(request,
		 * JvRequestStructMapper.class); List<JvIdDto>
		 * jvIdDto=Arrays.asList(modelMapper.map(request.getIds(),JvIdDto[].class));
		 * TypeResult type=VoidJournalVoucher(requestMapper,jvIdDto);
		 * if(type.getStatus()==1) isSuccess=true;
		 * 
		 * ServiceResponse response= new ServiceResponse(isSuccess, type.getMessage());
		 * return response;
		 * 
		 * }catch (Exception ex){ ServiceResponse response = new ServiceResponse(false,
		 * ex.getMessage()); return response; } }
		 * 
		 * 
		 * public ServiceResponse delete(long id){
		 * 
		 * // Session session = HibernateUtil.getSessionFactory().openSession(); //
		 * session.beginTransaction(); // session.getTransaction().commit();
		 * 
		 * boolean isSuccess=false; try { if(jvRepository.existsById(id)) { // TmsJV
		 * jv=get(id); // // JvDto request=new JvDto(); //
		 * request.setAgencyId(jv.getAgencyId()); // request.setAmount(0.00); //
		 * request.setCode(jv.getCode()); // request.setNdate(jv.getNdate()); //
		 * request.setFiscalYearId(jv.getFiscalYearId()); //
		 * request.setJvNo(jv.getJvNo()); // request.setCreatedBy(jv.getCreatedBy()); //
		 * request.setId(jv.getId()); //
		 * request.setVoucherTypeId(jv.getVoucherTypeId()); // // // // JvMapper jvDto =
		 * modelMapper.map(request, JvMapper.class); // TypeResult
		 * type=DeleteRecord(jvDto); // if(type.getStatus()==1) // isSuccess=true; // //
		 * ServiceResponse response= new ServiceResponse(isSuccess, type.getMessage());
		 * ServiceResponse response = new ServiceResponse(false,
		 * "Unable to delete record !"); return response; } }catch (DataAccessException
		 * ex){ ServiceResponse response = new ServiceResponse(false,
		 * "Unable to delete record !"); return response; } return null; }
		 */
		  
		  
		  private class AgencywithFiscalYearAccountSpecification implements
		  Specification<TmsJVView> { private AgencyRequestDto requestDto; public
		  AgencywithFiscalYearAccountSpecification(AgencyRequestDto
		  request,DataTablesInput input) { requestDto=request; }
		  
		  @Override public Predicate toPredicate(Root<TmsJVView> root, CriteriaQuery<?>
		  criteriaQuery, CriteriaBuilder criteriaBuilder) { Expression<Long> accountId
		  = root.get("accountId").as(Long.class); Expression<Long> agencyId =
		  root.get("agencyId").as(Long.class); Expression<Long> fiscalYearId =
		  root.get("fiscalYearId").as(Long.class);
		  
		  Predicate a=
		  criteriaBuilder.and(criteriaBuilder.equal(agencyId,requestDto.getAgencyId()))
		  ; Predicate
		  b=criteriaBuilder.and(criteriaBuilder.equal(fiscalYearId,requestDto.
		  getFiscalYearId())); Predicate
		  c=criteriaBuilder.and(criteriaBuilder.equal(accountId,requestDto.getAccountId
		  ())); return criteriaBuilder.and(a,b,c); } }
		  
		  
		  
		 
    
    @Data
    public class JVResponseData{
        private Long jvId;
        private String jvNo;
        private Integer agencyId;
        private Integer accountId;
        private Integer fiscalYearId;
    }
}