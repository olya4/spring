package com.geekbrains.homework3.configs;




import com.geekbrains.homework3.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    // приходит JSON-токен, а выходит стандартный спринговый объект UsernamePasswordAuthenticationToken
    // фильтры работают с запросами(HttpServletRequest request) и ответами(HttpServletResponse response)
    // FilterChain filterChain - цепочка фильтров
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // токен подшивается в запрос в заголовок Authorization
        String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        // если в запросе есть заголовок Authorization
        // со слова Bearer начинается токен доступа
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // отпилить первые 7 символов (Bearer + пробел)
            jwt = authHeader.substring(7);
            try {
                // получить имя пользователя из токена
                // при получении любой информации из токена, проводится его валидация по времени жизни и по подписи
                username = jwtTokenUtil.getUsernameFromToken(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("The token is expired");
            }
        }

        // если имя пользователя удалось получить (токен валиден) и в контексте никого нет
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // II ВАРИАНТ: редкие обращения к БД, но информация о пользователе взята из JSON-токена
            // токен не может обновлять данные из БД.
            // В нем будут данные, кот. попали в момент его создания и будут там до конца его жизни

            // то формируется служебный объект UsernamePasswordAuthenticationToken
            // (имя пользователя из токена, очистить пароль, список ролей из токена)
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null,
                    jwtTokenUtil.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            // положить в контекст служебный объект UsernamePasswordAuthenticationToken
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        // пользователь добавлен в контекст и цепочка фильтров продолжает работать, не прерываясь
        filterChain.doFilter(request, response);
    }
}
