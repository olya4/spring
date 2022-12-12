package com.geekbrains.homework6.cart.controllers;


import com.geekbrains.homework6.api.CartDto;
import com.geekbrains.homework6.cart.converters.CartConverter;
import com.geekbrains.homework6.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// http://localhost:8190/winter-carts/api/v1/cart

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
// ("*") - принимать запросы от всех приложений
//@CrossOrigin("*")
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping
    public CartDto getCurrentCart() {
        return cartConverter.modelToDto(cartService.getCurrentCart());
    }

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        cartService.addProduct(id);
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.remove(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clear();
    }


}
