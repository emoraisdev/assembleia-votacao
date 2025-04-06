package com.sicredi.assembleiaservice.service;

import com.sicredi.assembleiaservice.dto.PautaResponseDTO;
import com.sicredi.assembleiaservice.dto.PautaRequestDTO;
import com.sicredi.assembleiaservice.exception.EntityNotFoundException;
import com.sicredi.assembleiaservice.exception.ParameterNotFoundException;
import com.sicredi.assembleiaservice.model.Pauta;
import com.sicredi.assembleiaservice.repository.PautaRepository;
import com.sicredi.assembleiaservice.service.mapper.PautaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository repo;
    private final PautaMapper mapper;

    public PautaResponseDTO incluir(PautaRequestDTO pautaRequest) {

        var pauta = mapper.toEntity(pautaRequest);

        return mapper.toDTO(repo.save(pauta));
    }

    public PautaResponseDTO buscar(Long id) {

        if (id == null) {
            throw new ParameterNotFoundException("id");
        }

        var pauta = buscarEntidade(id);

        return mapper.toDTO(pauta);
    }

    public Pauta buscarEntidade(Long id) {

        if (id == null) {
            throw new ParameterNotFoundException("id");
        }

        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Pauta"));
    }
}
