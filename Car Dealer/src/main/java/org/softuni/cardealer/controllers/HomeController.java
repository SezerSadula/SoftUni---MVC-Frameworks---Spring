package org.softuni.cardealer.controllers;

import org.softuni.cardealer.services.CarService;
import org.softuni.cardealer.utils.MessageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public final class HomeController extends BaseController {

    @Autowired
    public HomeController(final MessageWrapper messageWrapper,
                          final CarService carService) {
        super(messageWrapper, carService);
    }

    @GetMapping(value = {"", "/"})
    public ModelAndView rootGet() {
        return super.view("/index");
    }
}
