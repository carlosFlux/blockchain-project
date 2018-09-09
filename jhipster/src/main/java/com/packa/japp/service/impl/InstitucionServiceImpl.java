package com.packa.japp.service.impl;

import com.packa.japp.service.InstitucionService;
import com.packa.japp.domain.Institucion;
import com.packa.japp.repository.InstitucionRepository;
import com.packa.japp.service.dto.InstitucionDTO;
import com.packa.japp.service.mapper.InstitucionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Institucion.
 */
@Service
@Transactional
public class InstitucionServiceImpl implements InstitucionService{

    private final Logger log = LoggerFactory.getLogger(InstitucionServiceImpl.class);

    private final InstitucionRepository institucionRepository;

    private final InstitucionMapper institucionMapper;

    public InstitucionServiceImpl(InstitucionRepository institucionRepository, InstitucionMapper institucionMapper) {
        this.institucionRepository = institucionRepository;
        this.institucionMapper = institucionMapper;
    }

    /**
     * Save a institucion.
     *
     * @param institucionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InstitucionDTO save(InstitucionDTO institucionDTO) {
        log.debug("Request to save Institucion : {}", institucionDTO);
        Institucion institucion = institucionMapper.toEntity(institucionDTO);
        institucion = institucionRepository.save(institucion);
        return institucionMapper.toDto(institucion);
    }

    /**
     * Get all the institucions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<InstitucionDTO> findAll() {
        log.debug("Request to get all Institucions");
        return institucionRepository.findAll().stream()
            .map(institucionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one institucion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InstitucionDTO findOne(Long id) {
        log.debug("Request to get Institucion : {}", id);
        Institucion institucion = institucionRepository.findOne(id);
        return institucionMapper.toDto(institucion);
    }

    /**
     * Delete the institucion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Institucion : {}", id);
        institucionRepository.delete(id);
    }
}
