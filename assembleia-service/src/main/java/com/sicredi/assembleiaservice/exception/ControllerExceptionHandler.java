package com.sicredi.assembleiaservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFoundException(final EntityNotFoundException erro,
                                                                 final HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(getStandardError(HttpStatus.NOT_FOUND.value(), "Entidade Não Encontrada",
                        Collections.singletonList(erro.getMessage()),
                        request.getRequestURI()));
    }

    @ExceptionHandler(ParameterNotFoundException.class)
    public ResponseEntity<StandardError> parameterNotFoundException(final EntityNotFoundException erro,
                                                                    final HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(getStandardError(HttpStatus.BAD_REQUEST.value(), "Parâmetro Obrigatório",
                        Collections.singletonList(erro.getMessage()),
                        request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(final MethodArgumentNotValidException erro,
                                                                         final HttpServletRequest request){

        List<String> errors = erro.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " - " + error.getDefaultMessage()).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(getStandardError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação", errors,
                        request.getRequestURI()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> businessException(BusinessException erro, HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(getStandardError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação",
                        Collections.singletonList(erro.getMessage()), request.getRequestURI()));
    }

    private StandardError getStandardError(Integer status, String tipoErro, List<String> mensagens, String uri){

        var erro = new StandardError();

        erro.setTimestamp(Instant.now());
        erro.setStatus(status);
        erro.setError(tipoErro);
        erro.setMessages(mensagens);
        erro.setPath(uri);

        return erro;
    }
}
