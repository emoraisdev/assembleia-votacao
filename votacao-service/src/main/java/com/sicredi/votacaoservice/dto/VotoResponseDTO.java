package com.sicredi.votacaoservice.dto;

import com.sicredi.votacaoservice.model.enums.OpcaoVoto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record VotoResponseDTO(
        String id,
        Long associadoId,
        Long sessaoVotacaoId,
        int opcao,
        LocalDateTime dataVoto
) {
}
