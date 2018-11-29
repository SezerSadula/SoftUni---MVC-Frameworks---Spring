package org.softuni.nuggets.controllers;

import org.softuni.nuggets.models.binding.RegisterUserBindingModel;
import org.softuni.nuggets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AccountController {
    private final JmsTemplate jmsTemplate;

    private final UserService userService;

    @Autowired
    public AccountController(JmsTemplate jmsTemplate, UserService userService) {
        this.jmsTemplate = jmsTemplate;
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false, name = "error") String error, ModelAndView modelAndView) {
        modelAndView.setViewName("login");

        if(error != null) modelAndView.addObject("error", error);

        return modelAndView;
    }

    @PostMapping("/logout")
    public ModelAndView logout(@RequestParam(required = false, name = "logout") String logout, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        modelAndView.setViewName("redirect:/login");

        if(logout != null) redirectAttributes.addFlashAttribute("logout", logout);

        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute RegisterUserBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/login");

        if(bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            this.userService.register(bindingModel);

            Map<String, Object> messageMap = new HashMap<>();

            messageMap.put("username", bindingModel.getUsername());
            messageMap.put("preferences", Arrays.stream(bindingModel.getPreferences().split("\\,")).collect(Collectors.toList()));

            this.jmsTemplate.convertAndSend("register-user", messageMap);
        }

        return modelAndView;
    }
}
