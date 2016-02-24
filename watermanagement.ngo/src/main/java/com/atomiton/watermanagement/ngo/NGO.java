// Copyright 2010-2014 Oleg Danilov (a.k.a ODA, Novos40) under the terms of 
// the MIT license found at http://www.opensource.org/licenses/mit-license.html
package com.atomiton.watermanagement.ngo;

/**
 * The purpose of this class is to install all instruments and hire attendants on all water sources. 
 * @author GS-1045
 */
public class NGO {

	/**
	 * Main method to start NGO Services.
	 * @param args
	 */
	public static void main(String[] args) {
		NGOServices ngoServices = new NGOServices();
        ngoServices.installedAllInstumentsAndHireAttendent();
		/*Resource.getAllMobileVendor();*/
	}

}
