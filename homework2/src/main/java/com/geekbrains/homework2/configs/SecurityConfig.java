package com.geekbrains.homework2.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// включить безопасность
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // источник данных
    private final JwtRequestFilter jwtRequestFilter;

    // прописываются end-point методов, доступ к которым будет ограничен ролями пользователей
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()

                // НАСТРОЙКА БЕЗОПАСНОСТИ
                // если кто-то отправил запрос по адресу "/api/v1/orders/**" (** - продолжение запроса),
                // то этот пользователь должен быть авторизован (роль значения не имеет)
                .antMatchers("/api/v1/orders/**").authenticated()
                // если кто-то отправил запрос по адресу "/api/v1/profile"
                // то этот пользователь должен быть авторизован (роль значения не имеет)
                .antMatchers("/api/v1/profile").authenticated()
                // запрос по адресу "/h2-console/**" (** - продолжение запроса) может быть доступен абсолютно всем
                .antMatchers("/h2-console/**").permitAll()
                // все остальные запросы могут быть доступны абсолютно всем
                .anyRequest().permitAll()

                .and()
                // для безопасности сессии не хранятся, они отключены
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                // обработка исключений
                .exceptionHandling()
                // если пользователь попытается получить доступ к незащищенной информации,
                // ему вылетит исключение о необходимости автоизации
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        // HttpSecurity http - зашита настройка безопасности и цепочка фильтров

        // jwtRequestFilter - подкинуть пользователя в контекст
        // преобразует входящий JSON-токен в пользователя, кот. находится в контексте

        // UsernamePasswordAuthenticationFilter.class - получает логин и пароль из входящих данных, собирает и проверяет токен
        // выполняет логику аутентификации
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // хэширует пароли
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager - сравнивает полученный токен от авторизации пользователя
    // с полями сущности, полученной  из БД, и, преобразованной в урезанную версию UserDetails
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
