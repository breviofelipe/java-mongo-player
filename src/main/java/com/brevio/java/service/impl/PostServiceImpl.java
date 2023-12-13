package com.brevio.java.service.impl;


import com.brevio.java.controller.dto.CreatePostRequest;
import com.brevio.java.entity.post.Post;
import com.brevio.java.entity.user.User;
import com.brevio.java.repository.post.feed.PostFeedRepository;
import com.brevio.java.repository.user.UserRepository;
import com.brevio.java.service.CloudinaryService;
import com.brevio.java.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

@Service
@AllArgsConstructor
@Log
public class PostServiceImpl implements PostService {

    private final PostFeedRepository repository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public ResponseEntity<?> createPost(CreatePostRequest request) {
        User user = userRepository.findById(request.getUserId()).get();
        if (request.getFile() != null && !request.getFile().isBlank()) {
            String fileBase64 = request.getFile().split(",")[1];
            byte[] decode = Base64.getDecoder().decode(fileBase64);

            cloudinaryService.upload(decode, uploaded -> {
                if(uploaded!=null){
                    log.info("Salvando novo post com imagem");
                    String picturePath = (String) uploaded.get("secure_url");
                    String urlPicturePath = (String) uploaded.get("public_id");
                    Post post = Post.builder()
                            .userId(request.getUserId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .location(user.getLocation())
                            .description(request.getDescription())
                            .userPicturePath(user.getPicturePath())
                            .picturePath(picturePath)
                            .likes(new HashMap<>())
                            .comments(new ArrayList<>())
                            .urlPicturePath(urlPicturePath)
                            .build();
                    repository.save(post);
                } else {
                    log.warning("New post not saved!");
                }
            }, "posts");
            var response = repository.findAll();
            return ResponseEntity.ok(response);
        } else {
            Post post = Post.builder()
                    .userId(request.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .location(user.getLocation())
                    .description(request.getDescription())
                    .userPicturePath(user.getPicturePath())
                    .likes(new HashMap<>())
                    .comments(new ArrayList<>())
                    .build();
            repository.save(post);
            var response = repository.findAll();
            return ResponseEntity.ok(response);
        }
    }
    @Override
    public ResponseEntity<?> getUserPosts(String userId) {
        var response = repository.findByUserId(userId);
        if (response.isPresent()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
    @Override
    public ResponseEntity<?> posts() {
        log.info("Consultando posts");
        var response = repository.findAll();
        if (!response.isEmpty()) {
            log.info("retornando posts");
            return ResponseEntity.ok(response);
        } else {
            log.info("retornando posts");
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @Override
    public ResponseEntity<?> likePost(String id, String userId) {
        Post post = repository.findById(id).get();
        boolean isLiked = false;
        if(post.getLikes().size() > 0 ){
            isLiked = post.getLikes().get(userId);
        }
        if (isLiked) {
            post.getLikes().remove(userId);
        } else {
            post.getLikes().put(userId, true);
        }
        var response = repository.save(post);
        return ResponseEntity.ok(response);
    }
}
