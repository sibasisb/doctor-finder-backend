package com.doctorapps.DoctorFinder.services;

import com.doctorapps.DoctorFinder.models.Doctor;
import com.doctorapps.DoctorFinder.models.Institute;

public interface DoctorDaoService {
	
	public Doctor addDoctor(Doctor newDoctor);
	public Institute assignDoctorToInstitute(String instituteId, String doctorId);
	public Doctor findDoctorByDoctorId(String doctorId);
	public Doctor deleteDoctorByDoctorId(String doctorId);
	public Doctor deleteDoctorFromInstitute(String instituteId, String doctorId);
	public Doctor updateDoctor(Doctor doctor);
	
}
