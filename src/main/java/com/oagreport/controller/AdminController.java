package com.oagreport.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oagreport.domain.ERole;
import com.oagreport.domain.MessageResponse;
import com.oagreport.domain.Password;
import com.oagreport.domain.Role;
import com.oagreport.domain.SignupRequest;
import com.oagreport.domain.User;
import com.oagreport.repository.RoleRepository;
import com.oagreport.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired
	PasswordEncoder encoder;
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, Principal principal) {
		String username= principal.getName();
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		/*
		 * // Create new user's account User user = new
		 * User(signUpRequest.getUsername(), signUpRequest.getEmail(),
		 * signUpRequest.getPhoneNo(), encoder.encode(signUpRequest.getPassword()));
		 */

		User user =new User();
		Set<Role> roles = new HashSet<>();
		user.setUsername(signUpRequest.getUsername());
		user.setEmail(signUpRequest.getEmail());
		user.setPhoneNo(signUpRequest.getPhoneNo());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setUserOf(username);
		user.setRoles(new HashSet<>(roleRepository.findByName(ERole.ROLE_OAG)));
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	

	@GetMapping("/userList")
	public ResponseEntity<List<User>> userList(Principal principal){
		String userName = principal.getName();
		return ResponseEntity.ok().body(userRepository.findByUserOf(userName));
	}
	
	  @PostMapping("/editUser")
	  public ResponseEntity<?> editUser(@RequestBody User euser){
	  User currentUser= userRepository.getOne(euser.getId()); if(currentUser==null)
	  { return ResponseEntity.ok(new MessageResponse("User Not Found!")); }else {
	  currentUser.setPassword(encoder.encode(euser.getPassword()));
	  currentUser.setPhoneNo(euser.getPhoneNo()); userRepository.save(currentUser);
	  return ResponseEntity.ok(new MessageResponse("Edit Completed!")); } }
	  
	  
	  @PostMapping("/changePassword")
	  public ResponseEntity<?> ChangePassword(Principal
	  principal,@RequestBody Password password){
	  
	  Optional<User> currentUser =
	  userRepository.findByUsername(principal.getName()); if (currentUser==null) {
	  return ResponseEntity.ok(new MessageResponse("User Not Found Error!")); }else
	  { User currentUser1= userRepository.getOne(currentUser.get().getId()); String
	  oldPassword = encoder.encode(password.getCurrentPassword());
	  if(currentUser1.getPassword().equals(oldPassword)) {
	  currentUser1.setPassword(encoder.encode(password.getNewPassword()));
	  userRepository.save(currentUser1); return ResponseEntity.ok(new
	  MessageResponse("Password Changed Successfully!")); }else { return
	  ResponseEntity.ok(new MessageResponse("Password Does not Match!")); } }
	  
	  }
	 
	

}
