package com.sicredi.votacaoservice.controller.v1;

import com.sicredi.votacaoservice.dto.ResultadoVotacaoDTO;
import com.sicredi.votacaoservice.service.ApuracaoVotacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/apuracao-votacao")
public class ApuracaoVotacaoController {

    private final ApuracaoVotacaoService service;


    @GetMapping("/{id}")
    public Mono<ResponseEntity<ResultadoVotacaoDTO>> obterResultadoVotacao(
            @PathVariable Long id) {

        return service.verificarResultado(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
