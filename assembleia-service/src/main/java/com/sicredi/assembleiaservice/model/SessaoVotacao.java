package com.sicredi.assembleiaservice.model;

import com.sicredi.assembleiaservice.model.enums.ResultadoVotacao;
import com.sicredi.assembleiaservice.model.enums.SituacaoVotacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SessaoVotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pauta_id", referencedColumnName = "id")
    private Pauta pauta;

    @Column(nullable = false)
    private LocalDateTime inicioVotacao;

    @Column(nullable = false)
    private LocalDateTime fimVotacao;

    private Long quantidadeSim;

    private Long quantidadeNao;

    private SituacaoVotacao situacao;

    private ResultadoVotacao resultado;
}
