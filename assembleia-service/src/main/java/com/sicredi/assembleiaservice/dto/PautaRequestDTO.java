package com.sicredi.assembleiaservice.dto;

import jakarta.validation.constraints.NotNull;

public record PautaRequestDTO(
        @NotNull
        String titulo,
        @NotNull
        String descricao
) {
}
