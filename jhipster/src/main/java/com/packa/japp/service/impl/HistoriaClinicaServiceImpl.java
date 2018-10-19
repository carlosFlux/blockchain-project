package com.packa.japp.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.packa.japp.domain.HistoriaClinica;
import com.packa.japp.naivechain.BlockChainRepository;
import com.packa.japp.repository.HistoriaClinicaRepository;
import com.packa.japp.service.HistoriaClinicaService;
import com.packa.japp.service.dto.HistoriaClinicaDTO;
import com.packa.japp.service.dto.OperationWrapper;
import com.packa.japp.service.dto.OperationStatusEnum;
import com.packa.japp.service.mapper.HistoriaClinicaMapper;

/**
 * Service Implementation for managing HistoriaClinica.
 */
@Service
@Transactional
public class HistoriaClinicaServiceImpl implements HistoriaClinicaService {

	private final Logger log = LoggerFactory.getLogger(HistoriaClinicaServiceImpl.class);

	private final HistoriaClinicaRepository historiaClinicaRepository;

	private final HistoriaClinicaMapper historiaClinicaMapper;

	@Autowired
	private BlockChainRepository blockChainRepository;

	public HistoriaClinicaServiceImpl(HistoriaClinicaRepository historiaClinicaRepository,
			HistoriaClinicaMapper historiaClinicaMapper) {
		this.historiaClinicaRepository = historiaClinicaRepository;
		this.historiaClinicaMapper = historiaClinicaMapper;
	}

	/**
	 * Save a historiaClinica.
	 *
	 * @param historiaClinicaDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public OperationWrapper<HistoriaClinicaDTO> save(HistoriaClinicaDTO historiaClinicaDTO) {
		log.debug("Request to save HistoriaClinica : {}", historiaClinicaDTO);
		OperationWrapper<HistoriaClinicaDTO> wrapper = new OperationWrapper<HistoriaClinicaDTO>();
		HistoriaClinica historiaClinica = this.saveHc(historiaClinicaDTO);
		HistoriaClinicaDTO dto = historiaClinicaMapper.toDto(historiaClinica);
		wrapper.setOperationResult(dto);

		try {
			String resultado = blockChainRepository.save(historiaClinica);
			log.debug("El blockchain devolvio --> ", resultado);
			dto = historiaClinicaMapper.toDto(historiaClinica);
			wrapper.setMessage(resultado);
			wrapper.setStatus(OperationStatusEnum.OK);

		} catch (Exception e) {
			log.debug("Error en el blockchain --> ", e.getMessage() + " -- " + e.getCause());
			wrapper.setMessage("Error en el blockchain --> " + e.getMessage() + " -- " + e.getCause());
			wrapper.setStatus(OperationStatusEnum.ERROR);
		}

		return wrapper;
	}

	private HistoriaClinica saveHc(HistoriaClinicaDTO historiaClinicaDTO) {
		HistoriaClinica historiaClinica = historiaClinicaMapper.toEntity(historiaClinicaDTO);
		historiaClinica = historiaClinicaRepository.save(historiaClinica);
		return historiaClinica;
	}

	/**
	 * Get all the historiaClinicas.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public OperationWrapper<List<HistoriaClinicaDTO>> findAll() {
		log.debug("Request to get all HistoriaClinicas");
		List<HistoriaClinicaDTO> listaLocal = historiaClinicaRepository.findAll().stream()
				.map(historiaClinicaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));

		OperationWrapper<List<HistoriaClinicaDTO>> wrapper = new OperationWrapper<List<HistoriaClinicaDTO>>();

		try {
			List<HistoriaClinicaDTO> localHcsToSave = blockChainRepository.findAll(listaLocal);

			if (!localHcsToSave.isEmpty()) {
				for (HistoriaClinicaDTO hcItem : localHcsToSave) {
					this.saveHc(hcItem);
				}
			}
		} catch (Exception e) {
			log.debug("Error en el blockchain --> ", e.getMessage() + " -- " + e.getCause());
			wrapper.setMessage("Error en el blockchain --> " + e.getMessage() + " -- " + e.getCause());
			wrapper.setStatus(OperationStatusEnum.ERROR);
		}

		List<HistoriaClinicaDTO> operationResult = historiaClinicaRepository.findAll().stream()
				.map(historiaClinicaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
		wrapper.setOperationResult(operationResult);
		return wrapper;
	}

	/**
	 * Get one historiaClinica by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public HistoriaClinicaDTO findOne(Long id) {
		log.debug("Request to get HistoriaClinica : {}", id);
		HistoriaClinica historiaClinica = historiaClinicaRepository.findOne(id);
		return historiaClinicaMapper.toDto(historiaClinica);
	}

	/**
	 * Delete the historiaClinica by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete HistoriaClinica : {}", id);
		historiaClinicaRepository.delete(id);
	}

}
