package com.sicredi.votacaoservice.service.mapper;

import com.sicredi.votacaoservice.dto.VotoRequestDTO;
import com.sicredi.votacaoservice.dto.VotoResponseDTO;
import com.sicredi.votacaoservice.model.Voto;
import com.sicredi.votacaoservice.model.enums.OpcaoVoto;
import org.springframework.stereotype.Component;

@Component
public class VotoMapper {

    public Voto toEntity(VotoRequestDTO dto){
        return Voto.builder()
                .associadoId(dto.associadoId())
                .sessaoVotacaoId(dto.sessaoVotacaoId())
                .opcao(OpcaoVoto.fromValue(Integer.parseInt(dto.opcao())))
                .build();
    }

    public VotoResponseDTO toDTO(Voto voto){
        return new VotoResponseDTO(
                voto.getId().toString(),
                voto.getAssociadoId(),
                voto.getSessaoVotacaoId(),
                voto.getOpcao().getValue(),
                voto.getDataVoto()
        );
    }

}
