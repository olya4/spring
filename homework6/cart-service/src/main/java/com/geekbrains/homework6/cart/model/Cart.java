package com.geekbrains.homework6.cart.model;

import com.geekbrains.homework6.api.ProductDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Cart {
    // состав корзинки
    private List<CartItem> items;
    // суммарная стоимость корзинки
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        // запрет на изменение содержимого корзинки
        return Collections.unmodifiableList(items);
    }

    // пересчет суммарной стоимости корзинки
    private void recalculate() {
        totalPrice = new BigDecimal(0);
        for (CartItem cartItem : items) {
            totalPrice = totalPrice.add(cartItem.getPrice());
        }
    }

    // добавление продукта в корзинку
    public void add(ProductDto productDto) {
        for (CartItem item : items) {
            if (productDto.getId().equals(item.getProductId())) {
                item.changeQuantity(1);
                recalculate();
                return;
            }
        }
        items.add(new CartItem(productDto.getId(), productDto.getTitle(), 1,
                productDto.getPrice(), productDto.getPrice()));
        recalculate();
    }

    // удаление продукта из корзинки
    public void remove(Long productId) {
        if (items.removeIf(item -> item.getProductId().equals(productId))) {
            recalculate();
        }
    }

    // очистить корзинку
    public void clear() {
        items.clear();
        totalPrice = new BigDecimal(0);
    }

}
