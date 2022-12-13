package com.geekbrains.market.winter7.core.test;

import com.geekbrains.market.winter7.api.ProductDto;
import com.geekbrains.market.winter7.core.converters.ProductConverter;
import com.geekbrains.market.winter7.core.entities.Category;
import com.geekbrains.market.winter7.core.entities.Product;
import com.geekbrains.market.winter7.core.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductConverter productConverter;

    @Test
    public void findProductByIdTest() throws Exception {

        Category category = new Category();
        category.setId(5L);
        category.setTitle("some");

        Product product = new Product();
        product.setId(100L);
        product.setTitle("Lemon");
        product.setPrice(150);
        product.setCategory(category);

        ProductDto productDto = new ProductDto();
        productDto.setId(100L);
        productDto.setTitle("Lemon");
        productDto.setPrice(150);
        productDto.setCategoryTitle("some");

        Mockito.doReturn(Optional.of(product)).when(productService).findProductById(100L);
        Mockito.doReturn(productDto).when(productConverter).entityToDto(product);

        mvc.perform(get("/api/v1/products/100")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.title").value("Lemon"))
                .andExpect(jsonPath("$.price").value(150));
    }

    @Test
    public void getAllProductsTest() throws Exception {

        Category category = new Category();
        category.setId(5L);
        category.setTitle("some");

        Product product1 = new Product();
        product1.setId(200L);
        product1.setTitle("Bread");
        product1.setPrice(100);
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setId(201L);
        product2.setTitle("Milk");
        product2.setPrice(200);
        product2.setCategory(category);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        Mockito.doReturn(products).when(productService).getAllProducts();

        mvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
