package com.mapfinder.modal;

import java.util.Date;

public class Location {
	private int Location_Id;
	private int mapId;
	private String locationName;
	private String regionType;
	private Date CREATED_AT;

	public Location() {
		locationName = "tamilnadu";
		regionType = "forest";
		CREATED_AT = new Date();
	}

	public Location(int Location_Id, int mapId, String locationName, String regionType, Date CREATED_AT) {
		this.Location_Id = Location_Id;
		this.mapId = mapId;
		this.locationName = locationName;
		this.regionType = regionType;
		this.CREATED_AT = CREATED_AT;
	}
	
	public Location( int mapId, String locationName, String regionType, Date CREATED_AT) {
		this.mapId = mapId;
		this.locationName = locationName;
		this.regionType = regionType;
		this.CREATED_AT = CREATED_AT;
	}

	public int getLocation_Id() {
		return this.Location_Id;
	}

	public void setLocation_Id(int Location_Id) {
		if (Location_Id > 0)
			this.Location_Id = Location_Id;
	}

	public int getMapId() {
		return this.mapId;
	}

	public void setMapId(int mapId) {
		if (mapId > 0)
			this.mapId = mapId;
	}

	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		if (locationName != null || !locationName.isEmpty())
			this.locationName = locationName;
	}

	public String getRegionType() {
		return this.regionType;
	}

	public void setRegionType(String regionType) {
		if (regionType != null || !regionType.isEmpty())
			this.regionType = regionType;
	}

	public Date getCREATED_AT() {
		return this.CREATED_AT;
	}

	public void setCREATED_AT(Date CREATED_AT) {
		if (CREATED_AT != null)
			this.CREATED_AT = CREATED_AT;
	}

}
