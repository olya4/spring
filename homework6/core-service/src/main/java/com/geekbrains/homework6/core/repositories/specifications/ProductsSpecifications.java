package com.geekbrains.homework6.core.repositories.specifications;


import com.geekbrains.homework6.core.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

// генерирует запросы на основе заданных критериев
public class ProductsSpecifications {

    // Specification - критерий поиска
    // criteriaBuilder - позволяет строить различные критерии (что-то должно быть больше чего-то)
    // root - объект корневого типа, для кот. строится спецификация (<Product>)
    public static Specification<Product> priceGreaterOrEqualsThan(BigDecimal price) {
        // price продукта root.get("price") должен быть больше или равен указанному price из входящего параметра
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessThanOrEqualsThan(BigDecimal price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    // %Bo% = String.format("%%%s%%", Bo)
    public static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }
}