package com.doctorapps.DoctorFinder.services;

import com.doctorapps.DoctorFinder.models.Institute;

public interface InstituteDaoService {

	public Institute findInstituteByInstituteId(String instituteId);
	public Institute addInstitute(Institute institute);
	public Institute deleteInstituteById(String insituteId);
	public Institute updateInstitute(Institute institute);
	
}
