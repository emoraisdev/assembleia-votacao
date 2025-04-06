package com.sicredi.assembleiaservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SessaoVotacaoRequestDTO(
        @NotNull
        Long pautaId,
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime inicioVotacao,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime fimVotacao
) {
}
