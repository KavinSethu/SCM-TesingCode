package com.elar.attandance.list;

public class GetSetData {

	String id = null; // user id
	String name = null;
	String timeDuration = null;
	String image = null;
	String checkStatus = null;
	String checkTime = null;
	String leaveReasonType = null;

	public GetSetData(String id, String name, String timeDuration,
			String image, String checkStatus, String checkTime,
			String leaveReasonType) {
		super();
		this.id = id;
		this.name = name;
		this.timeDuration = timeDuration;
		this.image = image;
		this.checkStatus = checkStatus;
		this.checkTime = checkTime;
		this.leaveReasonType = leaveReasonType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public String setCheckStatus(String checkStatus) {
		return checkStatus;
	}

	public String getTimeDuration() {
		return timeDuration;
	}

	public String setTimeDuration(String timeDuration) {
		return timeDuration;
	}

	public String getImage() {
		return image;
	}

	public String setImage(String image) {
		return image;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public String setCheckTime(String checkTime) {
		return checkTime;
	}

	public String getLeaveReasonType() {
		return leaveReasonType;
	}

	public String setLeaveReasonType(String leaveReasonType) {
		return leaveReasonType;
	}

}