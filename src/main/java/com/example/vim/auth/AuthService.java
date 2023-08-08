package com.example.vim.auth;

import com.example.vim.jwt.JwtService;
import com.example.vim.models.Role;
import com.example.vim.models.User;
import com.example.vim.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        User userD = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .role(userD.getRole())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .role(request.getRole())
                .build();
    }

    public AuthUserInfo usuarioInfo(String token){
        String username =  jwtService.getUsername(token);
        User userData = userRepository.findByUsername(username).orElseThrow();
        return new AuthUserInfo(userData.getId_usuario(), userData.getUsername(), userData.getFirstname(), userData.getLastname());

    }

    public String usuarioUsername(String token) {
        return jwtService.getUsername(token);
    }
}
