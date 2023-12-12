package com.brevio.java.entity.games.memory;

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
@Document("MemoryGame")
public class Memory {

    @Id
    private String id;
    private String turmaId;
    private List<DataGame> dataGame;
    private int bestScore;
    private String bestPlayer;
}
