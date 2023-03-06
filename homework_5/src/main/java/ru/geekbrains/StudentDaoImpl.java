package ru.geekbrains;

import org.hibernate.Session;

import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private SessionFactory sessionFactory;

    public StudentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Student student) {
        // получить сессию для запроса в БД
        try (Session session = sessionFactory.getSession()) {
            // открыть транзакцию
            session.beginTransaction();
            // передать объект
            session.saveOrUpdate(student);
            // закрыть транзакцию
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Long id) {
        // получить сессию для запроса в БД
        try (Session session = sessionFactory.getSession()) {
            // открыть транзакцию
            session.beginTransaction();
            // удалить объект по id
            session.remove(session.get(Student.class, id));
            // закрыть транзакцию
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Long id, String name, Integer mark) {
        // получить сессию для запроса в БД
        try (Session session = sessionFactory.getSession()) {
            // открыть транзакцию
            session.beginTransaction();
            // получить объект по id
            Student student = session.get(Student.class, id);
            // присвоить объекту новое имя
            student.setName(name);
            // присвоить объекту новую оценку
            student.setMark(mark);
            // закрыть транзакцию
            session.getTransaction().commit();
        }
    }

    @Override
    public Student findById(Long id) {
        // получить сессию для запроса в БД
        try (Session session = sessionFactory.getSession()) {
            // открыть транзакцию
            session.beginTransaction();
            // получить сущность из БД по id и преобразовать к java-объекту Student
            Student student = session.get(Student.class, id);
            // закрыть транзакцию
            session.getTransaction().commit();
            // вернуть полученный объект
            return student;
        }
    }

    @Override
    public List<Student> findAll() {
        // получить сессию для запроса в БД
        try (Session session = sessionFactory.getSession()) {
            // открыть транзакцию
            session.beginTransaction();
            // getResultList() - получить список сущностей из БД
            // session.createQuery("select s from Student s") - сформировать запрос
            // "s from Student s" - объектно-ориентированный язык запросов HQL
            List<Student> students = session.createQuery("select s from Student s").getResultList();
            // закрыть транзакцию
            session.getTransaction().commit();
            return students;
        }
    }
}
