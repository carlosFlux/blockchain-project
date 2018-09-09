package com.packa.japp.service.impl;

import com.packa.japp.service.SintomaService;
import com.packa.japp.domain.Sintoma;
import com.packa.japp.repository.SintomaRepository;
import com.packa.japp.service.dto.SintomaDTO;
import com.packa.japp.service.mapper.SintomaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Sintoma.
 */
@Service
@Transactional
public class SintomaServiceImpl implements SintomaService{

    private final Logger log = LoggerFactory.getLogger(SintomaServiceImpl.class);

    private final SintomaRepository sintomaRepository;

    private final SintomaMapper sintomaMapper;

    public SintomaServiceImpl(SintomaRepository sintomaRepository, SintomaMapper sintomaMapper) {
        this.sintomaRepository = sintomaRepository;
        this.sintomaMapper = sintomaMapper;
    }

    /**
     * Save a sintoma.
     *
     * @param sintomaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SintomaDTO save(SintomaDTO sintomaDTO) {
        log.debug("Request to save Sintoma : {}", sintomaDTO);
        Sintoma sintoma = sintomaMapper.toEntity(sintomaDTO);
        sintoma = sintomaRepository.save(sintoma);
        return sintomaMapper.toDto(sintoma);
    }

    /**
     * Get all the sintomas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SintomaDTO> findAll() {
        log.debug("Request to get all Sintomas");
        return sintomaRepository.findAll().stream()
            .map(sintomaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the sintomas where HistoriaClinica is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SintomaDTO> findAllWhereHistoriaClinicaIsNull() {
        log.debug("Request to get all sintomas where HistoriaClinica is null");
        return StreamSupport
            .stream(sintomaRepository.findAll().spliterator(), false)
            .filter(sintoma -> sintoma.getHistoriaClinica() == null)
            .map(sintomaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sintoma by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SintomaDTO findOne(Long id) {
        log.debug("Request to get Sintoma : {}", id);
        Sintoma sintoma = sintomaRepository.findOne(id);
        return sintomaMapper.toDto(sintoma);
    }

    /**
     * Delete the sintoma by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sintoma : {}", id);
        sintomaRepository.delete(id);
    }
}
