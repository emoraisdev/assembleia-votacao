package com.sicredi.votacaoservice.dto;

public record ResultadoVotacaoDTO(
        Long sessaoVotacaoId,
        Long quantidadeSim,
        Long quantidadeNao
) {
}
