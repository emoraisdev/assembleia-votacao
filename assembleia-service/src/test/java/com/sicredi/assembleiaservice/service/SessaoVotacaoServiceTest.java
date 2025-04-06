package com.sicredi.assembleiaservice.service;

import com.sicredi.assembleiaservice.dto.SessaoVotacaoRequestDTO;
import com.sicredi.assembleiaservice.dto.SessaoVotacaoResponseDTO;
import com.sicredi.assembleiaservice.dto.PautaResponseDTO;
import com.sicredi.assembleiaservice.exception.EntityNotFoundException;
import com.sicredi.assembleiaservice.exception.ParameterNotFoundException;
import com.sicredi.assembleiaservice.model.SessaoVotacao;
import com.sicredi.assembleiaservice.model.Pauta;
import com.sicredi.assembleiaservice.model.enums.SituacaoVotacao;
import com.sicredi.assembleiaservice.repository.SessaoVotacaoRepository;
import com.sicredi.assembleiaservice.service.mapper.SessaoVotacaoMapper;
import com.sicredi.assembleiaservice.service.producer.event.SessaoVotacaoEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessaoVotacaoServiceTest {

    @Mock
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Mock
    private SessaoVotacaoMapper sessaoVotacaoMapper;

    @Mock
    private PautaService pautaService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private SessaoVotacaoService sessaoVotacaoService;

    private SessaoVotacaoRequestDTO sessaoVotacaoRequestDTO;
    private SessaoVotacao sessaoVotacao;
    private SessaoVotacaoResponseDTO sessaoVotacaoDTO;
    private Pauta pauta;
    private final Long ID_VALIDO = 1L;
    private final Long ID_INVALIDO = 999L;
    private final LocalDateTime NOW = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        sessaoVotacaoRequestDTO = new SessaoVotacaoRequestDTO(
                ID_VALIDO,
                NOW,
                NOW.plusMinutes(1)
        );

        pauta = new Pauta();
        pauta.setId(ID_VALIDO);
        pauta.setTitulo("Pauta de Teste");

        sessaoVotacao = new SessaoVotacao();
        sessaoVotacao.setId(ID_VALIDO);
        sessaoVotacao.setPauta(pauta);
        sessaoVotacao.setInicioVotacao(NOW);
        sessaoVotacao.setFimVotacao(NOW.plusMinutes(1));
        sessaoVotacao.setSituacao(SituacaoVotacao.REGISTRADA);

        sessaoVotacaoDTO = new SessaoVotacaoResponseDTO(
                ID_VALIDO,
                NOW,
                NOW.plusMinutes(1),
                SituacaoVotacao.REGISTRADA,
                0L,
                0L,
                null
        );
    }

    @Test
    @DisplayName("Deve incluir uma nova sessão de votação com sucesso")
    void incluir_deveRetornarSessaoVotacaoDTO_quandoSucesso() {
        when(pautaService.buscarEntidade(ID_VALIDO)).thenReturn(pauta);
        when(sessaoVotacaoMapper.toEntity(sessaoVotacaoRequestDTO)).thenReturn(sessaoVotacao);
        when(sessaoVotacaoRepository.save(sessaoVotacao)).thenReturn(sessaoVotacao);
        when(sessaoVotacaoMapper.toDTO(sessaoVotacao)).thenReturn(sessaoVotacaoDTO);

        SessaoVotacaoResponseDTO resultado = sessaoVotacaoService.incluir(sessaoVotacaoRequestDTO);

        assertNotNull(resultado);
        assertEquals(ID_VALIDO, resultado.id());
        assertEquals(NOW, resultado.inicioVotacao());
        assertEquals(SituacaoVotacao.REGISTRADA, resultado.situacao());

        verify(pautaService).buscarEntidade(ID_VALIDO);
        verify(sessaoVotacaoMapper).toEntity(sessaoVotacaoRequestDTO);
        verify(sessaoVotacaoRepository).save(sessaoVotacao);
        verify(sessaoVotacaoMapper).toDTO(sessaoVotacao);
        verify(eventPublisher).publishEvent(any(SessaoVotacaoEvent.class));
    }

    @Test
    @DisplayName("Deve incluir sessão com tempo padrão quando fim não informado")
    void incluir_deveUsarTempoPadrao_quandoFimNaoInformado() {
        SessaoVotacaoRequestDTO requestSemFim = new SessaoVotacaoRequestDTO(
                ID_VALIDO,
                NOW,
                null
        );

        SessaoVotacao sessaoComFimPadrao = new SessaoVotacao();
        sessaoComFimPadrao.setId(ID_VALIDO);
        sessaoComFimPadrao.setPauta(pauta);
        sessaoComFimPadrao.setInicioVotacao(NOW);
        sessaoComFimPadrao.setFimVotacao(NOW.plusMinutes(1));
        sessaoComFimPadrao.setSituacao(SituacaoVotacao.REGISTRADA);

        when(pautaService.buscarEntidade(ID_VALIDO)).thenReturn(pauta);
        when(sessaoVotacaoMapper.toEntity(requestSemFim)).thenReturn(sessaoComFimPadrao);
        when(sessaoVotacaoRepository.save(sessaoComFimPadrao)).thenReturn(sessaoComFimPadrao);
        when(sessaoVotacaoMapper.toDTO(sessaoComFimPadrao)).thenReturn(sessaoVotacaoDTO);

        SessaoVotacaoResponseDTO resultado = sessaoVotacaoService.incluir(requestSemFim);

        assertNotNull(resultado);
        assertEquals(NOW.plusMinutes(1), resultado.fimVotacao());

        verify(pautaService).buscarEntidade(ID_VALIDO);
        verify(sessaoVotacaoRepository).save(sessaoComFimPadrao);
    }

    @Test
    @DisplayName("Deve buscar uma sessão de votação existente")
    void buscar_deveRetornarSessaoVotacaoDTO_quandoEncontrado() {
        when(sessaoVotacaoRepository.findById(ID_VALIDO)).thenReturn(Optional.of(sessaoVotacao));
        when(sessaoVotacaoMapper.toDTO(sessaoVotacao)).thenReturn(sessaoVotacaoDTO);

        SessaoVotacaoResponseDTO resultado = sessaoVotacaoService.buscar(ID_VALIDO);

        assertNotNull(resultado);
        assertEquals(ID_VALIDO, resultado.id());
        assertEquals(NOW, resultado.inicioVotacao());

        verify(sessaoVotacaoRepository).findById(ID_VALIDO);
        verify(sessaoVotacaoMapper).toDTO(sessaoVotacao);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar com ID nulo")
    void buscar_deveLancarExcecao_quandoIdNulo() {
        assertThrows(ParameterNotFoundException.class,
                () -> sessaoVotacaoService.buscar(null),
                "Deveria lançar ParameterNotFoundException para ID nulo"
        );

        verify(sessaoVotacaoRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar sessão inexistente")
    void buscar_deveLancarExcecao_quandoNaoEncontrado() {
        when(sessaoVotacaoRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> sessaoVotacaoService.buscar(ID_INVALIDO),
                "Deveria lançar EntityNotFoundException para ID inválido"
        );

        verify(sessaoVotacaoRepository).findById(ID_INVALIDO);
        verify(sessaoVotacaoMapper, never()).toDTO(any());
    }
}