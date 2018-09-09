package com.packa.japp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Institucion.
 */
@Entity
@Table(name = "institucion")
public class Institucion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "institucion")
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

    public Institucion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<HistoriaClinica> getHistoriaClinicas() {
        return historiaClinicas;
    }

    public Institucion historiaClinicas(Set<HistoriaClinica> historiaClinicas) {
        this.historiaClinicas = historiaClinicas;
        return this;
    }

    public Institucion addHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinicas.add(historiaClinica);
        historiaClinica.setInstitucion(this);
        return this;
    }

    public Institucion removeHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinicas.remove(historiaClinica);
        historiaClinica.setInstitucion(null);
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
        Institucion institucion = (Institucion) o;
        if (institucion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), institucion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Institucion{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
