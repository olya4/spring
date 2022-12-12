package com.geekbrains.homework6.core.services;

import com.geekbrains.homework6.core.entities.Category;
import com.geekbrains.homework6.core.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
// для увязки бинов ProductService и ProductRepository
@RequiredArgsConstructor
public class CategoryService {
    // пометить final все поля, кот. надо увязать с бином ProductService
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }
}
