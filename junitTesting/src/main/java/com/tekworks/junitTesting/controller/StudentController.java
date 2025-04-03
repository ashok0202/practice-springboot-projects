package com.tekworks.junitTesting.controller;

import com.tekworks.junitTesting.entity.Student;
import com.tekworks.junitTesting.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;


    @PostMapping("/save")
    public String saveStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/get/{id}")
    public Optional<Student> getStudent(@PathVariable int id) {
        return Optional.ofNullable(studentService.getStudent(id));
    }
}
