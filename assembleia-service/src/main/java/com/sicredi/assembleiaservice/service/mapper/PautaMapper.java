package com.sicredi.assembleiaservice.service.mapper;

import com.sicredi.assembleiaservice.dto.PautaResponseDTO;
import com.sicredi.assembleiaservice.dto.SalvarPautaRequestDTO;
import com.sicredi.assembleiaservice.model.Pauta;
import org.springframework.stereotype.Component;

@Component
public class PautaMapper {

    public Pauta toEntity(SalvarPautaRequestDTO dto){

        return Pauta.builder()
                .titulo(dto.titulo())
                .descricao(dto.descricao())
                .build();
    }

    public PautaResponseDTO toDTO(Pauta pauta) {
        return new PautaResponseDTO(
                pauta.getId(),
                pauta.getTitulo(),
                pauta.getDescricao()
        );
    }
}
