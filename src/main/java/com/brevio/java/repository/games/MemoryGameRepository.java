package com.brevio.java.repository.games;

import com.brevio.java.entity.games.memory.Memory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MemoryGameRepository extends MongoRepository<Memory, String> {

    @Query("{ 'turmaId' : '?0'}")
    List<Memory> findByTurmaId(String turmaId);
}
