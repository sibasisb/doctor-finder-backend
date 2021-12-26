package com.doctorapps.DoctorFinder.services;

import java.util.List;

import com.doctorapps.DoctorFinder.DTO.DoctorsInfoDTO;
import com.doctorapps.DoctorFinder.models.Address;

public interface AddressDaoService {

	public Address createAddress(String instituteId, Address addressInfo);
	public Address getAddressFromInstitute(String instituteId);
	public Address updateAddressForInstitute(String instituteId, Address addressInfo);
	public Address deleteAddressFromInstitute(String instituteId);
	public List<DoctorsInfoDTO> getClosestDoctors(Address userAddress);
	
}
