package com.sicredi.votacaoservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public Mono<ResponseEntity<StandardError>> entityNotFoundException(final EntityNotFoundException erro){

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(getStandardError(HttpStatus.NOT_FOUND.value(), "Entidade Não Encontrada",
                        Collections.singletonList(erro.getMessage()))));
    }

    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<StandardError>> businessException(final BusinessException erro){

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(getStandardError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação",
                        Collections.singletonList(erro.getMessage()))));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<StandardError>> methodArgumentNotValidException(final MethodArgumentNotValidException erro){

        List<String> errors = erro.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " - " + error.getDefaultMessage()).toList();

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(getStandardError(HttpStatus.BAD_REQUEST.value(),
                        "Erro de Validação",
                        errors)));
    }

    private StandardError getStandardError(Integer status, String tipoErro, List<String> mensagens){

        var erro = new StandardError();

        erro.setTimestamp(Instant.now());
        erro.setStatus(status);
        erro.setError(tipoErro);
        erro.setMessages(mensagens);

        return erro;
    }
}
