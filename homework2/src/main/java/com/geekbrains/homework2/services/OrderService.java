package com.geekbrains.homework2.services;

import com.geekbrains.homework2.dto.Cart;
import com.geekbrains.homework2.dto.OrderDto;
import com.geekbrains.homework2.entities.Order;
import com.geekbrains.homework2.entities.OrderItem;
import com.geekbrains.homework2.entities.User;
import com.geekbrains.homework2.exceptions.ResourceNotFoundException;
import com.geekbrains.homework2.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    public void createOrder(User user, OrderDto orderDto) {
        Order order = new Order();
        // заполнить поля order
        order.setUser(user);
        // получить текущую корзинку
        Cart cart = cartService.getCurrentCart();
        List<OrderItem> items = cart.getItems()
                .stream()
                .map(orderItemDto -> {
                    OrderItem item = new OrderItem();
                    item.setProduct(productService.findProductById(orderItemDto.getProductId()).orElseThrow(() ->
                            new ResourceNotFoundException("Product not found")));
                    item.setOrder(order);
                    item.setQuantity(orderItemDto.getQuantity());
                    item.setPricePerPiece(orderItemDto.getPricePerPiece());
                    item.setPrice(orderItemDto.getPrice());
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        order.setTotalPrice(cart.getTotalPrice());
        order.setAddress(orderDto.getAddress());
        order.setTelephone(orderDto.getTelephone());

        // сохранить заказ
        orderRepository.save(order);
    }
}
