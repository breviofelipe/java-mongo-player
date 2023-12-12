package com.brevio.java.entity.turmas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("Personagem")
public class Personagem {

    @Id
    private String id;
    private String espetaculoId;
    private String nome;
    private String actorId;
}
