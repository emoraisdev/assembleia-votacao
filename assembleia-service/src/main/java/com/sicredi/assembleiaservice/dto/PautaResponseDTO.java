package com.sicredi.assembleiaservice.dto;

import java.util.List;

public record PautaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        List<SessaoVotacaoResponseDTO> sessoesVotacao
) {
}
