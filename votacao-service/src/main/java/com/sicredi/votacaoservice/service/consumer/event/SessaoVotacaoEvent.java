package com.sicredi.votacaoservice.service.consumer.event;

import java.time.LocalDateTime;

public record SessaoVotacaoEvent(
        Long id,
        LocalDateTime inicioVotacao,
        LocalDateTime fimVotacao,
        LocalDateTime dataEvento,
        String sourceService
) {
}
