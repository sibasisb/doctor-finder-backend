package com.doctorapps.DoctorFinder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AddressNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6081327051186355000L;

	public AddressNotFoundException(String message) {
		super(message);
	}
	
}
