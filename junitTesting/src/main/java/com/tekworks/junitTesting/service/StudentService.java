package com.tekworks.junitTesting.service;


import com.tekworks.junitTesting.entity.Student;
import com.tekworks.junitTesting.reposistory.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;



    public String addStudent(Student student){
        studentRepository.save(student);
        return "Student added successfully";
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudent(int id){
        return studentRepository.findById(id).get();
    }




}
