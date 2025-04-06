package com.sicredi.votacaoservice.service.consumer;

import com.sicredi.votacaoservice.model.SessaoVotacaoCache;
import com.sicredi.votacaoservice.service.SessaoVotacaoCacheService;
import com.sicredi.votacaoservice.service.consumer.event.SessaoVotacaoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessaoVotacaoCacheConsumer {

    private final SessaoVotacaoCacheService service;

    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");

    @KafkaListener(topics = "sessao-votacao",
            groupId = "votacao-group",
            containerFactory = "sessaoVotacaoKafkaListenerContainerFactory")
    public void consumirSessaoVotacao(SessaoVotacaoEvent evento) {

        var sessaoVotacao = SessaoVotacaoCache.builder()
                .sessaoVotacaoId(evento.id())
                .inicioVotacao(evento.inicioVotacao())
                .fimVotacao(evento.fimVotacao())
                .dataCricao(evento.dataEvento())
                .build();

        service.criar(sessaoVotacao).subscribe(
                cache -> log.info("Cache salvo com sucesso: {}", cache),
                error -> log.error("Falha ao salvar cache", error)
        );;
    }
}
