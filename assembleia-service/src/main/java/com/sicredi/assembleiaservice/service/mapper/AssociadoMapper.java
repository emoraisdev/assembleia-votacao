package com.sicredi.assembleiaservice.service.mapper;

import com.sicredi.assembleiaservice.dto.AssociadoResponseDTO;
import com.sicredi.assembleiaservice.dto.SalvarAssociadoRequestDTO;
import com.sicredi.assembleiaservice.model.Associado;
import org.springframework.stereotype.Component;

@Component
public class AssociadoMapper {

    public Associado toEntity(SalvarAssociadoRequestDTO dto){

        return Associado.builder()
                .nome(dto.nome())
                .dataNascimento(dto.dataNascimento())
                .cpf(dto.cpf()).build();
    }

    public AssociadoResponseDTO toDTO(Associado entity){

        return new AssociadoResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDataNascimento(),
                entity.getCpf());
    }
}
