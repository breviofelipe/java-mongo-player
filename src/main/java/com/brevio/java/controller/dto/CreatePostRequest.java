package com.brevio.java.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreatePostRequest {
    private String file;
    private String userId;
    private String description;
    private String picturePath;
}
