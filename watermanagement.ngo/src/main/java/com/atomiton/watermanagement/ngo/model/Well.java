// Copyright 2010-2014 Oleg Danilov (a.k.a ODA, Novos40) under the terms of 
// the MIT license found at http://www.opensource.org/licenses/mit-license.html
package com.atomiton.watermanagement.ngo.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Well extends WaterSource{
	
	@Column(name="MaxFlowRate")
	protected double MaxFlowRate;
	@Column(name="MeterReading")
	protected double MeterReading;
	@Column(name="Metered")
	protected boolean Metered;
	@Column(name="QueueSize")
	protected Integer QueueSize;
	@Column(name="PersonTime")
	protected double PersonTime;
	@Column(name="WaitTime")
	protected double WaitTime;
	@Column(name="EcoliSensorId")
	protected String EcoliSensorId;
	@Column(name="WaterMeterId")
	protected String WaterMeterId;
	@Column(name="WaterRevenue")
	protected double WaterRevenue;
	@Column(name="Attendant")
	protected boolean Attendant;
	@Column(name="CustomerCount")
	protected Integer CustomerCount;
	
	public double getMaxFlowRate() {
		return MaxFlowRate;
	}
	public void setMaxFlowRate(double maxFlowRate) {
		MaxFlowRate = maxFlowRate;
	}
	public double getMeterReading() {
		return MeterReading;
	}
	public void setMeterReading(double meterReading) {
		MeterReading = meterReading;
	}
	public boolean isMetered() {
		return Metered;
	}
	public void setMetered(boolean metered) {
		Metered = metered;
	}
	public Integer getQueueSize() {
		return QueueSize;
	}
	public void setQueueSize(Integer queueSize) {
		QueueSize = queueSize;
	}
	public double getPersonTime() {
		return PersonTime;
	}
	public void setPersonTime(double personTime) {
		PersonTime = personTime;
	}
	public double getWaitTime() {
		return WaitTime;
	}
	public void setWaitTime(double waitTime) {
		WaitTime = waitTime;
	}
	public String getEcoliSensorId() {
		return EcoliSensorId;
	}
	public void setEcoliSensorId(String ecoliSensorId) {
		EcoliSensorId = ecoliSensorId;
	}
	public String getWaterMeterId() {
		return WaterMeterId;
	}
	public void setWaterMeterId(String waterMeterId) {
		WaterMeterId = waterMeterId;
	}
	public double getWaterRevenue() {
		return WaterRevenue;
	}
	public void setWaterRevenue(double waterRevenue) {
		WaterRevenue = waterRevenue;
	}
	public boolean isAttendant() {
		return Attendant;
	}
	public void setAttendant(boolean attendant) {
		Attendant = attendant;
	}
	public Integer getCustomerCount() {
		return CustomerCount;
	}
	public void setCustomerCount(Integer customerCount) {
		CustomerCount = customerCount;
	}
	
}
