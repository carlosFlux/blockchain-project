package com.packa.japp.web.rest;

import com.packa.japp.JappApp;

import com.packa.japp.domain.Institucion;
import com.packa.japp.repository.InstitucionRepository;
import com.packa.japp.service.InstitucionService;
import com.packa.japp.service.dto.InstitucionDTO;
import com.packa.japp.service.mapper.InstitucionMapper;
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
 * Test class for the InstitucionResource REST controller.
 *
 * @see InstitucionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JappApp.class)
public class InstitucionResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private InstitucionRepository institucionRepository;

    @Autowired
    private InstitucionMapper institucionMapper;

    @Autowired
    private InstitucionService institucionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInstitucionMockMvc;

    private Institucion institucion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstitucionResource institucionResource = new InstitucionResource(institucionService);
        this.restInstitucionMockMvc = MockMvcBuilders.standaloneSetup(institucionResource)
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
    public static Institucion createEntity(EntityManager em) {
        Institucion institucion = new Institucion()
            .nombre(DEFAULT_NOMBRE);
        return institucion;
    }

    @Before
    public void initTest() {
        institucion = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstitucion() throws Exception {
        int databaseSizeBeforeCreate = institucionRepository.findAll().size();

        // Create the Institucion
        InstitucionDTO institucionDTO = institucionMapper.toDto(institucion);
        restInstitucionMockMvc.perform(post("/api/institucions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institucionDTO)))
            .andExpect(status().isCreated());

        // Validate the Institucion in the database
        List<Institucion> institucionList = institucionRepository.findAll();
        assertThat(institucionList).hasSize(databaseSizeBeforeCreate + 1);
        Institucion testInstitucion = institucionList.get(institucionList.size() - 1);
        assertThat(testInstitucion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createInstitucionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = institucionRepository.findAll().size();

        // Create the Institucion with an existing ID
        institucion.setId(1L);
        InstitucionDTO institucionDTO = institucionMapper.toDto(institucion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstitucionMockMvc.perform(post("/api/institucions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institucionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Institucion in the database
        List<Institucion> institucionList = institucionRepository.findAll();
        assertThat(institucionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInstitucions() throws Exception {
        // Initialize the database
        institucionRepository.saveAndFlush(institucion);

        // Get all the institucionList
        restInstitucionMockMvc.perform(get("/api/institucions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institucion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getInstitucion() throws Exception {
        // Initialize the database
        institucionRepository.saveAndFlush(institucion);

        // Get the institucion
        restInstitucionMockMvc.perform(get("/api/institucions/{id}", institucion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(institucion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstitucion() throws Exception {
        // Get the institucion
        restInstitucionMockMvc.perform(get("/api/institucions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstitucion() throws Exception {
        // Initialize the database
        institucionRepository.saveAndFlush(institucion);
        int databaseSizeBeforeUpdate = institucionRepository.findAll().size();

        // Update the institucion
        Institucion updatedInstitucion = institucionRepository.findOne(institucion.getId());
        // Disconnect from session so that the updates on updatedInstitucion are not directly saved in db
        em.detach(updatedInstitucion);
        updatedInstitucion
            .nombre(UPDATED_NOMBRE);
        InstitucionDTO institucionDTO = institucionMapper.toDto(updatedInstitucion);

        restInstitucionMockMvc.perform(put("/api/institucions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institucionDTO)))
            .andExpect(status().isOk());

        // Validate the Institucion in the database
        List<Institucion> institucionList = institucionRepository.findAll();
        assertThat(institucionList).hasSize(databaseSizeBeforeUpdate);
        Institucion testInstitucion = institucionList.get(institucionList.size() - 1);
        assertThat(testInstitucion.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingInstitucion() throws Exception {
        int databaseSizeBeforeUpdate = institucionRepository.findAll().size();

        // Create the Institucion
        InstitucionDTO institucionDTO = institucionMapper.toDto(institucion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInstitucionMockMvc.perform(put("/api/institucions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institucionDTO)))
            .andExpect(status().isCreated());

        // Validate the Institucion in the database
        List<Institucion> institucionList = institucionRepository.findAll();
        assertThat(institucionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInstitucion() throws Exception {
        // Initialize the database
        institucionRepository.saveAndFlush(institucion);
        int databaseSizeBeforeDelete = institucionRepository.findAll().size();

        // Get the institucion
        restInstitucionMockMvc.perform(delete("/api/institucions/{id}", institucion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Institucion> institucionList = institucionRepository.findAll();
        assertThat(institucionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Institucion.class);
        Institucion institucion1 = new Institucion();
        institucion1.setId(1L);
        Institucion institucion2 = new Institucion();
        institucion2.setId(institucion1.getId());
        assertThat(institucion1).isEqualTo(institucion2);
        institucion2.setId(2L);
        assertThat(institucion1).isNotEqualTo(institucion2);
        institucion1.setId(null);
        assertThat(institucion1).isNotEqualTo(institucion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstitucionDTO.class);
        InstitucionDTO institucionDTO1 = new InstitucionDTO();
        institucionDTO1.setId(1L);
        InstitucionDTO institucionDTO2 = new InstitucionDTO();
        assertThat(institucionDTO1).isNotEqualTo(institucionDTO2);
        institucionDTO2.setId(institucionDTO1.getId());
        assertThat(institucionDTO1).isEqualTo(institucionDTO2);
        institucionDTO2.setId(2L);
        assertThat(institucionDTO1).isNotEqualTo(institucionDTO2);
        institucionDTO1.setId(null);
        assertThat(institucionDTO1).isNotEqualTo(institucionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(institucionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(institucionMapper.fromId(null)).isNull();
    }
}
