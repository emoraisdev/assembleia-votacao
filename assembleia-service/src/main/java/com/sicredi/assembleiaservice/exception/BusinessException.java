package com.sicredi.assembleiaservice.exception;

public class BusinessException extends RuntimeException{

    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
