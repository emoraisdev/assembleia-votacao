package com.sicredi.votacaoservice.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "associados_cache")
public class AssociadoCache {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private Long associadoId;

    private boolean podeVotar;

    private LocalDateTime dataAtualizacao;

    private int versaoDados;
}
