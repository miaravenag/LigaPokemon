/*
package cl.duoc.centropokemon;

import cl.duoc.centropokemon.controller.CentroPkController;
import cl.duoc.centropokemon.dto.CentroPkDTO;
import cl.duoc.centropokemon.service.CentroPkdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CentroPkController.class)
class CentroPkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CentroPkdService centroPkdService;

    @Autowired
    private ObjectMapper objectMapper;

    private CentroPkDTO centroDTO;

    @BeforeEach
    void setUp() {
        // Inicializamos el DTO con datos de prueba consistentes
        centroDTO = new CentroPkDTO(1L, "Centro PK", "Pueblo Lavanda", "Kanto", "calle ghastly", 5, true);
    }

    @Test
    void testFindAll_DeberiaRetornarStatus200() throws Exception {
        // GIVEN
        when(centroPkdService.findAll()).thenReturn(List.of(centroDTO));

        // WHEN & THEN
        mockMvc.perform(get("/centropokemon")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Centro PK"))
                .andExpect(jsonPath("$[0].ciudad").value("Pueblo Lavanda"));
    }

    @Test
    void testFindById_DeberiaRetornarObjeto() throws Exception {
        // GIVEN
        when(centroPkdService.findById(1L)).thenReturn(centroDTO);

        // WHEN & THEN
        mockMvc.perform(get("/centropokemon/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Centro PK"));
    }

    @Test
    void testSave_DeberiaRetornarStatus201Created() throws Exception {
        // GIVEN
        when(centroPkdService.save(any(CentroPkDTO.class))).thenReturn(centroDTO);

        // WHEN & THEN
        mockMvc.perform(post("/centropokemon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(centroDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Centro PK"));
    }

    @Test
    void testUpdate_DeberiaRetornarStatus200() throws Exception {
        // GIVEN
        when(centroPkdService.update(eq(1L), any(CentroPkDTO.class))).thenReturn(centroDTO);

        // WHEN & THEN
        mockMvc.perform(put("/centropokemon/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(centroDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Centro PK"));
    }

    @Test
    void testDelete_DeberiaRetornarStatus204NoContent() throws Exception {
        // GIVEN
        doNothing().when(centroPkdService.delete(1L));

        // WHEN & THEN
        mockMvc.perform(delete("/centropokemon/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPorCiudad_DeberiaFiltrarCorrectamente() throws Exception { */