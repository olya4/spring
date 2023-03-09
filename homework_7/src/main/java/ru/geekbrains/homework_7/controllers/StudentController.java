package ru.geekbrains.homework_7.controllers;

import org.springframework.web.bind.annotation.*;
import ru.geekbrains.homework_7.dto.StudentDto;
import ru.geekbrains.homework_7.entities.Student;
import ru.geekbrains.homework_7.exceptions.ResourceNotFoundException;
import ru.geekbrains.homework_7.services.StudentService;

import java.util.List;

//http://localhost:8189/app/api/v1/students

@RestController
// @RestController - все методы этого класса возвращают объект или значение
@RequestMapping("/api/v1/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> findAllStudents() {
        return studentService.findAllStudents();
    }

    // возвращается Student, т.к. после сохранения бд присвоит ему id
    @PostMapping
    public Student saveNewStudent(@RequestBody StudentDto studentDto) {
        // если у новового студента указан id, чтоб не обновить существующего, лучше новому занулить id
        Student student = new Student(null, studentDto.getName(), studentDto.getAge());
        return studentService.save(student);
    }

    @PutMapping
    public void updateStudent(@RequestBody StudentDto studentDto) {
        studentService.update(studentDto.getId(), studentDto.getName(), studentDto.getAge());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}
