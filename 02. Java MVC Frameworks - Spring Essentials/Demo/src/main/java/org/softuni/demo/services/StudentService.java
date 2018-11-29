package org.softuni.demo.services;

import org.softuni.demo.entities.Student;
import org.softuni.demo.models.binding.CreateStudentBindingModel;

import java.util.Set;

public interface StudentService {
    boolean create(CreateStudentBindingModel createStudentBindingModel);

    Set<Student> getAll();

    Student findById(String id);
}
