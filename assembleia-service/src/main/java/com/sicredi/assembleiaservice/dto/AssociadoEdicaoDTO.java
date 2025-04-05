package com.sicredi.assembleiaservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record AssociadoEdicaoDTO(
        @NotNull
        String nome,

        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,

        @CPF
        @NotNull
        String cpf
) {
}
