package com.geekbrains.homework6.core.services;

import com.geekbrains.homework6.api.CartDto;
import com.geekbrains.homework6.api.ResourceNotFoundException;
import com.geekbrains.homework6.core.entities.Order;
import com.geekbrains.homework6.core.entities.OrderItem;
import com.geekbrains.homework6.core.integrations.CartServiceIntegration;
import com.geekbrains.homework6.core.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
// для увязки бинов OrderService и OrderRepository
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartServiceIntegration cartServiceIntegration;

    // создать заказ
    @Transactional
    public void createOrder(String username) {
        // получить текущую корзинку
        CartDto cartDto = cartServiceIntegration.getCurrentCart();
        // создать заказ
        Order order = new Order();
        // заполнить заказ
        order.setUsername(username);
        List<OrderItem> currentItems = cartDto.getItems()
                .stream()
                .map(orderItemDto -> {
                    OrderItem item = new OrderItem(
                            productService.findProductById(orderItemDto.getProductId()).orElseThrow(() ->
                                    new ResourceNotFoundException("Product not found")),
                            order,
                            orderItemDto.getQuantity(),
                            orderItemDto.getPricePerProduct(),
                            orderItemDto.getPrice()
                    );
                    return item;
                }).collect(Collectors.toList());
        order.setItems(currentItems);
        order.setTotalPrice(cartDto.getTotalPrice());

        orderRepository.save(order);
        cartServiceIntegration.clearCart();

    }

}
