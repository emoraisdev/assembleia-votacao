package com.sicredi.votacaoservice.service;

import com.sicredi.votacaoservice.exception.EntityNotFoundException;
import com.sicredi.votacaoservice.model.AssociadoCache;
import com.sicredi.votacaoservice.repository.AssociadoCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssociadoCacheService {

    private final AssociadoCacheRepository repo;

    /**
     * Salva ou atualiza um associado no cache
     */
    public Mono<AssociadoCache> salvarOuAtualizar(AssociadoCache associado){

        return repo.findByAssociadoId(associado.getAssociadoId())
                .flatMap(existente -> atualizarCache(existente, associado))
                .switchIfEmpty(Mono.defer(() -> criarNovoCache(associado)))
                .doOnError(e -> log.error("Erro em salvarOuAtualizar", e));
    }

    /**
     * Cria um novo registro de cache
     */
    public Mono<AssociadoCache> criarNovoCache(AssociadoCache associado) {
        log.info("Salvando Associado Cache: {}", associado.getAssociadoId());

        return repo.save(associado);
    }

    /**
     * Atualiza o cache com os dados do Associado.
     */
    private Mono<AssociadoCache> atualizarCache(AssociadoCache existente, AssociadoCache associadoAtualizado) {

        existente.setPodeVotar(associadoAtualizado.isPodeVotar());
        existente.setDataAtualizacao(associadoAtualizado.getDataAtualizacao());
        existente.setVersaoDados(existente.getVersaoDados() + 1);

        return repo.save(existente);
    }

    public Mono<AssociadoCache> buscarPorAssociadoId(Long associadoId){

        return repo.findByAssociadoId(associadoId)
                .switchIfEmpty(Mono.error(
                        new EntityNotFoundException("Associado")
                ));
    }
}
