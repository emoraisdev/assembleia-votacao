package com.sicredi.assembleiaservice.service.producer.event;

import java.time.LocalDateTime;

public record AssociadoAtualizacaoEvent(
        Long id,
        String nome,
        LocalDateTime dataAtualizacao,
        String sourceService
) {
}
