package com.sicredi.assembleiaservice.service;

import com.sicredi.assembleiaservice.dto.PautaResponseDTO;
import com.sicredi.assembleiaservice.dto.SalvarPautaRequestDTO;
import com.sicredi.assembleiaservice.exception.EntityNotFoundException;
import com.sicredi.assembleiaservice.exception.ParameterNotFoundException;
import com.sicredi.assembleiaservice.model.Pauta;
import com.sicredi.assembleiaservice.repository.PautaRepository;
import com.sicredi.assembleiaservice.service.mapper.PautaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private PautaMapper pautaMapper;

    @InjectMocks
    private PautaService pautaService;

    private SalvarPautaRequestDTO pautaRequestDTO;
    private Pauta pauta;
    private PautaResponseDTO pautaDTO;
    private final Long ID_VALIDO = 1L;
    private final Long ID_INVALIDO = 999L;

    @BeforeEach
    void setUp() {
        pautaRequestDTO = new SalvarPautaRequestDTO(
                "Pauta de Teste",
                "Esta é uma pauta de teste"
        );

        pauta = new Pauta();
        pauta.setId(ID_VALIDO);
        pauta.setTitulo("Pauta de Teste");
        pauta.setDescricao("Esta é uma pauta de teste");

        pautaDTO = new PautaResponseDTO(
                ID_VALIDO,
                "Pauta de Teste",
                "Esta é uma pauta de teste"
        );
    }

    @Test
    @DisplayName("Deve incluir uma nova pauta com sucesso")
    void incluir_deveRetornarPautaDTO_quandoSucesso() {
        when(pautaMapper.toEntity(pautaRequestDTO)).thenReturn(pauta);
        when(pautaRepository.save(pauta)).thenReturn(pauta);
        when(pautaMapper.toDTO(pauta)).thenReturn(pautaDTO);

        PautaResponseDTO resultado = pautaService.incluir(pautaRequestDTO);

        assertNotNull(resultado);
        assertEquals(ID_VALIDO, resultado.id());
        assertEquals("Pauta de Teste", resultado.titulo());
        assertEquals("Esta é uma pauta de teste", resultado.descricao());

        verify(pautaMapper).toEntity(pautaRequestDTO);
        verify(pautaRepository).save(pauta);
        verify(pautaMapper).toDTO(pauta);
    }

    @Test
    @DisplayName("Deve buscar uma pauta existente")
    void buscar_deveRetornarPautaDTO_quandoEncontrado() {
        when(pautaRepository.findById(ID_VALIDO)).thenReturn(Optional.of(pauta));
        when(pautaMapper.toDTO(pauta)).thenReturn(pautaDTO);

        PautaResponseDTO resultado = pautaService.buscar(ID_VALIDO);

        assertNotNull(resultado);
        assertEquals(ID_VALIDO, resultado.id());
        assertEquals("Pauta de Teste", resultado.titulo());

        verify(pautaRepository).findById(ID_VALIDO);
        verify(pautaMapper).toDTO(pauta);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar com ID nulo")
    void buscar_deveLancarExcecao_quandoIdNulo() {
        assertThrows(ParameterNotFoundException.class,
                () -> pautaService.buscar(null),
                "Deveria lançar ParameterNotFoundException para ID nulo"
        );

        verify(pautaRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar pauta inexistente")
    void buscar_deveLancarExcecao_quandoNaoEncontrado() {
        when(pautaRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> pautaService.buscar(ID_INVALIDO),
                "Deveria lançar EntityNotFoundException para ID inválido"
        );

        verify(pautaRepository).findById(ID_INVALIDO);
        verify(pautaMapper, never()).toDTO(any());
    }
}