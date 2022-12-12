package com.geekbrains.homework6.cart.converters;

import com.geekbrains.homework6.api.CartItemDto;
import com.geekbrains.homework6.cart.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemConverter {

    public CartItemDto modelToDto(CartItem cartItem){
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(cartItem.getProductId());
        cartItemDto.setProductTitle(cartItem.getProductTitle());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setPricePerProduct(cartItem.getPricePerProduct());
        cartItemDto.setPrice(cartItem.getPrice());
        return cartItemDto;
    }
}
