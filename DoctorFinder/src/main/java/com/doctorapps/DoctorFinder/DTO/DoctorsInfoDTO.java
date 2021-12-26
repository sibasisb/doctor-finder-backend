package com.doctorapps.DoctorFinder.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorsInfoDTO {

	private String doctorId;

	private String speciality;

	private float fees;

	private String firstName;

	private String lastName;
	
	private String instituteId;

	private String instituteName;

	private Long addressId;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String city;
	
	private String pincode;
	
	private Double latitude;
	
	private Double longitude;
	
}
