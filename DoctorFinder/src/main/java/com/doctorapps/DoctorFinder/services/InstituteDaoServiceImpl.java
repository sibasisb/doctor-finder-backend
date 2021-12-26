package com.doctorapps.DoctorFinder.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorapps.DoctorFinder.exceptions.InstituteNotFoundException;
import com.doctorapps.DoctorFinder.models.Institute;
import com.doctorapps.DoctorFinder.repositories.InstituteRepository;

@Service
public class InstituteDaoServiceImpl implements InstituteDaoService {

	@Autowired
	private InstituteRepository instituteRepository;
	
	@Override
	@Transactional
	public Institute findInstituteByInstituteId(String instituteId) {
		Optional<Institute> attachedInstitute = instituteRepository.findById(instituteId);
		if (!attachedInstitute.isPresent()) {
			String message = "Institute Not Found with given ID";
			throw new InstituteNotFoundException(message);
		}
		return attachedInstitute.get();
	}

	@Override
	@Transactional
	public Institute addInstitute(Institute newInstitute) {
		Institute savedInstitute = instituteRepository.save(newInstitute);
		return savedInstitute;
	}

	@Override
	@Transactional
	public Institute updateInstitute(Institute institute) {
		Optional<Institute> attachedInstitute = instituteRepository.findById(institute.getInstituteId());
		if (!attachedInstitute.isPresent()) {
			String message = "Institute Not Found with given ID";
			throw new InstituteNotFoundException(message);
		}
		Institute updatedInstitute = attachedInstitute.get();
		updatedInstitute.setInstituteName(institute.getInstituteName());
		return instituteRepository.save(updatedInstitute);
	}
	
	@Override
	@Transactional
	public Institute deleteInstituteById(String instituteId) {
		Optional<Institute> attachedInstitute = instituteRepository.findById(instituteId);
		if (!attachedInstitute.isPresent()) {
			String message = "Institute Not Found with given ID";
			throw new InstituteNotFoundException(message);
		}
		Institute associatedInstitute = attachedInstitute.get();
		associatedInstitute.removeAssociatedDoctors();
		instituteRepository.save(associatedInstitute);
		instituteRepository.deleteById(instituteId);
		return attachedInstitute.get();
	}

}
