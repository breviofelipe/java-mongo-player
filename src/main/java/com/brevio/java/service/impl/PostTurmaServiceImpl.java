package com.brevio.java.service.impl;

import com.brevio.java.repository.turmas.PostTurmaRepository;
import com.brevio.java.service.CloudinaryService;
import com.brevio.java.controller.turmas.dto.CreatePostTurmaRequest;
import com.brevio.java.entity.turmas.PostTurma;
import com.brevio.java.entity.user.User;
import com.brevio.java.repository.user.UserRepository;
import com.brevio.java.service.PostTurmaService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Log
public class PostTurmaServiceImpl implements PostTurmaService {

    private final CloudinaryService cloudinaryService;
    private final PostTurmaRepository repository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> createPost(CreatePostTurmaRequest request) {
        User user = userRepository.findById(request.getUserId()).get();
        if (request.getFile() != null && !request.getFile().isBlank()) {
            String fileBase64 = request.getFile().split(",")[1];
            byte[] decode = Base64.getDecoder().decode(fileBase64);

            cloudinaryService.upload(decode, uploaded -> {
                if (uploaded != null) {
                    log.info("Salving new post with image");
                    String picturePath = (String) uploaded.get("secure_url");
                    PostTurma post = PostTurma.builder()
                            .userId(request.getUserId())
                            .userName(user.getFirstName())
                            .turmaId(request.getTurmaId())
                            .description(request.getDescription())
                            .userPicturePath(user.getPicturePath())
                            .picturePath(picturePath)
                            .likes(new HashMap<>())
                            .createdAt(new Date())
                            .build();
                    repository.save(post);
                } else {
                    log.warning("New post not saved!");
                }
            }, "posts");

            return ResponseEntity.accepted().build();
        } else {
            PostTurma post = PostTurma.builder()
                    .userId(request.getUserId())
                    .userName(user.getFirstName())
                    .turmaId(request.getTurmaId())
                    .createdAt(new Date())
                    .description(request.getDescription())
                    .userPicturePath(user.getPicturePath())
                    .likes(new HashMap<>())
                    .build();
            if (!request.getLink().isBlank())
                getEmbedId(request, post);
            repository.save(post);
            return ResponseEntity.accepted().build();
        }
    }

    private void getEmbedId(CreatePostTurmaRequest request, PostTurma post) {
        log.info("new post with link");
        if (request.getLink().contains("drive.google.com")) {
            post.setDriveEmbedId(request.getLink().substring(32).split("/")[0]);
        } else {
           String regExp ="(?<=youtu.be/|watch\\\\?v=|/videos/|embed\\\\/)[^#\\\\&\\\\?]*";
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(request.getLink());
            if(matcher.find()){
//                post.setYoutubeEmbedId(request.getLink().substring(32).split("&")[0]);
                post.setYoutubeEmbedId(matcher.group(0));
            }



        }
    }

    @Override
    public ResponseEntity<?> getUserPosts(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<?> posts(String turmaId) {
        log.info("Consultando posts turma");
        var response = repository.findByTurmaId(turmaId, Sort.by(Sort.Direction.DESC, "createdAt"));
        if (!response.isEmpty()) {
            log.info("retornando posts turma");
            return ResponseEntity.ok(response);
        } else {
            log.info("retornando posts turma");
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @Override
    public ResponseEntity<?> likePost(String id, String userId) {
        PostTurma post = repository.findById(id).get();
        boolean isLiked = false;
        if (post.getLikes() == null){
            post.setLikes(new HashMap<>());
        }
        if(post.getLikes().size() > 0 ){
            isLiked = post.getLikes().getOrDefault(userId, false);
        }
        if (isLiked) {
            post.getLikes().remove(userId);
        } else {
            post.getLikes().put(userId, true);
        }
        var response = repository.save(post);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> deletePost(String id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
