package com.geekbrains.market.winter7.api;


import java.util.List;

public class CartDto {
    // состав корзинки
    private List<CartItemDto> items;
    // суммарная стоимость корзинки
    private int totalPrice;

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
