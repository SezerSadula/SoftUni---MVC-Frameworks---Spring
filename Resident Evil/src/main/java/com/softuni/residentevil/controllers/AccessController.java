package com.softuni.residentevil.controllers;

import com.softuni.residentevil.utils.MessageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccessController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);

    protected AccessController(final MessageWrapper messageWrapper) {
        super(messageWrapper);
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(final HttpServletRequest request) {
        final Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth != null) {
            LOG.warn("User: " + auth.getName()
                    + " attempted to access the protected URL: "
                    + request.getRequestURI());
        }

        return "/error/unauthorized";
    }
}
