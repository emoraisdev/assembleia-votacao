package com.sicredi.assembleiaservice.exception;

public class ParameterNotFoundException extends RuntimeException{

    public ParameterNotFoundException(String param) {
        super("O Parâmetro %s é obrigatório.".formatted(param));
    }
}
