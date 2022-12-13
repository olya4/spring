package com.geekbrains.market.winter7.core.controllers;

import com.geekbrains.market.winter7.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// http://localhost:8189/winter-market-app/api/v1/orders

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    // при успешном создании заказа - вернется код 201 - операция прошла успешно
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username) {
        // получить пользователя

        // создать заказ
        orderService.createOrder(username);
    }

}
