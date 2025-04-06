package com.sicredi.assembleiaservice.service.producer.event;

import java.time.LocalDateTime;

public record ApurarVotacaoEvent(
        Long sessaoVotacaoId,
        LocalDateTime dataEvento,
        String sourceService
) {
}
