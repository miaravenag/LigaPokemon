/*
package cl.duoc.centropokemon.service;

import cl.duoc.centropokemon.dto.CentroPkDTO;
import cl.duoc.centropokemon.mapper.CentroPkMapper;
import cl.duoc.centropokemon.model.CentroPk;
import cl.duoc.centropokemon.repository.CentroPkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CentroPkdServiceTest {

    @Mock
    private CentroPkRepository centroPkRepository;

    @Mock
    private CentroPkMapper centroPkMapper;

    @InjectMocks
    private CentroPkdService centroPkdService;

    private CentroPk centroEntity;
    private CentroPkDTO centroDTO;

    @BeforeEach
    void setUp() {
        // Inicializamos objetos de prueba con la estructura exacta de tu modelo
        centroEntity = new CentroPk(1L, "Centro PK", "Pueblo Lavanda", "Kanto", "calle ghastly", 5, true);
        centroDTO = new CentroPkDTO(1L, "Centro PK", "Pueblo Lavanda", "Kanto", "calle ghastly", 5, true);
    }

    @Test
    void testFindAll() {
        // GIVEN
        when(centroPkRepository.findAll()).thenReturn(List.of(centroEntity));
        when(centroPkMapper.toDTO(centroEntity)).thenReturn(centroDTO);

        // WHEN
        List<CentroPkDTO> result = centroPkdService.findAll();

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pueblo Lavanda", result.get(0).getCiudad());
        verify(centroPkRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Exitoso() {
        // GIVEN
        when(centroPkRepository.findById(1L)).thenReturn(Optional.of(centroEntity));
        when(centroPkMapper.toDTO(centroEntity)).thenReturn(centroDTO);

        // WHEN
        CentroPkDTO result = centroPkdService.findById(1L);

        // THEN
        assertNotNull(result);
        assertEquals("Centro PK", result.getNombre());
        verify(centroPkRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NoEncontrado_LanzaExcepcion() {
        // GIVEN
        when(centroPkRepository.findById(2L)).thenReturn(Optional.empty());

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            centroPkdService.findById(2L);
        });

        assertEquals("Centro Pokémon no encontrado con id: 2", exception.getMessage());
    }

    @Test
    void testSave() {
        // GIVEN
        when(centroPkMapper.toEntity(centroDTO)).thenReturn(centroEntity);
        when(centroPkRepository.save(centroEntity)).thenReturn(centroEntity);
        when(centroPkMapper.toDTO(centroEntity)).thenReturn(centroDTO);

        // WHEN
        CentroPkDTO result = centroPkdService.save(centroDTO);

        // THEN
        assertNotNull(result);
        assertEquals("Centro PK", result.getNombre());
        verify(centroPkRepository, times(1)).save(centroEntity);
    }

    @Test
    void testUpdate_Exitoso() {
        // GIVEN
        when(centroPkRepository.findById(1L)).thenReturn(Optional.of(centroEntity));
        when(centroPkMapper.toEntity(centroDTO)).thenReturn(centroEntity);
        when(centroPkRepository.save(centroEntity)).thenReturn(centroEntity);
        when(centroPkMapper.toDTO(centroEntity)).thenReturn(centroDTO);

        // WHEN
        CentroPkDTO result = centroPkdService.update(1L, centroDTO);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(centroPkRepository, times(1)).save(any(CentroPk.class));
    }

    @Test
    void testDelete_Exitoso() {
        // GIVEN
        when(centroPkRepository.findById(1L)).thenReturn(Optional.of(centroEntity));
        doNothing().when(centroPkRepository).deleteById(1L);

        // WHEN & THEN
        assertDoesNotThrow(() -> centroPkdService.delete(1L));
        verify(centroPkRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByCiudad() {
        // GIVEN
        when(centroPkRepository.findByCiudad("Pueblo Lavanda")).thenReturn(List.of(centroEntity));
        when(centroPkMapper.toDTO(centroEntity)).thenReturn(centroDTO);

        // WHEN
        List<CentroPkDTO> result = centroPkdService.findByCiudad("Pueblo Lavanda");

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pueblo Lavanda", result.get(0).getCiudad());
    }

    @Test
    void testFindByRegion() {
        // GIVEN
        when(centroPkRepository.findByRegion("Kanto")).thenReturn(List.of(centroEntity));
        when(centroPkMapper.toDTO(centroEntity)).thenReturn(centroDTO);

        // WHEN
        List<CentroPkDTO> result = centroPkdService.findByRegion("Kanto");

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Kanto", result.get(0).getRegion());
    }
}
*/