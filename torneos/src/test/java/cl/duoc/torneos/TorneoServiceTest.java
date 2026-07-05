package cl.duoc.torneos;
import cl.duoc.torneos.dto.TorneoDTO;
import cl.duoc.torneos.mapper.TorneoMapper;
import cl.duoc.torneos.model.Torneo;
import cl.duoc.torneos.model.Inscripcion;
import cl.duoc.torneos.repository.TorneoRepositoy;

import java.util.List;
import java.util.Optional;

import cl.duoc.torneos.service.TorneoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para TorneoService")
public class TorneoServiceTest {

    @Mock
    private TorneoRepositoy torRepositoy;

    @Mock
    private TorneoMapper torMapper;

    @InjectMocks
    private TorneoService torneoService;

    private Torneo torneoEntity;
    private TorneoDTO torneoDto;

    @BeforeEach
    public void setUp() {
        // FIX 2a: Creamos un objeto Inscripcion en lugar de pasar un Long numérico
        Inscripcion inscripcionMock = new Inscripcion();

        // Inicializamos la entidad de base de datos
        torneoEntity = new Torneo();
        torneoEntity.setIdTorneo(1L);
        torneoEntity.setIdInscripcion(inscripcionMock); // <-- Ahora pasamos el objeto
        torneoEntity.setNombreEvento("Torneo Liga Añil");
        torneoEntity.setMedallasMinimasRequeridas(8);
        torneoEntity.setEstadoConvocatoria("ABIERTA");

        // Inicializamos el DTO mapeado
        torneoDto = new TorneoDTO();
        torneoDto.setIdTorneo(1L);
        torneoDto.setNombreEvento("Torneo Liga Añil");
        torneoDto.setMedallasMinimasRequeridas(8);
        torneoDto.setEstadoConvocatoria("ABIERTA");
    }

    @Test
    @DisplayName("Debe retornar una lista con todos los torneos")
    public void findAll_deberiaRetornarListaDeEntidades() {
        // Arrange
        when(torRepositoy.findAll()).thenReturn(List.of(torneoEntity));

        // Act
        List<Torneo> resultado = torneoService.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Torneo Liga Añil", resultado.get(0).getNombreEvento());
        verify(torRepositoy, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar un torneo por ID y mapearlo a DTO exitosamente")
    public void findID_cuandoExiste_deberiaRetornarTorneoDTO() {
        // Arrange
        when(torRepositoy.findById(1L)).thenReturn(Optional.of(torneoEntity));
        when(torMapper.toDTO(torneoEntity)).thenReturn(torneoDto);

        // Act
        TorneoDTO resultado = torneoService.findID(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(torneoDto.getNombreEvento(), resultado.getNombreEvento());
        verify(torRepositoy, times(1)).findById(1L);
        verify(torMapper, times(1)).toDTO(torneoEntity);
    }

    @Test
    @DisplayName("Debe retornar null al buscar un ID que no existe")
    public void findID_cuandoNoExiste_deberiaRetornarNull() {
        // Arrange
        when(torRepositoy.findById(99L)).thenReturn(Optional.empty());
        // Ajuste en caso de que el mapper reciba null
        lenient().when(torMapper.toDTO(null)).thenReturn(null);

        // Act
        TorneoDTO resultado = torneoService.findID(99L);

        // Assert
        assertNull(resultado);
        verify(torRepositoy, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar un torneo en la base de datos de forma exitosa")
    public void save_deberiaGuardarYRetornarEntidad() {
        // Arrange
        when(torRepositoy.save(any(Torneo.class))).thenReturn(torneoEntity);

        // Act
        Torneo resultado = torneoService.save(torneoEntity);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdTorneo());
        verify(torRepositoy, times(1)).save(torneoEntity);
    }

    @Test
    @DisplayName("Debe ejecutar la eliminación de un torneo sin errores")
    public void delete_deberiaEliminarPorId() {
        // Arrange
        doNothing().when(torRepositoy).deleteById(1L);

        // Act
        torneoService.delete(1L);

        // Assert
        verify(torRepositoy, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe actualizar los datos de un torneo existente con éxito")
    public void update_cuandoExiste_deberiaActualizarYRetornarEntidad() {
        // Arrange
        // FIX 2b: Creamos otro objeto Inscripcion para la actualización
        Inscripcion inscripcionModificada = new Inscripcion();

        Torneo torneoModificado = new Torneo();
        torneoModificado.setIdTorneo(1L);
        torneoModificado.setIdInscripcion(inscripcionModificada); // <-- Pasamos el objeto
        torneoModificado.setNombreEvento("Torneo Kanto");
        torneoModificado.setMedallasMinimasRequeridas(4);
        torneoModificado.setEstadoConvocatoria("CERRADA");

        when(torRepositoy.findById(1L)).thenReturn(Optional.of(torneoEntity));
        when(torRepositoy.save(any(Torneo.class))).thenReturn(torneoModificado);

        // Act
        Torneo resultado = torneoService.update(1L, torneoModificado);

        // Assert
        assertNotNull(resultado);
        assertEquals("Torneo Kanto", resultado.getNombreEvento());
        assertEquals("CERRADA", resultado.getEstadoConvocatoria());
        verify(torRepositoy, times(1)).findById(1L);
        verify(torRepositoy, times(1)).save(any(Torneo.class));
    }

    @Test
    @DisplayName("Debe retornar null al intentar actualizar un torneo inexistente")
    public void update_cuandoNoExiste_deberiaRetornarNull() {
        // Arrange
        when(torRepositoy.findById(99L)).thenReturn(Optional.empty());

        // Act
        Torneo resultado = torneoService.update(99L, torneoEntity);

        // Assert
        assertNull(resultado);
        verify(torRepositoy, times(1)).findById(99L);
        verify(torRepositoy, never()).save(any(Torneo.class));
    }
}