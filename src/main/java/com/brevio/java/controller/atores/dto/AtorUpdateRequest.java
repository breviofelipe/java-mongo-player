package com.brevio.java.controller.atores.dto;

import lombok.Data;

@Data
public class AtorUpdateRequest {

    private String userId;
    private String turmaId;
    private String nome;
    private String sobrenome;
    private String userPicturePath;

}
