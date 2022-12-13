package com.geekbrains.market.winter7.core.test;

import com.geekbrains.market.winter7.api.CartDto;
import com.geekbrains.market.winter7.api.CartItemDto;
import com.geekbrains.market.winter7.core.entities.Category;
import com.geekbrains.market.winter7.core.entities.Order;
import com.geekbrains.market.winter7.core.entities.Product;
import com.geekbrains.market.winter7.core.integrations.CartServiceIntegration;
import com.geekbrains.market.winter7.core.repositories.OrderRepository;
import com.geekbrains.market.winter7.core.services.OrderService;
import com.geekbrains.market.winter7.core.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private CartServiceIntegration cartServiceIntegration;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void createOrderTest(){
        CartDto cartDto = new CartDto();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(100L);
        cartItemDto.setProductTitle("Lemon");
        cartItemDto.setPricePerProduct(150);
        cartItemDto.setQuantity(2);
        cartItemDto.setPrice(300);

        cartDto.setTotalPrice(300);
        cartDto.setItems(List.of(cartItemDto));

        Mockito.doReturn(cartDto).when(cartServiceIntegration).getCurrentCart();

        Category category = new Category();
        category.setId(5L);
        category.setTitle("some");

        Product product = new Product();
        product.setId(100L);
        product.setTitle("Lemon");
        product.setPrice(150);
        product.setCategory(category);

        Mockito.doReturn(Optional.of(product)).when(productService).findProductById(100L);

        Order order = orderService.createOrder("bob");
        Assertions.assertEquals(order.getTotalPrice(), 300);

        // при выполнении теста у orderRepository 1 раз был вызван метод save с любым аргументом (any())
        Mockito.verify(orderRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }
}
