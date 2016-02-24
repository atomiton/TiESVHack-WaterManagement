package com.atomiton.watermanagement.ngo.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.websocket.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.atomiton.watermanagement.ngo.NGOServices;

/**
 * The purpose of this class is to listen to event over a web sockets.
 * Following events will be received.
 * Virtual Clock
 * Effective Pressure
 * Well Meter Reading
 * EColiSensor value
 * @author pramodmali
 */
@ClientEndpoint
public class WebSocketClient {
	private static Object waitLock = new Object();
	public Map<String, Double> wellMeterReading = new HashMap<String, Double>();
	private NGOServices services = new NGOServices();

	/**
	 * This method is get called once message is received to websocket client.
	 * @param message
	 */
	@OnMessage
	public void onMessage(String message) {
		System.out.println("Received msg: " + message);
		if(message.contains("TqlNotification")) {
			this.readWSNotification(message);
		}
	}
	private static void wait4TerminateSignal() {
		synchronized (waitLock) {
			try {
				waitLock.wait();
			} catch (InterruptedException e) {
			}
		}
	}
	
	
	/**
	 * The purpose of this method is to start subscribing water management project notifications
	 */
	public static void startSubscription() {
		WebSocketContainer container = null;//
		Session session = null;
		try {
			container = ContainerProvider.getWebSocketContainer();
			session = container.connectToServer(WebSocketClient.class, URI.create(Constants.WM_EVENTS_WSURL));
			session.getAsyncRemote().sendText("<Query Storage='TqlSubscription'><Save><TqlSubscription Label='MyLabel00' sid='1q'><Topic>*</Topic></TqlSubscription></Save></Query>");
			wait4TerminateSignal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * This method reads websocket message and call readVirtualClockNotification or readWaterSourceStatusNotification method depending on message.
	 * In case of VirtualClock notification message this method send call to readVirtualClockNotification method and get clock time.
	 * If clock hour is 4 AM it will call  startWaterSourceAllocationProcess method.
	 * If clock hour is 2 PM it  will call startReallocatingWaterSourcesToHouseholdResellers method.
	 * If clock hour is 5 PM it  will call startReallocatingWaterSourcesToMobileVendors method.
	 * @param message
	 */
	public void readWSNotification(String message) {
		if(message.contains("VirtualClock")) {
			Map<String, String> clockTime = readVirtualClockNotification(message);
			int hours = Integer.parseInt((String) (clockTime.containsKey("Hour") ? clockTime.get("Hour") : "0"));
			System.out.println("Virtual clock hours ============= "+hours);
			boolean flag = false;
			if(hours >= 1 && hours <2 && !flag) {
				services.grantSubsidyToResident();
			}else if(hours >= 4 && hours <5) {
				services.startWaterSourceAllocationProcess();
			}else if(hours >= 14 && hours < 15) {
				services.startReallocatingWaterSources();
			}
		}else if(message.contains("watermanagement.distribution.WaterSourceStatus")) {
			readWaterSourceStatusNotification(message);
		}
	}
	
	/**
	 * This method reads websocket message related to WaterSourceStatus and take action depending on effective pressure, well meter reading and EColiSensor value.
	 * If EffectivePressure for stand pipe mention in message is less than expected pressure -10 then it will call <oneOffRepairMaintenance> api for this stand pipe.
	 * If WellMeterReading for well is less than previous day meter reading it will call <oneOffRepairMaintenanceWell> api for given well.
	 * In case of WellEColiSensorValue and Contamination it will read value and add it to map to use in NGO services.
	 * @param xmlStr
	 */
	public void readWaterSourceStatusNotification(String xmlStr){
		if(!xmlStr.contains("Create")) {
			return;
		}
		JSONObject xmlJSONObj = XML.toJSONObject(xmlStr);
		JSONObject obj = xmlJSONObj.getJSONObject("TqlNotification").getJSONObject("Create");
		Iterator keys = xmlJSONObj.getJSONObject("TqlNotification").getJSONObject("Create").keys();
		while(keys.hasNext()) {
			String key = (String) keys.next();
			JSONObject notificationObj = obj.getJSONObject(key);
			if(notificationObj.toString().contains("watermanagement.distribution.WaterSourceStatus.Attribute")) {
				JSONObject sourceObj = notificationObj.getJSONObject("watermanagement.distribution.WaterSourceStatus.sourceName");
				JSONObject attributeObj = notificationObj.getJSONObject("watermanagement.distribution.WaterSourceStatus.Attribute");
				JSONObject valueObject = notificationObj.getJSONObject("watermanagement.distribution.WaterSourceStatus.Value");
				if("EffectivePressure".equalsIgnoreCase(attributeObj.getString("Value"))) {
					String name = sourceObj.getString("Value");
					System.out.println("================StandPipe Name =============="+name);
					double pressureValue = valueObject.getDouble("Value");
					String query = "<Find><Standpipe><Name>"+ name +"</Name></Standpipe></Find>";
					try {
						String response = HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query);
						if(response.contains("Find Status=\"Success\"")) {
							JSONObject jsonObj = WaterMgmtNGOUtility.convertXMLToJSON(response);
							JSONArray standpipeArray = jsonObj.getJSONObject("Find").getJSONArray("Result");
							for(int i=0; i<standpipeArray.length(); i++) {
								JSONObject standpipeObj = standpipeArray.getJSONObject(i).getJSONObject("Standpipe");
								double expectedPressure = standpipeObj.getString("ExpectedPressure").equals("") ? 0 : Double.parseDouble(standpipeObj.getString("ExpectedPressure"));
								if(pressureValue < (expectedPressure-10)) {
									String query1="<oneOffRepairMaintenance><StandpipeName>"+ name +"</StandpipeName></oneOffRepairMaintenance>";
									HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query1);
								}
							}
						}
					}catch(Exception ex) {
						ex.printStackTrace();	
					}
				}else if("WellMeterReading".equalsIgnoreCase(attributeObj.getString("Value"))) {
					String name = sourceObj.getString("Value");
					double meterReading = valueObject.getDouble("Value");
					double previousReading = 0;
					if(wellMeterReading != null) {
						if(!wellMeterReading.isEmpty() && wellMeterReading.size() > 0 && wellMeterReading.containsKey(name)){
							previousReading = wellMeterReading.get(name);
						}else {
							wellMeterReading.put(name, meterReading);
						}
					}else {
						wellMeterReading = new HashMap<String, Double>();
						wellMeterReading.put(name, meterReading);
					}
						
					if(meterReading < previousReading) {
						try {
							String query1="<oneOffRepairMaintenanceWell><wid>"+ name +"</wid></oneOffRepairMaintenanceWell>";
							HttpRequestResponseHandler.sendPost(Constants.SERVER_URL, query1);
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
				}else if("WellEColiSensorValue".equalsIgnoreCase(attributeObj.getString("Value"))) {
					String name = sourceObj.getString("Value");
					String eColiSensorValueStr = "0";
					Double eColiSensorValue = valueObject.getDouble("Value");
					if (eColiSensorValue == null || eColiSensorValue == 0)
						eColiSensorValueStr = "0";
					else
						eColiSensorValueStr = String.valueOf(eColiSensorValue);
					NGOServices.wellEcoliSensorReadingMap.put(name, eColiSensorValueStr);
				}else if ("Contamination".equalsIgnoreCase(attributeObj.getString("Value"))) {
					String name = sourceObj.getString("Value");
					String eColiSensorValue = valueObject.getString("Value") == null ||  valueObject.getString("Value").equals("") ? "0" : valueObject.getString("Value");
					NGOServices.mobileVendorEcoliSensorReadingMap.put(name, eColiSensorValue);
				}
			}
		}
	}
	
	/**
	 * This method reads websocket message related to virtual clock and start water source allocation process.
	 * It reads message and returns hash map containing current date and current hour.
	 * @param xmlStr
	 * @return
	 */
	public Map<String, String> readVirtualClockNotification(String xmlStr) {
		Map<String, String> clockTime = new HashMap<String, String>();
		JSONObject xmlJSONObj = XML.toJSONObject(xmlStr);
		JSONObject obj;
		Iterator keys;
		if(xmlStr.contains("Update")) {
			obj = xmlJSONObj.getJSONObject("TqlNotification").getJSONObject("Update");
			keys = xmlJSONObj.getJSONObject("TqlNotification").getJSONObject("Update").keys();
			while(keys.hasNext()) {
				String key = (String) keys.next();
				JSONObject notificationObj = obj.getJSONObject(key);
				if(notificationObj.toString().contains("watermanagement.distribution.VirtualClock.CurrentDate")) {
					JSONObject publisherObj = notificationObj.getJSONObject("watermanagement.distribution.VirtualClock.CurrentDate");
					clockTime.put("Date", publisherObj.getString("Known"));
					System.out.println("CurrentDate ==== "+publisherObj.getString("Known"));
				}
				if(notificationObj.toString().contains("watermanagement.distribution.VirtualClock.CurrentHour")) {
					JSONObject publisherObj = notificationObj.getJSONObject("watermanagement.distribution.VirtualClock.CurrentHour");
					clockTime.put("Hour", String.valueOf(publisherObj.getInt("Known")));
					System.out.println("CurrentHour ==== "+publisherObj.getInt("Known"));
				}
			}
		}
		return clockTime;
	}
	
}