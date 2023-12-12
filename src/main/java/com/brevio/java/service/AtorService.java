package com.brevio.java.service;

import com.brevio.java.controller.atores.dto.UpdateProfileRequest;
import com.brevio.java.entity.turmas.Ator;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AtorService {

    void updateOpnionsActor(Ator ator);

    ResponseEntity<?> updateProfilePicture(UpdateProfileRequest request) throws IOException;
}
