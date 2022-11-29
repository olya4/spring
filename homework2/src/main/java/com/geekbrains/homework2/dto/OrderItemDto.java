package com.geekbrains.homework2.dto;

import com.geekbrains.homework2.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// строчка в корзинке
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private int pricePerPiece;
    private int price;

 }
