package com.sicredi.votacaoservice.service;

import com.sicredi.votacaoservice.exception.EntityNotFoundException;
import com.sicredi.votacaoservice.model.SessaoVotacaoCache;
import com.sicredi.votacaoservice.repository.SessaoVotacaoCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessaoVotacaoCacheService {

    private final SessaoVotacaoCacheRepository repo;

    public Mono<SessaoVotacaoCache> criar(SessaoVotacaoCache sessaoVotacao){
        log.info("Salvando Sessão Votação Cache: {}", sessaoVotacao.getSessaoVotacaoId());

        return repo.save(sessaoVotacao);
    }

    public Mono<SessaoVotacaoCache> buscarPorSessaoVotacaoId(Long sessaoId){

        return repo.findBySessaoVotacaoId(sessaoId)
                .switchIfEmpty(Mono.error(
                        new EntityNotFoundException("Sessão Votação")
                ));
    }
}
