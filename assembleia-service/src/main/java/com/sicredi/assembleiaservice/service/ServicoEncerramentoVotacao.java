package com.sicredi.assembleiaservice.service;

import com.sicredi.assembleiaservice.exception.EntityNotFoundException;
import com.sicredi.assembleiaservice.service.integration.votacaoservice.VotacaoServiceAPI;
import com.sicredi.assembleiaservice.model.SessaoVotacao;
import com.sicredi.assembleiaservice.model.enums.ResultadoVotacao;
import com.sicredi.assembleiaservice.model.enums.SituacaoVotacao;
import com.sicredi.assembleiaservice.repository.SessaoVotacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicoEncerramentoVotacao {

    private final SessaoVotacaoRepository repo;
    private final VotacaoServiceAPI votacaoServiceAPI;

    @Value("${spring.application.name}")
    private String applicationName;

    @Scheduled(fixedRate = 60000) // Verifica a cada minuto
    public void checkSessoesVotacaoRegistradasFinalizadas() {

        log.info("Verificando Sessões de Votação Registradas para Análise.");

        LocalDateTime now = LocalDateTime.now();
        repo.findBySituacaoAndFimVotacaoLessThan(SituacaoVotacao.REGISTRADA, now)
                .forEach(session -> {
                    colocarSessaoEmAnalise(session.getId());
                });
    }

    private void colocarSessaoEmAnalise(Long id) {
        var sessao = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Sessão de Validação"));

        sessao.setSituacao(SituacaoVotacao.EM_ANALISE);
        repo.save(sessao);
    }

    @Scheduled(fixedRate = 60000) // Verifica a cada minuto
    public void encerrarSessoesVotacaoEmAnalise() {

        log.info("Verificando Sessões de Votação Em Análise para Encerramento.");

        LocalDateTime now = LocalDateTime.now();
        repo.findBySituacao(SituacaoVotacao.EM_ANALISE)
                .forEach(this::verificarResultado);
    }

    private void verificarResultado(SessaoVotacao sessao){

        var resultado =  votacaoServiceAPI.consultarResultadoVotacao(sessao.getId());

        if (resultado != null) {

            int comparador = resultado.quantidadeSim().compareTo(resultado.quantidadeNao());

            if (comparador > 0) {
                sessao.setResultado(ResultadoVotacao.APROVADA);
            } else if (comparador < 0) {
                sessao.setResultado(ResultadoVotacao.NAO_APROVADA);
            } else {
                sessao.setResultado(ResultadoVotacao.EMPATE);
            }

            sessao.setQuantidadeSim(resultado.quantidadeSim());
            sessao.setQuantidadeNao(resultado.quantidadeNao());
            sessao.setSituacao(SituacaoVotacao.ENCERRADA);
            repo.save(sessao);
        }
    }
}
