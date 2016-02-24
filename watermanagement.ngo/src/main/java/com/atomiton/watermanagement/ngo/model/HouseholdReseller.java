// Copyright 2010-2014 Oleg Danilov (a.k.a ODA, Novos40) under the terms of 
// the MIT license found at http://www.opensource.org/licenses/mit-license.html
package com.atomiton.watermanagement.ngo.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HouseholdReseller extends WaterSource {
	
	@Column(name="UnitPrice")
	protected double UnitPrice;
	@Column(name="QueueSize")
	protected Integer QueueSize;
	@Column(name="WaterSold")
	protected double WaterSold;
	@Column(name="WaterRevenue")
	protected double WaterRevenue;
	@Column(name="WaitTime")
	protected double WaitTime;
	
	public double getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		UnitPrice = unitPrice;
	}
	public Integer getQueueSize() {
		return QueueSize;
	}
	public void setQueueSize(Integer queueSize) {
		QueueSize = queueSize;
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
	public double getWaitTime() {
		return WaitTime;
	}
	public void setWaitTime(double waitTime) {
		WaitTime = waitTime;
	}
	
}
