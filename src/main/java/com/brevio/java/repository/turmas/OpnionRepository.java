package com.brevio.java.repository.turmas;

import com.brevio.java.entity.turmas.Opnion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OpnionRepository extends MongoRepository<Opnion, String> {

    @Query("{actorId:'?0'}")
    Optional<List<Opnion>> findByActorId(String actorId);
}
