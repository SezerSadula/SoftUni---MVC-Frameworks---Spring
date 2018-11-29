package org.softuni.residentevil.models.service;

import lombok.Data;
import org.softuni.residentevil.entities.enums.UserRole;

@Data
public class UserServiceModel {

    private String id;

    private String username;

    private String password;

    private String email;

    private UserRole userRole;
}
