package com.oagreport.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oagreport.domain.ERole;
import com.oagreport.domain.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Set<Role> findByName(ERole name);
}
