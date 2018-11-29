package org.softuni;

import org.softuni.bindingModels.UserRegisterDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/indexPage/")
    public String index(Model model) {
        return this.index(model, null, 20L);
    }

    @GetMapping("/indexPage/{name}")
    public String index(Model model,
                        @PathVariable String name,
                        @RequestParam(required = false) Long age) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "home/Hello";
    }

    @GetMapping("/users/register")
    public String register() {
        return "register";
    }

//    @PostMapping("/users/register")
//    public ModelAndView registerProcess(UserRegisterDto dto) {
//        return new ModelAndView("register-success")
//                .addObject("userDto", dto);
//    }

    @PostMapping("/users/register")
    public ModelAndView registerProcess(UserRegisterDto dto,
                                        ModelAndView modelAndView) {
        modelAndView.setViewName("register-success");
        modelAndView.addObject("userDto", dto);
        String[] names = {"gosho", "pesho", "maria", "stamatka"};
        modelAndView.addObject("names", names);

        return modelAndView;
    }

}
