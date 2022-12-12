package gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    // секретный ключ
    // в yaml-файле есть строчка jwt.secret - ключ будет получен из этой строки
    @Value("${jwt.secret}")
    private String secret;

    // достать данные из входящего токена
    // Claims - список ключей и значений в токене
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                // чтобы токен смог сгенерировать подпись по ключу и сравнить с входящим
                .setSigningKey(secret)
                // вытащить данные из токена
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }
}
