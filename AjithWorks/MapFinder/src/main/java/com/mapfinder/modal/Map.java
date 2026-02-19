package com.mapfinder.modal;

import java.util.Date;

public class Map {
	private int mapId;
	private String mapName;
	private String mapType;
	private boolean isActive;
	private Date created_at;

	public Map() {

		mapName = "india";
		mapType = "country";
		isActive = true;
		created_at = new Date();

	}

	public Map(int mapId, String mapName, String mapType, boolean isActive, Date created_at) {
		this.mapId = mapId;
		this.mapName = mapName;
		this.mapType = mapType;
		this.isActive = isActive;
		this.created_at = created_at;
	}
	
	

	public Map( String mapName, String mapType, boolean isActive, Date created_at) {
		this.mapName = mapName;
		this.mapType = mapType;
		this.isActive = isActive;
		this.created_at = created_at;
	}

	

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		if (mapId > 0)
			this.mapId = mapId;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		if (mapName != null || !mapName.isEmpty())
			this.mapName = mapName;
	}

	public String getMapType() {
		return mapType;
	}

	public void setMapType(String mapType) {
		if (mapType != null || !mapType.isEmpty())
			this.mapType = mapType;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		if (created_at != null)
			this.created_at = created_at;
	}

}
