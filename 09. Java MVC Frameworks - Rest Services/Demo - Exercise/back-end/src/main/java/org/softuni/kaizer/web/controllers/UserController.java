package org.softuni.kaizer.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.kaizer.domain.entities.UserRole;
import org.softuni.kaizer.domain.models.binding.UserRegisterBindingModel;
import org.softuni.kaizer.domain.models.service.UserServiceModel;
import org.softuni.kaizer.domain.models.view.AllUsersUserViewModel;
import org.softuni.kaizer.repository.RoleRepository;
import org.softuni.kaizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users", consumes = "application/json", produces = "application/json")
public class UserController {
    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(RoleRepository roleRepository, UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserRegisterBindingModel userRegisterBindingModel) throws URISyntaxException {
        if(!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Error: Passwords do not match!");
        }

        boolean result = this.userService
                .createUser(this.modelMapper
                        .map(userRegisterBindingModel, UserServiceModel.class));

        return ResponseEntity.created(new URI("/users/register")).body(result);
    }

    @GetMapping(value = "/all")
    public Set<AllUsersUserViewModel> all() {
        Set<AllUsersUserViewModel> allUsers =
                this.userService.getAll()
                .stream()
                .map(x -> {
                    AllUsersUserViewModel currentUserViewModel = this.modelMapper
                            .map(x, AllUsersUserViewModel.class);

                    currentUserViewModel.setRole(x.extractAuthority());

                    return currentUserViewModel;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));

            return allUsers;
    }

    @PostMapping("/promote")
    public ResponseEntity promoteUser(@RequestParam(name = "id") String id) {
        boolean resultOfPromoting = this.userService.promoteUser(id);

        if(resultOfPromoting) {
            return ResponseEntity.ok().body("User promoted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failure promoting user!");
        }
    }

    @PostMapping("/demote")
    public ResponseEntity demoteUser(@RequestParam(name = "id") String id) {
        boolean resultOfDemoting = this.userService.demoteUser(id);

        if(resultOfDemoting) {
            return ResponseEntity.ok("User demoted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failure demoting user!");
        }
    }
}
