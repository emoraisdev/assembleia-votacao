package com.sicredi.assembleiaservice.service.integration.consultacpf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.assembleiaservice.dto.ResultadoVotacaoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ConsultaCPFAPI {

    @Value("${api.consulta-cpf-associado}")
    private String urlConsultaCPFAssociado;

    private static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
    private static final String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

    RestTemplate restTemplate;

    public ConsultaCPFAPI() {
        restTemplate = new RestTemplate();
    }

    public boolean consultarCPFHabilitadoVoto(String cpf) {

        try {
            var retorno = restTemplate.getForObject(urlConsultaCPFAssociado + cpf, String.class);

            if (ABLE_TO_VOTE.equals(retorno)) {
                return true;
            }

        } catch (Exception e) {
            log.error("Erro ao consultar CPF Associado. CPF: {}", cpf, e);
        }
        return false;
    }
}
