package com.geekbrains.homework3.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("secrets.properties")
public class AppConfig {
    // "secrets.properties" - файл с ключом и временем жизни токена из папки resources
}
