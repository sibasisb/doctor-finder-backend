package com.doctorapps.DoctorFinder.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.doctorapps.DoctorFinder.DTO.DoctorsInfoDTO;
import com.doctorapps.DoctorFinder.models.Address;
import com.doctorapps.DoctorFinder.services.AddressDaoService;

@RestController
public class AddressController {

	@Autowired
	private AddressDaoService addressDaoService;

	@PostMapping(value = "/institutes/{instituteId}/addresses")
	public ResponseEntity<Object> createAddress(@PathVariable("instituteId") String instituteId,
			@RequestBody @Valid Address addressInfo) {
		if (instituteId == null) {
			return new ResponseEntity<Object>("Institute id cannot be null", HttpStatus.BAD_REQUEST);
		}
		Address createdAddress = addressDaoService.createAddress(instituteId, addressInfo);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{addressId}")
				.buildAndExpand(createdAddress.getAddressId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(value = "/institutes/{instituteId}/addresses")
	public ResponseEntity<Object> getAddressFromInstitute(@PathVariable("instituteId") String instituteId) {
		if (instituteId == null) {
			return new ResponseEntity<Object>("Institute id cannot be null", HttpStatus.BAD_REQUEST);
		}
		Address fetchedAddress = addressDaoService.getAddressFromInstitute(instituteId);
		return ResponseEntity.ok(fetchedAddress);
	}
	
	@PutMapping(value = "/institutes/{instituteId}/addresses")
	public ResponseEntity<Object> updateAddress(@PathVariable("instituteId") String instituteId,
			@RequestBody @Valid Address addressInfo) {
		if (instituteId == null) {
			return new ResponseEntity<Object>("Institute id cannot be null", HttpStatus.BAD_REQUEST);
		}
		if (addressInfo.getAddressId() == null) {
			return new ResponseEntity<Object>("Address id cannot be null", HttpStatus.BAD_REQUEST);
		}
		Address updatedAddress = addressDaoService.updateAddressForInstitute(instituteId, addressInfo);
		return ResponseEntity.ok(updatedAddress);
	}

	@DeleteMapping(value = "/institutes/{instituteId}/addresses")
	public ResponseEntity<Object> deleteAddressFromInstitute(@PathVariable("instituteId") String instituteId) {
		if (instituteId == null) {
			return new ResponseEntity<Object>("Institute id cannot be null", HttpStatus.BAD_REQUEST);
		}
		Address deletedAddress = addressDaoService.deleteAddressFromInstitute(instituteId);
		return ResponseEntity.ok(deletedAddress);
	}
	
	@GetMapping("/doctorsinfo")
	public ResponseEntity<Object> getClosestDoctors(@RequestBody @Valid Address userAddress) {
		List<DoctorsInfoDTO> closestDoctorsInfo = addressDaoService.getClosestDoctors(userAddress);
		return ResponseEntity.ok(closestDoctorsInfo);
	}
	
}
