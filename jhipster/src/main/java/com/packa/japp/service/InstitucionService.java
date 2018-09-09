package com.packa.japp.service;

import com.packa.japp.service.dto.InstitucionDTO;
import java.util.List;

/**
 * Service Interface for managing Institucion.
 */
public interface InstitucionService {

    /**
     * Save a institucion.
     *
     * @param institucionDTO the entity to save
     * @return the persisted entity
     */
    InstitucionDTO save(InstitucionDTO institucionDTO);

    /**
     * Get all the institucions.
     *
     * @return the list of entities
     */
    List<InstitucionDTO> findAll();

    /**
     * Get the "id" institucion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    InstitucionDTO findOne(Long id);

    /**
     * Delete the "id" institucion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
