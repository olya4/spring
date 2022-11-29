package com.geekbrains.homework2.utils;

import io.jsonwebtoken.Claims;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    // секретный ключ
    // в папке resources есть файл secrets.properties, а в файле есть строчка jwt.secret
    // jwt.secret - ключ будет получен из этой строки
    @Value("${jwt.secret}")
    private String secret;

    // время жизни токена
    // в папке resources есть файл secrets.properties, а в файле есть строчка jwt.lifetime
    // jwt.lifetime - время будет получено из этой строки
    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    // достать данные из входящего токена
    // Claims - список ключей и значений в токене
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                // чтобы токен смог сгенерировать подпись по ключу и сравнить с входящим
                .setSigningKey(secret)
                // вытащить данные из токена
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        // достать весь список ключей и значений из токена
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // вытащить из токена имя пользователя
    public String getUsernameFromToken(String token) {
        // из всего списка ключей и значений токена достать имя пользователя (Claims::getSubject)
        return getClaimFromToken(token, Claims::getSubject);
    }

    // вытащить из токена список ролей
    public List<String> getRoles(String token) {
        // из всего списка ключей и значений токена достать все поля "roles" и преобразовать результат в список
        return getClaimFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("roles", List.class));
    }

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
