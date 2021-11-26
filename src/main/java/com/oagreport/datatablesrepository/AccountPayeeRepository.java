package com.oagreport.datatablesrepository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.AccountPayee;

import java.util.Optional;

@Repository
@Transactional
public  interface AccountPayeeRepository extends DataTablesRepository<AccountPayee, Long>{
    Optional<AccountPayee> findById(long id);
    Boolean existsById(long id);

    @Query(value = "select a from AccountPayee a where a.agencyId=?1 and a.isFtAccount=1 and a.typeId<>5 and a.status=1 order by a.id")
    Iterable<AccountPayee> finalAllPayeeEFTPayee(Long agencyId);

    @Query(value = "select a from AccountPayee a where a.agencyId=?1 and a.isFtAccount=1 and  a.isDepositPayee=1 and a.typeId<>5 and a.status=1 order by a.id")
    Iterable<AccountPayee> finalAllDepositPayeeEFTPayee(Long agencyId);


    @Query(value = "select a from AccountPayee a where a.agencyId=?1 and a.status=1 order by a.id")
    Iterable<AccountPayee> finalAllPayee(Long agencyId);

    @Query(value = "select a from AccountPayee a where a.agencyId=?1 and  a.status=1  and a.typeId <>5 order by a.typeId")
    Iterable<AccountPayee> finalAllPayeeACPayee(Long agencyId);


    @Query(value = "select a from AccountPayee a where a.agencyId=?1 and  a.status=1  and a.isDepositPayee=1 and a.typeId <>5 order by a.typeId")
    Iterable<AccountPayee> finalAllDepositPayeeACPayee(Long agencyId);


    @Query(value = "select a from AccountPayee a where a.agencyId=?1 and a.isDeductionPayee=1 and  a.isFtAccount=1  and (a.typeId <>2  or a.typeId<>5 )and a.status=1 order by a.id")
    Iterable<AccountPayee> findAllDeductinEPFPayee(Long agencyId);

    @Query(value = "select a from AccountPayee a where a.agencyId=?1 and   a.isDeductionPayee=1 and a.status=1 order by a.id")
    Iterable<AccountPayee> findAllDeductinACPayee(Long agencyId);


    @Query(value = "select count(*) from AccountPayee a where a.agencyId=?1 and a.pan=?2 and a.ndescription=?3 and a.status=1")
    long findByPan(Long agencyId,String pan,String nname);


    @Query(value = "select coalesce(max(a.id), '0') from AccountPayee a")
    long getMax();

    @Query(value = "SELECT Payee.GetPayeeCode(:agencyId) FROM DUAL", nativeQuery = true)
    String getPayeeCode(@Param("agencyId") Long agencyId);
}