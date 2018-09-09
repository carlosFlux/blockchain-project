package com.packa.japp.repository;

import com.packa.japp.domain.Sintoma;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sintoma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SintomaRepository extends JpaRepository<Sintoma, Long> {

}
