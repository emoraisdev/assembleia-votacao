package com.sicredi.assembleiaservice.service;

import com.sicredi.assembleiaservice.dto.AssociadoResponseDTO;
import com.sicredi.assembleiaservice.dto.AssociadoRequestDTO;
import com.sicredi.assembleiaservice.dto.EdicaoAssociadoRequestDTO;
import com.sicredi.assembleiaservice.exception.BusinessException;
import com.sicredi.assembleiaservice.exception.EntityNotFoundException;
import com.sicredi.assembleiaservice.exception.ParameterNotFoundException;
import com.sicredi.assembleiaservice.model.Associado;
import com.sicredi.assembleiaservice.repository.AssociadoRepository;
import com.sicredi.assembleiaservice.service.integration.consultacpf.ConsultaCPFAPI;
import com.sicredi.assembleiaservice.service.mapper.AssociadoMapper;
import com.sicredi.assembleiaservice.service.producer.event.AssociadoAtualizacaoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AssociadoService {

    private final AssociadoRepository repo;
    private final AssociadoMapper mapper;
    private final ApplicationEventPublisher eventPublisher;
    private final ConsultaCPFAPI consultaCPFAPI;

    @Value("${spring.application.name}")
    private String applicationName;

    public AssociadoResponseDTO incluir(AssociadoRequestDTO associadoDTO) {

        if (repo.findByCpf(associadoDTO.cpf()).isPresent()){
            throw new BusinessException("CPF Já cadastrado.");
        }

        var novoAssociado = mapper.toEntity(associadoDTO);

        // A API retorna um indicando que a aplcicação não existe.
        // novoAssociado.setPodeVotar(consultaCPFAPI.consultarCPFHabilitadoVoto(associadoDTO.cpf()));
        novoAssociado.setPodeVotar(true);

        repo.save(novoAssociado);

        publicarAtualizacao(novoAssociado);

        return mapper.toDTO(novoAssociado);
    }

    private void publicarAtualizacao(Associado novoAssociado) {
        eventPublisher.publishEvent(
                new AssociadoAtualizacaoEvent(
                        novoAssociado.getId(),
                        novoAssociado.getNome(),
                        novoAssociado.isPodeVotar(),
                        LocalDateTime.now(),
                        applicationName
                )
        );
    }

    public AssociadoResponseDTO buscar(Long id) {

        if (id == null) {
            throw new ParameterNotFoundException("id");
        }

        var associado = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Associado"));

        return mapper.toDTO(associado);
    }

    public AssociadoResponseDTO alterar(Long id, EdicaoAssociadoRequestDTO associadoDTO){

        if (id == null) {
            throw new ParameterNotFoundException("id");
        }

        // verifica a existencia do Associado.
        Associado entidadeExistente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Associado"));

        entidadeExistente.setNome(associadoDTO.nome());
        entidadeExistente.setDataNascimento(associadoDTO.dataNascimento());

        var associadoAlterado = repo.save(entidadeExistente);

        publicarAtualizacao(associadoAlterado);

        return mapper.toDTO(associadoAlterado);
    }
}
