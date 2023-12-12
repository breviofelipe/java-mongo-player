package com.brevio.java.entity.games.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("QuestionGame")
public class Pergunta {

    @Id
    private String id;
    private String turmaId;
    private String pergunta;
    private String resposta;
    @JsonIgnore
    private List<String> respondidoPor;
}
