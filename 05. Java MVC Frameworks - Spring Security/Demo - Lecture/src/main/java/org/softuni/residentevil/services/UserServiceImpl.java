package org.softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.common.factories.UserRoleFactory;
import org.softuni.residentevil.entities.User;
import org.softuni.residentevil.entities.UserRole;
import org.softuni.residentevil.models.binding.UserRegisterBindingModel;
import org.softuni.residentevil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapper modelMapper;

    private final UserRoleFactory userRoleFactory;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, UserRoleFactory userRoleFactory) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRoleFactory = userRoleFactory;
    }

    @Override
    public boolean createUser(UserRegisterBindingModel userRegisterBindingModel) {
        User userEntity = this.modelMapper.map(userRegisterBindingModel, User.class);

        userEntity.setPassword(this.bCryptPasswordEncoder.encode(userEntity.getPassword()));

        Set<UserRole> authorities = new HashSet<>();

        if (this.userRepository.findAll().isEmpty()) {
            authorities.add(userRoleFactory.createUserRole("USER"));
            authorities.add(userRoleFactory.createUserRole("ADMIN"));
        } else {
            authorities.add(userRoleFactory.createUserRole("USER"));
        }

        userEntity.setAuthorities(authorities);

        return this.userRepository.save(userEntity) != null;
    }

    @Override
    public Set<User> getAllUsers() {
        return this
                .userRepository
                .findAll()
                .stream()
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = this.userRepository
                .findByUsername(username)
                .orElse(null);

        if (foundUser == null) {
            throw new UsernameNotFoundException("Username not found.");
        }

        return foundUser;
    }
}
