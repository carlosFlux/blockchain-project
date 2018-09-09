package com.packa.japp.service.mapper;

import com.packa.japp.domain.*;
import com.packa.japp.service.dto.HistoriaClinicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HistoriaClinica and its DTO HistoriaClinicaDTO.
 */
@Mapper(componentModel = "spring", uses = {MedicoMapper.class, InstitucionMapper.class, PacienteMapper.class, SintomaMapper.class})
public interface HistoriaClinicaMapper extends EntityMapper<HistoriaClinicaDTO, HistoriaClinica> {

    @Mapping(source = "medico.id", target = "medicoId")
    @Mapping(source = "institucion.id", target = "institucionId")
    @Mapping(source = "institucion.nombre", target = "institucionNombre")
    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "paciente.dni", target = "pacienteDni")
    @Mapping(source = "sintoma.id", target = "sintomaId")
    HistoriaClinicaDTO toDto(HistoriaClinica historiaClinica); 

    @Mapping(source = "medicoId", target = "medico")
    @Mapping(source = "institucionId", target = "institucion")
    @Mapping(source = "pacienteId", target = "paciente")
    @Mapping(source = "sintomaId", target = "sintoma")
    HistoriaClinica toEntity(HistoriaClinicaDTO historiaClinicaDTO);

    default HistoriaClinica fromId(Long id) {
        if (id == null) {
            return null;
        }
        HistoriaClinica historiaClinica = new HistoriaClinica();
        historiaClinica.setId(id);
        return historiaClinica;
    }
}
