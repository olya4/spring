package com.geekbrains.market.winter7.core.integrations;


import com.geekbrains.market.winter7.api.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    // получить корзинку, кот. находится в модуле cart
    public CartDto getCurrentCart() {
        // послать GET-запрос ProductService
        CartDto cartDto = cartServiceWebClient.get()
                // послать запрос на указанный end-point в модуль core классу CartController
                .uri("/api/v1/cart")
                // ожидается ответ
                .retrieve()
                // перобразовать тело ответа в CartDto
                .bodyToMono(CartDto.class)
//                // если запрос асинхронный то,когда придет ответ, сделать что-то:
//                .filter() или  .log()

                // синхронный запрос
                // дождаться ответа
                .block();
        return cartDto;
    }

    // получить корзинку, кот. находится в модуле cart
    public void clearCart() {
        // послать GET-запрос ProductService
        cartServiceWebClient.get()
                // послать запрос на указанный end-point в модуль core классу CartController
                .uri("/api/v1/cart/clear")
                // ожидается ответ
                .retrieve()
                // перобразовать тело ответа в ProductDto
                .toBodilessEntity()
                .block();
    }
}



