package com.doctorapps.DoctorFinder.util;

import com.doctorapps.DoctorFinder.models.Address;

public class DistanceUtil {

	public static Double getDistanceBetweenAddresses(Address firstAddress, Address secondAddress) {
		double lat1 = firstAddress.getLatitude(), lat2 = secondAddress.getLatitude();
		double lon1 = firstAddress.getLongitude(), lon2 = secondAddress.getLongitude();
		double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
 
        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
 
        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.pow(Math.sin(dLon / 2), 2) *
                   Math.cos(lat1) *
                   Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
	}
	
}
