package cl.duoc.torneos;

// 1. Imports de tus propios modelos
import cl.duoc.torneos.controller.TorneoController;
import cl.duoc.torneos.dto.TorneoDTO;
import cl.duoc.torneos.model.Torneo;
import cl.duoc.torneos.service.TorneoService;

// 2. Herramientas de Java y Spring (Agrupadas con *)
import java.util.List;
import org.springframework.http.*;

// 3. Motor de Pruebas y Mockito (Agrupados con *)
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// 4. Imports Estáticos para aserciones limpias
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para TorneoController")
 public class TorneoControllerTest {

    @Mock
    private TorneoService torService;

    @InjectMocks
    private TorneoController torneoController;

    private Torneo torneo;
    private TorneoDTO torneoDTO;

    @BeforeEach
    public void setUp() {
        // Inicializamos un objeto Torneo simulado para usar en los tests
        torneo = new Torneo();
        torneo.setIdTorneo(1L);
        torneo.setNombreEvento("Torneo Liga Añil");
        torneo.setMedallasMinimasRequeridas(8);
        torneo.setEstadoConvocatoria("ABIERTA");

        torneoDTO = new TorneoDTO();
        torneoDTO.setIdTorneo(1l);
        torneoDTO.setNombreEvento("Torneo Liga Añil");
        torneoDTO.setMedallasMinimasRequeridas(8);
        torneoDTO.setEstadoConvocatoria("ABIERTA");
    }

    @Test
    @DisplayName("Debe listar todos los torneos correctamente")
    public void findAll_deberiaRetornarListaDeTorneos() {
        // Arrange
        when(torService.findAll()).thenReturn(List.of(torneo));

        // Act
        ResponseEntity<?> resultado = torneoController.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        verify(torService).findAll();
    }

    @Test
    @DisplayName("Debe buscar un torneo por ID cuando existe")
    public void findById_cuandoExiste_deberiaRetornarTorneo() {
        // Arrange
        // Nota: Se usa el ID 1L para simular una respuesta exitosa
        when(torService.findID(1L)).thenReturn(torneoDTO);

        // Act
        ResponseEntity<?> resultado = torneoController.findById(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(torneo, resultado.getBody());

        // ¡Ojo aquí! El controlador real llama al servicio 2 veces en su lógica de IF
        verify(torService, times(2)).findID(1L);
    }

    @Test
    @DisplayName("Debe retornar NOT FOUND cuando el torneo no existe")
    public void findById_cuandoNoExiste_deberiaRetornarNotFound() {
        // Arrange
        when(torService.findID(99L)).thenReturn(null);

        // Act
        ResponseEntity<?> resultado = torneoController.findById(99L);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.NOT_FOUND, resultado.getStatusCode());
        verify(torService, times(1)).findID(99L);
    }

    @Test
    @DisplayName("Debe guardar un torneo correctamente")
    public void save_deberiaCrearTorneo() {
        // Arrange
        when(torService.save(any(Torneo.class))).thenReturn(torneo);

        // Act
        ResponseEntity<?> resultado = torneoController.save(torneo);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        verify(torService).save(any(Torneo.class));
    }

    @Test
    @DisplayName("Debe eliminar un torneo por ID correctamente")
    public void delete_deberiaEliminarTorneoPorId() {
        // Arrange
        doNothing().when(torService).delete(1L);

        // Act
        ResponseEntity<?> resultado = torneoController.delete(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
        verify(torService).delete(1L);
    }

    @Test
    @DisplayName("Debe actualizar un torneo correctamente")
    public void update_deberiaRetornarTorneoActualizado() {
        // Arrange
        when(torService.update(eq(1L), any(Torneo.class))).thenReturn(torneo);

        // Act
        ResponseEntity<?> resultado = torneoController.update(1L, torneo);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        verify(torService).update(eq(1L), any(Torneo.class));
    }
}