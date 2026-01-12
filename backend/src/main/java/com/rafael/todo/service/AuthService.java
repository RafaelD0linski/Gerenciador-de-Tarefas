package com.rafael.todo.service;

import com.rafael.todo.config.JwtUtil;
import com.rafael.todo.dto.JwtResponse;
import com.rafael.todo.dto.LoginRequest;
import com.rafael.todo.dto.SignupRequest;
import com.rafael.todo.entity.User;
import com.rafael.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public String signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username já existe!");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        userRepository.save(user);
        return "Usuário criado com sucesso!";
    }
    
    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha inválida!");
        }
        
        String token = jwtUtil.generateToken(user.getUsername());
        return new JwtResponse(token, user.getUsername());
    }
}
