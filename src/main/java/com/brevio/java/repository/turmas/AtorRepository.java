package com.brevio.java.repository.turmas;

import com.brevio.java.entity.turmas.Ator;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AtorRepository extends MongoRepository<Ator, String> {

    @Query("{ 'turmaId' : '?0'}")
    List<Ator> findByTurmaId(String turmaId, Sort sort);
    @Query("{ 'userId' : '?0'}")
    Optional<Ator> findByUserId(String userId);
}
