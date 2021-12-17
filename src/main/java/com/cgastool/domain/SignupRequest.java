package com.cgastool.domain;

import java.util.Set;

import javax.validation.constraints.*;

public class SignupRequest {
	@NotBlank
	@Size(min = 6, max = 50)
	private String userName;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	private Set<String> role;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	private String phoneNo;
	private String fullName;
	private String oIdentity;
	private Long officeId;
	private String checkPassword;
	private String PhoneNumberPrefix;

	public String getCheckPassword() {
		return checkPassword;
	}

	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}

	public String getPhoneNumberPrefix() {
		return PhoneNumberPrefix;
	}

	public void setPhoneNumberPrefix(String phoneNumberPrefix) {
		PhoneNumberPrefix = phoneNumberPrefix;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getoIdentity() {
		return oIdentity;
	}

	public void setoIdentity(String oIdentity) {
		this.oIdentity = oIdentity;
	}

	public Long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRole() {
		return this.role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
}
