package com.geekbrains.market.winter7.auth.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// логика формирования токена
@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    // генерация токена
    public String generateToken(UserDetails userDetails) {
        // создать мапу
        Map<String, Object> claims = new HashMap<>();
        // получить у UserDetails список строк
        List<String> rolesList = userDetails.getAuthorities().stream()
                // каждую роль из списка и преобразовать в GrantedAuthority
                .map(GrantedAuthority::getAuthority)
                // собрать в список
                .collect(Collectors.toList());
        // положить в мапу под ключом "roles" полученный список ролей
        claims.put("roles", rolesList);

        // время создания токена
        Date issuedDate = new Date();
        // время окончания жизни токена
        // в папке resources есть файл secrets.properties, а в файле есть строчка jwt.lifetime, кот. указано время жизни токена
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime);
        // собрать токен
        return Jwts.builder()
                // список ролей
                .setClaims(claims)
                // имя пользователя
                .setSubject(userDetails.getUsername())
                // дата создания токена
                .setIssuedAt(issuedDate)
                // дата окончания жизни токена
                .setExpiration(expiredDate)
                // подпись токена (SignatureAlgorithm.HS256 - алгоритм кодирования, secret - секретный ключ)
                .signWith(SignatureAlgorithm.HS256, secret)
                // собрать токен
                .compact();
    }
}
