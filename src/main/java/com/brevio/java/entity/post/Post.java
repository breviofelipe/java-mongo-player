package com.brevio.java.entity.post;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("Post")
public class Post {

    @Id
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String location;
    private String description;
    private String picturePath;
    private String userPicturePath;
    private Map<String, Boolean> likes;
    private List<String> comments;
    private String urlPicturePath;
}
