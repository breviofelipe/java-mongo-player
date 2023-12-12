package com.brevio.java.entity.turmas;


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
@Document("turmas")
public class Turmas {

    @Id
    private String id;
    private String turmaId;
    private List<String> atores;
    private String espetaculo;
    private String imagemEspetaculo;
    private List<String> posts;
}
