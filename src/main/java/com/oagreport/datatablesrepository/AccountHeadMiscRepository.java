package com.oagreport.datatablesrepository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.MiscAccountHead;

import java.util.Optional;

@Repository
@Transactional
public interface AccountHeadMiscRepository extends DataTablesRepository<MiscAccountHead, Long> {
	Optional<MiscAccountHead> findById(long id);

	Boolean existsById(long id);

	@Query(value = "select coalesce(max(a.id), '0')+1 from MiscAccountHead a")
	long getMax();

	
	  @Query(value =
	  "select a from MiscAccountHead a join MiscAccountType t on a.accountTypeId=t.id order by t.sortOrder asc "
	  ) Iterable<MiscAccountHead> getAccountHead();
	  
	 
	/*
	 * @Query(value =
	 * "select a from MiscAccountHead a join MiscAccountType t on a.accountTypeId=t.id where a.accountTypeId=:groupId order by t.sortOrder asc "
	 * ) Iterable<MiscAccountHead> getAccountHeadByGroup(@Param("groupId") Integer
	 * groupId);
	 */

}
