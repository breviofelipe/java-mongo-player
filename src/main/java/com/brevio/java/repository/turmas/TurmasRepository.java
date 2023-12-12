package com.brevio.java.repository.turmas;

import com.brevio.java.entity.turmas.Turmas;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TurmasRepository extends MongoRepository<Turmas, String> {
}
