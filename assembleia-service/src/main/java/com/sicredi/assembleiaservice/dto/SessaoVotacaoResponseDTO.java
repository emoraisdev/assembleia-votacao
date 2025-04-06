package com.sicredi.assembleiaservice.dto;

import com.sicredi.assembleiaservice.model.enums.ResultadoVotacao;
import com.sicredi.assembleiaservice.model.enums.SituacaoVotacao;

import java.time.LocalDateTime;

public record SessaoVotacaoResponseDTO(
        Long id,
        LocalDateTime inicioVotacao,
        LocalDateTime fimVotacao,
        SituacaoVotacao situacao,
        Long quantidadeSim,
        Long quantidadeNao,
        ResultadoVotacao resultado
) {
}
