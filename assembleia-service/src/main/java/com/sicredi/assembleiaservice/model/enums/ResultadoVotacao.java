package com.sicredi.assembleiaservice.model.enums;

public enum ResultadoVotacao {
    NAO_APROVADA(0,"Não Aprovada"),
    APROVADA(1, "Aprovada"),
    EMPATE(2, "Empate");

    private final int value;
    private final String descricao;

    ResultadoVotacao(int value, String descricao){
        this.value = value;
        this.descricao = descricao;
    }
}
