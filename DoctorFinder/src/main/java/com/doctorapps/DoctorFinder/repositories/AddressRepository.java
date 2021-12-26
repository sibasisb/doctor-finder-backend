package com.doctorapps.DoctorFinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorapps.DoctorFinder.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
