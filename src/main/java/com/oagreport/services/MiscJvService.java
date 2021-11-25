package com.oagreport.services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oagreport.datatablesrepository.AccountHeadMiscRepository;
import com.oagreport.datatablesrepository.MiscAccountRepository;
import com.oagreport.datatablesrepository.MiscJvViewRepository;
import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.MiscAccount;
import com.oagreport.domain.MiscJvView;
import com.oagreport.domain.ServiceResponse;


import lombok.Data;
import oracle.jdbc.OracleTypes;
import org.modelmapper.ModelMapper;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MiscJvService {

    final PaymentTypeService paymentTypeService;
    final VoucherTypeService voucherTypeService;
    final VoucherSubTypeService voucherSubTypeService;
    final AccountHeadMiscRepository accountHeadMiscRepository;
    final MiscJvViewRepository miscJvViewRepository;
    final AmountTypeService amountTypeService;
    final AdvanceTypeService advanceTypeService;

    final AuthorizationService authorizationService;
    private final DataSource dataSource;
    private JdbcTemplate jdbcPaymentOrderTemplate;
    private SimpleJdbcCall generateBillVoucher;
    private SimpleJdbcCall generatePaymentOrder;
    private SimpleJdbcCall voidJournalVoucher;
    private SimpleJdbcCall generateSalaryVoucher;
    private SimpleJdbcCall generateNikasaVoucher;
    private SimpleJdbcCall generateEFTRefundJV;
    private SimpleJdbcCall generateTSANikasaVoucher;
    private SimpleJdbcCall generatePreviousPeski;
    private SimpleJdbcCall generateIncomeVoucher;
    final ModelMapper modelMapper;


    @PersistenceContext
    private EntityManager em;

    final MiscAccountRepository miscAccountRepository;



    public MiscJvService( PaymentTypeService paymentTypeService, DataSource dataSource, VoucherTypeService voucherTypeService, VoucherSubTypeService voucherSubTypeService,  AccountHeadMiscRepository accountHeadMiscRepository, ModelMapper modelMapper, AmountTypeService amountTypeService, AdvanceTypeService advanceTypeService, AuthorizationService authorizationService, MiscAccountRepository miscAccountRepository, MiscJvViewRepository miscJvViewRepository) {

        this.paymentTypeService = paymentTypeService;
		this.miscJvViewRepository = miscJvViewRepository;

        this.dataSource = dataSource;
        this.voucherTypeService = voucherTypeService;
        this.voucherSubTypeService = voucherSubTypeService;
        this.accountHeadMiscRepository = accountHeadMiscRepository;

        this.modelMapper = modelMapper;
        this.amountTypeService = amountTypeService;

        this.advanceTypeService = advanceTypeService;

        this.authorizationService = authorizationService;

        this.miscAccountRepository = miscAccountRepository;
    }




    public ServiceResponse getAccount(AgencyRequestDto request) {

        Object accountHeads=accountHeadMiscRepository.findAll();
        Object paymentTypes=paymentTypeService.FindAll();
        Object voucherTypes=voucherTypeService.FindAll();
        Object advanceTypes=advanceTypeService.FindAll();
        Object voucherSubTypes=voucherSubTypeService.FindAll();
        Iterable<MiscAccount> accounts=miscAccountRepository.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("accountHeads", accountHeads);
        result.put("paymentTypes", paymentTypes);
        result.put("voucherTypes", voucherTypes);
        result.put("voucherSubTypes", voucherSubTypes);
        result.put("advanceTypes",advanceTypes);
        result.put("accounts",accounts);

        ServiceResponse response=new ServiceResponse(true,result);
        return  response;
    }

	/*
	 * public ServiceResponse getActivities(AgencyRequestDto request) {
	 * 
	 * Iterable<MiscEconomic> economics=miscEconomicRepository.findAll();
	 * Iterable<MiscProgram> activities =
	 * miscProgramRepository.findByFiscalYearIdAndAgencyId(request.getFiscalYearId()
	 * ,request.agencyId); Map<String, Object> result = new HashMap<>();
	 * result.put("activities", activities); result.put("economics",economics);
	 * 
	 * ServiceResponse response = new ServiceResponse(true, result); return
	 * response; }
	 */

    public DataTablesOutput<MiscJvView>FindAll(AgencyRequestDto request, DataTablesInput input){
        DataTablesOutput<MiscJvView> data = miscJvViewRepository.findAll(input, new AgencywithFiscalYearAccountSpecification(request,input));
        return  data;
    }


    public Iterable<MiscJvView> FindAll(){
        Iterable<MiscJvView> data = miscJvViewRepository.findAll();
        return  data;
    }
    
    
    
    
    private class AgencywithFiscalYearAccountSpecification implements Specification<MiscJvView> {
        private AgencyRequestDto requestDto;
        public AgencywithFiscalYearAccountSpecification(AgencyRequestDto request,DataTablesInput input) {
            requestDto=request;
        }

        @Override
        public Predicate toPredicate(Root<MiscJvView> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            Expression<Long> accountId = root.get("accountId").as(Long.class);
            Expression<Long> agencyId = root.get("agencyId").as(Long.class);
            Expression<Long> fiscalYearId = root.get("fiscalYearId").as(Long.class);

            Predicate a= criteriaBuilder.and(criteriaBuilder.equal(agencyId,requestDto.getAgencyId()));
            Predicate b=criteriaBuilder.and(criteriaBuilder.equal(fiscalYearId,requestDto.getFiscalYearId()));
            Predicate c=criteriaBuilder.and(criteriaBuilder.equal(accountId,requestDto.getAccountId()));
            return criteriaBuilder.and(a,b,c);
        }
    }

    
    
    

	/*
	 * @Transactional public ServiceResponse Update(MiscJV request) { MiscJV
	 * toUpdate = jvRepository.findById(request.getId()).get(); if
	 * (toUpdate.getStatus() == 1) { // request.setVoucherTypeId(3);//Adjustment
	 * Voucher // if(request.getVoucherSubTypeId() !=33) // { //
	 * request.setVoucherNo(""); // } // // Integer nCount=0; //
	 * if(request.getPoGenVoucher()==1) // { // for ( TmsJvDetailInfo detail:
	 * request.getJvDetails()) { // if (detail.getHeadId() == 9) { // nCount = 1; //
	 * } // } // if(nCount==0) // { // ServiceResponse response = new
	 * ServiceResponse(false, "भुक्तानी आदेश बनाउने Dr/Cr Budget छैन"); // return
	 * response; // } // } // // TmsJvInfo info=AddPerform(request); try {
	 * //TmsJvInfo info1 = AddPerform(request); GsonBuilder builder = new
	 * GsonBuilder().serializeNulls(); Gson gson = builder.create(); String
	 * strRequest = gson.toJson(request); String nuserId =
	 * SecurityUtils.getUserName();
	 * 
	 * StoredProcedureQuery query = em.createStoredProcedureQuery("MISC_JV.EditJV")
	 * .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
	 * .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
	 * .registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
	 * .registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
	 * .setParameter(1, strRequest) .setParameter(2, nuserId); query.execute();
	 * 
	 * Integer nStatus = (Integer) query.getOutputParameterValue(3); String nMessage
	 * = (String) query.getOutputParameterValue(4); if (nStatus == 0) return new
	 * ServiceResponse(true, nMessage); else return new ServiceResponse(false,
	 * nMessage); } catch (Exception ex) { ServiceResponse response = new
	 * ServiceResponse(false, "Error during process. Contact to Support."); return
	 * response; } } return null; } //
	 *///    private TmsJvInfo AddPerform(TmsJvInfo request) {
//        TmsJvInfo info=new TmsJvInfo();
//
//        info.setId(request.getId());
//        info.setFiscalYearId(request.getFiscalYearId());
//        info.setAgencyId(request.getAgencyId());
//        info.setAccountId(request.getAccountId());
//        info.setVoucherTypeId(request.getVoucherTypeId());
//        info.setVoucherSubTypeId(request.getVoucherSubTypeId());
//        info.setVoucherNo(request.getVoucherNo());
//        info.setPoGenVoucher(request.getPoGenVoucher());
//        info.setEdate(request.getEdate());
//        info.setNdate(request.getNdate());
//        info.setRemarks(request.getRemarks());
//        info.setStatus(1);
//        info.setCreatedBy(SecurityUtils.getUserName());
//        info.setCreatedOn(LocalDateTime.now());
//        //     info.setCode(jvRepository.getCode(request.getAgencyId(),request.getAccountId()));
////        info.setJvNo(info.getCode());
//        info.setRemarks(request.getRemarks());
//        info.setNarration(request.getNarration());
//        info.setTotalcramount(request.getTotalcramount());
//        Long i = 0L;
//
//        List<TmsJvDetailInfo> _details=new ArrayList<>();
//
//        for ( TmsJvDetailInfo detail: request.getJvDetails()) {
//            i=i+1;
//            TmsJvDetailInfo _detail=new TmsJvDetailInfo();
//            if(detail.getHeadId()==31){
//                detail.setActivityId(null);
//                detail.setComponentId(null);
//                detail.setDonorGrpId(null);
//                detail.setDonorId(null);
//                detail.setEconomicId(null);
//                detail.setSourceTypeId(null);
//            }
//            _detail.setSno(i);
//            _detail.setTxnType(detail.getTxnType());
//            _detail.setHeadId(detail.getHeadId());
//            _detail.setEconomicId(detail.getEconomicId());
//            _detail.setComponentId(detail.getComponentId());
//            _detail.setActivityId(detail.getActivityId());
//            _detail.setDonorGrpId(detail.getDonorGrpId());
//            _detail.setDonorId(detail.getDonorId());
//            _detail.setSourceTypeId(detail.getSourceTypeId());
//            _detail.setDrAmount(detail.getDrAmount());
//            _detail.setCrAmount(detail.getCrAmount());
//            _detail.setStatus(info.getStatus());
//            _detail.setCreatedBy(info.getCreatedBy());
//            _detail.setCreatedOn(info.getCreatedOn());
//
//            _detail.setVoucherNo(info.getVoucherNo());
//            _details.add(_detail);
//
//        }
//        info.setJvDetails(_details);
//        return  info;
//    }
//
//    private TmsJvInfo AdjustPerform(TmsJvInfo request) {
//        TmsJvInfo info=new TmsJvInfo();
//        info.setId(request.getId());
//        info.setRemarks(request.getRemarks());
//        info.setStatus(request.getStatus());
//        info.setNarration(request.getNarration());
//        Long i = 0L;
//
//        List<TmsJvDetailInfo> _details=new ArrayList<>();
//
//        for ( TmsJvDetailInfo detail: request.getJvDetails()) {
//            i=i+1;
//            TmsJvDetailInfo _detail=new TmsJvDetailInfo();
//            if(detail.getHeadId()==31){
//                detail.setActivityId(null);
//                detail.setComponentId(null);
//                detail.setDonorGrpId(null);
//                detail.setDonorId(null);
//                detail.setEconomicId(null);
//                detail.setSourceTypeId(null);
//            }
//            _detail.setId(detail.getId());
//            _detail.setSno(detail.getSno());
//            _detail.setTxnType(detail.getTxnType());
//            _detail.setHeadId(detail.getHeadId());
//            _detail.setEconomicId(detail.getEconomicId());
//            _detail.setComponentId(detail.getComponentId());
//            _detail.setActivityId(detail.getActivityId());
//            _detail.setDonorGrpId(detail.getDonorGrpId());
//            _detail.setDonorId(detail.getDonorId());
//            _detail.setSourceTypeId(detail.getSourceTypeId());
//            _detail.setDrAmount(detail.getDrAmount());
//            _detail.setCrAmount(detail.getCrAmount());
//            _details.add(_detail);
//
//        }
//        info.setJvDetails(_details);
//        return  info;
//    }
	/*
	 * @Transactional public ServiceResponse Create(MiscJV request){ //
	 * if(SecurityUtils.hasRole(RoleNames.FCGO_ADMIN)) { try { //TmsJvInfo info =
	 * AdjustPerform(request); GsonBuilder builder=new
	 * GsonBuilder().serializeNulls(); Gson gson = builder.create(); String
	 * strRequest = gson.toJson(request); String nuserId =
	 * SecurityUtils.getUserName();
	 * 
	 * StoredProcedureQuery query =
	 * em.createStoredProcedureQuery("MISC_JV.CreateJv")
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
	 * response; } // }else{ // ServiceResponse response = new
	 * ServiceResponse(false, "No Permission to adjust Voucher."); // return
	 * response; // } }
	 * 
	 * // @Transactional // public ServiceResponse Adjustment(TmsJvInfo request){ //
	 * if(SecurityUtils.hasRole(RoleNames.FCGO_ADMIN)) { // // // try { // TmsJvInfo
	 * info = AdjustPerform(request); // GsonBuilder builder=new
	 * GsonBuilder().serializeNulls(); // Gson gson = builder.create(); // String
	 * strRequest = gson.toJson(info); // String nuserId =
	 * SecurityUtils.getUserName(); // // StoredProcedureQuery query =
	 * em.createStoredProcedureQuery("Adjustment.JVAdjustment") //
	 * .registerStoredProcedureParameter(1, String.class, ParameterMode.IN) //
	 * .registerStoredProcedureParameter(2, String.class, ParameterMode.IN) //
	 * .registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT) //
	 * .registerStoredProcedureParameter(4, String.class, ParameterMode.OUT) //
	 * .setParameter(1, strRequest) // .setParameter(2, nuserId); // //
	 * query.execute(); // Integer nStatus = (Integer)
	 * query.getOutputParameterValue(3); // String nMessage = (String)
	 * query.getOutputParameterValue(4); // // if (nStatus == 0) // return new
	 * ServiceResponse(true, nMessage); // else // return new ServiceResponse(false,
	 * nMessage); // // } catch (Exception ex) { // ServiceResponse response = new
	 * ServiceResponse(false, "Error during process. Contact to Support."); //
	 * return response; // } // }else{ // ServiceResponse response = new
	 * ServiceResponse(false, "No Permission to adjust Voucher."); // return
	 * response; // } // // }
	 * 
	 * @Transactional public ServiceResponse GenerateJvByBill(JvRequestDto request){
	 * try { boolean isSuccess=false;
	 * 
	 * request.setCreatedBy(SecurityUtils.getUserName()); JvRequestStructMapper
	 * requestMapper = modelMapper.map(request, JvRequestStructMapper.class);
	 * List<JvIdDto>
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
	 * @Transactional public ServiceResponse GenerateJvByIncomeVoucher(JvRequestDto
	 * request){ try { boolean isSuccess=false;
	 * 
	 * request.setCreatedBy(SecurityUtils.getUserName()); JvRequestStructMapper
	 * requestMapper = modelMapper.map(request, JvRequestStructMapper.class);
	 * List<JvIdDto>
	 * jvIdDto=Arrays.asList(modelMapper.map(request.getIds(),JvIdDto[].class));
	 * TypeResult type=GenerateIncomeJV(requestMapper,jvIdDto);
	 * if(type.getStatus()==1) isSuccess=true;
	 * 
	 * ServiceResponse response= new ServiceResponse(isSuccess, type.getMessage());
	 * return response;
	 * 
	 * }catch (Exception ex){ ServiceResponse response = new ServiceResponse(false,
	 * "Unable to generate JV Voucher."); return response; } }
	 * 
	 * 
	 * 
	 * @Transactional public ServiceResponse VerifyNoPoJV(JvRequestDto request){
	 * if(SecurityUtils.hasRole(RoleNames.AGENCY_ADMIN)) {
	 * 
	 * try { // boolean isSuccess = false; //
	 * request.setCreatedBy(SecurityUtils.getUserName()); // List<JvIdDto>
	 * _Id=request.getIds(); // // TmsJV jv=
	 * jvRepository.findById(_Id.get(0).getId()).get(); // jv.setStatus(2); //
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
	 */
	/*
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
	 * MiscJV jv= jvRepository.findById(id).get(); // List<TmsJVDetail>
	 * details=jv.getJvDetails(); // // jv.setJvDetails(null); // // Gson gson = new
	 * Gson(); // String jsonString = gson.toJson(jv); // TmsJvRes response =
	 * gson.fromJson(jsonString, TmsJvRes.class); // // // // //
	 * List<TmsJvDetailRes> listDetails = new ArrayList<TmsJvDetailRes>(); // //
	 * details.forEach(i -> { // TmsJvDetailRes res = new TmsJvDetailRes(); //
	 * res.setActivityId(i.getActivityId()); //
	 * res.setComponentId(i.getComponentId()); // res.setCrAmount(i.getCrAmount());
	 * // res.setDonorGrpId(i.getDonorGrpId()); // res.setDonorId(i.getDonorId());
	 * // res.setDrAmount(i.getDrAmount()); // res.setActivityId(i.getActivityId());
	 * // res.setEconomicId(i.getEconomicId()); //
	 * res.setSourceTypeId(i.getSourceTypeId()); // res.setSno(i.getSno()); //
	 * res.setHeadId(i.getHeadId()); // res.setTxnType(i.getTxnType()); //
	 * res.setId(i.getId()); // listDetails.add(res); // }); // // //
	 * response.setJvDetails(listDetails);
	 * 
	 * Map<String, Object> result = new HashMap<>(); result.put("jv", jv);
	 * ServiceResponse responseResult=new ServiceResponse(true,result); return
	 * responseResult;
	 * 
	 * }
	 * 
	 * ServiceResponse response=new ServiceResponse(false,"Not Found"); return
	 * response; }
	 * 
	 * public ServiceResponse print(long id){
	 * if(miscJvViewRepository.existsById(id)) { Optional<MiscJvView> jv=
	 * miscJvViewRepository.findById(id); Iterable<MiscJVDetailView> details=
	 * miscJvDetailViewRepository.getJvDetails(id); Iterable<MiscJVPayeeView>
	 * payees= miscJvPayeeViewRepository.getPayees(id); List<Object[]>
	 * advanceObjects= miscJvViewRepository.getJvDetailByAdvances(id);
	 * List<TmsJvAdvanceViewResponse> advanceList = advanceObjects.stream().map( o
	 * -> new TmsJvAdvanceViewResponse( (BigDecimal) o[0], (BigDecimal) o[1],
	 * (String) o[2], (String) o[3], (String) o[4], (String) o[5], (BigDecimal) o[6]
	 * )) .collect(Collectors.toList()); Map<String, Object> result = new
	 * HashMap<>(); result.put("jv", jv); result.put("details", details);
	 * result.put("payees", payees); result.put("advances", advanceList);
	 * 
	 * ServiceResponse response=new ServiceResponse(true,result); return response; }
	 * 
	 * ServiceResponse response=new ServiceResponse(false,"Not Found"); return
	 * response;
	 * 
	 * }
	 * 
	 * 
	 * // public TmsJV get(long id){ // if(jvRepository.existsById(id)) { //
	 * Optional<TmsJV> jv= jvRepository.findById(id); // return jv.get(); // } //
	 * return null; // }
	 * 
	 * 
	 * 
	 * 
	 * // public ServiceResponse getAdvances(AgencyRequestDto request) { //
	 * List<Object[]> advances=
	 * jvRepository.getAdvances(request.agencyId,request.accountId,request.payeeId);
	 * // List<AdvanceDto> activitiesItems = advances.stream().map( // o -> new
	 * AdvanceDto( // (BigDecimal) o[0], // (BigDecimal) o[1], // (BigDecimal) o[2],
	 * // (BigDecimal) o[3], // (String) o[4], // (BigDecimal) o[5], // (BigDecimal)
	 * o[6], // (String) o[7], // (BigDecimal) o[8], // (String) o[9], //
	 * (BigDecimal) o[10], // (String) o[11], // (BigDecimal) o[12], //
	 * (String)o[13], // (BigDecimal) o[14], // (String) o[15], // (String) o[16],
	 * // (BigDecimal) o[17] // )) // .collect(Collectors.toList()); // //
	 * Map<String, Object> result = new HashMap<>(); // result.put("advances",
	 * activitiesItems); // ServiceResponse response=new
	 * ServiceResponse(true,result); // return response; // // } // // public
	 * ServiceResponse getAdvancePayees(AgencyRequestDto request) { //
	 * List<Object[]> advances=
	 * jvRepository.getAdvancePayee(request.fiscalYearId,request.agencyId,request.
	 * accountId); // List<PayeeDto> activitiesItems = advances.stream().map( // o
	 * -> new PayeeDto( // (BigDecimal) o[0], // (String) o[1], // (String) o[2] //
	 * )) // .collect(Collectors.toList()); // Map<String, Object> result = new
	 * HashMap<>(); // result.put("advancePayees", activitiesItems); //
	 * ServiceResponse response=new ServiceResponse(true,result); // return
	 * response; // }
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
	 * boolean isSuccess=false; try { if(jvRepository.existsById(id)) {
	 * ServiceResponse response = new ServiceResponse(false,
	 * "Unable to delete record !"); return response; } }catch (DataAccessException
	 * ex){ ServiceResponse response = new ServiceResponse(false,
	 * "Unable to delete record !"); return response; } return null; }
	 * 
	 * 
	 * private class AgencywithFiscalYearAccountSpecification implements
	 * Specification<MiscJvView> { private AgencyRequestDto requestDto; public
	 * AgencywithFiscalYearAccountSpecification(AgencyRequestDto
	 * request,DataTablesInput input) { requestDto=request; }
	 * 
	 * @Override public Predicate toPredicate(Root<MiscJvView> root,
	 * CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	 * Expression<Long> accountId = root.get("accountId").as(Long.class);
	 * Expression<Long> agencyId = root.get("agencyId").as(Long.class);
	 * Expression<Long> fiscalYearId = root.get("fiscalYearId").as(Long.class);
	 * 
	 * Predicate a=
	 * criteriaBuilder.and(criteriaBuilder.equal(agencyId,requestDto.getAgencyId()))
	 * ; Predicate
	 * b=criteriaBuilder.and(criteriaBuilder.equal(fiscalYearId,requestDto.
	 * getFiscalYearId())); Predicate
	 * c=criteriaBuilder.and(criteriaBuilder.equal(accountId,requestDto.getAccountId
	 * ())); return criteriaBuilder.and(a,b,c); } }
	 */



    @Data
    public class JVResponseData{
        private Long jvId;
        private String jvNo;
        private Integer agencyId;
        private Integer accountId;
        private Integer fiscalYearId;
    }
}

