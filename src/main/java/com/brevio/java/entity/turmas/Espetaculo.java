package com.brevio.java.entity.turmas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("Espetaculo")
public class Espetaculo {

    @Id
    private String id;
    private String titulo;
    private List<Personagem> personagens;
    private List<Date> apresentacoes;
}
