package com.sicredi.votacaoservice.service.consumer;

import com.sicredi.votacaoservice.model.AssociadoCache;
import com.sicredi.votacaoservice.service.AssociadoCacheService;
import com.sicredi.votacaoservice.service.consumer.event.AssociadoAtualizacaoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssociadoCacheConsumer {

    private final AssociadoCacheService service;

    @KafkaListener(topics = "associado-atualizacao",
            groupId = "votacao-group",
            containerFactory = "associadoKafkaListenerContainerFactory")
    public void consumirAtualizacao(AssociadoAtualizacaoEvent evento) {

        var associado = AssociadoCache.builder()
                .podeVotar(evento.podeVotar())
                .dataAtualizacao(evento.dataAtualizacao())
                .associadoId(evento.id()).build();

        service.salvarOuAtualizar(associado).subscribe(
                cache -> log.info("Cache salvo com sucesso: {}", cache),
                error -> log.error("Falha ao salvar cache", error)
        );;
    }
}
