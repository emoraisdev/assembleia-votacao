package com.sicredi.assembleiaservice.controller.v1;

import com.sicredi.assembleiaservice.dto.AssociadoResponseDTO;
import com.sicredi.assembleiaservice.dto.AssociadoRequestDTO;
import com.sicredi.assembleiaservice.dto.EdicaoAssociadoRequestDTO;
import com.sicredi.assembleiaservice.service.AssociadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/associados")
@RequiredArgsConstructor
public class AssociadoController {

    private final AssociadoService service;

    @PostMapping
    public ResponseEntity<AssociadoResponseDTO> incluir(@RequestBody @Valid AssociadoRequestDTO associado){

        var associadoSalvo = service.incluir(associado);

        return new ResponseEntity<>(associadoSalvo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscar(@PathVariable Long id) {

        var associado = service.buscar(id);
        return new ResponseEntity<>(associado, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@Valid @RequestBody EdicaoAssociadoRequestDTO associado, @PathVariable Long id) {

        var associadoAlterado = service.alterar(id, associado);
        return new ResponseEntity<>(associadoAlterado, HttpStatus.ACCEPTED);
    }
}
