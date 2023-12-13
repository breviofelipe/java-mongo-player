package com.brevio.java.service;


import com.brevio.java.controller.dto.CreatePostRequest;
import org.springframework.http.ResponseEntity;

public interface PostService {

    ResponseEntity<?> createPost(CreatePostRequest request);

    ResponseEntity<?> getUserPosts(String userId);

    ResponseEntity<?> posts();

    ResponseEntity<?> likePost(String id, String userId);

}
