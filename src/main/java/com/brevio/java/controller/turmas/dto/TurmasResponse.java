package com.brevio.java.controller.turmas.dto;

import com.brevio.java.entity.turmas.Ator;
import com.brevio.java.entity.turmas.Espetaculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurmasResponse {
    private String id;
    private String turmaId;
    private List<Ator> atores;
    private Espetaculo espetaculo;
    private String imagemEspetaculo;
}



