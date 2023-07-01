package com.example.service;

import com.example.Service.UserService;
import com.example.entity.Token;
import com.example.interfaces.UserRepository;
import com.example.controller.AuthenticationRequest;
import com.example.controller.AuthenticationResponse;
import com.example.controller.RegisterRequest;
import com.example.repo.TokenRepository;
import lombok.RequiredArgsConstructor;
import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private  final TokenRepository tokenRepository;
    private final UserService userService;
    //TODO here can make a gateway
    public AuthenticationResponse register(RegisterRequest request) {
        var user = new User(request.getFirstname(),request.getLastname(),request.getEmail(),request.getUsername(), passwordEncoder.encode(request.getPassword()));
        repository.save(user);
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_USER"));

        var jwtToken = jwtService.generateToken(user,null);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        var user = repository.findByUsername(request.getUsername());
        if (user == null) {
            throw new Exception("User not found");
        } else {
            Set<String> roles = new HashSet<>(Set.of("ROLE_USER"));
            userService.addRoleToUser(user.getUsername(),roles.iterator().next());
            var jwtToken = jwtService.generateToken(user,roles);
            saveUserToken(jwtToken);
            jwtService.getAuthorities(jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
    }
    private void saveUserToken(String jwtToken) {
        var token = new Token(jwtToken);
        System.out.println(token);
        System.out.println(token.getToken().length());
        tokenRepository.save(token);
    }

}
