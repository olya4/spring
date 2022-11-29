package com.geekbrains.homework2.controllers;


import com.geekbrains.homework2.dto.ProductDto;
import com.geekbrains.homework2.entities.Product;
import com.geekbrains.homework2.exceptions.ResourceNotFoundException;
import com.geekbrains.homework2.services.ProductService;
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
public class ProductController {
    // пометить final все поля, кот. надо увязать с бином ProductController
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllStudents() {
        return productService.getAllProducts().stream().map(p ->
                new ProductDto(p.getId(), p.getTitle(), p.getPrice())).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        // вернуть найденный продукт или кинуть исключение, если продукт не найден
        Product product = productService.findProductById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product not found id: " + id));
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }

    // возвращается Product, т.к. после сохранения бд присвоит ему id
    @PostMapping
    public ProductDto saveNewProduct(@RequestBody ProductDto productDto) {
        productService.save(productDto);
        return new ProductDto(productService.save(productDto).getId(), productService.save(productDto).getTitle(), productService.save(productDto).getPrice());
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productService.update(productDto);
        return new ProductDto(productService.update(productDto).getId(), productService.update(productDto).getTitle(), productService.update(productDto).getPrice());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

}
