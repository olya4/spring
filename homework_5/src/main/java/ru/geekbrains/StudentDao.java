package ru.geekbrains;

import java.util.List;

public interface StudentDao {

    void save(Student student);

    void remove(Long id);

    void update(Long id, String name, Integer mark);

    Student findById(Long id);

    List<Student> findAll();

}
