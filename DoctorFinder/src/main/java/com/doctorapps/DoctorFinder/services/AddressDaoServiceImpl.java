package com.doctorapps.DoctorFinder.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorapps.DoctorFinder.DTO.DoctorsInfoDTO;
import com.doctorapps.DoctorFinder.exceptions.AddressNotFoundException;
import com.doctorapps.DoctorFinder.exceptions.InstituteNotFoundException;
import com.doctorapps.DoctorFinder.models.Address;
import com.doctorapps.DoctorFinder.models.Institute;
import com.doctorapps.DoctorFinder.repositories.AddressRepository;
import com.doctorapps.DoctorFinder.repositories.InstituteRepository;
import com.doctorapps.DoctorFinder.util.AddressUtil;
import com.doctorapps.DoctorFinder.util.DistanceUtil;

@Service
public class AddressDaoServiceImpl implements AddressDaoService {

	@Autowired
	private InstituteRepository instituteRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	@Transactional
	public Address createAddress(String instituteId, Address addressInfo) {
		Optional<Institute> attachedInstitute = instituteRepository.findById(instituteId);
		if (!attachedInstitute.isPresent()) {
			String message = "Institute Not Found with given ID";
			throw new InstituteNotFoundException(message);
		}
		Institute associatedInstitute = attachedInstitute.get();
		associatedInstitute.setAddress(addressInfo);
		Institute updatedInstitute = instituteRepository.save(associatedInstitute);
		return updatedInstitute.getAddress();
	}

	@Override
	@Transactional
	public Address getAddressFromInstitute(String instituteId) {
		Optional<Institute> attachedInstitute = instituteRepository.findById(instituteId);
		if (!attachedInstitute.isPresent()) {
			String message = "Institute Not Found with given ID";
			throw new InstituteNotFoundException(message);
		}
		Institute associatedInstitute = attachedInstitute.get();
		return associatedInstitute.getAddress();
	}

	@Override
	@Transactional
	public Address updateAddressForInstitute(String instituteId, Address addressInfo) {
		Optional<Institute> attachedInstitute = instituteRepository.findById(instituteId);
		if (!attachedInstitute.isPresent()) {
			String message = "Institute Not Found with given ID";
			throw new InstituteNotFoundException(message);
		}
		Institute associatedInstitute = attachedInstitute.get();
		associatedInstitute.setAddress(addressInfo);
		Institute updatedInstitute = instituteRepository.save(associatedInstitute);
		return updatedInstitute.getAddress();
	}

	@Override
	@Transactional
	public Address deleteAddressFromInstitute(String instituteId) {
		Optional<Institute> attachedInstitute = instituteRepository.findById(instituteId);
		if (!attachedInstitute.isPresent()) {
			String message = "Institute Not Found with given ID";
			throw new InstituteNotFoundException(message);
		}
		Institute associatedInstitute = attachedInstitute.get();
		Address oldAddress = associatedInstitute.getAddress();
		associatedInstitute.setAddress(null);
		instituteRepository.save(associatedInstitute);
		if (oldAddress == null) {
			String message = "Address Not Found with associated institute";
			throw new AddressNotFoundException(message);
		}
		addressRepository.deleteById(oldAddress.getAddressId());
		return oldAddress;
	}
	
	public List<AddressUtil> getClosestAddresses(Address userAddress) {
		Set<Address> addresses = addressRepository.findAll().stream().collect(Collectors.toSet());
		List<AddressUtil> contendingAddresses = AddressUtil.getInitialAddressList();
		addresses.stream().forEach(address->{
			double distance = DistanceUtil.getDistanceBetweenAddresses(address, userAddress);
			double definedLimit = 1.5;
			if (distance <= definedLimit) {
				contendingAddresses.add(AddressUtil.builder().addressId(address.getAddressId()).distance(distance).build());
			}
		});
		Collections.sort(contendingAddresses);
		return contendingAddresses;
	}

	@Override
	@Transactional
	public List<DoctorsInfoDTO> getClosestDoctors(Address userAddress) {
		List<AddressUtil> closestAddresses = getClosestAddresses(userAddress);
		List<DoctorsInfoDTO> closestDoctors = new ArrayList<DoctorsInfoDTO>();
		closestAddresses.stream().forEach(closestAddress -> {
			Optional<Address> attachedAddress = addressRepository.findById(closestAddress.getAddressId());
			if (!attachedAddress.isPresent()) {
				String message = "Address Not Found with associated institute";
				throw new AddressNotFoundException(message);
			}
			Address associatedAddress = attachedAddress.get();
			Institute associatedInstitute = associatedAddress.getInstitute();
			associatedInstitute.getDoctors().stream().forEach(associatedDoctor -> {
				DoctorsInfoDTO closestDoctorEntry = DoctorsInfoDTO.builder().addressId(associatedAddress.getAddressId())
						.addressLine1(associatedAddress.getAddressLine1())
						.addressLine2(associatedAddress.getAddressLine2())
						.city(associatedAddress.getCity())
						.pincode(associatedAddress.getPincode())
						.latitude(associatedAddress.getLatitude())
						.longitude(associatedAddress.getLongitude())
						.instituteId(associatedInstitute.getInstituteId())
						.instituteName(associatedInstitute.getInstituteName())
						.doctorId(associatedDoctor.getDoctorId())
						.fees(associatedDoctor.getFees())
						.firstName(associatedDoctor.getFirstName())
						.lastName(associatedDoctor.getLastName())
						.speciality(associatedDoctor.getSpeciality())
						.build();
				closestDoctors.add(closestDoctorEntry);
			});
		});
		return closestDoctors;
	}
	
}
