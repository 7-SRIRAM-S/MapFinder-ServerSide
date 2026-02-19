package com.mapfinder.modal;

import java.util.Date;

public class Gamemode {
	private int mode_id;
	private String modeName;
	private String description;
	private int timeLimitSeconds;
	private boolean isRanked;
	private Date createdAt;

	public Gamemode() {
	}

	public Gamemode(int mode_id, String modeName, String description, int timeLimitSeconds, boolean isRanked, Date createdAt) {
		this.mode_id = mode_id;
		this.modeName = modeName;
		this.description = description;
		this.timeLimitSeconds = timeLimitSeconds;
		this.isRanked = isRanked;
		this.createdAt = createdAt;
	}
	
	
	public Gamemode( String modeName, String description, int timeLimitSeconds, boolean isRanked, Date createdAt) {
		this.modeName = modeName;
		this.description = description;
		this.timeLimitSeconds = timeLimitSeconds;
		this.isRanked = isRanked;
		this.createdAt = createdAt;
	}


	public int getMode_id() {
		return this.mode_id;
	}

	public void setMode_id(int mode_id) {
		if (mode_id > 0)
			this.mode_id = mode_id;
	}

	public String getModeName() {
		return this.modeName;
	}

	public void setModeName(String modeName) {
		if (modeName != null && !modeName.isEmpty())
			this.modeName = modeName;
	}

	public String getText() {
		return this.description;
	}

	public void setText(String description) {
		if (description != null && !description.isEmpty())
			this.description = description;
	}

	public int getTimeLimitSeconds() {
		return this.timeLimitSeconds;
	}

	public void setTimeLimitSeconds(int timeLimitSeconds) {
		if (timeLimitSeconds > 0)
			this.timeLimitSeconds = timeLimitSeconds;
	}

	public boolean getIsRanked() {

		return this.isRanked;
	}

	public void setIsRanked(boolean isRanked) {

		this.isRanked = isRanked;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		if (createdAt != null)
			this.createdAt = createdAt;
	}

}
