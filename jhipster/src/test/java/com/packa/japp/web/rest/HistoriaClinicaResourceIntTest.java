package com.packa.japp.web.rest;

import com.packa.japp.JappApp;

import com.packa.japp.domain.HistoriaClinica;
import com.packa.japp.repository.HistoriaClinicaRepository;
import com.packa.japp.service.HistoriaClinicaService;
import com.packa.japp.service.dto.HistoriaClinicaDTO;
import com.packa.japp.service.mapper.HistoriaClinicaMapper;
import com.packa.japp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.packa.japp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HistoriaClinicaResource REST controller.
 *
 * @see HistoriaClinicaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JappApp.class)
public class HistoriaClinicaResourceIntTest {

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Autowired
    private HistoriaClinicaMapper historiaClinicaMapper;

    @Autowired
    private HistoriaClinicaService historiaClinicaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHistoriaClinicaMockMvc;

    private HistoriaClinica historiaClinica;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HistoriaClinicaResource historiaClinicaResource = new HistoriaClinicaResource(historiaClinicaService);
        this.restHistoriaClinicaMockMvc = MockMvcBuilders.standaloneSetup(historiaClinicaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoriaClinica createEntity(EntityManager em) {
        HistoriaClinica historiaClinica = new HistoriaClinica();
        return historiaClinica;
    }

    @Before
    public void initTest() {
        historiaClinica = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistoriaClinica() throws Exception {
        int databaseSizeBeforeCreate = historiaClinicaRepository.findAll().size();

        // Create the HistoriaClinica
        HistoriaClinicaDTO historiaClinicaDTO = historiaClinicaMapper.toDto(historiaClinica);
        restHistoriaClinicaMockMvc.perform(post("/api/historia-clinicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaClinicaDTO)))
            .andExpect(status().isCreated());

        // Validate the HistoriaClinica in the database
        List<HistoriaClinica> historiaClinicaList = historiaClinicaRepository.findAll();
        assertThat(historiaClinicaList).hasSize(databaseSizeBeforeCreate + 1);
        HistoriaClinica testHistoriaClinica = historiaClinicaList.get(historiaClinicaList.size() - 1);
    }

    @Test
    @Transactional
    public void createHistoriaClinicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historiaClinicaRepository.findAll().size();

        // Create the HistoriaClinica with an existing ID
        historiaClinica.setId(1L);
        HistoriaClinicaDTO historiaClinicaDTO = historiaClinicaMapper.toDto(historiaClinica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriaClinicaMockMvc.perform(post("/api/historia-clinicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaClinicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HistoriaClinica in the database
        List<HistoriaClinica> historiaClinicaList = historiaClinicaRepository.findAll();
        assertThat(historiaClinicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHistoriaClinicas() throws Exception {
        // Initialize the database
        historiaClinicaRepository.saveAndFlush(historiaClinica);

        // Get all the historiaClinicaList
        restHistoriaClinicaMockMvc.perform(get("/api/historia-clinicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historiaClinica.getId().intValue())));
    }

    @Test
    @Transactional
    public void getHistoriaClinica() throws Exception {
        // Initialize the database
        historiaClinicaRepository.saveAndFlush(historiaClinica);

        // Get the historiaClinica
        restHistoriaClinicaMockMvc.perform(get("/api/historia-clinicas/{id}", historiaClinica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(historiaClinica.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHistoriaClinica() throws Exception {
        // Get the historiaClinica
        restHistoriaClinicaMockMvc.perform(get("/api/historia-clinicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoriaClinica() throws Exception {
        // Initialize the database
        historiaClinicaRepository.saveAndFlush(historiaClinica);
        int databaseSizeBeforeUpdate = historiaClinicaRepository.findAll().size();

        // Update the historiaClinica
        HistoriaClinica updatedHistoriaClinica = historiaClinicaRepository.findOne(historiaClinica.getId());
        // Disconnect from session so that the updates on updatedHistoriaClinica are not directly saved in db
        em.detach(updatedHistoriaClinica);
        HistoriaClinicaDTO historiaClinicaDTO = historiaClinicaMapper.toDto(updatedHistoriaClinica);

        restHistoriaClinicaMockMvc.perform(put("/api/historia-clinicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaClinicaDTO)))
            .andExpect(status().isOk());

        // Validate the HistoriaClinica in the database
        List<HistoriaClinica> historiaClinicaList = historiaClinicaRepository.findAll();
        assertThat(historiaClinicaList).hasSize(databaseSizeBeforeUpdate);
        HistoriaClinica testHistoriaClinica = historiaClinicaList.get(historiaClinicaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingHistoriaClinica() throws Exception {
        int databaseSizeBeforeUpdate = historiaClinicaRepository.findAll().size();

        // Create the HistoriaClinica
        HistoriaClinicaDTO historiaClinicaDTO = historiaClinicaMapper.toDto(historiaClinica);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHistoriaClinicaMockMvc.perform(put("/api/historia-clinicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaClinicaDTO)))
            .andExpect(status().isCreated());

        // Validate the HistoriaClinica in the database
        List<HistoriaClinica> historiaClinicaList = historiaClinicaRepository.findAll();
        assertThat(historiaClinicaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHistoriaClinica() throws Exception {
        // Initialize the database
        historiaClinicaRepository.saveAndFlush(historiaClinica);
        int databaseSizeBeforeDelete = historiaClinicaRepository.findAll().size();

        // Get the historiaClinica
        restHistoriaClinicaMockMvc.perform(delete("/api/historia-clinicas/{id}", historiaClinica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HistoriaClinica> historiaClinicaList = historiaClinicaRepository.findAll();
        assertThat(historiaClinicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriaClinica.class);
        HistoriaClinica historiaClinica1 = new HistoriaClinica();
        historiaClinica1.setId(1L);
        HistoriaClinica historiaClinica2 = new HistoriaClinica();
        historiaClinica2.setId(historiaClinica1.getId());
        assertThat(historiaClinica1).isEqualTo(historiaClinica2);
        historiaClinica2.setId(2L);
        assertThat(historiaClinica1).isNotEqualTo(historiaClinica2);
        historiaClinica1.setId(null);
        assertThat(historiaClinica1).isNotEqualTo(historiaClinica2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriaClinicaDTO.class);
        HistoriaClinicaDTO historiaClinicaDTO1 = new HistoriaClinicaDTO();
        historiaClinicaDTO1.setId(1L);
        HistoriaClinicaDTO historiaClinicaDTO2 = new HistoriaClinicaDTO();
        assertThat(historiaClinicaDTO1).isNotEqualTo(historiaClinicaDTO2);
        historiaClinicaDTO2.setId(historiaClinicaDTO1.getId());
        assertThat(historiaClinicaDTO1).isEqualTo(historiaClinicaDTO2);
        historiaClinicaDTO2.setId(2L);
        assertThat(historiaClinicaDTO1).isNotEqualTo(historiaClinicaDTO2);
        historiaClinicaDTO1.setId(null);
        assertThat(historiaClinicaDTO1).isNotEqualTo(historiaClinicaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(historiaClinicaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(historiaClinicaMapper.fromId(null)).isNull();
    }
}
