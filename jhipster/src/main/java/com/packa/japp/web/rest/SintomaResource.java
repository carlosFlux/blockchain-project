package com.packa.japp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.packa.japp.service.SintomaService;
import com.packa.japp.web.rest.errors.BadRequestAlertException;
import com.packa.japp.web.rest.util.HeaderUtil;
import com.packa.japp.service.dto.SintomaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Sintoma.
 */
@RestController
@RequestMapping("/api")
public class SintomaResource {

    private final Logger log = LoggerFactory.getLogger(SintomaResource.class);

    private static final String ENTITY_NAME = "sintoma";

    private final SintomaService sintomaService;

    public SintomaResource(SintomaService sintomaService) {
        this.sintomaService = sintomaService;
    }

    /**
     * POST  /sintomas : Create a new sintoma.
     *
     * @param sintomaDTO the sintomaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sintomaDTO, or with status 400 (Bad Request) if the sintoma has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sintomas")
    @Timed
    public ResponseEntity<SintomaDTO> createSintoma(@RequestBody SintomaDTO sintomaDTO) throws URISyntaxException {
        log.debug("REST request to save Sintoma : {}", sintomaDTO);
        if (sintomaDTO.getId() != null) {
            throw new BadRequestAlertException("A new sintoma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SintomaDTO result = sintomaService.save(sintomaDTO);
        return ResponseEntity.created(new URI("/api/sintomas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sintomas : Updates an existing sintoma.
     *
     * @param sintomaDTO the sintomaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sintomaDTO,
     * or with status 400 (Bad Request) if the sintomaDTO is not valid,
     * or with status 500 (Internal Server Error) if the sintomaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sintomas")
    @Timed
    public ResponseEntity<SintomaDTO> updateSintoma(@RequestBody SintomaDTO sintomaDTO) throws URISyntaxException {
        log.debug("REST request to update Sintoma : {}", sintomaDTO);
        if (sintomaDTO.getId() == null) {
            return createSintoma(sintomaDTO);
        }
        SintomaDTO result = sintomaService.save(sintomaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sintomaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sintomas : get all the sintomas.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of sintomas in body
     */
    @GetMapping("/sintomas")
    @Timed
    public List<SintomaDTO> getAllSintomas(@RequestParam(required = false) String filter) {
        if ("historiaclinica-is-null".equals(filter)) {
            log.debug("REST request to get all Sintomas where historiaClinica is null");
            return sintomaService.findAllWhereHistoriaClinicaIsNull();
        }
        log.debug("REST request to get all Sintomas");
        return sintomaService.findAll();
        }

    /**
     * GET  /sintomas/:id : get the "id" sintoma.
     *
     * @param id the id of the sintomaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sintomaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sintomas/{id}")
    @Timed
    public ResponseEntity<SintomaDTO> getSintoma(@PathVariable Long id) {
        log.debug("REST request to get Sintoma : {}", id);
        SintomaDTO sintomaDTO = sintomaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sintomaDTO));
    }

    /**
     * DELETE  /sintomas/:id : delete the "id" sintoma.
     *
     * @param id the id of the sintomaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sintomas/{id}")
    @Timed
    public ResponseEntity<Void> deleteSintoma(@PathVariable Long id) {
        log.debug("REST request to delete Sintoma : {}", id);
        sintomaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
