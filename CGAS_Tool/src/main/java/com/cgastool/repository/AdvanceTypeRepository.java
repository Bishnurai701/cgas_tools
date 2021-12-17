package com.oagreport.repository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.AdvanceType;

import java.util.Optional;

@Repository
@Transactional
public  interface AdvanceTypeRepository extends DataTablesRepository<AdvanceType, Long>{
    Optional<AdvanceType> findById(long id);
    Boolean existsById(long id);

    @Query(value = "select coalesce(max(a.id), '0')+1 from AdvanceType a")
    long getMax();
}
