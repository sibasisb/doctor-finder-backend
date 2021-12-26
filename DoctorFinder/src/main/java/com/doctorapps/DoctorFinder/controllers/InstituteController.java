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

import com.doctorapps.DoctorFinder.models.Institute;
import com.doctorapps.DoctorFinder.services.InstituteDaoService;

@RestController
public class InstituteController {

	@Autowired
	private InstituteDaoService instituteDaoService;

	@PostMapping(value = "/institutes")
	public ResponseEntity<Object> registerInstitute(@RequestBody @Valid Institute instituteData) {
		Institute savedInstitute = instituteDaoService.addInstitute(instituteData);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedInstitute.getInstituteId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping(value = "/institutes/{instituteId}")
	public ResponseEntity<Object> findInstituteById(@PathVariable("instituteId") String instituteId) {
		if (instituteId == null) {
			return new ResponseEntity<Object>("Institute id cannot be null",HttpStatus.BAD_REQUEST);
		}
		Institute attachedInstitute = instituteDaoService.findInstituteByInstituteId(instituteId);
		return ResponseEntity.ok(attachedInstitute);
	}

	@PutMapping(value = "/institutes")
	public ResponseEntity<Object> updateInstitute(@RequestBody @Valid Institute newInstitute) {
		if (newInstitute.getInstituteId() == null) {
			return new ResponseEntity<Object>("Institute id cannot be null",HttpStatus.BAD_REQUEST);
		}
		Institute updatedInstitute = instituteDaoService.updateInstitute(newInstitute);
		return new ResponseEntity<Object>(updatedInstitute, HttpStatus.OK);
	}

	@DeleteMapping(value = "/institutes/{instituteId}")
	public ResponseEntity<Object> deleteInstituteById(@PathVariable("instituteId") String instituteId) {
		if (instituteId == null) {
			return new ResponseEntity<Object>("Institute id cannot be null",HttpStatus.BAD_REQUEST);
		}
		Institute deletedInstitute = instituteDaoService.deleteInstituteById(instituteId);
		return new ResponseEntity<Object>(deletedInstitute, HttpStatus.OK);
	}

}
