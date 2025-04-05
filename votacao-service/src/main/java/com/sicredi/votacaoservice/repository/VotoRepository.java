package com.sicredi.votacaoservice.repository;

import com.sicredi.votacaoservice.model.Voto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VotoRepository extends ReactiveMongoRepository<Voto, ObjectId> {
}
