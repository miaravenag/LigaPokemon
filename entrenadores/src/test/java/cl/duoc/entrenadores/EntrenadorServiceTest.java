package cl.duoc.entrenadores;

import cl.duoc.entrenadores.dto.EntrenadorDTO;
import cl.duoc.entrenadores.exception.RecursoNoEncontradoException;
import cl.duoc.entrenadores.mapper.EntrenadorMapper;
import cl.duoc.entrenadores.model.Entrenador;
import cl.duoc.entrenadores.repository.EntrenadorRepository;
import cl.duoc.entrenadores.service.EntrenadorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para EntrenadorService")
public class EntrenadorServiceTest {

    @Mock
    private EntrenadorRepository entrenadorRepository;

    @Mock
    private EntrenadorMapper entrenadorMapper;

    @InjectMocks
    private EntrenadorService entrenadorService;

    private Entrenador entrenador;
    private EntrenadorDTO entrenadorDTO;

    @BeforeEach
    public void setUp() {

        entrenador = new Entrenador(1L, "Ash", "Ketchum", "kanto", 10, null);

        entrenadorDTO = new EntrenadorDTO();
        entrenadorDTO.setId(1L);
        entrenadorDTO.setNombre("Ash");
        entrenadorDTO.setApellido("Ketchum");
        entrenadorDTO.setNombreCompleto("Ash Ketchum");
        entrenadorDTO.setRegion("kanto");
        entrenadorDTO.setNivel(10);
    }

    @Test
    @DisplayName("Debe listar todos los entrenadores correctamente")
    public void listarTodos_deberiaRetornarListaDeEntrenadores() {
        // Arrange
        when(entrenadorRepository.findAll()).thenReturn(List.of(entrenador));
        when(entrenadorMapper.toDTO(entrenador)).thenReturn(entrenadorDTO);

        // Act
        List<EntrenadorDTO> resultado = entrenadorService.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ash Ketchum", resultado.get(0).getNombreCompleto());

        verify(entrenadorRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar un entrenador por ID cuando existe")
    public void buscarPorId_cuandoExiste_deberiaRetornarEntrenador() {
        // Arrange
        when(entrenadorRepository.findById(1L)).thenReturn(Optional.of(entrenador));
        when(entrenadorMapper.toDTO(entrenador)).thenReturn(entrenadorDTO);

        // Act
        EntrenadorDTO resultado = entrenadorService.findById(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Ash", resultado.getNombre());

        verify(entrenadorRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar RecursoNoEncontradoException cuando el entrenador no existe")
    public void buscarPorId_cuandoNoExiste_deberiaLanzarException() {
        // Arrange
        when(entrenadorRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RecursoNoEncontradoException exception = assertThrows(
                RecursoNoEncontradoException.class,
                () -> entrenadorService.findById(99L)
        );

        assertEquals("Entrenador no encontrado con id: 99", exception.getMessage());
        verify(entrenadorRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe eliminar un entrenador por ID correctamente")
    public void eliminar_deberiaEliminarEntrenadorPorId() {
        // Arrange
        when(entrenadorRepository.existsById(1L)).thenReturn(true);

        // Act
        entrenadorService.delete(1L);

        // Assert
        verify(entrenadorRepository).deleteById(1L);
    }
}