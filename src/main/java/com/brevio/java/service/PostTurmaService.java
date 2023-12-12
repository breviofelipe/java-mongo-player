package com.brevio.java.service;

import com.brevio.java.controller.turmas.dto.CreatePostTurmaRequest;
import org.springframework.http.ResponseEntity;

public interface PostTurmaService {

    ResponseEntity<?> createPost(CreatePostTurmaRequest request);

    ResponseEntity<?> getUserPosts(String userId);

    ResponseEntity<?> posts(String turmaId);

    ResponseEntity<?> likePost(String id, String userId);
    ResponseEntity<?> deletePost(String id);
}
