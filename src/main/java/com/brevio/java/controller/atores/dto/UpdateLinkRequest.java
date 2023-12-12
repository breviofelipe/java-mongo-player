package com.brevio.java.controller.atores.dto;

import lombok.Data;

@Data
public class UpdateLinkRequest {

    private String link;
    private String userId;
    private String type;
}
