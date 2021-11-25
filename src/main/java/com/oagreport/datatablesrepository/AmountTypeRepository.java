package com.oagreport.datatablesrepository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.AmountType;

import java.util.Optional;

@Repository
@Transactional
public  interface AmountTypeRepository extends DataTablesRepository<AmountType, Long>{
    Optional<AmountType> findById(long id);
    Boolean existsById(long id);

    @Query(value = "select coalesce(max(a.id), '0') from AmountType a")
    long getMax();
}