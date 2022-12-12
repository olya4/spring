package com.geekbrains.homework6.core.converters;

import com.geekbrains.homework6.api.CategoryDto;
import com.geekbrains.homework6.core.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryConverter {
    private final ProductConverter productConverter;

    // преобразовать сущность в dto
    public CategoryDto entityToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categoryDto.getId());
        categoryDto.setTitle(category.getTitle());
        categoryDto.setProducts(category.getProducts().stream().map(productConverter::entityToDto).collect(Collectors.toList()));
        return categoryDto;
    }

    // преобразовать dto в сущность

}
