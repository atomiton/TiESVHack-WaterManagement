// Copyright 2010-2014 Oleg Danilov (a.k.a ODA, Novos40) under the terms of 
// the MIT license found at http://www.opensource.org/licenses/mit-license.html
package com.atomiton.watermanagement.ngo.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Standpipe extends WaterSource {
	
	@Column(name = "OperatingTime_StartTime")
	protected String OperatingTime_StartTime;
	@Column(name = "OperatingTime_EndTime")
	protected String OperatingTime_EndTime;
	@Column(name = "UnitPrice")
	protected double UnitPrice;
	@Column(name = "QueueSize")
	protected double QueueSize;
	@Column(name = "ExpectedPressure")
	protected double ExpectedPressure;
	@Column(name = "EffectivePressure")
	protected double EffectivePressure;
	@Column(name = "WaitTime")
	protected double WaitTime;
	@Column(name = "Contamination")
	protected boolean Contamination;
	@Column(name = "Attendant")
	protected boolean Attendant;
	@Column(name = "WaterSold")
	protected double WaterSold;
	@Column(name = "WaterRevenue")
	protected double WaterRevenue;
	@Column(name = "PressureSensorId")
	protected String PressureSensorId;
	@Column(name = "AMR")
	protected boolean AMR;
	@Column(name = "CustomerCount")
	protected Integer CustomerCount;
	@Column(name = "Instrumented")
	protected boolean Instrumented;

	public String getOperatingTime_StartTime() {
		return OperatingTime_StartTime;
	}
	public void setOperatingTime_StartTime(String operatingTime_StartTime) {
		OperatingTime_StartTime = operatingTime_StartTime;
	}
	public String getOperatingTime_EndTime() {
		return OperatingTime_EndTime;
	}
	public void setOperatingTime_EndTime(String operatingTime_EndTime) {
		OperatingTime_EndTime = operatingTime_EndTime;
	}
	public double getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		UnitPrice = unitPrice;
	}
	public double getQueueSize() {
		return QueueSize;
	}
	public void setQueueSize(double queueSize) {
		QueueSize = queueSize;
	}
	public double getExpectedPressure() {
		return ExpectedPressure;
	}
	public void setExpectedPressure(double expectedPressure) {
		ExpectedPressure = expectedPressure;
	}
	public double getEffectivePressure() {
		return EffectivePressure;
	}
	public void setEffectivePressure(double effectivePressure) {
		EffectivePressure = effectivePressure;
	}
	public double getWaitTime() {
		return WaitTime;
	}
	public void setWaitTime(double waitTime) {
		WaitTime = waitTime;
	}
	public boolean isContamination() {
		return Contamination;
	}
	public void setContamination(boolean contamination) {
		Contamination = contamination;
	}
	public boolean isAttendant() {
		return Attendant;
	}
	public void setAttendant(boolean attendant) {
		Attendant = attendant;
	}
	public double getWaterSold() {
		return WaterSold;
	}
	public void setWaterSold(double waterSold) {
		WaterSold = waterSold;
	}
	public double getWaterRevenue() {
		return WaterRevenue;
	}
	public void setWaterRevenue(double waterRevenue) {
		WaterRevenue = waterRevenue;
	}
	public String getPressureSensorId() {
		return PressureSensorId;
	}
	public void setPressureSensorId(String pressureSensorId) {
		PressureSensorId = pressureSensorId;
	}
	public boolean isAMR() {
		return AMR;
	}
	public void setAMR(boolean aMR) {
		AMR = aMR;
	}
	public Integer getCustomerCount() {
		return CustomerCount;
	}
	public void setCustomerCount(Integer customerCount) {
		CustomerCount = customerCount;
	}
	public boolean isInstrumented() {
		return Instrumented;
	}
	public void setInstrumented(boolean instrumented) {
		Instrumented = instrumented;
	}
	
}
