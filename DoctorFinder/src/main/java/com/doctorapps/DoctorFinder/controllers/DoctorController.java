package com.doctorapps.DoctorFinder.controllers;

import java.net.URI;

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

import com.doctorapps.DoctorFinder.models.Doctor;
import com.doctorapps.DoctorFinder.models.Institute;
import com.doctorapps.DoctorFinder.services.DoctorDaoService;

@RestController
public class DoctorController {

	@Autowired
	private DoctorDaoService doctorDaoService;

	@PostMapping(value = "/doctors")
	public ResponseEntity<Object> registerDoctor(@RequestBody @Valid Doctor doctorData) {
		Doctor savedDoctor = doctorDaoService.addDoctor(doctorData);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedDoctor.getDoctorId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PostMapping(value = "/institutes/{instituteId}/doctors")
	public ResponseEntity<Object> registerDoctorByInstitute(@PathVariable("instituteId") String instituteId,
			@RequestBody @Valid Doctor doctorData) {
		if (instituteId == null) {
			return new ResponseEntity<Object>("Institute id cannot be null", HttpStatus.BAD_REQUEST);
		}
		Doctor savedDoctor = doctorDaoService.addDoctor(doctorData);
		doctorDaoService.assignDoctorToInstitute(instituteId, doctorData.getDoctorId());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedDoctor.getDoctorId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PostMapping(value = "/institutes/{instituteId}/doctors/{doctorId}")
	public ResponseEntity<Object> registerDoctorToInstitute(@PathVariable("instituteId") String instituteId,
			@PathVariable("doctorId") String doctorId) {
		if (instituteId == null || doctorId == null) {
			return new ResponseEntity<Object>("Doctor id and institute id cannot be null", HttpStatus.BAD_REQUEST);
		}
		Institute attachedInstitute = doctorDaoService.assignDoctorToInstitute(instituteId, doctorId);

		return ResponseEntity.ok(attachedInstitute);
	}

	@GetMapping(value = "/doctors/{doctorId}")
	public ResponseEntity<Object> findDoctorById(@PathVariable("doctorId") String doctorId) {
		if (doctorId == null) {
			return new ResponseEntity<Object>("Doctor id cannot be null", HttpStatus.BAD_REQUEST);
		}
		Doctor doctor = doctorDaoService.findDoctorByDoctorId(doctorId);
		return ResponseEntity.ok(doctor);
	}

	@PutMapping(value = "/doctors")
	public ResponseEntity<Object> updateDoctor(@RequestBody @Valid Doctor doctorInfo) {
		if (doctorInfo.getDoctorId() == null) {
			return new ResponseEntity<Object>("Doctor id cannot be null", HttpStatus.BAD_REQUEST);
		}
		Doctor updatedDoctor = doctorDaoService.updateDoctor(doctorInfo);
		return ResponseEntity.ok(updatedDoctor);
	}

	@DeleteMapping(value = "/doctors/{doctorId}")
	public ResponseEntity<Object> deleteDoctorById(@PathVariable("doctorId") String doctorId) {
		if (doctorId == null) {
			return new ResponseEntity<Object>("Doctor id cannot be null", HttpStatus.BAD_REQUEST);
		}
		Doctor deletedDoctor = doctorDaoService.deleteDoctorByDoctorId(doctorId);
		return ResponseEntity.ok(deletedDoctor);
	}

	@DeleteMapping(value = "/institutes/{instituteId}/doctors/{doctorId}")
	public ResponseEntity<Object> deleteDoctorFromInstitute(@PathVariable("instituteId") String instituteId,
			@PathVariable("doctorId") String doctorId) {
		if (instituteId == null || doctorId == null) {
			return new ResponseEntity<Object>("Doctor id and institute id cannot be null", HttpStatus.BAD_REQUEST);
		}
		Doctor deletedDoctor = doctorDaoService.deleteDoctorFromInstitute(instituteId, doctorId);
		if(deletedDoctor == null) {
			return new ResponseEntity<Object>("Institute does not have this doctor", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(deletedDoctor);
	}

}
