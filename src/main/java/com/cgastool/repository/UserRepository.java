package com.cgastool.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgastool.domain.ResetPasswordTokenDto;
import com.cgastool.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserName(String userName);

	Boolean existsByUserName(String userName);

	Boolean existsByEmail(String email);

	List<User> findByUserOf(String userName);

	User findUserByEmail(String email);

	



	
}
