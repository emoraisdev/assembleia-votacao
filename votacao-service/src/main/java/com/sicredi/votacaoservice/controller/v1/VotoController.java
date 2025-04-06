package com.sicredi.votacaoservice.controller.v1;

import com.sicredi.votacaoservice.dto.VotoRequestDTO;
import com.sicredi.votacaoservice.service.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votos")
public class VotoController {

    private final VotoService service;

    @PostMapping
    public Mono<ResponseEntity<String>> registrarVoto(
            @RequestBody VotoRequestDTO voto) {

        return service.registrarVoto(voto)
                            .map(v -> ResponseEntity
                                    .ok()
                                    .body("Voto registrado com sucesso"));

    }

}
