package com.packa.japp.service.impl;

import com.packa.japp.service.MedicoService;
import com.packa.japp.domain.Medico;
import com.packa.japp.repository.MedicoRepository;
import com.packa.japp.service.dto.MedicoDTO;
import com.packa.japp.service.mapper.MedicoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Medico.
 */
@Service
@Transactional
public class MedicoServiceImpl implements MedicoService{

    private final Logger log = LoggerFactory.getLogger(MedicoServiceImpl.class);

    private final MedicoRepository medicoRepository;

    private final MedicoMapper medicoMapper;

    public MedicoServiceImpl(MedicoRepository medicoRepository, MedicoMapper medicoMapper) {
        this.medicoRepository = medicoRepository;
        this.medicoMapper = medicoMapper;
    }

    /**
     * Save a medico.
     *
     * @param medicoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MedicoDTO save(MedicoDTO medicoDTO) {
        log.debug("Request to save Medico : {}", medicoDTO);
        Medico medico = medicoMapper.toEntity(medicoDTO);
        medico = medicoRepository.save(medico);
        return medicoMapper.toDto(medico);
    }

    /**
     * Get all the medicos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MedicoDTO> findAll() {
        log.debug("Request to get all Medicos");
        return medicoRepository.findAll().stream()
            .map(medicoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one medico by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MedicoDTO findOne(Long id) {
        log.debug("Request to get Medico : {}", id);
        Medico medico = medicoRepository.findOne(id);
        return medicoMapper.toDto(medico);
    }

    /**
     * Delete the medico by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medico : {}", id);
        medicoRepository.delete(id);
    }
}
