package com.brevio.java.service.impl;


import com.brevio.java.entity.user.Role;
import com.brevio.java.entity.wallet.Wallet;
import com.brevio.java.model.JwtAuthenticationResponse;
import com.brevio.java.model.SigninRequest;
import com.brevio.java.repository.wallet.WalletRepository;
import com.brevio.java.service.AuthenticationService;
import com.brevio.java.model.dto.RegisterRequest;
import com.brevio.java.entity.user.User;
import com.brevio.java.repository.user.UserRepository;
import com.brevio.java.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Log
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    private final WalletRepository walletRepository;

    @Override
    public ResponseEntity<?> signup(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isEmpty()) {
            var user = User.builder()
                    .__v(LocalDateTime.now().toString())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                    .friends(new ArrayList<>())
                    .medals(new ArrayList<>())
                    .posts(new ArrayList<>())
                    .missions(new ArrayList<>())
                    .groups(new ArrayList<>())
                    .role(Role.USER).build();
            log.info("Criando wallet");
            Wallet wallet = walletRepository.save(Wallet.builder()
                    .amount(100)
                    .build());
            log.info("Wallet criado com sucesso");
            user.setWallet(wallet.getId());
            log.info("Savando user");
            userRepository.save(user);
            var jwt = jwtService.generateToken(user);
            log.info("User " + request.getEmail() + " criado executado com sucesso.");
            return ResponseEntity.ok(JwtAuthenticationResponse.builder().user(user).token(jwt).build());
        } else {
            log.warning("Email já cadastrado.");
            return ResponseEntity.badRequest().body("Email já cadastrado.");
        }
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario ou senha invalido."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().user(user).token(jwt).build();
    }
}