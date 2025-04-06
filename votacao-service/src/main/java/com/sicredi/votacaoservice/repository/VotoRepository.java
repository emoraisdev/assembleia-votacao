package com.sicredi.votacaoservice.repository;

import com.sicredi.votacaoservice.model.Voto;
import com.sicredi.votacaoservice.model.enums.OpcaoVoto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface VotoRepository extends ReactiveMongoRepository<Voto, ObjectId> {

    Mono<Boolean> existsByAssociadoIdAndSessaoVotacaoId(Long associadoId, Long sessaoId);

    Mono<Long> countBySessaoVotacaoIdAndOpcao(Long sessaoId, OpcaoVoto opcao);
}
