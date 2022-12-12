package com.geekbrains.homework6.core.controllers;




import com.geekbrains.homework6.api.ProductDto;
import com.geekbrains.homework6.api.ResourceNotFoundException;
import com.geekbrains.homework6.core.converters.ProductConverter;
import com.geekbrains.homework6.core.entities.Product;
import com.geekbrains.homework6.core.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

// http://localhost:8189/winter-app/api/v1/products

@RestController
// @RestController - все методы этого класса возвращают объект или значение
@RequestMapping("/api/v1/products")
// для увязки бинов ProductService и ProductController
@RequiredArgsConstructor
//@CrossOrigin("*")
public class ProductController {
    // пометить final все поля, кот. надо увязать с бином ProductController
    private final ProductService productService;

    private final ProductConverter productConverter;

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        // вернуть найденный продукт или кинуть исключение, если продукт не найден
        Product product = productService.findProductById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product not found id: " + id));
        return productConverter.entityToDto(product);
    }


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

    //  пагинация (разбиение на страницы) и фильтрация
    @GetMapping
    public Page<ProductDto> getAllProducts(@RequestParam(name = "p", defaultValue = "1") Integer page,
                                           @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
                                           @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
                                           @RequestParam(name = "title_part", required = false) String titlePart) {
        if (page < 1) {
            page = 1;
        }

        return productService.find(minPrice, maxPrice, titlePart, page).map(p -> productConverter.entityToDto(p));
    }
}
