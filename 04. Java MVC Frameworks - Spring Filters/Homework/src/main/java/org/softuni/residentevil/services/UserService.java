package org.softuni.residentevil.services;

import org.softuni.residentevil.models.binding.UserRegisterBindingModel;
import org.softuni.residentevil.models.service.UserServiceModel;

public interface UserService {

    void createUser(UserRegisterBindingModel bindingModel);

    UserServiceModel getUserByUsername(String username);
}
