package com.packa.japp.service;

import com.packa.japp.service.dto.HistoriaClinicaDTO;
import java.util.List;

/**
 * Service Interface for managing HistoriaClinica.
 */
public interface HistoriaClinicaService {

    /**
     * Save a historiaClinica.
     *
     * @param historiaClinicaDTO the entity to save
     * @return the persisted entity
     */
    HistoriaClinicaDTO save(HistoriaClinicaDTO historiaClinicaDTO);

    /**
     * Get all the historiaClinicas.
     *
     * @return the list of entities
     */
    List<HistoriaClinicaDTO> findAll();

    /**
     * Get the "id" historiaClinica.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HistoriaClinicaDTO findOne(Long id);

    /**
     * Delete the "id" historiaClinica.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
