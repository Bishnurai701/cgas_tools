package com.cgastool.datatablesrepository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cgastool.domain.Agency;
import com.cgastool.domain.Department;
import com.cgastool.domain.Ministry;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public  interface AgencyRepository extends DataTablesRepository<Agency, Long>{
    Optional<Agency> findById(long id);
    Iterable<Agency> findAgenciesById(long id);
    Iterable<Agency> findAgenciesByMinistry(Ministry minitry);
    Iterable<Agency> findAgenciesByDepartment(Department department);
    List<Agency> findAgenciesByTreasuryOfficeId(long id);
    Boolean existsById(long id);
    List<Agency> findByCode(String code);
    
    //Optional<Agency> findByCode(String code);
    
//    @Query(value = "SELECT ID FROM IFMIS_C_AGENCY  WHERE CODE=:code",nativeQuery = true)
//    List<Object[]> findByCode(@Param("code") String code);

    @Query(value = "select coalesce(max(a.id), '0')+1 from Agency a")
    long getMax();

    @Query(value = " SELECT ID,CODE,E_DESCRIPTION,N_DESCRIPTION,E_ADDRESS,N_ADDRESS,TYPE,PROVINCE_ID,DISTRICT_ID FROM IFMIS_C_AGENCY  WHERE TREASURYOFFICE_ID=:treasuryOfficeId AND FISCALYEAR_ID=:fiscalYearId", nativeQuery = true)
    List<Object[]> getAgencyList(@Param("fiscalYearId")  Integer fiscalYearId, @Param("treasuryOfficeId") Long treasuryOfficeId);

}