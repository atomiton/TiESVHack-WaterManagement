// Copyright 2010-2014 Oleg Danilov (a.k.a ODA, Novos40) under the terms of 
// the MIT license found at http://www.opensource.org/licenses/mit-license.html
package com.atomiton.watermanagement.ngo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.atomiton.watermanagement.ngo.model.*;
import com.atomiton.watermanagement.ngo.util.Constants;
import com.atomiton.watermanagement.ngo.util.HttpRequestResponseHandler;
import com.atomiton.watermanagement.ngo.util.WaterMgmtNGOUtility;

/**
 * The purpose of this class is to get water management model data from TQL Engine.
 * @author pramodmali
 */
public class Resource {
	
	/**
	 * This method is use to get all residents model details.
	 * @return
	 */
	public static List<Residents> getAllResidents() {
		String query="<Query><Find><Residents><Id><ne></ne></Id></Residents></Find></Query>";
		List<Residents> residentsList = new ArrayList<Residents>();
		try {
			String response = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
			JSONObject object = WaterMgmtNGOUtility.convertXMLToJSON(response);
			JSONArray residentsObj = object.getJSONObject("Find").getJSONArray("Result");
			for(int i=0; i<residentsObj.length(); i++) {
				JSONObject resident = residentsObj.getJSONObject(i).getJSONObject("Residents");
				Residents obj = new Residents();
				obj.setName(resident.getString("Name"));
				obj.setMonthlyAllowance(resident.getDouble("MonthlyAllowance"));
				obj.setBudgetBalance((resident.getString("BudgetBalance").equals("") ) ? 0 : Double.parseDouble(resident.getString("BudgetBalance")));
				obj.setContamination(resident.getBoolean("Contamination"));
				obj.setDaysOfAccess(resident.getInt("DaysOfAccess"));
				obj.setMobile(resident.getString("Mobile"));
				obj.setHealthCost(resident.getString("HealthCost").equals("") ? 0 : Double.parseDouble(resident.getString("HealthCost")));
				/*System.out.println("Resident id  ==================="+resident.getString("Id"));
				System.out.println("HourCost ======================="+resident.get("HourCost").getClass());*/
				obj.setHourCost(resident.get("HourCost") == null || resident.get("HourCost").equals("") ? 0 : Double.parseDouble(resident.getString("HourCost")));
				obj.setResidentID(resident.getString("ResidentID"));
				obj.setSourceGuideID(resident.getString("SourceGuideID"));
				obj.setSourceType("");
//				System.out.println("Travel distance ======================="+resident.get("TravelDistance").getClass());
				obj.setTravelDistance(resident.get("TravelDistance") == null || resident.get("TravelDistance").equals("") ? 0 : resident.getDouble("TravelDistance"));
				obj.setTravelTime(resident.get("TravelTime") == null || resident.get("TravelTime").equals("") ? 0 : resident.getDouble("TravelTime"));
				obj.setUnitCost(resident.getString("UnitCost").equals("") || resident.getString("UnitCost").equals("0E-10") ? 0 : Double.parseDouble(resident.getString("UnitCost")));
				obj.setWaterCost(resident.getString("WaterCost").equals("") ? 0 : Double.parseDouble(resident.getString("WaterCost")));
				obj.setWaterAccess(resident.get("WaterAccess") == null || resident.get("WaterAccess").equals("") ? false : resident.getBoolean("WaterAccess"));
				obj.setWaterSourceID(resident.getString("WaterSourceID"));
				obj.setWaterNeed(resident.getString("WaterNeed").equals("") ? 0 : Double.parseDouble(resident.getString("WaterNeed")));
				/*GeoLocation location= new GeoLocation();*/
				JSONObject houseLocObject = resident.getJSONObject("HouseLocation");
				/*location.setLatitude(houseLocObject.getDouble("latitude"));
				location.setLongitude(houseLocObject.getDouble("longitude"));*/
				obj.setHouseLocation_latitude(String.valueOf(houseLocObject.getDouble("latitude")));
				obj.setHouseLocation_longitude(String.valueOf(houseLocObject.getDouble("longitude")));
				obj.setRecordDate(resident.getString("RecordDate"));
				obj.setId(resident.getString("Id"));
				residentsList.add(obj);
			}
			System.out.println("Response message for getAllResidents === "+residentsList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return residentsList;
	}
	
	/**
	 * This method is use to find all residents whose water access is false.
	 * @return
	 */
	public static List<Residents> findAllResidentsWaterAcessIsFalse() {
		String query="<Query><Find><Residents><WaterAccess>false</WaterAccess></Residents></Find></Query>";
		List<Residents> residentsList = new ArrayList<Residents>();
		try {
			String response = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
			JSONObject object = WaterMgmtNGOUtility.convertXMLToJSON(response);
			if(object.toString().contains("Success")) {
				JSONArray residentsObj = object.getJSONObject("Find").getJSONArray("Result");
				for(int i=0; i<residentsObj.length(); i++) {
					JSONObject resident = residentsObj.getJSONObject(i).getJSONObject("Residents");
					Residents obj = new Residents();
					obj.setName(resident.getString("Name"));
					obj.setMonthlyAllowance(resident.getDouble("MonthlyAllowance"));
					obj.setBudgetBalance((resident.getString("BudgetBalance").equals("") ) ? 0 : Double.parseDouble(resident.getString("BudgetBalance")));
					obj.setContamination(resident.getBoolean("Contamination"));
					obj.setDaysOfAccess(resident.getInt("DaysOfAccess"));
					obj.setMobile(resident.getString("Mobile"));
					obj.setHealthCost(resident.getString("HealthCost").equals("") ? 0 : Double.parseDouble(resident.getString("HealthCost")));
					obj.setHourCost(resident.getString("HourCost").equals("") ? 0 : Double.parseDouble(resident.getString("HourCost")));
					obj.setResidentID(resident.getString("ResidentID"));
					obj.setSourceGuideID(resident.getString("SourceGuideID"));
					obj.setSourceType("");
					obj.setTravelDistance(resident.get("TravelDistance") != null || resident.get("TravelDistance").equals("") ? 0 : Double.parseDouble(resident.getString("TravelDistance")));
					obj.setTravelTime(resident.get("TravelTime") != null ||  resident.get("TravelTime").equals("") ? 0 : Double.parseDouble(resident.getString("TravelTime")));
					obj.setUnitCost(resident.getString("UnitCost").equals("") || resident.getString("UnitCost").equals("0E-10") ? 0 : Double.parseDouble(resident.getString("UnitCost")));
					obj.setWaterCost(resident.getString("WaterCost").equals("") ? 0 : Double.parseDouble(resident.getString("WaterCost")));
					obj.setWaterAccess(resident.getBoolean("WaterAccess"));
					obj.setWaterSourceID(resident.getString("WaterSourceID"));
					obj.setWaterNeed(resident.getString("WaterNeed").equals("") ? 0 : Double.parseDouble(resident.getString("WaterNeed")));
					/*GeoLocation location= new GeoLocation();*/
					JSONObject houseLocObject = resident.getJSONObject("HouseLocation");
					/*location.setLatitude(houseLocObject.getDouble("latitude"));
					location.setLongitude(houseLocObject.getDouble("longitude"));*/
					obj.setHouseLocation_latitude(String.valueOf(houseLocObject.getDouble("latitude")));
					obj.setHouseLocation_longitude(String.valueOf(houseLocObject.getDouble("longitude")));
					obj.setRecordDate(resident.getString("RecordDate"));
					obj.setId(resident.getString("Id"));
					residentsList.add(obj);
				}
			}else {
				return residentsList;
			}
			System.out.println("Response message for getAllResidents === "+residentsList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return residentsList;
	}
		
	/**
	 * This method is use to get  all wells details.
	 * @return
	 */
	public static List<Well> getAllWells() {
		String query = "<Query><Find><Well><Id><ne></ne></Id></Well></Find></Query>";
		List<Well> waterSourceList = new ArrayList<Well>();
		try {
			String response = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
			JSONObject jsonObj = WaterMgmtNGOUtility.convertXMLToJSON(response);
			JSONArray wellArray = jsonObj.getJSONObject("Find").getJSONArray("Result");
			for (int i = 0; i < wellArray.length(); i++) {
				JSONObject wellObj = wellArray.getJSONObject(i).getJSONObject("Well");
				Well well = new Well();
				well.setId(wellObj.getString("Id"));
				well.setSourceType(wellObj.getString("SourceType"));
				well.setWaterSourceID(wellObj.getString("WaterSourceID"));
				well.setRecordDate(wellObj.getString("RecordDate"));
				well.setWaterMeterId(wellObj.getString("WaterMeterId"));
				well.setEcoliSensorId(wellObj.getString("EcoliSensorId"));
				well.setAttendant(wellObj.getString("Attendant").equals("") ? false : Boolean.parseBoolean(wellObj.getString("Attendant")));
				/*well.setCustomerCount(wellObj.getString("CustomerCount").equals("") ? 0 : Integer.parseInt(wellObj.getString("CustomerCount")));*/
				/*GeoLocation location= new GeoLocation();*/
				JSONObject houseLocObject = wellObj.getJSONObject("GeoLocation");
				/*location.setLatitude(houseLocObject.getDouble("latitude"));
				location.setLongitude(houseLocObject.getDouble("longitude"));*/
				well.setLocation_latitude(String.valueOf(houseLocObject.getDouble("latitude")));
				well.setLocation_longitude(String.valueOf(houseLocObject.getDouble("longitude")));
				well.setMaxFlowRate(wellObj.getString("MaxFlowRate").equals("") ? 0 : Double.parseDouble(wellObj.getString("MaxFlowRate")));
				well.setMetered(wellObj.getBoolean("Metered"));
			/*	well.setMeterReading(wellObj.getString("MeterReading").equals("") ? 0 : Double.parseDouble(wellObj.getString("MeterReading")));*/
				well.setPersonTime(wellObj.get("PersonTime") == null || wellObj.get("PersonTime").equals("") ? 0 : wellObj.getDouble("PersonTime"));
				well.setQueueSize(wellObj.getInt("QueueSize"));
				well.setWaitTime(wellObj.get("WaitTime") == null || wellObj.get("WaitTime").equals("") ? 0 : wellObj.getDouble("WaitTime"));
				well.setWaterRevenue(wellObj.getString("WaterRevenue").equals("") ? 0 : Double.parseDouble(wellObj.getString("WaterRevenue")));
				well.setName(wellObj.getString("Name"));
				waterSourceList.add(well);
			}
			System.out.println("Response message for getAllWaterSource ==== Wells ==== "+waterSourceList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return waterSourceList;
	}

	/**
	 * This method is use to get model data for all house hold reseller.
	 * @return
	 */
	public static List<HouseholdReseller> getAllHouseholdReseller() {
		String query="<Query><Find><HouseholdReseller><Id><ne></ne></Id></HouseholdReseller></Find></Query>";
		List<HouseholdReseller> householdResellerList = new ArrayList<HouseholdReseller>();
		try {
			String response = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
			JSONObject jsonObject = WaterMgmtNGOUtility.convertXMLToJSON(response);
			JSONArray resellerObj = jsonObject.getJSONObject("Find").getJSONArray("Result");
			for(int i=0; i<resellerObj.length(); i++) {
				JSONObject reseller = resellerObj.getJSONObject(i).getJSONObject("HouseholdReseller");
				HouseholdReseller householdReseller = new HouseholdReseller();
				householdReseller.setId(reseller.getString("Id"));
				householdReseller.setName(reseller.getString("Name"));
				householdReseller.setSourceType(reseller.getString("SourceType"));
				householdReseller.setWaterSourceID(reseller.getString("WaterSourceID"));
				GeoLocation location= new GeoLocation();
				JSONObject houseLocObject = reseller.getJSONObject("GeoLocation");
				/*location.setLatitude(houseLocObject.getDouble("latitude"));
				location.setLongitude(houseLocObject.getDouble("longitude"));*/
				householdReseller.setLocation_latitude(String.valueOf(houseLocObject.getDouble("latitude")));
				householdReseller.setLocation_longitude(String.valueOf(houseLocObject.getDouble("longitude")));
				/*householdReseller.setQueueSize(reseller.getInt("QueueSize"));*/
				householdReseller.setRecordDate(reseller.getString("RecordDate"));
				householdReseller.setUnitPrice(reseller.getString("UnitPrice").equals("") ? 0 : Double.parseDouble(reseller.getString("UnitPrice")));
				/*householdReseller.setWaitTime(reseller.getString("WaitTime").equals("") ? 0 : Double.parseDouble(reseller.getString("WaitTime")));*/
				householdReseller.setWaterRevenue(reseller.getString("WaterRevenue").equals("") ? 0 : Double.parseDouble(reseller.getString("WaterRevenue")));
				householdReseller.setWaterSold(reseller.getString("WaterSold").equals("") ? 0 : Double.parseDouble(reseller.getString("WaterSold")));
				householdResellerList.add(householdReseller);
			}
			System.out.println("Response message for getAllWaterSource ==== HouseholdReseller ==== "+householdResellerList.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return householdResellerList;
	}
	
	/**
	 * This method is use to get all mobile vendor details.
	 * @return
	 */
	public static List<MobileVendor> getAllMobileVendor() {
		String query="<Query><Find><MobileVendor><Id><ne></ne></Id></MobileVendor></Find></Query>";
		List<MobileVendor> mobileVendorList = new ArrayList<MobileVendor>();
		try {
			String response = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
			JSONObject jsonObject = WaterMgmtNGOUtility.convertXMLToJSON(response);
			JSONArray vendorObj = jsonObject.getJSONObject("Find").getJSONArray("Result");
			for(int i=0; i<vendorObj.length(); i++) {
				JSONObject vendor = vendorObj.getJSONObject(i).getJSONObject("MobileVendor");
				MobileVendor mobileVendor = new MobileVendor();
				mobileVendor.setId(vendor.getString("Id"));
				mobileVendor.setName(vendor.getString("Name"));
				mobileVendor.setSourceType(vendor.getString("SourceType"));
				mobileVendor.setWaterSourceID(vendor.getString("WaterSourceID"));
				GeoLocation location= new GeoLocation();
				JSONObject houseLocObject = vendor.getJSONObject("GeoLocation");
				/*location.setLatitude(houseLocObject.getDouble("latitude"));
				location.setLongitude(houseLocObject.getDouble("longitude"));*/
				mobileVendor.setLocation_latitude(String.valueOf(houseLocObject.getDouble("latitude")));
				mobileVendor.setLocation_longitude(String.valueOf(houseLocObject.getDouble("longitude")));
				mobileVendor.setRecordDate(vendor.getString("RecordDate"));
				mobileVendor.setUnitPrice(vendor.getString("UnitPrice").equals("") ? 0 : Double.parseDouble(vendor.getString("UnitPrice")));
				mobileVendor.setWaterRevenue(vendor.getString("WaterRevenue").equals("") ? 0 : Double.parseDouble(vendor.getString("WaterRevenue")));
				mobileVendor.setWaterSold(vendor.getString("WaterSold").equals("") ? 0 : Double.parseDouble(vendor.getString("WaterSold")));
				mobileVendor.setCertified(vendor.getBoolean("Certified"));
				/*mobileVendor.setEColiSensor(vendor.getString("EColiSensor").equals("") ? 0 : Integer.parseInt(vendor.getString("EColiSensor")));*/
				mobileVendor.setGeoCoverage(vendor.getString("GeoCoverage").equals("") ? 0 : Double.parseDouble(vendor.getString("GeoCoverage")));
				mobileVendor.setRadius(vendor.getString("Radius").equals("") ? 0 : Double.parseDouble(vendor.getString("Radius")));
				mobileVendor.setMaxCapacity(vendor.get("MaxCapacity") == null || vendor.get("MaxCapacity").equals("") ? 0 : vendor.getInt("MaxCapacity"));
				mobileVendorList.add(mobileVendor);
			}
			System.out.println("Response message for getAllWaterSource ==== MobileVendor ==== "+mobileVendorList.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mobileVendorList;
	}
	
	
	/**
	 * This method is to get all standpipe details.
	 * @return
	 */
	public static List<Standpipe> getAllStandpipes() {
		String query="<Query><Find><Standpipe><Id><ne></ne></Id></Standpipe></Find></Query>";
		List<Standpipe> waterSourceList = new ArrayList<Standpipe>();
		try {
			String response = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
			JSONObject jsonObj = WaterMgmtNGOUtility.convertXMLToJSON(response);
			JSONArray standpipeArray = jsonObj.getJSONObject("Find").getJSONArray("Result");
			for(int i=0; i<standpipeArray.length(); i++) {
				JSONObject standpipeObj = standpipeArray.getJSONObject(i).getJSONObject("Standpipe");
				Standpipe standpipe = new Standpipe();
				standpipe.setId(standpipeObj.getString("Id"));
				standpipe.setName(standpipeObj.getString("Name"));
				standpipe.setSourceType(standpipeObj.getString("SourceType"));
				standpipe.setWaterSourceID(standpipeObj.getString("WaterSourceID"));
				standpipe.setAMR(standpipeObj.getString("AMR").equals("") ? false : Boolean.parseBoolean(standpipeObj.getString("AMR")));
				standpipe.setAttendant(standpipeObj.getBoolean("Attendant"));
				/*standpipe.setContamination(standpipeObj.getBoolean("Contamination"));*/
/*				standpipe.setCustomerCount(standpipeObj.getString("CustomerCount").equals("") ? 0 : Integer.parseInt(standpipeObj.getString("CustomerCount")));*/
				standpipe.setEffectivePressure(standpipeObj.getString("EffectivePressure").equals("") ? 0 : Double.parseDouble(standpipeObj.getString("EffectivePressure")));
				standpipe.setExpectedPressure(standpipeObj.getString("ExpectedPressure").equals("") ? 0 : Double.parseDouble(standpipeObj.getString("ExpectedPressure")));
				standpipe.setPressureSensorId(standpipeObj.getString("PressureSensorId"));
				standpipe.setQueueSize(standpipeObj.getString("QueueSize").equals("") || standpipeObj.getString("QueueSize").equals("0E-10") ? 0 : Double.parseDouble(standpipeObj.getString("QueueSize")));
				standpipe.setRecordDate(standpipeObj.getString("RecordDate"));
				standpipe.setUnitPrice(standpipeObj.getString("UnitPrice").equals("") ? 0 : Double.parseDouble(standpipeObj.getString("UnitPrice")));
				standpipe.setWaitTime(standpipeObj.getString("WaitTime").equals("") ? 0 : Double.parseDouble(standpipeObj.getString("WaitTime")));
				standpipe.setWaterSold(standpipeObj.getString("WaterSold").equals("") ? 0 : Double.parseDouble(standpipeObj.getString("WaterSold")));
				standpipe.setWaterRevenue(standpipeObj.getString("WaterRevenue").equals("") ? 0 : Double.parseDouble(standpipeObj.getString("WaterRevenue")));
				standpipe.setInstrumented(standpipeObj.getBoolean("Instrumented"));
				/*OperatingTime opTime = new OperatingTime();
				opTime.setStartTime(standpipeObj.getJSONObject("OperatingHours").getString("StartTime"));
				opTime.setEndTime(standpipeObj.getJSONObject("OperatingHours").getString("EndTime"));
				standpipe.setOperatingTime(opTime);*/
				GeoLocation location= new GeoLocation();
				JSONObject houseLocObject = standpipeObj.getJSONObject("GeoLocation");
				/*location.setLatitude(houseLocObject.getDouble("latitude"));
				location.setLongitude(houseLocObject.getDouble("longitude"));*/
				standpipe.setLocation_latitude(String.valueOf(houseLocObject.getDouble("latitude")));
				standpipe.setLocation_longitude(String.valueOf(houseLocObject.getDouble("longitude")));
				waterSourceList.add(standpipe);
			}
			System.out.println("Response message for getAllWaterSource === Standpipe ==== "+waterSourceList.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return waterSourceList;
	}
	

}
