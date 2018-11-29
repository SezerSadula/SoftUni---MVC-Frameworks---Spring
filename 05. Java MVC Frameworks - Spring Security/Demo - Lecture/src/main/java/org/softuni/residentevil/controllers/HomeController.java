package org.softuni.residentevil.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController extends BaseController {
    @GetMapping("/")
    public ModelAndView index() {
        return this.view("index");
    }

    @GetMapping("/home")
    public ModelAndView home(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("username", principal.getName());

        return this.view("user-home", modelAndView);
    }

    @GetMapping(value = "/test", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    Map test() {
        return new HashMap<String, Integer>() {{
            put("Pesho", 250);
            put("Gosho", 350);
            put("tosho", 23);
        }};
    }
}
