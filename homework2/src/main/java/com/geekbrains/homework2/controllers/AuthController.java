package com.geekbrains.homework2.controllers;



import com.geekbrains.homework2.dto.JwtRequest;
import com.geekbrains.homework2.dto.JwtResponse;
import com.geekbrains.homework2.exceptions.AppError;
import com.geekbrains.homework2.services.UserService;
import com.geekbrains.homework2.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    // элемент Spring Security
    private final AuthenticationManager authenticationManager;

    // для получения от пользователя логина и пароля, а взамен отдать токен доступа
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        // @RequestBody JwtRequest authRequest - в теле запроса ожидабтся логин и пароль
        try {
            // I проверка
            // проверяет корректны ли логин и пароль, кот. лежат в запросе
            // authenticationManage проверяет по логину и паролю есть ли такой пользователь
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }

        // II проверка
        // если логин и пароль корретны, найти в БД пользователя по имени и собрать его UserDetails (урезанная информация о пользователе)
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        // сформировать токен по UserDetails
        String token = jwtTokenUtil.generateToken(userDetails);
        // отправить сформированный токен клиенту
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
