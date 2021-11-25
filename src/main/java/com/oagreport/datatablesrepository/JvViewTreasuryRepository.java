package com.oagreport.datatablesrepository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.JvVoucherDto;
import com.oagreport.domain.TmsJVView;

import java.util.List;

@Repository
@Transactional
public  interface JvViewTreasuryRepository extends DataTablesRepository<TmsJVView, Long>{
    Boolean existsById(long id);

    @Query(value =
            " select a.Id as Id ,a.Jv_No as JvNo,b.E_DESCRIPTION VoucherEType,b.N_DESCRIPTION AS VoucherNType, a.EDate as EDate, a.NDate as NDate,a.Agency_ID as AgencyId," +
                    "a.ACCOUNT_ID as AccountId,a.AMOUNT as Amount,a.NARRATION as Narration,a.STATUS as Status," +
                    "a.CREATED_BY as CreatedBy, a.CREATED_ON as CreatedOn " +
                    " FROM AC_JV a, AC_C_VOUCHER_TYPE c" +
                    " WHERE a.VOUCHER_TYPE_ID=c.Id AND FISCALYEAR_ID=:fiscalYearId AND AGENCY_ID=:agencyId" +
                    " AND ACCOUNT_ID=:accountId ORDER BY CREATED_ON DESC", nativeQuery = true)
    List<JvVoucherDto> getVouchers( @Param("fiscalYearId")  Long fiscalYearId,
                                   @Param("agencyId") Long agencyId,
                                   @Param("accountId") Long accountId);



    @Query(value =
            " select a.Jv_No as JvNo,a.Remarks" +
                    " FROM AC_TMS_JV a" +
                    " WHERE a.VOUCHER_TYPE_ID=1 and  FISCALYEAR_ID=:fiscalYearId AND AGENCY_ID=:agencyId" +
                    " AND ACCOUNT_ID=:accountId ORDER BY CREATED_ON DESC", nativeQuery = true)
    List<Object[]>  getDeductionVouchers( @Param("fiscalYearId")  Long fiscalYearId,
                                    @Param("agencyId") Long agencyId,
                                    @Param("accountId") Long accountId);


    @Query(value =
            " select a.Jv_No as JvNo,a.Remarks" +
                    " FROM AC_TMS_JV a" +
                    " WHERE a.VOUCHER_TYPE_ID=4 and FISCALYEAR_ID=:fiscalYearId AND AGENCY_ID=:agencyId" +
                    " AND ACCOUNT_ID=:accountId ORDER BY CREATED_ON DESC", nativeQuery = true)
    List<Object[]>  getSalaryVouchers( @Param("fiscalYearId")  Long fiscalYearId,
                                          @Param("agencyId") Long agencyId,
                                          @Param("accountId") Long accountId);




}