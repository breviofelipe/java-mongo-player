package com.brevio.java.repository.turmas;

import com.brevio.java.entity.turmas.Espetaculo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EspetaculoRepository extends MongoRepository<Espetaculo, String> {

}
