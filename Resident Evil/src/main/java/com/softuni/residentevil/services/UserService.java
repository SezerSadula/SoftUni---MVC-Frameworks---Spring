package com.softuni.residentevil.services;

import com.softuni.residentevil.domain.enums.Authority;
import com.softuni.residentevil.domain.models.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends Creatable, UserDetailsService {

    List<UserViewModel> getAll();

    boolean setAuthority(String id, Authority authority);
}
