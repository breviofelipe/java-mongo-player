package com.brevio.java.controller.turmas;


import com.brevio.java.controller.post.feed.dto.LikeRequest;
import com.brevio.java.controller.turmas.dto.*;
import com.brevio.java.entity.games.memory.Memory;
import com.brevio.java.entity.turmas.Ator;
import com.brevio.java.entity.turmas.Turmas;
import com.brevio.java.repository.games.MemoryGameRepository;
import com.brevio.java.repository.games.PerguntasRepository;
import com.brevio.java.repository.turmas.EspetaculoRepository;
import com.brevio.java.repository.turmas.PersonagemRepository;
import com.brevio.java.repository.turmas.TurmasRepository;
import com.brevio.java.service.PostTurmaService;

import com.brevio.java.entity.turmas.Espetaculo;
import com.brevio.java.repository.turmas.AtorRepository;
import com.brevio.java.service.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Log
@Tag(name = "Turmas", description = "Dados turmas")
@CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH}, allowedHeaders = "*")
public class TurmasController {

    private final TurmasRepository turmasRepository;
    private final AtorRepository atorRepository;
    private final PersonagemRepository personagemRepository;
    private final EspetaculoRepository espetaculoRepository;
    private final PostTurmaService postTurmaService;
    private final MemoryGameRepository memoryGameRepository;

    private final PerguntasRepository perguntasRepository;
    private final JwtService jwtService;


    @GetMapping("/turmas")
    @CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.GET})
    public ResponseEntity<?> turmas() {
        log.info("Consultando turmas");

//        personagemRepository.save(Personagem.builder()
//                        .espetaculoId("650791ba82411e683d55da86")
//                        .nome("Guardas")
//                .build());
//        atorRepository.save(Ator.builder()
//                        .turmaId("PA 5")
//                        .nome("Lucca")
//                        .sobrenome("Strabelli")
//                .build());
        List<Turmas> all = turmasRepository.findAll();
        var response = all.stream().map(turma -> {
            log.info("Listando atores");
            List<Ator> atores = atorRepository.findByTurmaId(turma.getTurmaId(), Sort.by(Sort.Direction.DESC, "estrelas"));
            log.info("Consultando espetaculo");

            Espetaculo espetaculo = espetaculoRepository.findById(turma.getEspetaculo()).get();
//            espetaculo.setPersonagens(personagemRepository.findAll());
//            espetaculoRepository.save(espetaculo);
//            espetaculo.setApresentacoes(Arrays.asList(new Date("2023/11/27"), new Date("2023/11/28"), new Date("2023/11/29")));
//            espetaculoRepository.save(espetaculo);
            return TurmasResponse.builder()
                    .id(turma.getId())
                    .turmaId(turma.getTurmaId())
                    .espetaculo(espetaculo)
                    .atores(atores)
                    .imagemEspetaculo(turma.getImagemEspetaculo())
                    .build();
        });

        log.info("Retornando turmas");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/turmas/perguntas")
    public ResponseEntity<?> questions(@RequestHeader("Authorization") String authorization) {
        String user = jwtService.extractUserName(authorization.substring(7));
        log.info("Sorteando pergunta para o user " + user);
        var response = perguntasRepository.random(user).getMappedResults();

        log.info("Retornando pergunta para o user " + user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/turmas/perguntas")
    public ResponseEntity<?> question(@RequestHeader("Authorization") String authorization, @RequestBody UpdateQuestionRequest request) {
        String user = jwtService.extractUserName(authorization.substring(7));
        Optional<Ator> atorOptional = atorRepository.findById(request.getUserId());
        log.info("Salvando resposta para o user " + user);
        atorOptional.ifPresent(ator -> {
            var response = perguntasRepository.findById(request.getId());
            if (response.isPresent()) {
                if (response.get().getRespondidoPor() == null) {
                    response.get().setRespondidoPor(Arrays.asList(user));
                } else {
                    response.get().getRespondidoPor().add(user);
                }
                perguntasRepository.save(response.get());
            }
            if (ator.getPerguntas() == null) {
                ator.setPerguntas(new HashMap<>());
            }
            ator.getPerguntas().put(response.get().getId(), request.isAcertou());
            atorRepository.save(ator);
        });

        var response = "";
        if(atorOptional.isPresent()){
            Map<String, Boolean> perguntas = atorOptional.get().getPerguntas();
            Map<String, Boolean> collected = perguntas.entrySet().stream().filter(pergunta -> pergunta.getValue().equals(Boolean.TRUE)).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
            response = "Acertou: "+collected.size()+"/"+ perguntas.size();
        }

        log.info("Resposta salva para o user " + user);
        return ResponseEntity.ok(QuestionResponse.builder().message(response).build());
    }

    @GetMapping("/turmas/{id}/games")
    public ResponseEntity<?> games(@PathVariable String id) {
//        List<DataGame> dataGame = new ArrayList<>();
//
//        dataGame.add(DataGame.builder()
//                .type("Ophelia")
//                .image("https://res.cloudinary.com/dosghtja7/image/upload/v1696729879/assets/games/memory_hamlet/i7yrbql89kdkpi1oexvc.jpg")
//                .build());
//
//        dataGame.add(DataGame.builder()
//                .type("Claudio")
//                .image("https://res.cloudinary.com/dosghtja7/image/upload/v1696729957/assets/games/memory_hamlet/np33enatloufmyhdlf5d.jpg")
//                .build());
//
//        dataGame.add(DataGame.builder()
//                .type("Hamlet")
//                .image("https://res.cloudinary.com/dosghtja7/image/upload/v1696730329/assets/games/memory_hamlet/jhsebbneaulte7xjundj.jpg")
//                .build());
//
//        dataGame.add(DataGame.builder()
//                .type("Rainha")
//                .image("https://res.cloudinary.com/dosghtja7/image/upload/v1696730541/assets/games/memory_hamlet/a7dic5ruwagtzljjwvtm.jpg")
//                .build());
//
//        dataGame.add(DataGame.builder()
//                .type("Laerte")
//                .image("https://res.cloudinary.com/dosghtja7/image/upload/v1696730626/assets/games/memory_hamlet/cak0nclt0zrdhmjtwhsp.jpg")
//                .build());
//
//        dataGame.add(DataGame.builder()
//                .type("Polônio")
//                .image("https://res.cloudinary.com/dosghtja7/image/upload/v1696730728/assets/games/memory_hamlet/oyqxujvc5rddn3jqce9d.jpg")
//                .build());
//
//        Memory game = Memory.builder()
//                .turmaId(id)
//                .dataGame(dataGame)
//                .bestPlayer("")
//                .bestScore(0)
//                .build();
//
//        memoryGameRepository.save(game);
        var response = memoryGameRepository.findByTurmaId(id);
        return ResponseEntity.ok(response.get(0));
    }

    @PatchMapping("/turmas/{id}/games")
    public ResponseEntity<?> updateBestPlayer(@PathVariable String id, @RequestBody UpdatePLayerGame request) {
        Memory memory = memoryGameRepository.findByTurmaId(id).get(0);
        memory.setBestPlayer(request.getPlayer());
        memory.setBestScore(Integer.parseInt(request.getScore()));
        memoryGameRepository.save(memory);
        return ResponseEntity.ok(memory);

    }


    @PostMapping("/turmas/post")
    public ResponseEntity<?> createPost(@RequestBody CreatePostTurmaRequest request) {
        log.info("Criando novo post turma");
        var response = postTurmaService.createPost(request);
        log.info("Retornando posts turmas");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/turmas/{turmaId}/posts")
    public ResponseEntity<?> posts(@PathVariable String turmaId) {
        return postTurmaService.posts(turmaId);
    }

    @PatchMapping("/turmas/{id}/like")
    @CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.PATCH}, allowedHeaders = "*")
    public ResponseEntity likePost(@PathVariable String id, @RequestBody LikeRequest request) {
        log.info("PostTurmaController - like post " + id + " by userId " + request.getUserId());
        return postTurmaService.likePost(id, request.getUserId());
    }

    @DeleteMapping("/turmas/{id}/posts")
    @CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.PATCH}, allowedHeaders = "*")
    public ResponseEntity deletePost(@PathVariable String id) {
        log.info("DELETE - PostTurmaController - delete post " + id);
        return postTurmaService.deletePost(id);
    }

}