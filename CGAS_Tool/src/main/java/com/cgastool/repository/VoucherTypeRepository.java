package com.oagreport.repository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.VoucherType;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public  interface VoucherTypeRepository extends DataTablesRepository<VoucherType, Long>{
   Optional<VoucherType> findById(long id);
    List<VoucherType> findAllByIdIn(List<Long> ids);
    Boolean existsById(long id);

    @Query(value = "select coalesce(max(a.id), '0')+1 from VoucherType a")
    long getMax();

//    @Query(value =
//            " select payee_id,eeconomicName, neconomicname,eactivityname,nactivityname, edonorname,ndonorname,esourcetype,nsourcetype,advance_due_edate,advance_due_ndate,advance_amount " +
//                    " from vw_advances" +
//                    " where AGENCY_ID=:agencyId AND FISCALYEAR_ID=:fiscalYearId AND ACCOUNT_ID=:accountId " +
//                    " AND ECONOMIC_ID=:economicId AND PAYEE_ID=:payeeId", nativeQuery = true)
//    List<Object[]> getDepositors(@Param("agencyId") Long agencyId, @Param("accountId") Long accountId
//                              );

}
