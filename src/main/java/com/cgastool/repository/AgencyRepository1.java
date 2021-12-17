package com.cgastool.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cgastool.domain.AgencyDto;


@Repository
public interface AgencyRepository1 extends JpaRepository<AgencyDto, Long> {
	
	
	@Query(value = "SELECT ID  FROM IFMIS_C_AGENCY WHERE CODE=:ag_code",nativeQuery = true)
	List<Map<String, Object>> getAgencyCodeRepo(@Param("ag_code") String ag_code);

}
