package com.geekbrains.homework2.dto;


import com.geekbrains.homework2.entities.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Cart {
    // состав корзинки
    private List<OrderItemDto> items;
    // суммарная стоимость корзинки
    private int totalPrice;

    // при создании новой корзинки,
    // создавать новый список товаров
    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<OrderItemDto> getItems() {
        // запрет на изменение содержимого корзинки
        return Collections.unmodifiableList(items);
    }

    // пересчет суммарной стоимости корзинки
    private void recalculate() {
        totalPrice = 0;
        for (OrderItemDto orderItemDto : items) {
            totalPrice += orderItemDto.getPrice();
        }
    }

    // добавление продукта в корзинку
    public void add(Product product) {
        for (OrderItemDto item : items) {
            if (item.getProductId().equals(product.getId())) {
                int count = item.getQuantity() + 1;
                item.setQuantity(count);
                item.setPrice(item.getQuantity() * item.getPricePerPiece());
                recalculate();
                return;
            }
        }
        items.add(new OrderItemDto(product.getId(), product.getTitle(), 1,
                product.getPrice(), product.getPrice()));
        recalculate();
    }

    // удаление продукта из корзинки
    public void remove(Product product) {
        for (OrderItemDto item : items) {
            if (item.getProductId().equals(product.getId())) {
                if (item.getQuantity() == 0 ) {
                    items.remove(item);
                    recalculate();
                    return;
                }
                int count = item.getQuantity() - 1;
                item.setQuantity(count);
                item.setPrice(item.getQuantity() * item.getPricePerPiece());
                recalculate();
                return;
            }
        }
    }

    // очистить корзинку от товаров
    public void clear() {
        // очистить список
        items.clear();
        // обнулить суммарную стоимость
        totalPrice = 0;
    }

}
