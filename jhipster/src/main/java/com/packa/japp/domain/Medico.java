package com.packa.japp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Medico.
 */
@Entity
@Table(name = "medico")
public class Medico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "especialidad")
    private String especialidad;

    @OneToMany(mappedBy = "medico")
    @JsonIgnore
    private Set<HistoriaClinica> historiaClinicas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Medico nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public Medico especialidad(String especialidad) {
        this.especialidad = especialidad;
        return this;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Set<HistoriaClinica> getHistoriaClinicas() {
        return historiaClinicas;
    }

    public Medico historiaClinicas(Set<HistoriaClinica> historiaClinicas) {
        this.historiaClinicas = historiaClinicas;
        return this;
    }

    public Medico addHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinicas.add(historiaClinica);
        historiaClinica.setMedico(this);
        return this;
    }

    public Medico removeHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinicas.remove(historiaClinica);
        historiaClinica.setMedico(null);
        return this;
    }

    public void setHistoriaClinicas(Set<HistoriaClinica> historiaClinicas) {
        this.historiaClinicas = historiaClinicas;
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
        Medico medico = (Medico) o;
        if (medico.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medico.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medico{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", especialidad='" + getEspecialidad() + "'" +
            "}";
    }
}
