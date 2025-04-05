package com.sicredi.votacaoservice.repository;

import com.sicredi.votacaoservice.model.AssociadoCache;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface AssociadoCacheRepository extends ReactiveMongoRepository<AssociadoCache, ObjectId> {

    Mono<AssociadoCache> findByAssociadoId(Long associadoId);
}
