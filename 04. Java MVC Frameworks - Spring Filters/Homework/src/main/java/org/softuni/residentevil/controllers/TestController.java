package org.softuni.residentevil.controllers;

import org.softuni.residentevil.common.annotations.PreAuthenticate;
import org.softuni.residentevil.entities.enums.UserRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PreAuthenticate(loggedIn = true, role = UserRole.ADMIN)
@RequestMapping("/test")
public class TestController extends BaseController {

    @GetMapping("/class-annotation-admin")
    @ResponseBody
    public String classAnnotation() {
        return "<h2>Using class annotation - route is accessible by ADMIN only</h2>" +
                "<a href=\"/\">Back to Index Page</a>";
    }

    @GetMapping("/method-annotation-guest")
    @PreAuthenticate(loggedIn = false)
    @ResponseBody
    public String methodAnnotationAny() {
        return "<h2>Method annotation overrides class annotation - route is accessible by GUEST only</h2>" +
                "<a href=\"/\">Back to Index Page</a>";
    }

    @GetMapping("/method-annotation-user")
    @PreAuthenticate(loggedIn = true, role = UserRole.USER)
    @ResponseBody
    public String methodAnnotationUser() {
        return "<h2>Method annotation overrides class annotation - route is accessible by both USER and ADMIN</h2>" +
                "<a href=\"/\">Back to Index Page</a>";
    }
}
