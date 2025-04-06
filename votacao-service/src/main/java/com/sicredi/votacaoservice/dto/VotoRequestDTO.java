package com.sicredi.votacaoservice.dto;

import com.sicredi.votacaoservice.model.enums.OpcaoVoto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record VotoRequestDTO(
        @NotNull
        Long associadoId,

        @NotNull
        Long sessaoVotacaoId,

        @Pattern(regexp = "[0-1]", message = "Opção deve ser 0 ou 1")
        String opcao
) {
}
