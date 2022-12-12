package com.geekbrains.homework6.api;


import java.math.BigDecimal;
import java.util.List;

public class CartDto {
    // состав корзинки
    private List<CartItemDto> items;
    // суммарная стоимость корзинки
    private BigDecimal totalPrice;

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
