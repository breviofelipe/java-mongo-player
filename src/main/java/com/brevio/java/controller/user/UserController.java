package com.brevio.java.controller.user;

import com.brevio.java.controller.user.dto.FriendsResponse;
import com.brevio.java.repository.user.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@Log
@Tag(name = "Usuários", description = "Consultar dados dos usuários")
@CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT}, allowedHeaders = "*")
public class UserController {

    private final UserRepository repository;
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> user(@PathVariable String userId) {
        log.info("Consultando user");
        var response = repository.findById(userId);
        log.info("retornando user");
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/friends")
    public ResponseEntity<?> userFriends(@PathVariable String userId) {
        List<FriendsResponse> response = new ArrayList<>();
        log.info("Consultando friends");
        repository.findAll().stream().forEach(user -> {
            if(!user.getId().equals(userId)){
                response.add(FriendsResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .occupation(user.getOccupation())
                        .location(user.getLocation())
                        .picturePath(user.getPicturePath())
                        .build());
            }
        });
        log.info("retornando friends");
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
