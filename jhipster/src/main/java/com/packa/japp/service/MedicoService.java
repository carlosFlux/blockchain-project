package com.packa.japp.service;

import com.packa.japp.service.dto.MedicoDTO;
import java.util.List;

/**
 * Service Interface for managing Medico.
 */
public interface MedicoService {

    /**
     * Save a medico.
     *
     * @param medicoDTO the entity to save
     * @return the persisted entity
     */
    MedicoDTO save(MedicoDTO medicoDTO);

    /**
     * Get all the medicos.
     *
     * @return the list of entities
     */
    List<MedicoDTO> findAll();

    /**
     * Get the "id" medico.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MedicoDTO findOne(Long id);

    /**
     * Delete the "id" medico.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
