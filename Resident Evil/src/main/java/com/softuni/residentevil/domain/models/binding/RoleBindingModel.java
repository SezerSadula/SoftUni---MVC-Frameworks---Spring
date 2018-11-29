package com.softuni.residentevil.domain.models.binding;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RoleBindingModel {

    @NotNull
    @Length(min = 1, max = 12)
    private String authority;
}
