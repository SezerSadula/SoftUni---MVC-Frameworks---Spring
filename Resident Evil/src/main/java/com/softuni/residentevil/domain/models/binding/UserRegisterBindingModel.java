package com.softuni.residentevil.domain.models.binding;

import com.softuni.residentevil.domain.validation.EqualFields;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@EqualFields(fields = {"password", "confirmPassword"}, message = "{user.registration.passwords-not-match}")
public class UserRegisterBindingModel {

    @NotNull
    @Length(min = 4, max = 16)
    private String username;

    @NotNull
    @Length(min = 4, max = 16)
    private String password;

    @NotNull
    @Length(min = 4, max = 16)
    private String confirmPassword;
}
