package com.geekbrains.market.winter7.core.services;

import com.geekbrains.market.winter7.api.ProductDto;
import com.geekbrains.market.winter7.api.ResourceNotFoundException;
import com.geekbrains.market.winter7.core.entities.Product;
import com.geekbrains.market.winter7.core.repositories.ProductRepository;
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
    private final CategoryService categoryService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    // возвращается Product, т.к. после сохранения бд присвоит ему id
    public Product save(Product product) {
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

    @Transactional
    // чтобы весь метод выполнялся в рамках одной транзакции
    public Product createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() ->
                new ResourceNotFoundException("Category not found")));
        productRepository.save(product);
        return product;
    }


}
