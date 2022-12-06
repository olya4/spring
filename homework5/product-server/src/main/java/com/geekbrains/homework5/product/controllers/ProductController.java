package com.geekbrains.homework5.product.controllers;


import com.geekbrains.homework5.ap.ProductDto;
import com.geekbrains.homework5.ap.ResourceNotFoundException;
import com.geekbrains.homework5.product.converters.ProductConverter;
import com.geekbrains.homework5.product.entities.Product;
import com.geekbrains.homework5.product.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// http://localhost:8189/app/api/v1/products

@RestController
// @RestController - все методы этого класса возвращают объект или значение
@RequestMapping("/api/v1/products")
// для увязки бинов ProductService и ProductController
@RequiredArgsConstructor
// ("*") - принимать запросы от всех приложений
@CrossOrigin("*")
public class ProductController {
    // пометить final все поля, кот. надо увязать с бином ProductController
    private final ProductService productService;

    private final ProductConverter productConverter;

    @GetMapping
    public List<ProductDto> getAllStudents() {
        return productService.getAllProducts().stream().map(productConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        // вернуть найденный продукт или кинуть исключение, если продукт не найден
        Product product = productService.findProductById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product not found id: " + id));
        return productConverter.entityToDto(product);
    }

    @PostMapping
    public ProductDto createNewProduct(@RequestBody ProductDto productDto) {
        Product product = productService.createNewProduct(productConverter.dtoToEntity(productDto));
        return productConverter.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }


}
