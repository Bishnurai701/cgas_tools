package com.cgastool.domain;

public class ProcSuccessDto {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ProcSuccessDto(boolean success) {
        this.success = success;
    }

    public ProcSuccessDto() {
    }
}
