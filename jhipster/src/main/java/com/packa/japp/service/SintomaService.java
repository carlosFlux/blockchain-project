package com.packa.japp.service;

import com.packa.japp.service.dto.SintomaDTO;
import java.util.List;

/**
 * Service Interface for managing Sintoma.
 */
public interface SintomaService {

    /**
     * Save a sintoma.
     *
     * @param sintomaDTO the entity to save
     * @return the persisted entity
     */
    SintomaDTO save(SintomaDTO sintomaDTO);

    /**
     * Get all the sintomas.
     *
     * @return the list of entities
     */
    List<SintomaDTO> findAll();
    /**
     * Get all the SintomaDTO where HistoriaClinica is null.
     *
     * @return the list of entities
     */
    List<SintomaDTO> findAllWhereHistoriaClinicaIsNull();

    /**
     * Get the "id" sintoma.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SintomaDTO findOne(Long id);

    /**
     * Delete the "id" sintoma.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
