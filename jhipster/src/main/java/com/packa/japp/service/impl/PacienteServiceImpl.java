package com.packa.japp.service.impl;

import com.packa.japp.service.PacienteService;
import com.packa.japp.domain.Paciente;
import com.packa.japp.repository.PacienteRepository;
import com.packa.japp.service.dto.PacienteDTO;
import com.packa.japp.service.mapper.PacienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Paciente.
 */
@Service
@Transactional
public class PacienteServiceImpl implements PacienteService{

    private final Logger log = LoggerFactory.getLogger(PacienteServiceImpl.class);

    private final PacienteRepository pacienteRepository;

    private final PacienteMapper pacienteMapper;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    /**
     * Save a paciente.
     *
     * @param pacienteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PacienteDTO save(PacienteDTO pacienteDTO) {
        log.debug("Request to save Paciente : {}", pacienteDTO);
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        return pacienteMapper.toDto(paciente);
    }

    /**
     * Get all the pacientes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PacienteDTO> findAll() {
        log.debug("Request to get all Pacientes");
        return pacienteRepository.findAll().stream()
            .map(pacienteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one paciente by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PacienteDTO findOne(Long id) {
        log.debug("Request to get Paciente : {}", id);
        Paciente paciente = pacienteRepository.findOne(id);
        return pacienteMapper.toDto(paciente);
    }

    /**
     * Delete the paciente by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paciente : {}", id);
        pacienteRepository.delete(id);
    }
}
