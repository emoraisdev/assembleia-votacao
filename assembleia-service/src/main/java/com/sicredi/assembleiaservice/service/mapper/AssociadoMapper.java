package com.sicredi.assembleiaservice.service.mapper;

import com.sicredi.assembleiaservice.dto.AssociadoDTO;
import com.sicredi.assembleiaservice.dto.AssociadoEdicaoDTO;
import com.sicredi.assembleiaservice.model.Associado;
import org.springframework.stereotype.Component;

@Component
public class AssociadoMapper {

    public Associado toEntity(AssociadoEdicaoDTO dto){

        return Associado.builder()
                .nome(dto.nome())
                .dataNascimento(dto.dataNascimento())
                .cpf(dto.cpf()).build();
    }

    public AssociadoDTO toDTO(Associado entity){

        return new AssociadoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDataNascimento(),
                entity.getCpf());
    }
}
