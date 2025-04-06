package com.sicredi.assembleiaservice.model.enums;

public enum SituacaoVotacao {

    REGISTRADA(0),
    EM_ANALISE(1),
    ENCERRADA(2);

    private final int value;

    SituacaoVotacao(int value){
        this.value = value;
    }
}
