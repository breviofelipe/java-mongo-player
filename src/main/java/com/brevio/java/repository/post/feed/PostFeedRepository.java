package com.brevio.java.repository.post.feed;

import com.brevio.java.entity.post.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PostFeedRepository extends MongoRepository<Post, String> {

    @Query("{userId:'?0'}")
    Optional<Post> findByUserId(String userId);
}
