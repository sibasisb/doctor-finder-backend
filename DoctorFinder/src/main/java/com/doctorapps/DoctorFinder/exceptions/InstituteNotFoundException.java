package com.doctorapps.DoctorFinder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InstituteNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InstituteNotFoundException(String message) {
		super(message);
	}
	
}
