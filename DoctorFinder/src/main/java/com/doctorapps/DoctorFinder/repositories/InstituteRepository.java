package com.doctorapps.DoctorFinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorapps.DoctorFinder.models.Institute;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, String> {

}
