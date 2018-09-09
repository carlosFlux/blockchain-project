package com.packa.japp.service.mapper;

import com.packa.japp.domain.*;
import com.packa.japp.service.dto.InstitucionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Institucion and its DTO InstitucionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InstitucionMapper extends EntityMapper<InstitucionDTO, Institucion> {

    

    @Mapping(target = "historiaClinicas", ignore = true)
    Institucion toEntity(InstitucionDTO institucionDTO);

    default Institucion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Institucion institucion = new Institucion();
        institucion.setId(id);
        return institucion;
    }
}
