package com.elar.attandance.list;

public class States {

	// String code = null;
	// String name = null;
	// String selected = "true";

	String id = null;
	String name = null;
	String timeDuration = null;
	String image = null;
	String checkStatus = null;
	String checkTime = null;
	String leaveReasonType = null;
	String className = null;
	String totalMarkedStudent = null;

	String absentDesc = null;
	String retrivalDesc = null;
	String retrivalColor = null;
	String leftColor = null;

	public States(String id, String name, String timeDuration, String image,
			String checkStatus, String checkTime, String leaveReasonType,
			String className, String totalMarkedStudent, String absentDesc,
			String retrivalDesc, String retrivalColor, String leftColor) {
		super();
		this.id = id;
		this.name = name;
		this.timeDuration = timeDuration;
		this.image = image;
		this.checkStatus = checkStatus;
		this.checkTime = checkTime;
		this.leaveReasonType = leaveReasonType;
		this.className = className;
		this.totalMarkedStudent = totalMarkedStudent;
		this.absentDesc = absentDesc;
		this.retrivalDesc = retrivalDesc;
		this.retrivalColor = retrivalColor;
		this.leftColor = leftColor;
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

	public String getClassName() {
		return className;
	}

	public String setCLassName(String className) {
		return className;
	}

	public String getTotalMarkedStudent() {
		return totalMarkedStudent;
	}

	public String setTotalMarkedStudent(String totalMarkedStudent) {
		return totalMarkedStudent;
	}

	public String getAbsentDesc() {
		return absentDesc;
	}

	public String setAbsentDesc(String absentDesc) {
		return absentDesc;
	}

	public String getRetrivalDesc() {
		return retrivalDesc;
	}

	public String setRetrivalDesc(String retrivalDesc) {
		return retrivalDesc;
	}

	public String getRetrivalColor() {
		return retrivalColor;
	}

	public String setRetrivalColor(String retrivalColor) {
		return retrivalColor;
	}

	public String getLeftColor() {
		return leftColor;
	}

	public String setLeftColor(String leftColor) {
		return leftColor;
	}

}