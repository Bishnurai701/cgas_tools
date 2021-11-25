package com.oagreport.datatablesrepository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.VoucherSubType;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public  interface VoucherSubTypeRepository extends DataTablesRepository<VoucherSubType, Long>{
   Optional<VoucherSubType> findById(long id);
    List<VoucherSubType> findAllByIdIn(List<Long> ids);
    Boolean existsById(long id);

    @Query(value = "select coalesce(max(a.id), '0')+1 from VoucherType a")
    long getMax();
}