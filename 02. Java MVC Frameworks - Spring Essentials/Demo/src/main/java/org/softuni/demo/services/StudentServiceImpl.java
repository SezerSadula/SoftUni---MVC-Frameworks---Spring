package org.softuni.demo.services;

import org.modelmapper.ModelMapper;
import org.softuni.demo.entities.Student;
import org.softuni.demo.models.binding.CreateStudentBindingModel;
import org.softuni.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean create(CreateStudentBindingModel createStudentBindingModel) {
        Student student = this.modelMapper.map(createStudentBindingModel, Student.class);

        return this.studentRepository.save(student) != null;
    }

    @Override
    public Set<Student> getAll() {
        return this.studentRepository
                .findAll()
                .stream()
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Student findById(String id) {
        return this.studentRepository
                .findById(id)
                .orElse(null);
    }
}
