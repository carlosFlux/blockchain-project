package com.packa.japp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.packa.japp.service.MedicoService;
import com.packa.japp.web.rest.errors.BadRequestAlertException;
import com.packa.japp.web.rest.util.HeaderUtil;
import com.packa.japp.service.dto.MedicoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Medico.
 */
@RestController
@RequestMapping("/api")
public class MedicoResource {

    private final Logger log = LoggerFactory.getLogger(MedicoResource.class);

    private static final String ENTITY_NAME = "medico";

    private final MedicoService medicoService;

    public MedicoResource(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    /**
     * POST  /medicos : Create a new medico.
     *
     * @param medicoDTO the medicoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicoDTO, or with status 400 (Bad Request) if the medico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medicos")
    @Timed
    public ResponseEntity<MedicoDTO> createMedico(@RequestBody MedicoDTO medicoDTO) throws URISyntaxException {
        log.debug("REST request to save Medico : {}", medicoDTO);
        if (medicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new medico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicoDTO result = medicoService.save(medicoDTO);
        return ResponseEntity.created(new URI("/api/medicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medicos : Updates an existing medico.
     *
     * @param medicoDTO the medicoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicoDTO,
     * or with status 400 (Bad Request) if the medicoDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medicos")
    @Timed
    public ResponseEntity<MedicoDTO> updateMedico(@RequestBody MedicoDTO medicoDTO) throws URISyntaxException {
        log.debug("REST request to update Medico : {}", medicoDTO);
        if (medicoDTO.getId() == null) {
            return createMedico(medicoDTO);
        }
        MedicoDTO result = medicoService.save(medicoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medicos : get all the medicos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medicos in body
     */
    @GetMapping("/medicos")
    @Timed
    public List<MedicoDTO> getAllMedicos() {
        log.debug("REST request to get all Medicos");
        return medicoService.findAll();
        }

    /**
     * GET  /medicos/:id : get the "id" medico.
     *
     * @param id the id of the medicoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medicos/{id}")
    @Timed
    public ResponseEntity<MedicoDTO> getMedico(@PathVariable Long id) {
        log.debug("REST request to get Medico : {}", id);
        MedicoDTO medicoDTO = medicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medicoDTO));
    }

    /**
     * DELETE  /medicos/:id : delete the "id" medico.
     *
     * @param id the id of the medicoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medicos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedico(@PathVariable Long id) {
        log.debug("REST request to delete Medico : {}", id);
        medicoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
