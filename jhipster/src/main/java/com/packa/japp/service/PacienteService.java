package com.packa.japp.service;

import com.packa.japp.service.dto.PacienteDTO;
import java.util.List;

/**
 * Service Interface for managing Paciente.
 */
public interface PacienteService {

    /**
     * Save a paciente.
     *
     * @param pacienteDTO the entity to save
     * @return the persisted entity
     */
    PacienteDTO save(PacienteDTO pacienteDTO);

    /**
     * Get all the pacientes.
     *
     * @return the list of entities
     */
    List<PacienteDTO> findAll();

    /**
     * Get the "id" paciente.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PacienteDTO findOne(Long id);

    /**
     * Delete the "id" paciente.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
