package com.sicredi.votacaoservice.service.event;

import java.time.LocalDateTime;

public record AssociadoAtualizacaoEvent(
        Long id,
        String nome,
        LocalDateTime dataAtualizacao,
        String sourceService
) {
}
