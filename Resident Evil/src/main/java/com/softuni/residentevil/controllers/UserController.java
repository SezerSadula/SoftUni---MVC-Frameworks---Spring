package com.softuni.residentevil.controllers;

import com.softuni.residentevil.domain.models.binding.UserRegisterBindingModel;
import com.softuni.residentevil.domain.models.view.UserViewModel;
import com.softuni.residentevil.services.UserService;
import com.softuni.residentevil.utils.MessageWrapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;

    protected UserController(final MessageWrapper messageWrapper,
                             final UserService userService) {
        super(messageWrapper);
        this.userService = userService;
    }

    @GetMapping(value = {"", "/", "/all"})
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getAll() {
        final List<UserViewModel> allUsers = this.userService.getAll();
        return super.view("/users/all", allUsers);
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return super.view("/users/login");
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(final UserRegisterBindingModel userRegisterBindingModel) {
        return super.view("/users/register", userRegisterBindingModel);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("viewModel") final UserRegisterBindingModel userRegisterBindingModel,
                                        final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("/users/register", userRegisterBindingModel);
        }

        this.userService.create(userRegisterBindingModel);

        return super.redirect("/users/login");
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView logout(final HttpSession session) {
        session.invalidate();
        return super.redirect("/");
    }
}
