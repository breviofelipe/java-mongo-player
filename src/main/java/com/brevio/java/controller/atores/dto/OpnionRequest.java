package com.brevio.java.controller.atores.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpnionRequest {
    private String userId;
    private String actorId;
    private long pontualidade;
    private long trabalhoEquipe;
    private long criatividade;
}
