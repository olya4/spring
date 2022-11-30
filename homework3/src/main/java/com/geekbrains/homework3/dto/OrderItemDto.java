package com.geekbrains.homework3.dto;

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
