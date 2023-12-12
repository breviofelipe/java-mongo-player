package com.brevio.java.service.impl;

import com.brevio.java.controller.atores.dto.UpdateProfileRequest;
import com.brevio.java.entity.turmas.Ator;
import com.brevio.java.entity.turmas.Opnion;
import com.brevio.java.repository.turmas.OpnionRepository;
import com.brevio.java.service.AtorService;
import com.brevio.java.service.CloudinaryService;
import com.brevio.java.repository.turmas.AtorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log
public class AtorServiceImpl implements AtorService {

    private final AtorRepository repository;
    private final OpnionRepository opnionRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    @Async
    public void updateOpnionsActor(Ator ator) {
        long pontualidade = 0;
        long trabalhoEquipe = 0;
        long criatividade = 0;

        repository.save(ator);
        Optional<List<Opnion>> byActorIdOpnions = opnionRepository.findByActorId(ator.getId());

        if (byActorIdOpnions.isPresent()) {
            List<Opnion> opnions = byActorIdOpnions.get();

            for (Opnion opnion : opnions) {
                pontualidade = pontualidade + opnion.getPontualidade();
                trabalhoEquipe = trabalhoEquipe + opnion.getTrabalhoEquipe();
                criatividade = criatividade + opnion.getCriatividade();
            }
            calculaMedia(pontualidade, trabalhoEquipe, criatividade, opnions.size(), ator);
        }

    }

    @Override
    public ResponseEntity<?> updateProfilePicture(UpdateProfileRequest request) throws IOException {
        Ator user = repository.findById(request.getUserId()).get();
        if (request.getFile() != null && !request.getFile().isBlank()) {
            String fileBase64 = request.getFile().split(",")[1];
            byte[] decode = Base64.getDecoder().decode(fileBase64);

            cloudinaryService.upload(decode, uploaded -> {
                if (uploaded != null) {
                    log.info("Salving new profile picture");
                    String picturePath = (String) uploaded.get("secure_url");
                    user.setUserPicturePath(picturePath);
                    repository.save(user);
                } else {
                    log.warning("Profile picture not saved!");
                }
            }, "profiles");
        }
        return ResponseEntity.ok(user);
    }

    private void calculaMedia(long pontualidade,
                              long trabalhoEquipe,
                              long criatividade,
                              long size, Ator ator) {

        ator.setPontualidade(pontualidade / size);
        ator.setTrabalhoEquipe(trabalhoEquipe / size);
        ator.setCriatividade(criatividade / size);
        long estrelas = (ator.getPontualidade() + ator.getTrabalhoEquipe() + ator.getCriatividade()) / 3;
        ator.setEstrelas(estrelas);
        repository.save(ator);
    }
}
