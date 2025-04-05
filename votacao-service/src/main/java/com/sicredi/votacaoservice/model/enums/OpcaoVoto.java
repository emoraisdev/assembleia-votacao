package com.sicredi.votacaoservice.model.enums;

import lombok.Getter;

@Getter
public enum OpcaoVoto {
    NAO(0),
    SIM(1);

    private final int value;

    OpcaoVoto(int value){
        this.value = value;
    }

    public static OpcaoVoto fromValue(int value){
        for (OpcaoVoto status : OpcaoVoto.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor numérico inválido: " + value);
    }
}
