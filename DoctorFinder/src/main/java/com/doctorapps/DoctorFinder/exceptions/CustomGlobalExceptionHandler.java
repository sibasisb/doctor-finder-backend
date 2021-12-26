package com.doctorapps.DoctorFinder.exceptions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.doctorapps.DoctorFinder.util.ExceptionResponse;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ExceptionResponse exceptionResponse;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		HashMap<String, List<String>> errorsMap = new LinkedHashMap<String, List<String>>();
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
		errorsMap.put("errors",errors);
		exceptionResponse.setMessage(errorsMap);
		exceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
		exceptionResponse.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<Object>(exceptionResponse, exceptionResponse.getStatus());
	}

	@ExceptionHandler(DoctorNotFoundException.class)
	public ResponseEntity<Object> handleDoctorNotFoundException(Exception ex, WebRequest request) {
		HashMap<String, List<String>> errorsMap = new LinkedHashMap<String, List<String>>();
		List<String> errors = Arrays.asList(ex.getMessage());
		errorsMap.put("errors",errors);
		exceptionResponse.setMessage(errorsMap);
		exceptionResponse.setStatus(HttpStatus.NOT_FOUND);
		exceptionResponse.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<Object>(exceptionResponse, exceptionResponse.getStatus());
	}

	@ExceptionHandler(InstituteNotFoundException.class)
	public ResponseEntity<Object> handleInstituteNotFoundException(Exception ex, WebRequest request) {
		HashMap<String, List<String>> errorsMap = new LinkedHashMap<String, List<String>>();
		List<String> errors = Arrays.asList(ex.getMessage());
		errorsMap.put("errors",errors);
		exceptionResponse.setMessage(errorsMap);
		exceptionResponse.setStatus(HttpStatus.NOT_FOUND);
		exceptionResponse.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<Object>(exceptionResponse, exceptionResponse.getStatus());
	}

	@ExceptionHandler(AddressNotFoundException.class)
	public ResponseEntity<Object> handleAddressNotFoundException(Exception ex, WebRequest request) {
		HashMap<String, List<String>> errorsMap = new LinkedHashMap<String, List<String>>();
		List<String> errors = Arrays.asList(ex.getMessage());
		errorsMap.put("errors",errors);
		exceptionResponse.setMessage(errorsMap);
		exceptionResponse.setStatus(HttpStatus.NOT_FOUND);
		exceptionResponse.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<Object>(exceptionResponse, exceptionResponse.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
		HashMap<String, List<String>> errorsMap = new LinkedHashMap<String, List<String>>();
		List<String> errors = Arrays.asList(ex.getMessage());
		errorsMap.put("errors",errors);
		exceptionResponse.setMessage(errorsMap);
		exceptionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		exceptionResponse.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<Object>(exceptionResponse, exceptionResponse.getStatus());
	}

}
