package com.sicredi.assembleiaservice.service;

import com.sicredi.assembleiaservice.dto.PautaResponseDTO;
import com.sicredi.assembleiaservice.dto.SessaoVotacaoRequestDTO;
import com.sicredi.assembleiaservice.dto.SessaoVotacaoResponseDTO;
import com.sicredi.assembleiaservice.exception.BusinessException;
import com.sicredi.assembleiaservice.exception.EntityNotFoundException;
import com.sicredi.assembleiaservice.exception.ParameterNotFoundException;
import com.sicredi.assembleiaservice.model.Associado;
import com.sicredi.assembleiaservice.model.SessaoVotacao;
import com.sicredi.assembleiaservice.model.enums.SituacaoVotacao;
import com.sicredi.assembleiaservice.repository.SessaoVotacaoRepository;
import com.sicredi.assembleiaservice.service.mapper.SessaoVotacaoMapper;
import com.sicredi.assembleiaservice.service.producer.event.AssociadoAtualizacaoEvent;
import com.sicredi.assembleiaservice.service.producer.event.SessaoVotacaoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SessaoVotacaoService {

    private final SessaoVotacaoRepository repo;
    private final SessaoVotacaoMapper mapper;
    private final PautaService pautaService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${spring.application.name}")
    private String applicationName;

    public SessaoVotacaoResponseDTO incluir(SessaoVotacaoRequestDTO sessaoVotacaoRequest) {

        if (sessaoVotacaoRequest.inicioVotacao().isAfter(sessaoVotacaoRequest.fimVotacao())) {
            throw new BusinessException("O data de início da votação não pode ser posterior a data Fim.");
        }

        var pauta = pautaService.buscarEntidade(sessaoVotacaoRequest.pautaId());

        var sessaoVotacao = mapper.toEntity(sessaoVotacaoRequest);

        sessaoVotacao.setPauta(pauta);
        sessaoVotacao.setSituacao(SituacaoVotacao.REGISTRADA);

        // Regra de Negócio:
        // Caso o usuário não informe o fim da sessão ela terá o valor padrão de 1 minuto.
        if (sessaoVotacao.getFimVotacao() == null) {
            sessaoVotacao.setFimVotacao(sessaoVotacao.getInicioVotacao().plusMinutes(1));
        }
        repo.save(sessaoVotacao);

        publicarSessaoVotacao(sessaoVotacao);

        return mapper.toDTO(sessaoVotacao);
    }

    private void publicarSessaoVotacao(SessaoVotacao sessaoVotacao) {
        eventPublisher.publishEvent(
                new SessaoVotacaoEvent(
                        sessaoVotacao.getId(),
                        sessaoVotacao.getInicioVotacao(),
                        sessaoVotacao.getFimVotacao(),
                        LocalDateTime.now(),
                        applicationName
                )
        );
    }

    public SessaoVotacaoResponseDTO buscar(Long id) {

        if (id == null) {
            throw new ParameterNotFoundException("id");
        }

        var sessaoVotacao = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão de Votação"));

        return mapper.toDTO(sessaoVotacao);
    }
}
