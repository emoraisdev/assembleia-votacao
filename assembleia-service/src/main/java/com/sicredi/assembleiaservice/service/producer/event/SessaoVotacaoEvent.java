package com.sicredi.assembleiaservice.service.producer.event;

import java.time.LocalDateTime;

public record SessaoVotacaoEvent(
        Long id,
        LocalDateTime inicioVotacao,
        LocalDateTime fimVotacao,
        LocalDateTime dataEvento,
        String sourceService
) {
}
