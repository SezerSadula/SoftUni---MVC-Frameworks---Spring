package org.softuni.residentevil.controllers;

import org.springframework.web.servlet.ModelAndView;

abstract class BaseController {

    ModelAndView view(String view, ModelAndView modelAndView) {
        modelAndView.setViewName(view);

        return modelAndView;
    }

    ModelAndView view(String view) {
        return this.view(view, new ModelAndView());
    }

    ModelAndView redirect(String route) {
        return this.view("redirect:" + route);
    }
}