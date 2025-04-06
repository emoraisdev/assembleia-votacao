package com.sicredi.assembleiaservice.service.integration.votacaoservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.assembleiaservice.dto.ResultadoVotacaoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class VotacaoServiceAPI {

    @Value("${api.votacao-service.server}")
    private String urlVotacaoService;

    private static final String API_APURACAO_VOTACAO = "/api/v1/apuracao-votacao/";

    RestTemplate restTemplate;

    public VotacaoServiceAPI() {
        restTemplate = new RestTemplate();
    }

    public ResultadoVotacaoDTO consultarResultadoVotacao(Long sessaoId) {

        var retorno = restTemplate.getForObject(urlVotacaoService +
                        API_APURACAO_VOTACAO + sessaoId, String.class);

        try {
            return new ObjectMapper().readValue(retorno, ResultadoVotacaoDTO.class);
        } catch (Exception e) {
            log.error("Erro ao consultar resultado da Sessão de Votação. Id: {}", sessaoId, e);
        }

        return null;
    }
}
