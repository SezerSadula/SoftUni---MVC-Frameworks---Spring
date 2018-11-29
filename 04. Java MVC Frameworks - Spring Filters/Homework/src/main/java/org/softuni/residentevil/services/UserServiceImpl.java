package org.softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.entities.User;
import org.softuni.residentevil.entities.enums.UserRole;
import org.softuni.residentevil.models.binding.UserRegisterBindingModel;
import org.softuni.residentevil.models.service.UserServiceModel;
import org.softuni.residentevil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserRegisterBindingModel bindingModel) {
        User userEntity = this.modelMapper.map(bindingModel, User.class);

        if (this.userRepository.findAll().isEmpty()) {
            userEntity.setUserRole(UserRole.ADMIN);
        } else {
            userEntity.setUserRole(UserRole.USER);
        }

        this.userRepository.save(userEntity);
    }

    @Override
    public UserServiceModel getUserByUsername(String username) {
        final User user = this.userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }
}
