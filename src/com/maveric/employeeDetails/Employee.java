package com.maveric.employeeDetails;

import java.util.ArrayList;

public class Employee {
	
	private String employeeId;
	private String employeeName;
	private String clientName;
	private String projectName;
	private String subProject;
	private String status;
	private int totalWorkingHours;
	private int totalNonWorkingHours;
	private ArrayList<TimeAndWorkLocation> timeAndWorkLocation;

	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getSubProject() {
		return subProject;
	}
	public void setSubProject(String subProject) {
		this.subProject = subProject;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<TimeAndWorkLocation> getTimeAndWorkLocation() {
		return timeAndWorkLocation;
	}
	public void setTimeAndWorkLocation(ArrayList<TimeAndWorkLocation> timeAndWorkLocation) {
		this.timeAndWorkLocation = timeAndWorkLocation;
	}
	public int getTotalWorkingHours() {
		return totalWorkingHours;
	}
	public void setTotalWorkingHours(int totalWorkingHours) {
		this.totalWorkingHours = totalWorkingHours;
	}
	public int getTotalNonWorkingHours() {
		return totalNonWorkingHours;
	}
	public void setTotalNonWorkingHours(int totalNonWorkingHours) {
		this.totalNonWorkingHours = totalNonWorkingHours;
	}
	

}
