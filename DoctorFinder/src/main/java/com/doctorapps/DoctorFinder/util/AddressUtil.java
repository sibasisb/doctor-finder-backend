package com.doctorapps.DoctorFinder.util;

import java.util.ArrayList;

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
public class AddressUtil implements Comparable<AddressUtil> {

	private long addressId;
	private double distance;

	public static ArrayList<AddressUtil> getInitialAddressList() {
		return new ArrayList<AddressUtil>();
	}

	public int compareTo(AddressUtil location) {
		return (this.distance - location.distance) >= 0 ? 1 : -1;
	}

}
