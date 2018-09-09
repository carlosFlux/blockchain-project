package com.packa.japp.web.rest;

import com.packa.japp.JappApp;

import com.packa.japp.domain.Medico;
import com.packa.japp.repository.MedicoRepository;
import com.packa.japp.service.MedicoService;
import com.packa.japp.service.dto.MedicoDTO;
import com.packa.japp.service.mapper.MedicoMapper;
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
 * Test class for the MedicoResource REST controller.
 *
 * @see MedicoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JappApp.class)
public class MedicoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALIDAD = "BBBBBBBBBB";

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private MedicoMapper medicoMapper;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicoMockMvc;

    private Medico medico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicoResource medicoResource = new MedicoResource(medicoService);
        this.restMedicoMockMvc = MockMvcBuilders.standaloneSetup(medicoResource)
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
    public static Medico createEntity(EntityManager em) {
        Medico medico = new Medico()
            .nombre(DEFAULT_NOMBRE)
            .especialidad(DEFAULT_ESPECIALIDAD);
        return medico;
    }

    @Before
    public void initTest() {
        medico = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedico() throws Exception {
        int databaseSizeBeforeCreate = medicoRepository.findAll().size();

        // Create the Medico
        MedicoDTO medicoDTO = medicoMapper.toDto(medico);
        restMedicoMockMvc.perform(post("/api/medicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicoDTO)))
            .andExpect(status().isCreated());

        // Validate the Medico in the database
        List<Medico> medicoList = medicoRepository.findAll();
        assertThat(medicoList).hasSize(databaseSizeBeforeCreate + 1);
        Medico testMedico = medicoList.get(medicoList.size() - 1);
        assertThat(testMedico.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testMedico.getEspecialidad()).isEqualTo(DEFAULT_ESPECIALIDAD);
    }

    @Test
    @Transactional
    public void createMedicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicoRepository.findAll().size();

        // Create the Medico with an existing ID
        medico.setId(1L);
        MedicoDTO medicoDTO = medicoMapper.toDto(medico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicoMockMvc.perform(post("/api/medicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medico in the database
        List<Medico> medicoList = medicoRepository.findAll();
        assertThat(medicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicos() throws Exception {
        // Initialize the database
        medicoRepository.saveAndFlush(medico);

        // Get all the medicoList
        restMedicoMockMvc.perform(get("/api/medicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].especialidad").value(hasItem(DEFAULT_ESPECIALIDAD.toString())));
    }

    @Test
    @Transactional
    public void getMedico() throws Exception {
        // Initialize the database
        medicoRepository.saveAndFlush(medico);

        // Get the medico
        restMedicoMockMvc.perform(get("/api/medicos/{id}", medico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medico.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.especialidad").value(DEFAULT_ESPECIALIDAD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedico() throws Exception {
        // Get the medico
        restMedicoMockMvc.perform(get("/api/medicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedico() throws Exception {
        // Initialize the database
        medicoRepository.saveAndFlush(medico);
        int databaseSizeBeforeUpdate = medicoRepository.findAll().size();

        // Update the medico
        Medico updatedMedico = medicoRepository.findOne(medico.getId());
        // Disconnect from session so that the updates on updatedMedico are not directly saved in db
        em.detach(updatedMedico);
        updatedMedico
            .nombre(UPDATED_NOMBRE)
            .especialidad(UPDATED_ESPECIALIDAD);
        MedicoDTO medicoDTO = medicoMapper.toDto(updatedMedico);

        restMedicoMockMvc.perform(put("/api/medicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicoDTO)))
            .andExpect(status().isOk());

        // Validate the Medico in the database
        List<Medico> medicoList = medicoRepository.findAll();
        assertThat(medicoList).hasSize(databaseSizeBeforeUpdate);
        Medico testMedico = medicoList.get(medicoList.size() - 1);
        assertThat(testMedico.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testMedico.getEspecialidad()).isEqualTo(UPDATED_ESPECIALIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingMedico() throws Exception {
        int databaseSizeBeforeUpdate = medicoRepository.findAll().size();

        // Create the Medico
        MedicoDTO medicoDTO = medicoMapper.toDto(medico);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicoMockMvc.perform(put("/api/medicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicoDTO)))
            .andExpect(status().isCreated());

        // Validate the Medico in the database
        List<Medico> medicoList = medicoRepository.findAll();
        assertThat(medicoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedico() throws Exception {
        // Initialize the database
        medicoRepository.saveAndFlush(medico);
        int databaseSizeBeforeDelete = medicoRepository.findAll().size();

        // Get the medico
        restMedicoMockMvc.perform(delete("/api/medicos/{id}", medico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medico> medicoList = medicoRepository.findAll();
        assertThat(medicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medico.class);
        Medico medico1 = new Medico();
        medico1.setId(1L);
        Medico medico2 = new Medico();
        medico2.setId(medico1.getId());
        assertThat(medico1).isEqualTo(medico2);
        medico2.setId(2L);
        assertThat(medico1).isNotEqualTo(medico2);
        medico1.setId(null);
        assertThat(medico1).isNotEqualTo(medico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicoDTO.class);
        MedicoDTO medicoDTO1 = new MedicoDTO();
        medicoDTO1.setId(1L);
        MedicoDTO medicoDTO2 = new MedicoDTO();
        assertThat(medicoDTO1).isNotEqualTo(medicoDTO2);
        medicoDTO2.setId(medicoDTO1.getId());
        assertThat(medicoDTO1).isEqualTo(medicoDTO2);
        medicoDTO2.setId(2L);
        assertThat(medicoDTO1).isNotEqualTo(medicoDTO2);
        medicoDTO1.setId(null);
        assertThat(medicoDTO1).isNotEqualTo(medicoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicoMapper.fromId(null)).isNull();
    }
}
