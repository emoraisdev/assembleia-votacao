package com.sicredi.assembleiaservice.service;

import com.sicredi.assembleiaservice.dto.AssociadoDTO;
import com.sicredi.assembleiaservice.dto.AssociadoEdicaoDTO;
import com.sicredi.assembleiaservice.exception.EntityNotFoundException;
import com.sicredi.assembleiaservice.exception.ParameterNotFoundException;
import com.sicredi.assembleiaservice.model.Associado;
import com.sicredi.assembleiaservice.repository.AssociadoRepository;
import com.sicredi.assembleiaservice.service.mapper.AssociadoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssociadoService {

    private final AssociadoRepository repo;

    private final AssociadoMapper mapper;

    public AssociadoDTO incluir(AssociadoEdicaoDTO associadoDTO) {

        var associado = mapper.toEntity(associadoDTO);

        return mapper.toDTO(repo.save(associado));
    }

    public AssociadoDTO buscar(Long id) {

        if (id == null) {
            throw new ParameterNotFoundException("id");
        }

        var associado = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Associado"));

        return mapper.toDTO(associado);
    }

    public AssociadoDTO alterar(Long id, AssociadoEdicaoDTO associadoDTO){

        if (id == null) {
            throw new ParameterNotFoundException("id");
        }

        // verifica a existencia do Associado.
        Associado entidadeExistente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Associado"));

        entidadeExistente.setNome(associadoDTO.nome());
        entidadeExistente.setDataNascimento(associadoDTO.dataNascimento());

        return mapper.toDTO(repo.save(entidadeExistente));
    }
}
