package com.geekbrains.homework2.controllers;


import com.geekbrains.homework2.dto.Cart;
import com.geekbrains.homework2.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        cartService.addProduct(id);
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.removeProduct(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }
}
