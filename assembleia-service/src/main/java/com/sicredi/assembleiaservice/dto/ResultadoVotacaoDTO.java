package com.sicredi.assembleiaservice.dto;

public record ResultadoVotacaoDTO(
        Long sessaoVotacaoId,
        Long quantidadeSim,
        Long quantidadeNao
) {
}
