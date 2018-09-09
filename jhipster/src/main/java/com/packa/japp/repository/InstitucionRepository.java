package com.packa.japp.repository;

import com.packa.japp.domain.Institucion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Institucion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstitucionRepository extends JpaRepository<Institucion, Long> {

}
