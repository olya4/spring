package com.geekbrains.homework3.services;


import com.geekbrains.homework3.dto.ProductDto;
import com.geekbrains.homework3.entities.Product;
import com.geekbrains.homework3.exceptions.ResourceNotFoundException;
import com.geekbrains.homework3.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
// для увязки бинов ProductService и ProductRepository
@RequiredArgsConstructor
public class ProductService {
    // пометить final все поля, кот. надо увязать с бином ProductService
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    // возвращается Product, т.к. после сохранения бд присвоит ему id
    public Product save(ProductDto productDto) {
        Product product = new Product(productDto.getTitle(),productDto.getPrice());
        return productRepository.save(product);
    }

    @Transactional
    // чтобы весь метод выполнялся в рамках одной транзакции
    public Product update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Невозможно обновить продукт, не найден в базе id: " + productDto.getId()));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        // save делать не надо, т.к. это транзакционный метод и hibernate сам сохранит обновленный продукт
        return product;
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
