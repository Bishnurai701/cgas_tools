package com.oagreport.repository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.PaymentType;

import java.util.Optional;

@Repository
@Transactional
public  interface PaymentTypeRepository extends DataTablesRepository<PaymentType, Long>{
    Optional<PaymentType> findById(long id);
    Boolean existsById(long id);


    @Query(value = "select a from PaymentType a where a.status=?1")
    Iterable<PaymentType> findAllByStatus(int status);


    @Query(value = "select coalesce(max(a.id), '0')+1 from PaymentType a")
    long getMax();
}