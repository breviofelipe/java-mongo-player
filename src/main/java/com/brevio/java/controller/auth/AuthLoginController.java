package com.brevio.java.controller.auth;


import com.brevio.java.controller.auth.dto.LoginRequest;
import com.brevio.java.model.JwtAuthenticationResponse;
import com.brevio.java.model.SigninRequest;
import com.brevio.java.repository.turmas.OpnionRepository;
import com.brevio.java.repository.turmas.PostTurmaRepository;
import com.brevio.java.service.AuthenticationService;
import com.brevio.java.controller.auth.dto.RegisterRequest;
//import com.heroku.java.repository.turmas.AtorRepository;
import com.brevio.java.repository.turmas.AtorRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Log
@Tag(name = "Autenticação", description = "Cadastro e login de usuários")
@CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT}, allowedHeaders = "*")
@RequestMapping("/auth")
public class AuthLoginController {


    private final AuthenticationService authenticationService;

    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody RegisterRequest request) {
        log.info("Criando user "+request.getEmail());
        var response = authenticationService.signup(request);
        return response;
    }


    @PostMapping("/login")
    @CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.POST})
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest request) {
        log.info("Validando email");
        var response = authenticationService.signin(SigninRequest.builder().email(request.getEmail()).password(request.getPassword()).build());
        log.info("email " + request.getEmail() + " validado com sucesso.");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));

    }
}
