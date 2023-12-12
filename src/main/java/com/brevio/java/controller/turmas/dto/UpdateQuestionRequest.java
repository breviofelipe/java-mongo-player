package com.brevio.java.controller.turmas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

@Data
@NoArgsConstructor
public class UpdateQuestionRequest {

    private String id;
    private String userId;
    private boolean acertou;
}
