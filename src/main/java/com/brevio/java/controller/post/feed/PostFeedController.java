package com.brevio.java.controller.post.feed;


import com.brevio.java.controller.post.feed.dto.CreatePostRequest;
import com.brevio.java.controller.post.feed.dto.LikeRequest;
import com.brevio.java.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@AllArgsConstructor
@Tag(name = "Posts", description = "Feed posts")
@CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH}, allowedHeaders = "*")
@RequestMapping("/posts")
@Log
public class PostFeedController {

    private final PostService postService;
    @GetMapping
    public ResponseEntity<?> posts() {
        return postService.posts();
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<?> getUserPosts(@PathVariable String userId) {
        return postService.getUserPosts(userId);
    }

    @PostMapping
    @CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.POST}, allowedHeaders = "*")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest request) throws IOException {

        return postService.createPost(request);
    }

    @PatchMapping("/{id}/like")
    @CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.PATCH}, allowedHeaders = "*")
    public ResponseEntity likePost(@PathVariable String id, @RequestBody LikeRequest request) {
        log.info("PostFeedController - like post "+ id+" by userId "+request.getUserId());
        return postService.likePost(id, request.getUserId());
    }


}
