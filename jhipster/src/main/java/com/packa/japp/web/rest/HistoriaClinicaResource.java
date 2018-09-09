package com.packa.japp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.packa.japp.security.AuthoritiesConstants;
import com.packa.japp.service.HistoriaClinicaService;
import com.packa.japp.web.rest.errors.BadRequestAlertException;
import com.packa.japp.web.rest.util.HeaderUtil;
import com.packa.japp.service.dto.HistoriaClinicaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HistoriaClinica.
 */
@RestController
@RequestMapping("/api")
public class HistoriaClinicaResource {

    private final Logger log = LoggerFactory.getLogger(HistoriaClinicaResource.class);

    private static final String ENTITY_NAME = "historiaClinica";

    private final HistoriaClinicaService historiaClinicaService;

    public HistoriaClinicaResource(HistoriaClinicaService historiaClinicaService) {
        this.historiaClinicaService = historiaClinicaService;
    }

    /**
     * POST  /historia-clinicas : Create a new historiaClinica.
     *
     * @param historiaClinicaDTO the historiaClinicaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new historiaClinicaDTO, or with status 400 (Bad Request) if the historiaClinica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/historia-clinicas")
    @Timed
    public ResponseEntity<HistoriaClinicaDTO> createHistoriaClinica(@RequestBody HistoriaClinicaDTO historiaClinicaDTO) throws URISyntaxException {
        log.debug("REST request to save HistoriaClinica : {}", historiaClinicaDTO);
        if (historiaClinicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new historiaClinica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoriaClinicaDTO result = historiaClinicaService.save(historiaClinicaDTO);
        return ResponseEntity.created(new URI("/api/historia-clinicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /historia-clinicas : Updates an existing historiaClinica.
     *
     * @param historiaClinicaDTO the historiaClinicaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated historiaClinicaDTO,
     * or with status 400 (Bad Request) if the historiaClinicaDTO is not valid,
     * or with status 500 (Internal Server Error) if the historiaClinicaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/historia-clinicas")
    @Timed
    public ResponseEntity<HistoriaClinicaDTO> updateHistoriaClinica(@RequestBody HistoriaClinicaDTO historiaClinicaDTO) throws URISyntaxException {
        log.debug("REST request to update HistoriaClinica : {}", historiaClinicaDTO);
        if (historiaClinicaDTO.getId() == null) {
            return createHistoriaClinica(historiaClinicaDTO);
        }
        HistoriaClinicaDTO result = historiaClinicaService.save(historiaClinicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, historiaClinicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /historia-clinicas : get all the historiaClinicas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of historiaClinicas in body
     */
    @GetMapping("/historia-clinicas")
    @Timed
    public List<HistoriaClinicaDTO> getAllHistoriaClinicas() {
        log.debug("REST request to get all HistoriaClinicas");
        return historiaClinicaService.findAll();
        }

    /**
     * GET  /historia-clinicas/:id : get the "id" historiaClinica.
     *
     * @param id the id of the historiaClinicaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the historiaClinicaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/historia-clinicas/{id}")
    @Timed
    public ResponseEntity<HistoriaClinicaDTO> getHistoriaClinica(@PathVariable Long id) {
        log.debug("REST request to get HistoriaClinica : {}", id);
        HistoriaClinicaDTO historiaClinicaDTO = historiaClinicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(historiaClinicaDTO));
    }

    /**
     * DELETE  /historia-clinicas/:id : delete the "id" historiaClinica.
     *
     * @param id the id of the historiaClinicaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/historia-clinicas/{id}")
    @Timed
    public ResponseEntity<Void> deleteHistoriaClinica(@PathVariable Long id) {
        log.debug("REST request to delete HistoriaClinica : {}", id);
        historiaClinicaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
