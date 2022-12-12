package com.geekbrains.homework6.auth.controllers;


import com.geekbrains.homework6.api.AppError;
import com.geekbrains.homework6.api.JwtRequest;
import com.geekbrains.homework6.api.JwtResponse;
import com.geekbrains.homework6.auth.services.UserService;
import com.geekbrains.homework6.auth.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

// http://localhost:8188/winter-auth/auth

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")

public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    // получить токен доступа
    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            // авторизовать пользователя
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            // если авторизация прошла неудачно
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        // если авторизация прошла успешно
        // получить имя пользователя из бд
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        // сформировать токен по пользователю
        String token = jwtTokenUtil.generateToken(userDetails);
        // вернуть токен клиенту
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
