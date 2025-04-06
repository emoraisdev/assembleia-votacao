package com.sicredi.votacaoservice.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "sessoes_votacao_cache")
public class SessaoVotacaoCache {

    @Id
    private ObjectId id;

    private Long sessaoVotacaoId;

    private LocalDateTime inicioVotacao;

    private LocalDateTime fimVotacao;

    private LocalDateTime dataCricao;
}
