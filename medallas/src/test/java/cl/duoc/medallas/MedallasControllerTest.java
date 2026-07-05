package cl.duoc.medallas;

// 1. Imports de tus propias clases
import cl.duoc.medallas.controller.MedallasController;
import cl.duoc.medallas.dto.MedallasDTO;
import cl.duoc.medallas.model.Medalla;
import cl.duoc.medallas.service.MedallasService;

// 2. Herramientas de Java y Spring (Agrupadas con *)
import java.util.List;
import org.springframework.http.*;

// 3. Motor de Pruebas y Mockito (Agrupados con *)
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// 4. Imports Estáticos para escribir menos código abajo (Agrupados con *)
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para MedallasController")
public class MedallasControllerTest {

    @Mock
    private MedallasService medallasService;

    @InjectMocks
    private MedallasController medallasController;

    private Medalla medalla;
    private MedallasDTO medallaDTO;

    @BeforeEach
    public void setUp() {
        // Inicializamos una Medalla (Entidad)
        medalla = new Medalla();
        medalla.setIdMedalla(1L);

        // Inicializamos el DTO con los campos reales que me pasaste
        medallaDTO = new MedallasDTO();
        medallaDTO.setNombreMedalla("Medalla Cascada");
        medallaDTO.setNombreEntrenador("Ash Ketchum");
        medallaDTO.setRegionEntrenador("Kanto");
        medallaDTO.setEsOficial(true);
    }

    @Test
    @DisplayName("Debe listar todas las medallas correctamente")
    public void findAll_deberiaRetornarListaDeMedallas() {
        // Arrange
        when(medallasService.finndAll()).thenReturn(List.of(medalla));

        // Act
        ResponseEntity<?> resultado = medallasController.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        verify(medallasService).finndAll();
    }

    @Test
    @DisplayName("Debe buscar una medalla por ID cuando existe")
    public void findById_cuandoExiste_deberiaRetornarMedalla() {
        // Arrange
        when(medallasService.findById(1L)).thenReturn(medalla);

        // Act
        ResponseEntity<?> resultado = medallasController.findById(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(medalla, resultado.getBody());
        verify(medallasService).findById(1L);
    }

    @Test
    @DisplayName("Debe retornar NOT FOUND cuando la medalla no existe")
    public void findById_cuandoNoExiste_deberiaRetornarNotFound() {
        // Arrange
        when(medallasService.findById(99L)).thenReturn(null);

        // Act
        ResponseEntity<?> resultado = medallasController.findById(99L);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.NOT_FOUND, resultado.getStatusCode());
        verify(medallasService).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar una medalla correctamente")
    public void save_deberiaCrearMedalla() {
        // Arrange
        String idEntrenador = "1";
        when(medallasService.save(any(MedallasDTO.class), eq(idEntrenador))).thenReturn(medallaDTO);

        // Act
        ResponseEntity<?> resultado = medallasController.save(medallaDTO, idEntrenador);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(medallaDTO, resultado.getBody());
        verify(medallasService).save(medallaDTO, idEntrenador);
    }

    @Test
    @DisplayName("Debe eliminar una medalla por ID correctamente")
    public void delete_deberiaEliminarMedallaPorId() {
        // Arrange
        doNothing().when(medallasService).delete(1L);

        // Act
        ResponseEntity<?> resultado = medallasController.delete(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
        verify(medallasService).delete(1L);
    }
}