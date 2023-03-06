package ru.geekbrains;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class SessionFactory {
    private org.hibernate.SessionFactory factory;

    // создание SessionFactory
    public void init() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    // получить сессию для запроса в БД
    public Session getSession() {
        return factory.getCurrentSession();
    }

    // закрытие SessionFactory
    public void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
