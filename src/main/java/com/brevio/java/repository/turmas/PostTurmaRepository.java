package com.brevio.java.repository.turmas;

import com.brevio.java.entity.turmas.PostTurma;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostTurmaRepository extends MongoRepository<PostTurma, String> {

    @Query("{ 'turmaId' : '?0'}")
    List<PostTurma> findByTurmaId(String turmaId, Sort sort);
}
