package com.sicredi.votacaoservice.model;

import com.sicredi.votacaoservice.model.enums.OpcaoVoto;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "votos")
public class Voto {

    @Id
    private ObjectId id;

    private Long associadoId;

    private OpcaoVoto opcao;

    private LocalDateTime dataVoto;
}
