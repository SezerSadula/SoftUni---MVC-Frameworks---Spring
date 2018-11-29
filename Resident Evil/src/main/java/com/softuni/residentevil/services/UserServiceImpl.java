package com.softuni.residentevil.services;

import com.softuni.residentevil.config.constants.Constants;
import com.softuni.residentevil.domain.enums.Authority;
import com.softuni.residentevil.domain.etities.Role;
import com.softuni.residentevil.domain.etities.User;
import com.softuni.residentevil.domain.models.view.UserViewModel;
import com.softuni.residentevil.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validator;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(final Validator validator,
                           final ModelMapper modelMapper,
                           final UserRepository userRepository,
                           final RoleService roleService,
                           final BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(validator, modelMapper);
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected <T> T mapDtoToEntity(final Object dto, final Class<T> entityClass) {
        final T user = super.map(dto, entityClass);

        ((User) user).setPassword(this.bCryptPasswordEncoder.encode(((User) user).getPassword()));

        Set<Role> authorities = new HashSet<>();

        if (this.userRepository.findAll().isEmpty()) {
            this.roleService.initRoles();
            authorities.add(this.roleService.getByAuthority(Authority.ROOT.toString()));
            authorities.add(this.roleService.getByAuthority(Authority.ADMIN.toString()));
            authorities.add(this.roleService.getByAuthority(Authority.MODERATOR.toString()));
            authorities.add(this.roleService.getByAuthority(Authority.USER.toString()));
        } else {
            authorities.add(this.roleService.getByAuthority(Authority.USER.toString()));
        }

        ((User) user).setAuthorities(authorities);

        return user;
    }

    @Override
    public boolean create(final Object dto) {
        return super.validateAndCreate(dto, User.class, this.userRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public List<UserViewModel> getAll() {
        return this.userRepository
                .findAll()
                .stream()
                .map(user -> {
                    UserViewModel model = super.map(user, UserViewModel.class);
                    Authority highestAuthority = user
                            .getAuthorities()
                            .stream()
                            .map(role -> Authority.valueOf(role.getAuthority()
                                    .substring(Constants.AUTHORITY_PREFIX.length())))
                            .max(Comparator.reverseOrder())
                            .orElse(null);
                    model.setHighestAuthority(highestAuthority);
                    return model;
                })
                .sorted(Comparator.comparing(UserViewModel::getHighestAuthority))
                .collect(Collectors.toList());
    }

    @Override
    public boolean setAuthority(final String id, final Authority authority) {
        final User user = this.userRepository
                .findById(id)
                .orElse(null);

        if (user == null || authority == Authority.ROOT || isRoot(user)) {
            return false;
        }

        final Set<Role> authorities = new HashSet<>();

        switch (authority) {
        case ADMIN:
            authorities.add(this.roleService.getByAuthority(Authority.ADMIN.toString()));
        case MODERATOR:
            authorities.add(this.roleService.getByAuthority(Authority.MODERATOR.toString()));
        case USER:
            authorities.add(this.roleService.getByAuthority(Authority.USER.toString()));
        }

        user.setAuthorities(authorities);

        this.userRepository.save(user);

        return true;
    }

    private boolean isRoot(User user) {
        return user.getAuthorities()
                .stream()
                .map(Role::getAuthority)
                .anyMatch(s -> Authority.ROOT.toString().equals(s));
    }
}
