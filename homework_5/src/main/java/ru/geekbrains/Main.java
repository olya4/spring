package ru.geekbrains;

public class Main {
    public static void main(String[] args) {

        // SessionFactory создается один раз при запуске приложения
        SessionFactory sessionFactory = new SessionFactory();
        sessionFactory.init();

        try {
            StudentDao studentDao = new StudentDaoImpl(sessionFactory);

            studentDao.save(new Student("April", 55));
            System.out.println(studentDao.findAll());

            studentDao.update(12L, "George", 40);
            System.out.println(studentDao.findById(12L));

            studentDao.save(new Student("Carl", 15));
            System.out.println(studentDao.findAll());
            studentDao.remove(23L);
            System.out.println(studentDao.findAll());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // закрыть SessionFactory по окончанию работы приложения
            sessionFactory.shutdown();
        }
    }
}

