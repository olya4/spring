package com.geekbrains.homework6.cart.converters;

import com.geekbrains.homework6.api.CartDto;
import com.geekbrains.homework6.cart.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDto modelToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setItems(cart.getItems().stream().map(cartItemConverter::modelToDto).collect(Collectors.toList()));
        cartDto.setTotalPrice(cart.getTotalPrice());
        return cartDto;
    }
}
