package com.softuni.residentevil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResidentEvilApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResidentEvilApplication.class, args);

    }

/*    @Bean
    InitializingBean initAuthorities(RoleService roleService) {
        return roleService::initRoles;
    }*/

/*    @Bean
    InitializingBean initUsers(final UserService userService) {
        return () -> {
            userService.create(new UserRegisterBindingModel("root", "root", "root"));
            userService.create(new UserRegisterBindingModel("admin", "admin", "admin"));
            userService.setAuthority(((User)userService.loadUserByUsername("admin")).getId(), Authority.ADMIN);
            userService.create(new UserRegisterBindingModel("moderator", "moderator", "moderator"));
            userService.setAuthority(((User)userService.loadUserByUsername("moderator")).getId(), Authority.MODERATOR);
            userService.create(new UserRegisterBindingModel("user", "user", "user"));
        };
    }*/
}
