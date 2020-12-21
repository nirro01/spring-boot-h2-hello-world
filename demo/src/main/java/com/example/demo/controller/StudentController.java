package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student postStudent(@RequestBody Student student) {
        Student createdStudent = studentRepository.save(student);
        return createdStudent;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Student getStudent(@PathVariable("id") long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found"));
        return student;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Student> findStudents(@RequestParam("name") String name) {
        List<Student> students = studentRepository.findByName(name);
        return students;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable("id") long id) {
        studentRepository.deleteById(id);
    }
}
