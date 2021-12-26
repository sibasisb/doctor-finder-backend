package com.doctorapps.DoctorFinder.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="address")
public class Address {

	@Id
	@Column(name="address_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	
	@NotEmpty(message = "Address line 1 cannot be empty")
	@Size(min = 2, max = 200, message = "Address line 1 should be between 2 and 200 characters")
	@Column(name="address_line_1")
	private String addressLine1;
	
	@Column(name="address_line_2")
	private String addressLine2;
	
	@NotEmpty(message = "City cannot be empty")
	@Size(min = 2, max = 200, message = "City should be between 2 and 200 characters")
	@Column(name="city")
	private String city;
	
	@NotEmpty(message = "Pincode cannot be empty")
	@Size(min = 2, max = 200, message = "Pincode should be between 2 and 200 characters")
	@Column(name="pincode")
	private String pincode;
	
	@NotNull(message = "Latitude needs to be present")
	@DecimalMin(value = "-90", message = "Latitude cannot be lesser than 90 degrees")
	@DecimalMax(value = "90", message = "Latitude cannot be greater than 90 degrees")
	@Column(name="latitude")
	private Double latitude;
	
	@NotNull(message = "Longitude needs to be present")
	@DecimalMin(value = "-180", message = "Longitude cannot be lesser than -180 degrees")
	@DecimalMax(value = "180", message = "Longitude cannot be greater than 180 degrees")
	@Column(name="longitude")
	private Double longitude;
	
	@OneToOne(mappedBy = "address")
	private Institute institute;
	
}
