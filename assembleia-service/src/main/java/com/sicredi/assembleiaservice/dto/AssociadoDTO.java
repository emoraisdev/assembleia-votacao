package com.sicredi.assembleiaservice.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record AssociadoDTO(
        Long id,
        String nome,
        LocalDate dataNascimento,
        String cpf
) {
}
