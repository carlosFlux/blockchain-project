package com.packa.japp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the HistoriaClinica entity.
 */
public class HistoriaClinicaDTO implements Serializable {

    private Long id;

    private Long medicoId;

    private Long institucionId;

    private String institucionNombre;

    private Long pacienteId;

    private String pacienteDni;

    private Long sintomaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public Long getInstitucionId() {
        return institucionId;
    }

    public void setInstitucionId(Long institucionId) {
        this.institucionId = institucionId;
    }

    public String getInstitucionNombre() {
        return institucionNombre;
    }

    public void setInstitucionNombre(String institucionNombre) {
        this.institucionNombre = institucionNombre;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getPacienteDni() {
        return pacienteDni;
    }

    public void setPacienteDni(String pacienteDni) {
        this.pacienteDni = pacienteDni;
    }

    public Long getSintomaId() {
        return sintomaId;
    }

    public void setSintomaId(Long sintomaId) {
        this.sintomaId = sintomaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HistoriaClinicaDTO historiaClinicaDTO = (HistoriaClinicaDTO) o;
        if(historiaClinicaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historiaClinicaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HistoriaClinicaDTO{" +
            "id=" + getId() +
            "}";
    }
}
