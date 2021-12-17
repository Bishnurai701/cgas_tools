package com.cgastool.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;


import com.cgastool.domain.Agency;
import com.cgastool.domain.AgencyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Repository
public interface AgencyRepo extends JpaRepository<Agency, Long> {
	
	Optional<Agency> existsByCode(String code);
	
	List<Agency> findAll();

	Optional<Agency> findById(long id);
	
	
	
	@Query(value = "SELECT ID FROM IFMIS_C_AGENCY WHERE CODE=:code",nativeQuery = true)
//	@Query(value = "SELECT Agency.ID,Agency.N_DESCRIPTION,Treasury.E_DESCRIPTION \r\n"
//					+ "FROM ifmis_c_agency Agency \r\n"
//					+ "INNER JOIN ifmis_c_treasuryoffice Treasury ON Agency.TREASURYOFFICE_ID = Treasury.ID \r\n"
//					+ "WHERE Agency.CODE =:code",nativeQuery = true)
	String getCodeRepo(@Param("code") String code);
	
	@Query(value = "SELECT Agency.ID, Agency.N_DESCRIPTION,Treasury.E_DESCRIPTION \r\n"
			+ "FROM ifmis_c_agency Agency \r\n"
			+ "INNER JOIN ifmis_c_treasuryoffice Treasury ON Agency.TREASURYOFFICE_ID = Treasury.ID \r\n"
			+ "WHERE Agency.CODE =:agencycode",nativeQuery = true)
	List<Map<String, Object>> getIdByCodeRepo(@Param("agencycode") String agencycode);
	
	
	@Query(value="SELECT ID  FROM IFMIS_C_AGENCY WHERE CODE=:ag_code",nativeQuery = true)
			List<String> getAgencyCodeRepo(@Param("ag_code") String ag_code);
	

	
	//Agency findOne(String code);
	
	//List<Agency> findByCode(String code);
	
	//Optional<Agency> findByCode(String code);
	
//	@Query(value ="SELECT AG FROM IFMIS_C_AGENCY AG WHERE code=?1")
//	Agency findAgencyByCode(String code);
	//List<Object[]
	
	
	//Agency findByCode(String code); 
	
	 
}
