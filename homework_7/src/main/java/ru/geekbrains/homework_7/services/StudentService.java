package ru.geekbrains.homework_7.services;

import org.springframework.stereotype.Service;
import ru.geekbrains.homework_7.entities.Student;
import ru.geekbrains.homework_7.exceptions.ResourceNotFoundException;
import ru.geekbrains.homework_7.repositories.StudentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    // для увязки бинов StudentRepository и StudentService
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    // возвращается Student, т.к. после сохранения бд присвоит ему id
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    // @Transactional - весь метод выполняется в рамках одной транзакции
    // @Transactional - открытие/закрытие транзакции происходит автоматически при обращении/завершении метода
    public void update(Long id, String name, Integer age) {
        // вернуть найденного студента или кинуть исключение, если студент не найден
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Unable to change student's score. Student not found id: " + id));
//        изменить имя полученному студенту
        student.setName(name);
//        изменить возраст полученному студенту
        student.setAge(age);
//        сохранить студента с изменениями
        studentRepository.save(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

}
