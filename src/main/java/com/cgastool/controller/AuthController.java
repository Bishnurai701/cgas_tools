package com.cgastool.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgastool.domain.EmailDto;
import com.cgastool.domain.EmailSender;
import com.cgastool.domain.JwtResponse;
import com.cgastool.domain.LoginRequest;
import com.cgastool.domain.Mail;
import com.cgastool.domain.ProcSuccessDto;
import com.cgastool.domain.ResetPasswordDto;
import com.cgastool.domain.ResetPasswordTokenDto;
import com.cgastool.domain.User;
import com.cgastool.repository.PasswordResetTokenRepository;
import com.cgastool.repository.RoleRepository;
import com.cgastool.repository.UserRepository;
import com.cgastool.securityconfig.impl.UserDetailsImpl;
import com.cgastool.securityconfig.jwt.JwtUtils;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private EmailSender emailSender;

	@Value("${reset_password_token_url}")
	private String resetPasswordTokenUrl;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),
						userDetails.getFullName(), userDetails.getOfficeId(), userDetails.getPhoneNo(), roles));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<ProcSuccessDto> forgotPassword(@RequestBody EmailDto emailDto) {

		User user = userRepository.findUserByEmail(emailDto.getEmail());
		if (user != null) {

			String resetPasswordToken = RandomString.make(30);
			String resetPasswordUrl = resetPasswordTokenUrl + resetPasswordToken;

			ResetPasswordTokenDto passwordTokenDto = new ResetPasswordTokenDto();
			passwordTokenDto.setResetPasswordToken(resetPasswordToken);
			passwordTokenDto.setExpiryDate(LocalDateTime.now());
			passwordTokenDto.setConsumed("N");
			passwordTokenDto.setEmail(user.getEmail());
			passwordTokenDto.setCreatedTimestamp(LocalDateTime.now());
			passwordTokenDto.setUser(user);
			passwordResetTokenRepository.save(passwordTokenDto);

			Mail mail = new Mail();
			mail.setMailTo(emailDto.getEmail());
			mail.setSubject("password Reset");
			Map<String, Object> model = new HashMap<>();
			model.put("reset_password_link", resetPasswordUrl);
			mail.setProps(model);
			try {
				emailSender.sendHtmlEmail(mail);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (MessagingException e) {
				System.out.println("Error");
			}
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/reset-password")
	public ResponseEntity<ProcSuccessDto> resetPassword(@RequestBody ResetPasswordDto passwordDto) {
		String token = passwordDto.getResetPasswordToken();
		ResetPasswordTokenDto resetPasswordTokenDto = (ResetPasswordTokenDto) passwordResetTokenRepository
				.findByResetPasswordToken(token);
		if (resetPasswordTokenDto == null) {
			return new ResponseEntity<>(new ProcSuccessDto(false), HttpStatus.OK);
		}

		if ((resetPasswordTokenDto.getConsumed()).equals("Y")) {
			return new ResponseEntity<>(new ProcSuccessDto(false), HttpStatus.OK);
		}
		User user = resetPasswordTokenDto.getUser();
		if (user == null) {
			return new ResponseEntity<>(new ProcSuccessDto(false), HttpStatus.OK);
		}
		user.setPassword(encoder.encode(passwordDto.getPassword()));
		userRepository.save(user);
		resetPasswordTokenDto.setConsumed("Y");
		passwordResetTokenRepository.save(resetPasswordTokenDto);
		return new ResponseEntity<>(new ProcSuccessDto(true), HttpStatus.OK);
	}

	@GetMapping("/verify-reset-password-token")
	public ResponseEntity<ProcSuccessDto> verifyResetPasswordToken(
			@RequestParam("reset_password_token") String resetPasswordToken) {
		ResetPasswordTokenDto resetPasswordTokenDto = (ResetPasswordTokenDto) passwordResetTokenRepository
				.findByResetPasswordToken(resetPasswordToken);
		if (resetPasswordTokenDto == null) {
			return new ResponseEntity<>(new ProcSuccessDto(false), HttpStatus.OK);
		}
		if (resetPasswordTokenDto != null && (resetPasswordTokenDto.getConsumed()).equals("N")) {
			LocalDateTime fromDateTime = resetPasswordTokenDto.getExpiryDate();
			LocalDateTime toDateTime = LocalDateTime.now();
			long hours = LocalDateTime.from(fromDateTime).until(toDateTime, ChronoUnit.HOURS);
			if (hours >= 24) {
				return new ResponseEntity<>(new ProcSuccessDto(false), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ProcSuccessDto(true), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ProcSuccessDto(false), HttpStatus.OK);
	}

}
