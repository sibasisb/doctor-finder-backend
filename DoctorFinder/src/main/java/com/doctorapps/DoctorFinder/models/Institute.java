package com.doctorapps.DoctorFinder.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.doctorapps.DoctorFinder.util.IdGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "institute")
public class Institute {

	@Id
	@Column(name = "institute_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "institute_seq")
	@GenericGenerator(name = "institute_seq", strategy = "com.doctorapps.DoctorFinder.util.IdGenerator", parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "INS"),
			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d") })
	private String instituteId;

	@NotEmpty(message = "Institute name cannot be null")
	@Column(name = "institute_name")
	private String instituteName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "institute_address_id")
	private Address address;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "institute_doctor", joinColumns = { @JoinColumn(name = "institute_id") }, inverseJoinColumns = {
			@JoinColumn(name = "doctor_id") })
	private Set<Doctor> doctors = new HashSet<Doctor>();

	public boolean deleteDoctorFromInstitute(Doctor doctor) {
		boolean removedDoctor = this.doctors.remove(doctor);
		boolean removedInstitute = doctor.getInstitutes().remove(this);
		return removedDoctor && removedInstitute;
	}

	public void addDoctorToInstitute(Doctor doctor) {
		doctor.getInstitutes().add(this);
		Set<Doctor> doctorsSet = this.doctors;
		doctorsSet.add(doctor);
		this.setDoctors(doctorsSet);
	}

	public void removeAssociatedDoctors() {
		for(Doctor doctor: this.doctors) {
			doctor.getInstitutes().remove(this);
		}
		this.setDoctors(new HashSet<Doctor>());
	}

}
