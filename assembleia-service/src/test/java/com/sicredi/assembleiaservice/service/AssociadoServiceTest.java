package com.sicredi.assembleiaservice.service;

import com.sicredi.assembleiaservice.dto.AssociadoResponseDTO;
import com.sicredi.assembleiaservice.dto.AssociadoRequestDTO;
import com.sicredi.assembleiaservice.dto.EdicaoAssociadoRequestDTO;
import com.sicredi.assembleiaservice.exception.EntityNotFoundException;
import com.sicredi.assembleiaservice.exception.ParameterNotFoundException;
import com.sicredi.assembleiaservice.model.Associado;
import com.sicredi.assembleiaservice.repository.AssociadoRepository;
import com.sicredi.assembleiaservice.service.mapper.AssociadoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssociadoServiceTest {

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private AssociadoMapper associadoMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private AssociadoService associadoService;

    private AssociadoRequestDTO associadoRequestDTO;
    private Associado associado;
    private AssociadoResponseDTO associadoDTO;
    private final Long ID_VALIDO = 1L;
    private final Long ID_INVALIDO = 999L;

    @BeforeEach
    void setUp() {
        associadoRequestDTO = new AssociadoRequestDTO(
                "Pedro",
                LocalDate.of(2000,10,15),
                "47556317005"
        );

        associado = new Associado();
        associado.setId(ID_VALIDO);
        associado.setNome("Pedro");
        associado.setCpf("47556317005");

        associadoDTO = new AssociadoResponseDTO(
                ID_VALIDO,
                "Pedro",
                LocalDate.of(2000,10,15),
                true,
                "47556317005"
        );
    }

    @Test
    @DisplayName("Deve incluir um novo associado com sucesso")
    void incluir_deveRetornarAssociadoDTO_quandoSucesso() {
        when(associadoMapper.toEntity(associadoRequestDTO)).thenReturn(associado);
        when(associadoRepository.save(associado)).thenReturn(associado);
        when(associadoMapper.toDTO(associado)).thenReturn(associadoDTO);

        AssociadoResponseDTO resultado = associadoService.incluir(associadoRequestDTO);

        assertNotNull(resultado);
        assertEquals(ID_VALIDO, resultado.id());
        assertEquals("Pedro", resultado.nome());

        verify(associadoMapper).toEntity(associadoRequestDTO);
        verify(associadoRepository).save(associado);
        verify(associadoMapper).toDTO(associado);
    }

    @Test
    @DisplayName("Deve buscar um associado existente")
    void buscar_deveRetornarAssociadoDTO_quandoEncontrado() {
        when(associadoRepository.findById(ID_VALIDO)).thenReturn(Optional.of(associado));
        when(associadoMapper.toDTO(associado)).thenReturn(associadoDTO);

        AssociadoResponseDTO resultado = associadoService.buscar(ID_VALIDO);

        assertNotNull(resultado);
        assertEquals(ID_VALIDO, resultado.id());

        verify(associadoRepository).findById(ID_VALIDO);
        verify(associadoMapper).toDTO(associado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar com ID nulo")
    void buscar_deveLancarExcecao_quandoIdNulo() {
        assertThrows(ParameterNotFoundException.class,
                () -> associadoService.buscar(null),
                "Deveria lançar ParameterNotFoundException para ID nulo"
        );

        verify(associadoRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar associado inexistente")
    void buscar_deveLancarExcecao_quandoNaoEncontrado() {
        when(associadoRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> associadoService.buscar(ID_INVALIDO),
                "Deveria lançar EntityNotFoundException para ID inválido"
        );

        verify(associadoRepository).findById(ID_INVALIDO);
        verify(associadoMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Deve alterar um associado existente")
    void alterar_deveRetornarAssociadoAtualizado_quandoSucesso() {

        var novaDataNascimento = LocalDate.of(2000,10,16);
        var novoNome = "Pedro Souza";
        var cpf = "47556317005";

        var associadoEdicaoDTO = new EdicaoAssociadoRequestDTO(
                novoNome,
                novaDataNascimento
        );

        var associadoAtualizado = Associado.builder()
                .id(ID_VALIDO)
                .nome(novoNome)
                .dataNascimento(novaDataNascimento)
                .cpf(cpf).build();

        var associadoRetornoDTO = new AssociadoResponseDTO(
                ID_VALIDO,
                novoNome,
                novaDataNascimento,
                true,
                cpf
        );

        when(associadoRepository.findById(ID_VALIDO)).thenReturn(Optional.of(associado));
        when(associadoRepository.save(associadoAtualizado)).thenReturn(associadoAtualizado);
        when(associadoMapper.toDTO(associadoAtualizado)).thenReturn(associadoRetornoDTO);

        AssociadoResponseDTO resultado = associadoService.alterar(ID_VALIDO, associadoEdicaoDTO);

        assertNotNull(resultado);
        assertEquals(ID_VALIDO, resultado.id());
        assertEquals(novoNome, resultado.nome());
        assertEquals(novaDataNascimento, resultado.dataNascimento());

        verify(associadoRepository).findById(ID_VALIDO);
        verify(associadoRepository).save(associadoAtualizado);
        verify(associadoMapper).toDTO(associadoAtualizado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao alterar com ID nulo")
    void alterar_deveLancarExcecao_quandoIdNulo() {
        assertThrows(ParameterNotFoundException.class,
                () -> associadoService.alterar(null, new EdicaoAssociadoRequestDTO(
                        "",
                        LocalDate.now()
                )),
                "Deveria lançar ParameterNotFoundException para ID nulo"
        );

        verify(associadoRepository, never()).findById(any());
        verify(associadoRepository, never()).save(any());
    }
}
