package com.brevio.java.repository.turmas;

import com.brevio.java.entity.turmas.Personagem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonagemRepository extends MongoRepository<Personagem, String> {
}
