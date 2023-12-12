package com.brevio.java.repository.games;

import com.brevio.java.entity.games.question.Pergunta;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PerguntasRepository extends MongoRepository<Pergunta, String> {

    @Query("{ 'turmaId' : '?0'}")
    List<Pergunta> findByTurmaId(String turmaId);

    @Aggregation(pipeline={"{$sample:{size:1}}","{$match:{'respondidoPor' : {$ne: ['?0']}}}"})
    AggregationResults<Pergunta> random(String user);

}
