package com.geekbrains.market.winter7.core.controllers;

import com.geekbrains.market.winter7.api.ProductDto;
import com.geekbrains.market.winter7.api.ResourceNotFoundException;
import com.geekbrains.market.winter7.core.converters.ProductConverter;
import com.geekbrains.market.winter7.core.entities.Product;
import com.geekbrains.market.winter7.core.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// http://localhost:8189/winter-market-app/api/v1/products

@RestController
// @RestController - все методы этого класса возвращают объект или значение
@RequestMapping("/api/v1/products")
// для увязки бинов ProductService и ProductController
@RequiredArgsConstructor
public class ProductController {
    // пометить final все поля, кот. надо увязать с бином ProductController
    private final ProductService productService;

    private final ProductConverter productConverter;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts().stream().map(productConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        // вернуть найденный продукт или кинуть исключение, если продукт не найден
        Product product = productService.findProductById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product not found id: " + id));
        return productConverter.entityToDto(product);
    }

//    // возвращается Product, т.к. после сохранения бд присвоит ему id
//    @PostMapping
//    public Product saveProduct(@RequestBody Product product) {
//        return productService.save(product);
//    }

    @PostMapping
    public ProductDto createNewProduct(@RequestBody ProductDto productDto){
        Product product = productService.createNewProduct(productDto);
        return productConverter.entityToDto(product);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productService.update(productDto);
        return new ProductDto(productService.update(productDto).getId(), productService.update(productDto).getTitle(),
                productService.update(productDto).getPrice(), productService.update(productDto).getCategory().getTitle());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }


}
