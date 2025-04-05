package com.sicredi.assembleiaservice.dto;

import jakarta.validation.constraints.NotNull;

public record SalvarPautaRequestDTO(
        @NotNull
        String titulo,
        @NotNull
        String descricao
) {
}
