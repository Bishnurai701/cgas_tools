package com.cgastool.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetPasswordDto {

    private String password;
    @JsonProperty("confirm_password")
    private String confirmPassword;
    @JsonProperty("reset_password_token")
    private String resetPasswordToken;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
}