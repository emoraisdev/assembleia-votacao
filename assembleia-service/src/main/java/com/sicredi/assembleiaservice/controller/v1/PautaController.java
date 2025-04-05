package com.sicredi.assembleiaservice.controller.v1;

import com.sicredi.assembleiaservice.dto.PautaResponseDTO;
import com.sicredi.assembleiaservice.dto.SalvarPautaRequestDTO;
import com.sicredi.assembleiaservice.service.PautaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pautas")
@RequiredArgsConstructor
public class PautaController {

    private final PautaService service;

    @PostMapping
    public ResponseEntity<PautaResponseDTO> incluir(@RequestBody @Valid SalvarPautaRequestDTO pauta){

        var pautaSalva = service.incluir(pauta);

        return new ResponseEntity<>(pautaSalva, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaResponseDTO> buscar(@PathVariable Long id) {

        var pauta = service.buscar(id);
        return new ResponseEntity<>(pauta, HttpStatus.OK);
    }
}
