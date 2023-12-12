package com.brevio.java.controller.turmas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreatePostTurmaRequest {

    private String file;
    private String userId;
    private String turmaId;
    private String description;
    private String link;
}
