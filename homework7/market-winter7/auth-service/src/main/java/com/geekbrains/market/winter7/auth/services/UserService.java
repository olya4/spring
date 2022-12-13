package com.geekbrains.market.winter7.auth.services;

import com.geekbrains.market.winter7.auth.entities.Role;
import com.geekbrains.market.winter7.auth.entities.User;
import com.geekbrains.market.winter7.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    // источник данных
    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // найти пользователя по username и преобразовать к UserDetails
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // найти пользователя в БД
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        // вернуть userdetails.User - это стандартный user Spring
        // преобразование собственного user в спрингового (userdetails.User):
        // user.getUsername(), user.getPassword() - передать стандартному user логин и пароль своего user из пакета Entities
        // mapRolesToAuthorities(user.getRoles()) - из преобразованного списка ролей в GrantedAuthority получить роли данного user
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    // GrantedAuthority - обертка над обычной строкой
    // GrantedAuthority - тип строк понятный для Spring
    // преобразование ролей из списка БД в GrantedAuthority
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        // взять каждую роль из списка и преобразовать в GrantedAuthority, затем вернуть список ролей с типом GrantedAuthority
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}