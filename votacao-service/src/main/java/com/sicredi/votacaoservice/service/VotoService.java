package com.sicredi.votacaoservice.service;

import com.sicredi.votacaoservice.dto.VotoRequestDTO;
import com.sicredi.votacaoservice.exception.BusinessException;
import com.sicredi.votacaoservice.model.Voto;
import com.sicredi.votacaoservice.repository.VotoRepository;
import com.sicredi.votacaoservice.service.mapper.VotoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Slf4j
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository repo;
    private final SessaoVotacaoCacheService sessaoVotacaoService;
    private final AssociadoCacheService associadoSevice;
    private final VotoMapper mapper;
    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");

    public Mono<Voto> registrarVoto(VotoRequestDTO votoRequest){

        return associadoSevice.buscarPorAssociadoId(votoRequest.associadoId())
                .flatMap(associado -> {
                    if (!associado.isPodeVotar()){
                        return Mono.error(new BusinessException("O Associado não pode votar."));
                    }

                    return Mono.just(associado);
                })
                .flatMap(associado -> sessaoVotacaoService.buscarPorSessaoVotacaoId(votoRequest.sessaoVotacaoId()))
                .flatMap(sessao -> {

                    log.info("Verificando período de votação, Início :{}, Fim: {}, Hora Atual: {}", sessao.getInicioVotacao(), sessao.getFimVotacao(),
                            LocalDateTime.now());

                    if (sessao.getInicioVotacao().isAfter(LocalDateTime.now())) {
                        return Mono.error(new BusinessException("A Votação não foi iniciada."));
                    }

                    if (sessao.getFimVotacao().isBefore(LocalDateTime.now())) {
                        return Mono.error(new BusinessException("A Votação já foi encerrada."));
                    }

                    return Mono.just(sessao);
                })
                .flatMap(sessao -> repo.existsByAssociadoIdAndSessaoVotacaoId(votoRequest.associadoId(), votoRequest.sessaoVotacaoId()))
                .flatMap(jaVotou -> {

                    if (jaVotou) {
                        return Mono.error(new BusinessException("Voto já registrado para esta Sessão."));
                    }
                    return repo.save(mapper.toEntity(votoRequest));
                });
    }
}
