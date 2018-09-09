package com.packa.japp.service.mapper;

import com.packa.japp.domain.*;
import com.packa.japp.service.dto.MedicoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Medico and its DTO MedicoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedicoMapper extends EntityMapper<MedicoDTO, Medico> {

    

    @Mapping(target = "historiaClinicas", ignore = true)
    Medico toEntity(MedicoDTO medicoDTO);

    default Medico fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medico medico = new Medico();
        medico.setId(id);
        return medico;
    }
}
