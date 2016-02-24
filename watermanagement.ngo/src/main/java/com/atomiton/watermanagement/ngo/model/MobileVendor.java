// Copyright 2010-2014 Oleg Danilov (a.k.a ODA, Novos40) under the terms of 
// the MIT license found at http://www.opensource.org/licenses/mit-license.html
package com.atomiton.watermanagement.ngo.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MobileVendor extends WaterSource{
	
	@Column(name="Radius")
	protected double Radius;
	@Column(name="GeoCoverage")
	protected double GeoCoverage;
	@Column(name="UnitPrice")
	protected double UnitPrice;
	@Column(name="MaxCapacity")
	protected Integer MaxCapacity;
	@Column(name="Certified")
	protected boolean Certified;
	@Column(name="Monitored")
	protected boolean Monitored;
	@Column(name="WaterSold")
	protected double WaterSold;
	@Column(name="WaterRevenue")
	protected double WaterRevenue;
	@Column(name="EColiSensor")
	protected Integer EColiSensor;
	
	public double getRadius() {
		return Radius;
	}
	public void setRadius(double radius) {
		Radius = radius;
	}
	public double getGeoCoverage() {
		return GeoCoverage;
	}
	public void setGeoCoverage(double geoCoverage) {
		GeoCoverage = geoCoverage;
	}
	public double getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		UnitPrice = unitPrice;
	}
	public Integer getMaxCapacity() {
		return MaxCapacity;
	}
	public void setMaxCapacity(Integer maxCapacity) {
		MaxCapacity = maxCapacity;
	}
	public boolean isCertified() {
		return Certified;
	}
	public void setCertified(boolean certified) {
		Certified = certified;
	}
	public boolean isMonitored() {
		return Monitored;
	}
	public void setMonitored(boolean monitored) {
		Monitored = monitored;
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
	public Integer getEColiSensor() {
		return EColiSensor;
	}
	public void setEColiSensor(Integer eColiSensor) {
		EColiSensor = eColiSensor;
	}
	
}
