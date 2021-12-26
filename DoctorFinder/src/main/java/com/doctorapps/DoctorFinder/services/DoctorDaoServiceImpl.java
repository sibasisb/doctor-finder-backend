package com.doctorapps.DoctorFinder.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorapps.DoctorFinder.exceptions.DoctorNotFoundException;
import com.doctorapps.DoctorFinder.exceptions.InstituteNotFoundException;
import com.doctorapps.DoctorFinder.models.Doctor;
import com.doctorapps.DoctorFinder.models.Institute;
import com.doctorapps.DoctorFinder.repositories.DoctorRepository;
import com.doctorapps.DoctorFinder.repositories.InstituteRepository;

@Service
public class DoctorDaoServiceImpl implements DoctorDaoService {

	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private InstituteRepository instituteRepository;

	@Override
	@Transactional
	public Doctor addDoctor(Doctor newDoctor) {
		Doctor savedDoctor = doctorRepository.save(newDoctor);
		return savedDoctor;
	}
	
	@Override
	@Transactional
	public Institute assignDoctorToInstitute(String instituteId, String doctorId) {
		Optional<Institute> attachedInstitute = instituteRepository.findById(instituteId);
		if (!attachedInstitute.isPresent()) {
			String message = "Institute Not Found with given ID";
			throw new InstituteNotFoundException(message);
		}
		Optional<Doctor> attachedDoctor = doctorRepository.findById(doctorId);
		if (!attachedDoctor.isPresent()) {
			String message = "Doctor Not Found with given ID";
			throw new DoctorNotFoundException(message);
		}
		Institute assigningInstitute = attachedInstitute.get();
		assigningInstitute.addDoctorToInstitute(attachedDoctor.get());
		Institute savedInstitute = instituteRepository.save(assigningInstitute);
		return savedInstitute;
	}

	@Override
	@Transactional
	public Doctor findDoctorByDoctorId(String doctorId) {
		Optional<Doctor> attachedDoctor = doctorRepository.findById(doctorId);
		if (!attachedDoctor.isPresent()) {
			String message = "Doctor Not Found with given ID";
			throw new DoctorNotFoundException(message);
		}
		return attachedDoctor.get();
	}

	@Override
	@Transactional
	public Doctor updateDoctor(Doctor doctor) {
		Optional<Doctor> attachedDoctor = doctorRepository.findById(doctor.getDoctorId());
		if (!attachedDoctor.isPresent()) {
			String message = "Doctor Not Found with given ID";
			throw new DoctorNotFoundException(message);
		}
		Doctor assignedDoctor = attachedDoctor.get().toBuilder().fees(doctor.getFees()).firstName(doctor.getFirstName())
				.lastName(doctor.getLastName()).speciality(doctor.getSpeciality()).build();
		Doctor updatedDoctor = doctorRepository.save(assignedDoctor);
		return updatedDoctor;
	}
	
	@Override
	@Transactional
	public Doctor deleteDoctorByDoctorId(String doctorId) {
		Optional<Doctor> attachedDoctor = doctorRepository.findById(doctorId);
		if (!attachedDoctor.isPresent()) {
			String message = "Doctor Not Found with given ID";
			throw new DoctorNotFoundException(message);
		}
		Doctor associatedDoctor = attachedDoctor.get(); 
		associatedDoctor.deleteDoctorFromInstitutes();
		doctorRepository.save(associatedDoctor);
		doctorRepository.deleteById(doctorId);
		return attachedDoctor.get();
	}

	@Override
	@Transactional
	public Doctor deleteDoctorFromInstitute(String instituteId, String doctorId) {
		Optional<Institute> attachedInstitute = instituteRepository.findById(instituteId);
		if (!attachedInstitute.isPresent()) {
			String message = "Institute Not Found with given ID";
			throw new InstituteNotFoundException(message);
		}
		Optional<Doctor> attachedDoctor = doctorRepository.findById(doctorId);
		if (!attachedDoctor.isPresent()) {
			String message = "Doctor Not Found with given ID";
			throw new DoctorNotFoundException(message);
		}
		Doctor associatedDoctor = attachedDoctor.get();
		Institute associatedInstitute = attachedInstitute.get();
		boolean flag = associatedInstitute.deleteDoctorFromInstitute(associatedDoctor);
		if(!flag) {
			return null;
		}
		instituteRepository.save(associatedInstitute);
		doctorRepository.save(associatedDoctor);
		return attachedDoctor.get();
	}

}
