package com.brevio.java.entity.turmas;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("Ator")
public class Ator {

    @Id
    private String id;
    private String userId;
    private String turmaId;
    private String nome;
    private String sobrenome;
    private List<Personagem> personagens;
    private String userPicturePath;
    private long estrelas;
    private long pontualidade;
    private long trabalhoEquipe;
    private long criatividade;
    private List<String> opnions;
    private String linkTiktok;
    private String linkInstagram;
    private int bestScoreMemoryGame;
    @JsonIgnore
    private Map<String, Boolean> perguntas;


}
