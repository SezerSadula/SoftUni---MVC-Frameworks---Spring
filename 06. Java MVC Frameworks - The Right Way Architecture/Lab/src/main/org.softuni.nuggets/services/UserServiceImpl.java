package org.softuni.nuggets.services;

import org.modelmapper.ModelMapper;
import org.softuni.nuggets.entities.User;
import org.softuni.nuggets.models.binding.RegisterUserBindingModel;
import org.softuni.nuggets.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final BCryptPasswordEncoder encoder;

    private final ModelMapper mapper;

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder encoder, ModelMapper mapper, UserRepository userRepository) {
        this.encoder = encoder;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User result = this.userRepository.findByUsername(username);

        if(result == null) throw new UsernameNotFoundException("Username not found.");

        return result;
    }

    @Override
    public void register(RegisterUserBindingModel bindingModel) {
        User user = this.mapper.map(bindingModel, User.class);

        user.setPassword(this.encoder.encode(bindingModel.getPassword()));

        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);

        this.userRepository.save(user);
    }

    @Override
    public void addPreferences(String username, List<String> preferences) {
        //TODO: Add preferences
    }
}
