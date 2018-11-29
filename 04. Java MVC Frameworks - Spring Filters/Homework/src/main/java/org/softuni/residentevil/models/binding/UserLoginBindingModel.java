package org.softuni.residentevil.models.binding;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginBindingModel {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
