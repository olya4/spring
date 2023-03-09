package ru.geekbrains.homework_7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.homework_7.entities.Student;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Long id;
    private String name;
    private Integer age;

    // сделать из класса dto-объект
    public StudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.age = student.getAge();
    }
}
