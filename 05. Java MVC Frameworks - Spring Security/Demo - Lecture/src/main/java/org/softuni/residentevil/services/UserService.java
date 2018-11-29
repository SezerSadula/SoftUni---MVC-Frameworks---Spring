package org.softuni.residentevil.services;

import org.softuni.residentevil.entities.User;
import org.softuni.residentevil.models.binding.UserRegisterBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {
    boolean createUser(UserRegisterBindingModel user);

    Set<User> getAllUsers();
}
