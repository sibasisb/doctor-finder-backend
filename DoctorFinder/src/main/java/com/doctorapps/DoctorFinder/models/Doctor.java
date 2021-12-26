package com.doctorapps.DoctorFinder.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.doctorapps.DoctorFinder.util.IdGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "doctor")
public class Doctor {

	@Id
	@Column(name = "doctor_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_seq")
	@GenericGenerator(name = "doctor_seq", strategy = "com.doctorapps.DoctorFinder.util.IdGenerator", parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "DOC"),
			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d") })
	private String doctorId;

	@NotEmpty(message = "Speciality cannot be empty")
	@Size(min = 3, max = 200, message = "Speciality should be between 3 and 200 characters")
	@Column(name = "speciality")
	private String speciality;

	@NotNull(message = "Doctor's fees cannot be empty")
	@Column(name = "fees")
	private float fees;

	@NotEmpty(message = "First name cannot be empty")
	@Size(min = 2, max = 120, message = "First name should be between 2 and 120 characters")
	@Column(name = "first_name")
	private String firstName;

	@NotEmpty(message = "Last name cannot be empty")
	@Size(min = 2, max = 120, message = "Last name should be between 2 and 120 characters")
	@Column(name = "last_name")
	private String lastName;

	@JsonIgnore
	@ManyToMany(mappedBy = "doctors")
	private Set<Institute> institutes;

	public void deleteDoctorFromInstitutes() {
		for (Institute associatedInstitute : this.institutes) {
			associatedInstitute.getDoctors().remove(this);
		}
		this.institutes.removeAll(getInstitutes());
	}

}
