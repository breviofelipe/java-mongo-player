package com.brevio.java.service;


import com.brevio.java.model.dto.RegisterRequest;
import com.brevio.java.model.JwtAuthenticationResponse;
import com.brevio.java.model.SigninRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> signup(RegisterRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
