package com.tekworks.docker_mysql.service;

import com.tekworks.docker_mysql.model.Student;
import com.tekworks.docker_mysql.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor Injection (Best Practice)
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Save Student (Returns the Saved Student)
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Get Student By ID (Handles Non-Existing ID Properly)
    public Student getStudent(int id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Student not found with ID: " + id)
        );
    }

    // Get All Students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
