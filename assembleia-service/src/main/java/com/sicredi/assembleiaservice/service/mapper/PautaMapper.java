package com.sicredi.assembleiaservice.service.mapper;

import com.sicredi.assembleiaservice.dto.PautaResponseDTO;
import com.sicredi.assembleiaservice.dto.PautaRequestDTO;
import com.sicredi.assembleiaservice.model.Pauta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PautaMapper {

    private final SessaoVotacaoMapper sessaoVotacaoMapper;

    public Pauta toEntity(PautaRequestDTO dto){

        return Pauta.builder()
                .titulo(dto.titulo())
                .descricao(dto.descricao())
                .build();
    }

    public PautaResponseDTO toDTO(Pauta pauta) {
        return new PautaResponseDTO(
                pauta.getId(),
                pauta.getTitulo(),
                pauta.getDescricao(),
                Optional.ofNullable(pauta.getSessoesVotacao()).orElse(Collections.emptyList())
                        .stream().map(sessaoVotacaoMapper::toDTO).toList()
        );
    }
}
