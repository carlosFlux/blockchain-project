package com.packa.japp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "dni")
    private Integer dni;

    @Column(name = "nacionalidad")
    private String nacionalidad;

    @OneToMany(mappedBy = "paciente")
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

    public Paciente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public Paciente edad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getDni() {
        return dni;
    }

    public Paciente dni(Integer dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public Paciente nacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
        return this;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Set<HistoriaClinica> getHistoriaClinicas() {
        return historiaClinicas;
    }

    public Paciente historiaClinicas(Set<HistoriaClinica> historiaClinicas) {
        this.historiaClinicas = historiaClinicas;
        return this;
    }

    public Paciente addHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinicas.add(historiaClinica);
        historiaClinica.setPaciente(this);
        return this;
    }

    public Paciente removeHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinicas.remove(historiaClinica);
        historiaClinica.setPaciente(null);
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
        Paciente paciente = (Paciente) o;
        if (paciente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paciente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", edad=" + getEdad() +
            ", dni=" + getDni() +
            ", nacionalidad='" + getNacionalidad() + "'" +
            "}";
    }
}
