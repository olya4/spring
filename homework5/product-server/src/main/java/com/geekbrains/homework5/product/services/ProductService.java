package com.geekbrains.homework5.product.services;


import com.geekbrains.homework5.ap.ProductDto;
import com.geekbrains.homework5.product.converters.ProductConverter;
import com.geekbrains.homework5.product.entities.Product;
import com.geekbrains.homework5.product.repositories.ProductRepository;
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
    public Product createNewProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
