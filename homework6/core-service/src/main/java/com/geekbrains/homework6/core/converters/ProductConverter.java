package com.geekbrains.homework6.core.converters;

import com.geekbrains.homework6.api.ProductDto;
import com.geekbrains.homework6.api.ResourceNotFoundException;
import com.geekbrains.homework6.core.entities.Product;
import com.geekbrains.homework6.core.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    // преобразовать сущность в dto
    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getTitle());
    }

    // преобразовать dto в сущность
    public Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(product.getId());
        product.setTitle(productDto.getTitle());
        product.setCategory(categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() ->
                new ResourceNotFoundException("Category not found")));
        product.setPrice(productDto.getPrice());
        return product;
    }
}
