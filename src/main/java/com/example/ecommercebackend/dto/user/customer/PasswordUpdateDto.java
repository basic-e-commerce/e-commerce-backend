package com.example.ecommercebackend.dto.user.customer;

import com.example.ecommercebackend.anotation.NotNullField;

public class PasswordUpdateDto {
    @NotNullField
    private String oldPassword;
    @NotNullField
    private String password;
    @NotNullField
    private String rePassword;

    public PasswordUpdateDto(String oldPassword, String password, String rePassword) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.rePassword = rePassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
