package com.geekbrains.homework2.services;


import com.geekbrains.homework2.dto.Cart;
import com.geekbrains.homework2.entities.Product;
import com.geekbrains.homework2.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart tempCart;

    // инициализация корзинки при запуске приложения
    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    // получить корзинку
    public Cart getCurrentCart() {
        return tempCart;
    }

    // добавить продукт в корзинку
    public void addProduct(Long productId) {
        Product product = productService.findProductById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Не удалось добавить продукт в корзинку с id: " + productId + " .Продукт не найден"));
        tempCart.add(product);
    }

    // удалить продукт из корзинки
    public void removeProduct(Long productId) {
        Product product = productService.findProductById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Не удалось удалить продукт из корзинки с id: " + productId + " .Продукт не найден"));
        tempCart.remove(product);
    }

    public void clearCart(){
        tempCart.clear();
    }

}
