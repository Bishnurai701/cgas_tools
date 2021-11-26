package com.oagreport.datatablesrepository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.LedgerType;

import java.util.Optional;

@Repository
@Transactional
public  interface LedgerTypeRepository extends DataTablesRepository<LedgerType, Long>{
    Optional<LedgerType> findById(long id);
    Boolean existsById(long id);

    @Query(value = "select coalesce(max(a.id), '0') from LedgerType a")
    long getMax();

    @Query(value = "select a from LedgerType a where a.status=1")
    Iterable<LedgerType> findAllByStatus();
}