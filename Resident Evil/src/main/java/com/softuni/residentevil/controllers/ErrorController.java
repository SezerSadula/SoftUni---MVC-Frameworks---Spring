package com.softuni.residentevil.controllers;

import com.softuni.residentevil.utils.MessageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);

    protected ErrorController(MessageWrapper messageWrapper) {
        super(messageWrapper);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exception(final Throwable throwable,
                                  final HttpServletRequest request) {
        if (throwable instanceof AccessDeniedException) {

            final Authentication auth = SecurityContextHolder
                    .getContext()
                    .getAuthentication();

            if (auth != null) {
                LOG.warn("User: " + auth.getName()
                        + " attempted to access the protected URL: "
                        + request.getRequestURI());
            }

            return super.view("/error/unauthorized");
        }

        LOG.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        return super.view("/error/error", (Object) errorMessage);
    }
}
