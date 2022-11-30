package com.geekbrains.homework3.controllers;

import com.geekbrains.homework3.dto.OrderDto;
import com.geekbrains.homework3.entities.User;
import com.geekbrains.homework3.exceptions.ResourceNotFoundException;
import com.geekbrains.homework3.services.OrderService;
import com.geekbrains.homework3.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
