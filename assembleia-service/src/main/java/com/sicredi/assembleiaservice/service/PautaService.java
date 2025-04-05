package com.sicredi.assembleiaservice.service;

import com.sicredi.assembleiaservice.dto.PautaResponseDTO;
import com.sicredi.assembleiaservice.dto.SalvarPautaRequestDTO;
import com.sicredi.assembleiaservice.exception.EntityNotFoundException;
import com.sicredi.assembleiaservice.exception.ParameterNotFoundException;
import com.sicredi.assembleiaservice.repository.PautaRepository;
import com.sicredi.assembleiaservice.service.mapper.PautaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository repo;
    private final PautaMapper mapper;

    public PautaResponseDTO incluir(SalvarPautaRequestDTO pautaRequest) {

        var pauta = mapper.toEntity(pautaRequest);

        return mapper.toDTO(repo.save(pauta));
    }

    public PautaResponseDTO buscar(Long id) {

        if (id == null) {
            throw new ParameterNotFoundException("id");
        }

        var pauta = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pauta"));

        return mapper.toDTO(pauta);
    }
}
