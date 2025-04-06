package com.sicredi.assembleiaservice.service.mapper;

import com.sicredi.assembleiaservice.dto.SessaoVotacaoRequestDTO;
import com.sicredi.assembleiaservice.dto.SessaoVotacaoResponseDTO;
import com.sicredi.assembleiaservice.model.SessaoVotacao;
import org.springframework.stereotype.Component;

@Component
public class SessaoVotacaoMapper {

    public SessaoVotacao toEntity(SessaoVotacaoRequestDTO dto){
        return SessaoVotacao.builder()
                .inicioVotacao(dto.inicioVotacao())
                .fimVotacao(dto.fimVotacao())
                .build();
    }

    public SessaoVotacaoResponseDTO toDTO(SessaoVotacao sessaoVotacao) {
        return new SessaoVotacaoResponseDTO(
                sessaoVotacao.getId(),
                sessaoVotacao.getInicioVotacao(),
                sessaoVotacao.getFimVotacao(),
                sessaoVotacao.getSituacao(),
                sessaoVotacao.getQuantidadeSim(),
                sessaoVotacao.getQuantidadeNao(),
                sessaoVotacao.getResultado()
        );
    }
}
