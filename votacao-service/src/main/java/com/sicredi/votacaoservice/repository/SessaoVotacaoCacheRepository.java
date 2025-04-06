package com.sicredi.votacaoservice.repository;

import com.sicredi.votacaoservice.model.SessaoVotacaoCache;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SessaoVotacaoCacheRepository extends ReactiveMongoRepository<SessaoVotacaoCache, ObjectId> {

    Mono<SessaoVotacaoCache> findBySessaoVotacaoId(Long sessaoId);
}
