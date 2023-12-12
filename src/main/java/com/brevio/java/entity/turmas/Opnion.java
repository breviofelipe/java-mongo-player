package com.brevio.java.entity.turmas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("Opniao")
public class Opnion {

    @Id
    private String id;
    private String userId;
    private String actorId;
    private long pontualidade;
    private long trabalhoEquipe;
    private long criatividade;
    private Date createAt;
}
