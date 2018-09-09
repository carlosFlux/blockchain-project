package com.packa.japp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.packa.japp.service.InstitucionService;
import com.packa.japp.web.rest.errors.BadRequestAlertException;
import com.packa.japp.web.rest.util.HeaderUtil;
import com.packa.japp.service.dto.InstitucionDTO;
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
 * REST controller for managing Institucion.
 */
@RestController
@RequestMapping("/api")
public class InstitucionResource {

    private final Logger log = LoggerFactory.getLogger(InstitucionResource.class);

    private static final String ENTITY_NAME = "institucion";

    private final InstitucionService institucionService;

    public InstitucionResource(InstitucionService institucionService) {
        this.institucionService = institucionService;
    }

    /**
     * POST  /institucions : Create a new institucion.
     *
     * @param institucionDTO the institucionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new institucionDTO, or with status 400 (Bad Request) if the institucion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/institucions")
    @Timed
    public ResponseEntity<InstitucionDTO> createInstitucion(@RequestBody InstitucionDTO institucionDTO) throws URISyntaxException {
        log.debug("REST request to save Institucion : {}", institucionDTO);
        if (institucionDTO.getId() != null) {
            throw new BadRequestAlertException("A new institucion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstitucionDTO result = institucionService.save(institucionDTO);
        return ResponseEntity.created(new URI("/api/institucions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /institucions : Updates an existing institucion.
     *
     * @param institucionDTO the institucionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated institucionDTO,
     * or with status 400 (Bad Request) if the institucionDTO is not valid,
     * or with status 500 (Internal Server Error) if the institucionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/institucions")
    @Timed
    public ResponseEntity<InstitucionDTO> updateInstitucion(@RequestBody InstitucionDTO institucionDTO) throws URISyntaxException {
        log.debug("REST request to update Institucion : {}", institucionDTO);
        if (institucionDTO.getId() == null) {
            return createInstitucion(institucionDTO);
        }
        InstitucionDTO result = institucionService.save(institucionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, institucionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /institucions : get all the institucions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of institucions in body
     */
    @GetMapping("/institucions")
    @Timed
    public List<InstitucionDTO> getAllInstitucions() {
        log.debug("REST request to get all Institucions");
        return institucionService.findAll();
        }

    /**
     * GET  /institucions/:id : get the "id" institucion.
     *
     * @param id the id of the institucionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the institucionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/institucions/{id}")
    @Timed
    public ResponseEntity<InstitucionDTO> getInstitucion(@PathVariable Long id) {
        log.debug("REST request to get Institucion : {}", id);
        InstitucionDTO institucionDTO = institucionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(institucionDTO));
    }

    /**
     * DELETE  /institucions/:id : delete the "id" institucion.
     *
     * @param id the id of the institucionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/institucions/{id}")
    @Timed
    public ResponseEntity<Void> deleteInstitucion(@PathVariable Long id) {
        log.debug("REST request to delete Institucion : {}", id);
        institucionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
