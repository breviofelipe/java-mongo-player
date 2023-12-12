package com.brevio.java.entity.turmas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("PostTurma")
public class PostTurma {

    @Id
    private String id;
    private String userId;
    private String userName;
    private String turmaId;
    private String description;
    private String picturePath;
    private String youtubeEmbedId;
    private String driveEmbedId;
    private String userPicturePath;
    private Date createdAt;
    private Map<String, Boolean> likes;
}
