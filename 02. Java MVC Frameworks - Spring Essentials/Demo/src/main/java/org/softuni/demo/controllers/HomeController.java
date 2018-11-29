package org.softuni.demo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController extends BaseController {
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.addObject("pesho", "Pecata");
        modelAndView.addObject("message", "Drastyeeee");

        List<Integer> collection = new ArrayList<Integer>() {{
            add(5);
            add(7);
            add(20);
            add(30);
        }};

        modelAndView.addObject("collection", collection);

        return this.view("index", modelAndView);
    }

    @PostMapping("/")
    public String indexPost(@RequestParam String name, @RequestParam int id) {
        System.out.println(name);
        System.out.println(id);

        return "redirect:/";
    }

    @GetMapping("/test/{id}")
    public ModelAndView test(@PathVariable(name = "id") int id) {
        System.out.println("My ID is: " + id);

        return this.redirect("/");
    }
}
