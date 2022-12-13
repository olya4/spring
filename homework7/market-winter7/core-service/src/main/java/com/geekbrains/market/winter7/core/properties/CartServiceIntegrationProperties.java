package com.geekbrains.market.winter7.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
// это бин

// ConstructorBinding - поля класса будут заполнены через конструктор по данным из yaml-файла, а не через сеттеры
@ConstructorBinding
// все данные, кот. идут после integrations: cart-service:
@ConfigurationProperties(prefix = "integrations.cart-service")
@Data
// собрать объект из yaml-файла
public class CartServiceIntegrationProperties {
        // названия полей полностью совпадают
        private String url;
        private Integer connectTimeout;
        private Integer readTimeout;
        private Integer writeTimeout;
}
