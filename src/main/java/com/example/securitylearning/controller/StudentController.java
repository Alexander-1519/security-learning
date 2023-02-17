package com.example.securitylearning.controller;

import com.example.securitylearning.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
      new Student(1, "Alexander"),
      new Student(2, "Kirill"),
      new Student(3, "Vladislav"),
      new Student(4, "Oleg")
    );

    @GetMapping("{studentId}")
    public Student getById(@PathVariable("studentId") Integer studentId) {
        return STUDENTS.stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student " + studentId + " does not exist"));
    }
}
