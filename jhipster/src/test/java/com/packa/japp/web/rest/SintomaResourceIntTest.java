package com.packa.japp.web.rest;

import com.packa.japp.JappApp;

import com.packa.japp.domain.Sintoma;
import com.packa.japp.repository.SintomaRepository;
import com.packa.japp.service.SintomaService;
import com.packa.japp.service.dto.SintomaDTO;
import com.packa.japp.service.mapper.SintomaMapper;
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
 * Test class for the SintomaResource REST controller.
 *
 * @see SintomaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JappApp.class)
public class SintomaResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private SintomaRepository sintomaRepository;

    @Autowired
    private SintomaMapper sintomaMapper;

    @Autowired
    private SintomaService sintomaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSintomaMockMvc;

    private Sintoma sintoma;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SintomaResource sintomaResource = new SintomaResource(sintomaService);
        this.restSintomaMockMvc = MockMvcBuilders.standaloneSetup(sintomaResource)
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
    public static Sintoma createEntity(EntityManager em) {
        Sintoma sintoma = new Sintoma()
            .descripcion(DEFAULT_DESCRIPCION);
        return sintoma;
    }

    @Before
    public void initTest() {
        sintoma = createEntity(em);
    }

    @Test
    @Transactional
    public void createSintoma() throws Exception {
        int databaseSizeBeforeCreate = sintomaRepository.findAll().size();

        // Create the Sintoma
        SintomaDTO sintomaDTO = sintomaMapper.toDto(sintoma);
        restSintomaMockMvc.perform(post("/api/sintomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sintomaDTO)))
            .andExpect(status().isCreated());

        // Validate the Sintoma in the database
        List<Sintoma> sintomaList = sintomaRepository.findAll();
        assertThat(sintomaList).hasSize(databaseSizeBeforeCreate + 1);
        Sintoma testSintoma = sintomaList.get(sintomaList.size() - 1);
        assertThat(testSintoma.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createSintomaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sintomaRepository.findAll().size();

        // Create the Sintoma with an existing ID
        sintoma.setId(1L);
        SintomaDTO sintomaDTO = sintomaMapper.toDto(sintoma);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSintomaMockMvc.perform(post("/api/sintomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sintomaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sintoma in the database
        List<Sintoma> sintomaList = sintomaRepository.findAll();
        assertThat(sintomaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSintomas() throws Exception {
        // Initialize the database
        sintomaRepository.saveAndFlush(sintoma);

        // Get all the sintomaList
        restSintomaMockMvc.perform(get("/api/sintomas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sintoma.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getSintoma() throws Exception {
        // Initialize the database
        sintomaRepository.saveAndFlush(sintoma);

        // Get the sintoma
        restSintomaMockMvc.perform(get("/api/sintomas/{id}", sintoma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sintoma.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSintoma() throws Exception {
        // Get the sintoma
        restSintomaMockMvc.perform(get("/api/sintomas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSintoma() throws Exception {
        // Initialize the database
        sintomaRepository.saveAndFlush(sintoma);
        int databaseSizeBeforeUpdate = sintomaRepository.findAll().size();

        // Update the sintoma
        Sintoma updatedSintoma = sintomaRepository.findOne(sintoma.getId());
        // Disconnect from session so that the updates on updatedSintoma are not directly saved in db
        em.detach(updatedSintoma);
        updatedSintoma
            .descripcion(UPDATED_DESCRIPCION);
        SintomaDTO sintomaDTO = sintomaMapper.toDto(updatedSintoma);

        restSintomaMockMvc.perform(put("/api/sintomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sintomaDTO)))
            .andExpect(status().isOk());

        // Validate the Sintoma in the database
        List<Sintoma> sintomaList = sintomaRepository.findAll();
        assertThat(sintomaList).hasSize(databaseSizeBeforeUpdate);
        Sintoma testSintoma = sintomaList.get(sintomaList.size() - 1);
        assertThat(testSintoma.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingSintoma() throws Exception {
        int databaseSizeBeforeUpdate = sintomaRepository.findAll().size();

        // Create the Sintoma
        SintomaDTO sintomaDTO = sintomaMapper.toDto(sintoma);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSintomaMockMvc.perform(put("/api/sintomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sintomaDTO)))
            .andExpect(status().isCreated());

        // Validate the Sintoma in the database
        List<Sintoma> sintomaList = sintomaRepository.findAll();
        assertThat(sintomaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSintoma() throws Exception {
        // Initialize the database
        sintomaRepository.saveAndFlush(sintoma);
        int databaseSizeBeforeDelete = sintomaRepository.findAll().size();

        // Get the sintoma
        restSintomaMockMvc.perform(delete("/api/sintomas/{id}", sintoma.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sintoma> sintomaList = sintomaRepository.findAll();
        assertThat(sintomaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sintoma.class);
        Sintoma sintoma1 = new Sintoma();
        sintoma1.setId(1L);
        Sintoma sintoma2 = new Sintoma();
        sintoma2.setId(sintoma1.getId());
        assertThat(sintoma1).isEqualTo(sintoma2);
        sintoma2.setId(2L);
        assertThat(sintoma1).isNotEqualTo(sintoma2);
        sintoma1.setId(null);
        assertThat(sintoma1).isNotEqualTo(sintoma2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SintomaDTO.class);
        SintomaDTO sintomaDTO1 = new SintomaDTO();
        sintomaDTO1.setId(1L);
        SintomaDTO sintomaDTO2 = new SintomaDTO();
        assertThat(sintomaDTO1).isNotEqualTo(sintomaDTO2);
        sintomaDTO2.setId(sintomaDTO1.getId());
        assertThat(sintomaDTO1).isEqualTo(sintomaDTO2);
        sintomaDTO2.setId(2L);
        assertThat(sintomaDTO1).isNotEqualTo(sintomaDTO2);
        sintomaDTO1.setId(null);
        assertThat(sintomaDTO1).isNotEqualTo(sintomaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sintomaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sintomaMapper.fromId(null)).isNull();
    }
}
