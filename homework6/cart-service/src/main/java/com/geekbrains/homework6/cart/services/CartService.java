package com.geekbrains.homework6.cart.services;

import com.geekbrains.homework6.api.ProductDto;
import com.geekbrains.homework6.cart.integrations.ProductServiceIntegration;
import com.geekbrains.homework6.cart.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
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
        ProductDto productDto = productServiceIntegration.getProductById(productId);
        tempCart.add(productDto);
    }

    // удалить продукт из корзинки
    public void remove(Long productId){
        tempCart.remove(productId);
    }

    // очистить корзинку
    public void clear(){
        tempCart.clear();
    }


}
