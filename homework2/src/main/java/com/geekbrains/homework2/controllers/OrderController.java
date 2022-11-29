package com.geekbrains.homework2.controllers;

import com.geekbrains.homework2.dto.OrderDto;
import com.geekbrains.homework2.entities.User;
import com.geekbrains.homework2.exceptions.ResourceNotFoundException;
import com.geekbrains.homework2.services.OrderService;
import com.geekbrains.homework2.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    public void createOrder(Principal principal, @RequestBody OrderDto orderDto) {
        // получить пользователя
        User user = userService.findByUsername(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
        // создать заказ
        orderService.createOrder(user, orderDto);
    }
}
