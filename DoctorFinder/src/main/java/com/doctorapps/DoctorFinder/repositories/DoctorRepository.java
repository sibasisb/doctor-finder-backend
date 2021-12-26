package com.doctorapps.DoctorFinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorapps.DoctorFinder.models.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {

}
