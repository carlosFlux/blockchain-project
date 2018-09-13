package com.packa.japp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Sintoma entity.
 */
public class SintomaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4682458584574423995L;

	private Long id;

	private String descripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SintomaDTO sintomaDTO = (SintomaDTO) o;
		if (sintomaDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sintomaDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SintomaDTO{" + "id=" + getId() + ", descripcion='" + getDescripcion() + "'" + "}";
	}
}
