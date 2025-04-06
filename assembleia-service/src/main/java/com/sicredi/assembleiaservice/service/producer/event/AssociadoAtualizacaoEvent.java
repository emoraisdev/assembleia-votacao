package com.sicredi.assembleiaservice.service.producer.event;

import java.time.LocalDateTime;

public record AssociadoAtualizacaoEvent(
        Long id,
        String nome,
        boolean podeVotar,
        LocalDateTime dataAtualizacao,
        String sourceService
) {
}
