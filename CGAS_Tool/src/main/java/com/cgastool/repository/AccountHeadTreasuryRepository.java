package com.oagreport.repository;

import java.util.Optional;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.TmsAccountHead;




@Repository
@Transactional
public  interface AccountHeadTreasuryRepository extends DataTablesRepository<TmsAccountHead, Long>{
    Optional<TmsAccountHead> findById(long id);
    Boolean existsById(long id);

    @Query(value = "select coalesce(max(a.id), '0')+1 from TmsAccountHead a")
    long getMax();

    @Query(value = "select a from TmsAccountHead a join MiscAccountType t on a.accountTypeId=t.id order by t.sortOrder asc ")
    Iterable<TmsAccountHead> getAccountHead();

    @Query(value = "select a from TmsAccountHead a join MiscAccountType t on a.accountTypeId=t.id where a.accountTypeId=:groupId order by t.sortOrder asc ")
    Iterable<TmsAccountHead> getAccountHeadByGroup(@Param("groupId") Integer groupId);

}
