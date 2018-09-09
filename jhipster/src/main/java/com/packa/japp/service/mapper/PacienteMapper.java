package com.packa.japp.service.mapper;

import com.packa.japp.domain.*;
import com.packa.japp.service.dto.PacienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Paciente and its DTO PacienteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PacienteMapper extends EntityMapper<PacienteDTO, Paciente> {

    

    @Mapping(target = "historiaClinicas", ignore = true)
    Paciente toEntity(PacienteDTO pacienteDTO);

    default Paciente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Paciente paciente = new Paciente();
        paciente.setId(id);
        return paciente;
    }
}
