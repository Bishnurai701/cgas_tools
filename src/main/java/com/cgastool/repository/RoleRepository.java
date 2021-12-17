package com.cgastool.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgastool.domain.ERole;
import com.cgastool.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Set<Role> findByName(ERole name);
}
