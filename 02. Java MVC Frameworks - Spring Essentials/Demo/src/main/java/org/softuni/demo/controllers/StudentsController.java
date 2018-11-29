package org.softuni.demo.controllers;

import org.softuni.demo.models.binding.CreateStudentBindingModel;
import org.softuni.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/students")
public class StudentsController extends BaseController {
    private final StudentService studentService;

    @Autowired
    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ModelAndView createStudent(CreateStudentBindingModel createStudentBindingModel) {
        this.studentService.create(createStudentBindingModel);

        return this.redirect("all");
    }

    @GetMapping("/all")
    public ModelAndView allStudents(ModelAndView modelAndView) {
        modelAndView.addObject("students", this.studentService.getAll());

        return this.view("students-all", modelAndView);
    }

    @GetMapping("/details/{id}")
    public @ResponseBody
    String details(@PathVariable String id) {
        return this
                .studentService
                .findById(id)
                .extractStudentAsString();
    }
}
