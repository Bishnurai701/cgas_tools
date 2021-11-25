package com.oagreport.datatablesrepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.MiscJvView;



@Repository
@Transactional
public  interface MiscJvViewRepository extends DataTablesRepository<MiscJvView, Long>{
    Optional<MiscJvView> findById(long id);
    Boolean existsById(long id);

    @Query(value = "select coalesce(max(a.id), '0') from MiscJvView a")
    long getMax();
    @Query(value =
            " select payee_id,eeconomicName, neconomicname,eactivityname,nactivityname, edonorname,ndonorname,esourcetype,nsourcetype,advance_due_edate,advance_due_ndate,advance_amount " +
                    " from vw_advances" +
                    " where AGENCY_ID=:agencyId AND FISCALYEAR_ID=:fiscalYearId AND ACCOUNT_ID=:accountId " +
                    " AND ECONOMIC_ID=:economicId AND PAYEE_ID=:payeeId", nativeQuery = true)
    List<Object[]> getAdvances(@Param("fiscalYearId") Integer fiscalYearId,
                               @Param("agencyId") Long agencyId, @Param("accountId") Long accountId,
                               @Param("economicId") Long economicId, @Param("payeeId") Long payeeId);

    @Query(value =
            "  SELECT JV_ID,PAYEE_ID,CODE,pan, PAYEE_NAME,PAYEE_NNAME,SUM(ADVANCE_AMOUNT) as Amount FROM AC_MISC_JV_ADVANCE\n" +
                    " where JV_ID=:jvId" +
                    " GROUP BY JV_ID,PAYEE_ID,CODE,pan,PAYEE_NAME,PAYEE_NNAME", nativeQuery = true)
    List<Object[]>  getJvDetailByAdvances(@Param("jvId") Long jvId);


}