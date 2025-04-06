package com.sicredi.assembleiaservice.controller.v1;

import com.sicredi.assembleiaservice.dto.PautaRequestDTO;
import com.sicredi.assembleiaservice.dto.PautaResponseDTO;
import com.sicredi.assembleiaservice.dto.SessaoVotacaoRequestDTO;
import com.sicredi.assembleiaservice.dto.SessaoVotacaoResponseDTO;
import com.sicredi.assembleiaservice.service.SessaoVotacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sessao-votacao")
@RequiredArgsConstructor
public class SessaoVotacaoController {

    private final SessaoVotacaoService service;

    @PostMapping("/registrar")
    public ResponseEntity<SessaoVotacaoResponseDTO> incluir(@RequestBody @Valid SessaoVotacaoRequestDTO sessaoVotacao){

        var sessaoVotacaoSalva = service.incluir(sessaoVotacao);

        return new ResponseEntity<>(sessaoVotacaoSalva, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoVotacaoResponseDTO> buscar(@PathVariable Long id) {

        var sessaoVotacao = service.buscar(id);
        return new ResponseEntity<>(sessaoVotacao, HttpStatus.OK);
    }
}
