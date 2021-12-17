package com.oagreport.repository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.TreasuryOffice;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public  interface TreasuryOfficeRepository extends DataTablesRepository<TreasuryOffice, Long>{
    Optional<TreasuryOffice> findById(long id);

    Iterable<TreasuryOffice> findTreasuryOfficeById(long id);
    Boolean existsById(long id);

    @Query(value = " SELECT ID,CODE,E_DESCRIPTION,N_DESCRIPTION,TYPE,PROVINCE_ID FROM IFMIS_C_TREASURYOFFICE  WHERE STATUS=1", nativeQuery = true)
    List<Object[]> getList();


    @Query(value = "select coalesce(max(a.id), '0')+1 from TreasuryOffice a")
    long getMax();
}