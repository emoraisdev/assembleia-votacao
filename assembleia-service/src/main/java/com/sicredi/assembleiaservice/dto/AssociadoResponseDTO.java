package com.sicredi.assembleiaservice.dto;

import java.time.LocalDate;

public record AssociadoResponseDTO(
        Long id,
        String nome,
        LocalDate dataNascimento,
        boolean podeVotar,
        String cpf
) {
}
