package com.cgastool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgastool.domain.ResetPasswordTokenDto;

public interface PasswordResetTokenRepository extends JpaRepository<ResetPasswordTokenDto,Long> {

	Object findByResetPasswordToken(String resetPasswordToken);

	

}
