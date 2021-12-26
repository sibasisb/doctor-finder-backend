package com.doctorapps.DoctorFinder.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
public class ExceptionResponse {

	private HashMap<String, List<String>> message;
	
	private HttpStatus status;
	
	private LocalDateTime timestamp;
	
}
