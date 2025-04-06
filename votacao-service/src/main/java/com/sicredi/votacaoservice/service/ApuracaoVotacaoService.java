package com.sicredi.votacaoservice.service;

import com.sicredi.votacaoservice.dto.ResultadoVotacaoDTO;
import com.sicredi.votacaoservice.exception.BusinessException;
import com.sicredi.votacaoservice.model.enums.OpcaoVoto;
import com.sicredi.votacaoservice.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ApuracaoVotacaoService {

    private final VotoRepository votoRepository;
    private final SessaoVotacaoCacheService sessaoVotacaoService;


    /**
     * Realiza a contagem de votos por uma Sessão de Votação.
     *
     * @param sessaoId
     * @return
     */
    public Mono<ResultadoVotacaoDTO> verificarResultado(Long sessaoId) {

        return sessaoVotacaoService.buscarPorSessaoVotacaoId(sessaoId)
                .flatMap(Sessao -> {

                    return Mono.zip(
                            votoRepository.countBySessaoVotacaoIdAndOpcao(sessaoId, OpcaoVoto.SIM),
                            votoRepository.countBySessaoVotacaoIdAndOpcao(sessaoId, OpcaoVoto.NAO)
                    ).map(resultados -> {
                        Long votosSim = resultados.getT1(); // Quantidade de "SIM"
                        Long votosNao = resultados.getT2(); // Quantidade de "NÃO"

                        return new ResultadoVotacaoDTO(
                                sessaoId,
                                votosSim,
                                votosNao
                        );
                    });
                });
    }

}
