package org.softuni.bindingModels;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping(value = "/testjsp", produces = "application/json")
    public @ResponseBody
    Object myIndex() {
        return new UserRegisterDto();
    }

}
