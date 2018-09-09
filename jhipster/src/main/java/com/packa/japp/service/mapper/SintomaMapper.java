package com.packa.japp.service.mapper;

import com.packa.japp.domain.*;
import com.packa.japp.service.dto.SintomaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sintoma and its DTO SintomaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SintomaMapper extends EntityMapper<SintomaDTO, Sintoma> {

    

    @Mapping(target = "historiaClinica", ignore = true)
    Sintoma toEntity(SintomaDTO sintomaDTO);

    default Sintoma fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sintoma sintoma = new Sintoma();
        sintoma.setId(id);
        return sintoma;
    }
}
