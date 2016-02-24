// Copyright 2010-2014 Oleg Danilov (a.k.a ODA, Novos40) under the terms of 
// the MIT license found at http://www.opensource.org/licenses/mit-license.html
package com.atomiton.watermanagement.ngo.model;

import javax.persistence.Column;
import javax.persistence.Entity;



@Entity
public class Residents {
	@javax.persistence.Id
	@Column(name = "id")
	protected String Id;
	@Column(name = "ResidentID")
	protected String ResidentID;
	@Column(name = "Name")
	protected String Name;
	@Column(name = "BudgetBalance")
	protected double BudgetBalance;
	@Column(name = "MonthlyAllowance")
	protected double MonthlyAllowance;
	@Column(name = "DaySubsidy")
	protected double DaySubsidy;
	@Column(name = "SourceGuideID")
	protected String SourceGuideID;
	@Column(name = "WaterSourceID")
	protected String WaterSourceID;
	@Column(name = "WaterNeed")
	protected double WaterNeed;
	@Column(name = "WaterAccess")
	protected Boolean WaterAccess;
	@Column(name = "DaysOfAccess")
	protected Integer DaysOfAccess;
	@Column(name = "SourceType")
	protected String SourceType;
	@Column(name = "HouseLocation_latitude")
	protected String HouseLocation_latitude;
	@Column(name = "HouseLocation_longitude")
	protected String HouseLocation_longitude;
	@Column(name = "UnitCost")
	protected double UnitCost;
	@Column(name = "TravelTime")
	protected double TravelTime;
	@Column(name = "TravelDistance")
	protected double TravelDistance;
	@Column(name = "WaitTime")
	protected double WaitTime;
	@Column(name = "HourCost")
	protected double HourCost;
	@Column(name = "WaterCost")
	protected double WaterCost;
	@Column(name = "HealthCost")
	protected double HealthCost;
	@Column(name = "Mobile")
	protected String Mobile;
	@Column(name = "Contamination")
	protected Boolean Contamination;
	@Column(name = "RecordDate")
	protected String RecordDate;
	// " format="$SimpleDateFormat(yyyy-MM-dd'T'HH:mm:ss'Z')
	public String getResidentID() {
		return ResidentID;
	}
	public void setResidentID(String residentID) {
		ResidentID = residentID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public double getBudgetBalance() {
		return BudgetBalance;
	}
	public void setBudgetBalance(double budgetBalance) {
		BudgetBalance = budgetBalance;
	}
	public double getMonthlyAllowance() {
		return MonthlyAllowance;
	}
	public void setMonthlyAllowance(double d) {
		MonthlyAllowance = d;
	}
	public double getDaySubsidy() {
		return DaySubsidy;
	}
	public void setDaySubsidy(double daySubsidy) {
		DaySubsidy = daySubsidy;
	}
	public String getSourceGuideID() {
		return SourceGuideID;
	}
	public void setSourceGuideID(String sourceGuideID) {
		SourceGuideID = sourceGuideID;
	}
	public String getWaterSourceID() {
		return WaterSourceID;
	}
	public void setWaterSourceID(String waterSourceID) {
		WaterSourceID = waterSourceID;
	}
	public double getWaterNeed() {
		return WaterNeed;
	}
	public void setWaterNeed(double waterNeed) {
		WaterNeed = waterNeed;
	}
	public Boolean getWaterAccess() {
		return WaterAccess;
	}
	public void setWaterAccess(Boolean waterAccess) {
		WaterAccess = waterAccess;
	}
	public Integer getDaysOfAccess() {
		return DaysOfAccess;
	}
	public void setDaysOfAccess(Integer daysOfAccess) {
		DaysOfAccess = daysOfAccess;
	}
	public String getSourceType() {
		return SourceType;
	}
	public void setSourceType(String sourceType) {
		SourceType = sourceType;
	}
	
	public String getHouseLocation_latitude() {
		return HouseLocation_latitude;
	}
	public void setHouseLocation_latitude(String houseLocation_latitude) {
		HouseLocation_latitude = houseLocation_latitude;
	}
	public String getHouseLocation_longitude() {
		return HouseLocation_longitude;
	}
	public void setHouseLocation_longitude(String houseLocation_longitude) {
		HouseLocation_longitude = houseLocation_longitude;
	}
	public double getUnitCost() {
		return UnitCost;
	}
	public void setUnitCost(double unitCost) {
		UnitCost = unitCost;
	}
	public double getTravelTime() {
		return TravelTime;
	}
	public void setTravelTime(double travelTime) {
		TravelTime = travelTime;
	}
	public double getTravelDistance() {
		return TravelDistance;
	}
	public void setTravelDistance(double travelDistance) {
		TravelDistance = travelDistance;
	}
	public double getWaitTime() {
		return WaitTime;
	}
	public void setWaitTime(double waitTime) {
		WaitTime = waitTime;
	}
	public double getHourCost() {
		return HourCost;
	}
	public void setHourCost(double hourCost) {
		HourCost = hourCost;
	}
	public double getWaterCost() {
		return WaterCost;
	}
	public void setWaterCost(double waterCost) {
		WaterCost = waterCost;
	}
	public double getHealthCost() {
		return HealthCost;
	}
	public void setHealthCost(double healthCost) {
		HealthCost = healthCost;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public Boolean getContamination() {
		return Contamination;
	}
	public void setContamination(Boolean contamination) {
		Contamination = contamination;
	}
	public String getRecordDate() {
		return RecordDate;
	}
	public void setRecordDate(String recordDate) {
		RecordDate = recordDate;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
}
