package io.devai.tutorials.app.service;


import io.devai.tutorials.app.dao.UserRepository;
import io.devai.tutorials.app.dto.AuthResponse;
import io.devai.tutorials.app.dto.LoginRequest;
import io.devai.tutorials.app.dto.RegisterRequest;
import io.devai.tutorials.app.model.Role;
import io.devai.tutorials.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
    @Autowired
    JwtService jwtService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetails getUserDetails(String email) {
        User user = repository.findByEmail(email);

        List<SimpleGrantedAuthority> roles = user.getRoles().stream().map(s -> new SimpleGrantedAuthority(s.name())).filter(Objects::nonNull).collect(Collectors.toList());
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(roles)
                .build();
    }

    public AuthResponse login(LoginRequest request) throws LoginException {
        try {
            User user = repository.findByEmail(request.getEmail());
            if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new LoginException("Invalid credentials");
            }

            user.setAccessToken(jwtService.createToken(request.getEmail()));
            repository.save(user);

            AuthResponse response = new AuthResponse(user.getEmail(), user.getName(), user.getRoles(), user.getAccessToken(), null);
            return response;

        } catch (LoginException e) {
            throw e;

        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }

    public AuthResponse register(RegisterRequest request) throws LoginException {
        try {
            User existing = repository.findByEmail(request.getEmail());
            if (existing != null) {
                throw new LoginException("User with given email already exists");
            }

            Date now = new Date();
            String token = null;
            String hash = passwordEncoder.encode(request.getPassword());
            User user = new User(null, request.getEmail(), token, request.getName(), hash, Arrays.asList(Role.USER), now, now);
            if (user == null) {
                throw new LoginException("Invalid credentials");
            }

            user.setAccessToken(jwtService.createToken(request.getEmail()));
            repository.save(user);

            AuthResponse response = new AuthResponse(user.getEmail(), user.getName(), user.getRoles(), user.getAccessToken(), null);
            return response;

        } catch (LoginException e) {
            throw e;

        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }
}
