package com.brevio.java.controller.atores;

import com.brevio.java.controller.atores.dto.OpnionRequest;
import com.brevio.java.controller.atores.dto.UpdateLinkRequest;
import com.brevio.java.controller.atores.dto.UpdateProfileRequest;
import com.brevio.java.entity.turmas.Ator;
import com.brevio.java.entity.turmas.Opnion;
import com.brevio.java.repository.turmas.OpnionRepository;
import com.brevio.java.repository.turmas.PersonagemRepository;
import com.brevio.java.controller.atores.dto.AtorUpdateRequest;
import com.brevio.java.entity.turmas.Personagem;
import com.brevio.java.repository.turmas.AtorRepository;
import com.brevio.java.service.AtorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "Atores", description = "Dados atores")
@CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH}, allowedHeaders = "*")
@RequestMapping("/actors")
@Log
public class AtoresController {

    private final AtorRepository repository;
    private final PersonagemRepository personagemRepository;
    private final OpnionRepository opnionRepository;
    private final AtorService service;

    @PostMapping("/opinion")
    public ResponseEntity<?> getOp(@RequestBody OpnionRequest request) {
        log.info("Nova opnion ator id " + request.getActorId());
        Optional<Ator> atorOptional = repository.findById(request.getActorId());
        atorOptional.ifPresent(ator -> {
            Opnion save = opnionRepository.save(Opnion.builder()
                    .actorId(request.getActorId())
                    .userId(request.getUserId())
                    .criatividade(request.getCriatividade())
                    .trabalhoEquipe(request.getTrabalhoEquipe())
                    .pontualidade(request.getPontualidade())
                    .createAt(new Date())
                    .build());
            if (ator.getOpnions() == null) {
                ator.setOpnions(new ArrayList<>());
            }
            ator.getOpnions().add(save.getUserId());
            service.updateOpnionsActor(ator);
        });
        return ResponseEntity.ok(atorOptional.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getActor(@PathVariable String id) {
        log.info("Consultado ator id " + id);
        var response = repository.findById(id);
        log.info("Retornando Ator "+response.get().getNome());
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{id}/{personagem}")
    public ResponseEntity<?> updateActorPersonagem(@PathVariable(value = "id") String id, @PathVariable(value = "personagem") String idPersonagem) {
        log.info("Consultado personagem id " + idPersonagem);
        Optional<Personagem> byId = personagemRepository.findById(idPersonagem);
        if (byId.isPresent()) {
            Optional<Ator> atorOptional = repository.findById(id);
            atorOptional.ifPresent(ator -> {
                if(ator.getPersonagens() == null){
                    ator.setPersonagens(new ArrayList<>());
                }
                ator.getPersonagens().add(byId.get());
                log.info("Salvado personagem actor id " + id);
                repository.save(ator);
            });
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/picture")
    public ResponseEntity<?> updateActorPicture(@RequestBody UpdateProfileRequest request) throws IOException {
        log.info("Atualizando profile ator id="+request.getUserId());
        return service.updateProfilePicture(request);
    }

    @PatchMapping("/link")
    public ResponseEntity<?> updateActorPicture(@RequestBody UpdateLinkRequest request) {
        log.info("Atualizando profile LINK ator id "+request.getUserId());
        Optional<Ator> optionalAtor = repository.findById(request.getUserId());
        optionalAtor.ifPresent(ator -> {
            if(request.getType().equals("TIKTOK")){
                log.info("TIKTOK");
                ator.setLinkTiktok(request.getLink());
            } else {
                log.info(request.getType());
                ator.setLinkInstagram(request.getLink());
            }
            repository.save(ator);
            log.info("LINK atualizado com sucesso");
        });
        return ResponseEntity.ok(optionalAtor);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> updateActorPersonagem(@PathVariable(value = "id") String id, @RequestBody AtorUpdateRequest request) {
        log.info("Atualizando dados actor id " + id);
        Optional<Ator> atorOptional = repository.findById(id);
        atorOptional.ifPresent(ator -> {

            ator.setUserId(request.getUserId());
            ator.setNome(request.getNome());
            ator.setSobrenome(request.getSobrenome());
            ator.setTurmaId(request.getTurmaId());
            ator.setUserPicturePath(request.getUserPicturePath());
            log.info("Salvado actor id " + id);
            repository.save(ator);
        });
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/{personagem}/remove")
    public ResponseEntity<?> updateActorPersonagemREmove(@PathVariable(value = "id") String id, @PathVariable(value = "personagem") String idPersonagem) {
        log.info("Removendo personagem id " + idPersonagem);
        Optional<Personagem> byId = personagemRepository.findById(idPersonagem);
        if (byId.isPresent()) {
            Optional<Ator> atorOptional = repository.findById(id);
            atorOptional.ifPresent(ator -> {
                ator.getPersonagens().remove(byId.get());
                log.info("Salvado personagem actor id " + id);
                repository.save(ator);
            });
        }
        return ResponseEntity.ok().build();
    }
}
