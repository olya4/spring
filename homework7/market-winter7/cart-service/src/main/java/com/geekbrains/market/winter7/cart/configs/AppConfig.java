package com.geekbrains.market.winter7.cart.configs;

import com.geekbrains.market.winter7.cart.properties.ProductServiceIntegrationProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(
        ProductServiceIntegrationProperties.class
)
@RequiredArgsConstructor
public class AppConfig {

//    // поле в yaml-файле
//    @Value("${integrations.product-service.url}")
//    private String url;

    // бин со всеми настройками для ProductService из yaml-файла
    private final ProductServiceIntegrationProperties productServiceIntegrationProperties;

    // в pom вместо зависимости web нужна webflux для WebClient
    // для интеграции с микросервисом ProductService
    @Bean
    public WebClient productServiceWebClient() {
        // создать TcpClient
        TcpClient tcpClient = TcpClient
                .create()
                // настройка таймаута на подключение
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, productServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    // настройка таймаута на ожидание ответа от сервиса
                    connection.addHandlerLast(new ReadTimeoutHandler(productServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
                    // настройка таймаута на запрос сервису
                    connection.addHandlerLast(new WriteTimeoutHandler(productServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
                });
        return WebClient
                .builder()
                // куда будет подключаться WebClient для общения с ProductService
                .baseUrl(productServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
