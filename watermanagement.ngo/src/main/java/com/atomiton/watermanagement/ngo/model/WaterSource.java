// Copyright 2010-2014 Oleg Danilov (a.k.a ODA, Novos40) under the terms of 
// the MIT license found at http://www.opensource.org/licenses/mit-license.html
package com.atomiton.watermanagement.ngo.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class WaterSource {
	
	@javax.persistence.Id
	@Column(name="Id")
	protected String Id;
	@Column(name = "SourceType")
	protected String SourceType;
	@Column(name = "Name")
	protected String Name;
	@Column(name = "WaterSourceID")
	protected String WaterSourceID;
	@Column(name = "location_latitude")
	protected String location_latitude;
	@Column(name = "location_longitude")
	protected String location_longitude;
	@Column(name = "RecordDate")
	protected String RecordDate;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getSourceType() {
		return SourceType;
	}
	public void setSourceType(String sourceType) {
		SourceType = sourceType;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getWaterSourceID() {
		return WaterSourceID;
	}
	public void setWaterSourceID(String waterSourceID) {
		WaterSourceID = waterSourceID;
	}
	public String getLocation_latitude() {
		return location_latitude;
	}
	public void setLocation_latitude(String location_latitude) {
		this.location_latitude = location_latitude;
	}
	public String getLocation_longitude() {
		return location_longitude;
	}
	public void setLocation_longitude(String location_longitude) {
		this.location_longitude = location_longitude;
	}
	public String getRecordDate() {
		return RecordDate;
	}
	public void setRecordDate(String recordDate) {
		RecordDate = recordDate;
	}
}
