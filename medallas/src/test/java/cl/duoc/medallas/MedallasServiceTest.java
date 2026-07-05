package cl.duoc.medallas;

// 1. Imports de tus propios modelos y DTOs
import cl.duoc.medallas.dto.EntrenadorDTO;
import cl.duoc.medallas.dto.MedallasDTO;
import cl.duoc.medallas.feign.EntrenadorFeing;
import cl.duoc.medallas.mapper.MedallasMapper;
import cl.duoc.medallas.model.Medalla;
import cl.duoc.medallas.repository.MedallasRepository;

// 2. Herramientas de Java y JUnit (Agrupadas con *)
import java.util.List;
import java.util.Optional;

import cl.duoc.medallas.service.MedallasService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

// 3. Herramientas de Mockito (Agrupadas con *)
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// 4. Assertions e Imports Estáticos
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para MedallasService")
public class MedallasServiceTest {

    @Mock
    private MedallasRepository medallasRepository;

    @Mock
    private EntrenadorFeing entrenadorFeing;

    @Mock
    private MedallasMapper medallasMapper;

    @InjectMocks
    private MedallasService medallasService;

    private Medalla medalla;
    private MedallasDTO medallaDTO;
    private EntrenadorDTO entrenadorDTO;

    @BeforeEach
    public void setUp() {
        // Inicializamos la entidad Medalla base
        medalla = new Medalla();
        medalla.setIdMedalla(1L);
        medalla.setNombreMedalla("Medalla Cascada");
        medalla.setEsOficial(true);
        medalla.setIdEntrenador("1");

        // Inicializamos el MedallasDTO base
        medallaDTO = new MedallasDTO();
        medallaDTO.setNombreMedalla("Medalla Cascada");
        medallaDTO.setEsOficial(true);
        medallaDTO.setNombreEntrenador("Ash Ketchum");
        medallaDTO.setRegionEntrenador("Kanto");

        // Inicializamos el EntrenadorDTO base para las llamadas Feign
        entrenadorDTO = new EntrenadorDTO();
        entrenadorDTO.setIdEntrenador(1L);
        entrenadorDTO.setNombreCompleto("Ash Ketchum");
        entrenadorDTO.setRegion("Kanto");
    }

    @Test
    @DisplayName("Debe listar todas las medallas correctamente")
    public void finndAll_deberiaRetornarListaDeMedallas() {
        // Arrange
        when(medallasRepository.findAll()).thenReturn(List.of(medalla));

        // Act
        List<Medalla> resultado = medallasService.finndAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Medalla Cascada", resultado.get(0).getNombreMedalla());
        verify(medallasRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar una medalla por ID cuando existe")
    public void findById_cuandoExiste_deberiaRetornarMedalla() {
        // Arrange
        when(medallasRepository.findById(1L)).thenReturn(Optional.of(medalla));

        // Act
        Medalla resultado = medallasService.findById(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdMedalla());
        verify(medallasRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar RuntimeException cuando la medalla no existe")
    public void findById_cuandoNoExiste_deberiaLanzarException() {
        // Arrange
        when(medallasRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> medallasService.findById(99L)
        );

        assertEquals("No se encontro la medalla con id: 99", exception.getMessage());
        verify(medallasRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar una medalla correctamente")
    public void save_deberiaCrearMedalla() {
        // Arrange
        when(medallasRepository.save(any(Medalla.class))).thenReturn(medalla);
        when(medallasMapper.toDTO(medalla)).thenReturn(medallaDTO);

        // Act
        MedallasDTO resultado = medallasService.save(medallaDTO, "1");

        // Assert
        assertNotNull(resultado);
        assertEquals("Medalla Cascada", resultado.getNombreMedalla());
        verify(medallasRepository).save(any(Medalla.class));
        verify(medallasMapper).toDTO(medalla);
    }

    @Test
    @DisplayName("Debe listar las medallas con el detalle del entrenador via Feign")
    public void listarDetallado_deberiaRetornarListaDetallada() {
        // Arrange
        when(medallasRepository.findAll()).thenReturn(List.of(medalla));
        when(medallasMapper.toDTO(medalla)).thenReturn(medallaDTO);
        when(entrenadorFeing.obtenerNombrePorId(1L)).thenReturn(entrenadorDTO);

        // Act
        List<MedallasDTO> resultado = medallasService.listarDetallado();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ash Ketchum", resultado.get(0).getNombreEntrenador());
        verify(medallasRepository).findAll();
        verify(entrenadorFeing).obtenerNombrePorId(1L);
    }

    @Test
    @DisplayName("Debe eliminar una medalla si existe por ID")
    public void delete_cuandoExiste_deberiaEliminar() {
        // Arrange
        when(medallasRepository.findById(1L)).thenReturn(Optional.of(medalla));

        // Act
        medallasService.delete(1L);

        // Assert
        verify(medallasRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar RuntimeException al eliminar si la medalla no existe")
    public void delete_cuandoNoExiste_deberiaLanzarException() {
        // Arrange
        when(medallasRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> medallasService.delete(99L)
        );

        assertEquals("No se puede eliminar. No existe la medalla con id: 99", exception.getMessage());
        verify(medallasRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Debe actualizar una medalla correctamente")
    public void update_cuandoExiste_deberiaActualizar() {
        // Arrange
        Medalla medallaActualizada = new Medalla();
        medallaActualizada.setNombreMedalla("Medalla Trueno");
        medallaActualizada.setEsOficial(true);
        medallaActualizada.setIdEntrenador("1");

        when(medallasRepository.findById(1L)).thenReturn(Optional.of(medalla));
        when(medallasRepository.save(any(Medalla.class))).thenReturn(medalla);
        when(medallasMapper.toDTO(medalla)).thenReturn(medallaDTO);
        when(entrenadorFeing.obtenerNombrePorId(1L)).thenReturn(entrenadorDTO);

        // Act
        MedallasDTO resultado = medallasService.update(1L, medallaActualizada);

        // Assert
        assertNotNull(resultado);
        verify(medallasRepository).save(any(Medalla.class));
        verify(entrenadorFeing).obtenerNombrePorId(1L);
    }
}