package com.packa.japp.repository;

import com.packa.japp.domain.HistoriaClinica;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HistoriaClinica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {

}
