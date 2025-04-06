package com.sicredi.assembleiaservice.repository;

import com.sicredi.assembleiaservice.model.SessaoVotacao;
import com.sicredi.assembleiaservice.model.enums.SituacaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

    List<SessaoVotacao> findBySituacaoAndFimVotacaoLessThan(SituacaoVotacao situacao, LocalDateTime fimVotacao);

    List<SessaoVotacao> findBySituacao(SituacaoVotacao situacao);
}
