package com.sicredi.assembleiaservice.controller.v1;

import com.sicredi.assembleiaservice.dto.AssociadoDTO;
import com.sicredi.assembleiaservice.dto.AssociadoEdicaoDTO;
import com.sicredi.assembleiaservice.exception.EntityNotFoundException;
import com.sicredi.assembleiaservice.model.Associado;
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
    public ResponseEntity<AssociadoDTO> incluir(@RequestBody @Valid AssociadoEdicaoDTO associado){

        var associadoSalvo = service.incluir(associado);

        return new ResponseEntity<>(associadoSalvo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscar(@PathVariable Long id) {

        var associado = service.buscar(id);
        return new ResponseEntity<>(associado, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@Valid @RequestBody AssociadoEdicaoDTO associado, @PathVariable Long id) {

        var associadoAlterado = service.alterar(id, associado);
        return new ResponseEntity<>(associadoAlterado, HttpStatus.ACCEPTED);
    }
}
