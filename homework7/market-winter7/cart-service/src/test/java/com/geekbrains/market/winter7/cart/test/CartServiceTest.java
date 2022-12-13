package com.geekbrains.market.winter7.cart.test;

import com.geekbrains.market.winter7.api.ProductDto;
import com.geekbrains.market.winter7.cart.integrations.ProductServiceIntegration;
import com.geekbrains.market.winter7.cart.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @Test
    public void addToCartTest() {

        ProductDto productDto = new ProductDto();
        productDto.setId(100L);
        productDto.setTitle("Lemon");
        productDto.setPrice(150);
        productDto.setCategoryTitle("some");


        Mockito.doReturn(productDto).when(productServiceIntegration).getProductById(100L);
        cartService.addProduct(100L);
        cartService.addProduct(100L);

        Assertions.assertEquals(1, cartService.getCurrentCart().getItems().size());
        Assertions.assertEquals(300, cartService.getCurrentCart().getTotalPrice());
    }
}
