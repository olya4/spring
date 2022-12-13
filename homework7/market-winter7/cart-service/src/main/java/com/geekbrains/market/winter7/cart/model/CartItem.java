package com.geekbrains.market.winter7.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// строчка в корзинке
public class CartItem {
    private Long productId;
    private String productTitle;
    private int quantity;
    private int pricePerProduct;
    private int price;

    // изменение количества и общей стоимости
    public void changeQuantity(int delta){
        quantity += delta;
        price = pricePerProduct * quantity;
    }

}
