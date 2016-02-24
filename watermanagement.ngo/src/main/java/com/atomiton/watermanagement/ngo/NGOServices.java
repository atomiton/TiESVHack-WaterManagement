// Copyright 2010-2014 Oleg Danilov (a.k.a ODA, Novos40) under the terms of 
// the MIT license found at http://www.opensource.org/licenses/mit-license.html
package com.atomiton.watermanagement.ngo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atomiton.watermanagement.ngo.model.*;
import com.atomiton.watermanagement.ngo.util.Constants;
import com.atomiton.watermanagement.ngo.util.HttpRequestResponseHandler;
import com.atomiton.watermanagement.ngo.util.WaterMgmtNGOUtility;

/**
 * The purpose of this class is to provide methods to install water meter, pressure sensor, EColiSensor 
 * @author pramodmali
 */
public class NGOServices {
	
	public static Map<String,String> wellEcoliSensorReadingMap = new HashMap<String, String>();
	public static Map<String, String> mobileVendorEcoliSensorReadingMap = new HashMap<String, String>();
	public static List<Residents> residents;
	public static List<Well> wells;
	public static List<Standpipe> standpipes;
	public static List<WaterSource> waterSources ;
	public static List<HouseholdReseller> householdResellers;
	public static List<MobileVendor> mobileVendors;
	
	static {
		residents = Resource.getAllResidents();
		wells = Resource.getAllWells();
		standpipes = Resource.getAllStandpipes();
		waterSources = getAllWaterSources();
		householdResellers = Resource.getAllHouseholdReseller();
		mobileVendors = Resource.getAllMobileVendor();
	}
	
	/**
	 * The purpose of this method to call methods to install water meter, pressure sensor, standpipe attendant and ecoli sensor.
	 */
	public void installedAllInstumentsAndHireAttendent() {
		System.out.println("Inside installedAllInstumentsAndHireAttendent");
		installedWaterMeterOnWell();
		installedPressureSensor();
		hireStandpipeAttendant();
		installedEcoliSensorForWell();
		certifyAllMobileVendors();
	}

	/**
	 * The method is use to install water meter on well.
	 */
	public void installedWaterMeterOnWell() {
		for(Well well : wells) {
			if(!well.isMetered()) {
				String query="<buyWellWaterMeter ><WellName >"+well.getName()+"</WellName ></buyWellWaterMeter >";
				try {
					HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method is used to install pressure sensor on stand pipe.
	 */
	public void installedPressureSensor() {
		for(Standpipe standpipe : standpipes) {
			if(!standpipe.isInstrumented()) {
				String query="<buyPressureSensor><StandPipeName>"+standpipe.getName()+"</StandPipeName></buyPressureSensor>";
				try {
					HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method is use to hire attendant to all stand pipes.
	 */
	public void hireStandpipeAttendant() {
		for(Standpipe standpipe : standpipes) {
			if(!standpipe.isAttendant()) {
				String query="<hireStandpipeAttendant><StandPipeName>"+standpipe.getName()+"</StandPipeName></hireStandpipeAttendant>";
				try {
					HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method is use to fire stand pipe attendant
	 */
	public void fireStandpipeAttendant() {
		double currentBalance =  getNGOCurrentBalance();
		for(Standpipe standpipe : standpipes) {
			if(standpipe.isAttendant() && currentBalance < 1000 ) {
				String query="<hireStandpipeAttendant><StandPipeName>"+standpipe.getName()+"</StandPipeName></hireStandpipeAttendant>";
				try {
					HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method is use to installed EcoliSensor for well. 
	 */
	public void installedEcoliSensorForWell() {
		for(Well well : wells) {
			if(well.getEcoliSensorId() == null || well.getEcoliSensorId().equals("")) {
				String query="<buyWellEColiSensor><WellName >"+well.getName()+"</WellName ></buyWellEColiSensor  >";
				try {
					HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method is to certify all mobile vendors.
	 */
	public void certifyAllMobileVendors() {
		String query = "<CertifyVendorsAll/>";
		try {
			HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method is to check total water demand
	 * @return
	 */
	private double getTotalWaterDemand() {
		String query = "<Query><Find><Residents><Id><ne></ne></Id></Residents></Find><Javascript><resp><Include>$Response.Message.Value</Include></resp>var resultList = resp.Find;var waterneed = 0;for each (var result in resultList.iterEntries(\"Result\")){var resultValue = result.getValue();  var count = resultValue.Residents.WaterNeed;waterneed = waterneed + count;}sffContext.setResponseData(\"Message.Value.TotalWaterDemand\", waterneed);</Javascript><DelResponseData key=\"Message.Value.Find\"/></Query>";
		try {
			String response = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
			String waterDemand = WaterMgmtNGOUtility.getXMLElementValue("TotalWaterDemand", response);
			System.out.println("Total Water Demand === "+waterDemand);
			return Double.parseDouble(waterDemand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

 /**
 * This method is to get current balance for NGO.
 * @return
 */
public double getNGOCurrentBalance() {
		String query = "<Query><Find><NGO><Id ne=''/></NGO></Find><SetResponseData key=\"Message.Value.CurrentBalance\" value=\"[:$Response.Message.Value.Find.Result.NGO.BudgetBalance:]\"/><DelResponseData key=\"Message.Value.Find\"/></Query>";
		double currentBalance = 0;
		try {
			String response = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
			currentBalance = Double.parseDouble(WaterMgmtNGOUtility.getXMLElementValue("CurrentBalance", response));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentBalance;
	}
  

 /**
 * This methos is use to provide subsidy to residents.
 * @param residents
 */
public void grantSubsidyToResident() {
		residents = Resource.getAllResidents();
		StringBuilder query= new StringBuilder(); 
		for (Residents resident : residents) {
			if (resident.getBudgetBalance() < 1) {
				query.append("<grantSubsidy><SubsidyAmount>1</SubsidyAmount><ResidentName>"+ resident.getName() + "</ResidentName></grantSubsidy>");
			}
		}
		try {
				HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
  }
	
	/**
	 * This method will start 1st round of water source allocation to all residents.
	 */
	public void startWaterSourceAllocationProcess(){
		System.out.println("============ Inside startWaterSourceAllocationProcess method =====================");
		int residentsCount = residents.size();
		int waterSourceCount = waterSources.size();
		int residentPerSource = residentsCount/waterSourceCount;
		if(residentsCount % waterSourceCount < 1) {
			residentPerSource ++;
		}else {
			residentPerSource = residentPerSource + 2; 
		}
		int ctr = 0;
		int i=0;
		StringBuilder suggestWaterSource = new StringBuilder();
		for (Residents res : residents) {
			String waterSourceId = waterSources.get(i).getWaterSourceID();
			ctr++;
			if(ctr >= residentPerSource*(i+1)) {
				i++;
			}
			res.setWaterSourceID(waterSourceId);
			System.out.println("waterSourceId for user =================== "+waterSourceId);
			suggestWaterSource.append("<suggestResidentWaterSource><WaterSourceID>"+waterSourceId+"</WaterSourceID><ResidentName>"+res.getName()+"</ResidentName></suggestResidentWaterSource>");
		}
		String respone = "";
			try {
			  respone = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, suggestWaterSource.toString());
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			System.out.println("Response Message after update == "+respone);
	}
	
	/**
	 * This method is use to get all water sources like standpipe and well. 
	 * @return
	 */
	public static List<WaterSource> getAllWaterSources() {
		List<WaterSource> waterSources = new ArrayList<WaterSource>();
		for(Well well : wells) {
			String eColiSensorValue = wellEcoliSensorReadingMap.get(well.getName()) != null && !wellEcoliSensorReadingMap.get(well.getName()).equals("") ? wellEcoliSensorReadingMap.get(well.getName()) : "0";
			int eColiSensorReadings = Integer.parseInt(eColiSensorValue);
			if( eColiSensorReadings <= 1) {
				waterSources.add(well);
			}
		}
		for(Standpipe standpipe : standpipes) {
			if(standpipe.isContamination()) {
				standpipes.remove(standpipe);
			}else {
				waterSources.add(standpipe);
			}
		}
		return waterSources;
	}


	/**
	 * This method is use to start reallocating water sources(house hold resellers) to residents. 
	 */
	public void startReallocatingWaterSources() {
		List<Residents> residents = Resource.findAllResidentsWaterAcessIsFalse();
		mobileVendors = Resource.getAllMobileVendor();
		// Condition to check contamination
		for(MobileVendor mobilevendor : mobileVendors) {
			String eColiSensorValue = mobileVendorEcoliSensorReadingMap.get(mobilevendor.getName()) != null && !mobileVendorEcoliSensorReadingMap.get(mobilevendor.getName()).equals("") ? mobileVendorEcoliSensorReadingMap.get(mobilevendor.getName()) : "0";
			int eColiSensorReadings = Integer.parseInt(eColiSensorValue);
			if( eColiSensorReadings > 1) {
				mobileVendors.remove(mobilevendor);
			}
		}
		int residentsCount = residents.size();
		int householdResllersCount = householdResellers.size();
		int residentPerWaterSource = residentsCount/householdResllersCount;
		if(residentsCount%householdResllersCount < 1) {
			residentPerWaterSource++; 
		}else {
			residentPerWaterSource = residentPerWaterSource + 2;
		}
		int ctr = 0;
		int i=0;
		StringBuilder suggestWaterSource = new StringBuilder();
		for (Residents res : residents) {
			boolean flag = false;
			for(MobileVendor mv : mobileVendors) {
				double res_lat = Double.parseDouble(res.getHouseLocation_latitude());
				double res_lon = Double.parseDouble(res.getHouseLocation_longitude());
				double mv_lat = Double.parseDouble(mv.getLocation_latitude());
				double mv_lon = Double.parseDouble(mv.getLocation_longitude());
				double distance = WaterMgmtNGOUtility.getDistance(res_lat, res_lon, mv_lat, mv_lon);
				if(distance < mv.getRadius()) {
					suggestWaterSource.append("<suggestResidentWaterSource><WaterSourceID>"+mv.getWaterSourceID()+"</WaterSourceID><ResidentName>"+res.getName()+"</ResidentName></suggestResidentWaterSource>");
					flag = true;
				}
			}
			if(!flag) {
				String waterSourceId = householdResellers.get(i).getWaterSourceID();
				res.setWaterSourceID(waterSourceId);
				System.out.println("waterSourceId for user =================== "+waterSourceId);
				ctr++;
				if(ctr >= residentPerWaterSource*(i+1)) {
					i++;
				}
				suggestWaterSource.append("<suggestResidentWaterSource><WaterSourceID>"+waterSourceId+"</WaterSourceID><ResidentName>"+res.getName()+"</ResidentName></suggestResidentWaterSource>");
			}
		}
		String respone = "";
		try {
		  respone = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, suggestWaterSource.toString());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Response Message after update == "+respone);
	}
	
	/**
	 * This method is use to start reallocating water sources(Mobile Vendors) to residents. 
	 */
	public void startReallocatingWaterSourcesToMobileVendors() {
		List<Residents> residents = Resource.findAllResidentsWaterAcessIsFalse();
		// Condition to check contamination
		for(MobileVendor mobilevendor : mobileVendors) {
			String eColiSensorValue = mobileVendorEcoliSensorReadingMap.get(mobilevendor.getName()) != null && !mobileVendorEcoliSensorReadingMap.get(mobilevendor.getName()).equals("") ? mobileVendorEcoliSensorReadingMap.get(mobilevendor.getName()) : "0";
			int eColiSensorReadings = Integer.parseInt(eColiSensorValue);
			if( eColiSensorReadings > 1) {
				mobileVendors.remove(mobilevendor);
			}
		}
		int residentsCount = residents.size();
		int mobileVendorsCount = mobileVendors.size();
		int residentPerWaterSource = residentsCount/mobileVendorsCount;
		if(residentsCount%mobileVendorsCount < 1) {
			residentPerWaterSource++; 
		}else {
			residentPerWaterSource = residentPerWaterSource + 2;
		}
		int ctr = 0;
		int i=0;
		StringBuilder suggestWaterSource = new StringBuilder();
		for (Residents res : residents) {
			String waterSourceId = mobileVendors.get(i).getWaterSourceID();
			res.setWaterSourceID(waterSourceId);
			System.out.println("waterSourceId for user =================== "+waterSourceId);
			ctr++;
			if(ctr >= residentPerWaterSource*(i+1)) {
				i++;
			}
			suggestWaterSource.append("<suggestResidentWaterSource><WaterSourceID>"+waterSourceId+"</WaterSourceID><ResidentName>"+res.getName()+"</ResidentName></suggestResidentWaterSource>");

		}
		String respone = "";
		try {
			respone = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, suggestWaterSource.toString());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Response Message after update == "+respone);
	}

}
