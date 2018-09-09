package com.packa.japp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A HistoriaClinica.
 */
@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Medico medico;

    @ManyToOne
    private Institucion institucion;

    @ManyToOne
    private Paciente paciente;

    @OneToOne
    @JoinColumn(unique = true)
    private Sintoma sintoma;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public HistoriaClinica medico(Medico medico) {
        this.medico = medico;
        return this;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Institucion getInstitucion() {
        return institucion;
    }

    public HistoriaClinica institucion(Institucion institucion) {
        this.institucion = institucion;
        return this;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public HistoriaClinica paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Sintoma getSintoma() {
        return sintoma;
    }

    public HistoriaClinica sintoma(Sintoma sintoma) {
        this.sintoma = sintoma;
        return this;
    }

    public void setSintoma(Sintoma sintoma) {
        this.sintoma = sintoma;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HistoriaClinica historiaClinica = (HistoriaClinica) o;
        if (historiaClinica.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historiaClinica.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HistoriaClinica{" +
            "id=" + getId() +
            "}";
    }
}
