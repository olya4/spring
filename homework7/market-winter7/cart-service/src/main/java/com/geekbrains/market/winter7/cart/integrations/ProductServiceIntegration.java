package com.geekbrains.market.winter7.cart.integrations;


import com.geekbrains.market.winter7.api.ProductDto;
import com.geekbrains.market.winter7.api.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceWebClient;

    // получить dto-продукта по id из ProductService, кот. находится в модуле core
    public ProductDto getProductById(Long id) {
        // послать GET-запрос ProductService
        ProductDto productDto = productServiceWebClient.get()
                // послать запрос на указанный end-point в модуль core классу ProductController
                .uri("/api/v1/products/" + id)
                // ожидается ответ
                .retrieve()
                .onStatus(
                        // если пришел статус ответа 404
                        httStatus -> httStatus.value() == HttpStatus.NOT_FOUND.value(),
                        // кинуть исключение I вариант
                        clientResponse -> Mono.error(new ResourceNotFoundException("Product not found"))
                        // кинуть исключение II вариант
//                        clientResponse -> clientResponse.bodyToMono(AppError.class).map(ae -> new ResourceNotFoundException(ae.getMessage()))

                        // из clientResponse можно получать доп.информацию
                        // можно прописывать несколько статусов
                )
                // перобразовать тело ответа в ProductDto
                .bodyToMono(ProductDto.class)
//                // если запрос асинхронный то,когда придет ответ, сделать что-то:
//                .filter() или  .log()

                // синхронный запрос
                // дождаться ответа
                .block();
        return productDto;
    }
}



