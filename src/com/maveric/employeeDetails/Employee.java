package com.maveric.employeeDetails;

import java.util.ArrayList;

public class Employee {
	
	private String employeeId;
	private String employeeName;
	private String clientName;
	private String projectName;
	private double totalWorkingHours;
	private double totalNonWorkingHours;
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
	public ArrayList<TimeAndWorkLocation> getTimeAndWorkLocation() {
		return timeAndWorkLocation;
	}
	public void setTimeAndWorkLocation(ArrayList<TimeAndWorkLocation> timeAndWorkLocation) {
		this.timeAndWorkLocation = timeAndWorkLocation;
	}
	public double getTotalWorkingHours() {
		return totalWorkingHours;
	}
	public void setTotalWorkingHours(int totalWorkingHours) {
		this.totalWorkingHours = totalWorkingHours;
	}
	public double getTotalNonWorkingHours() {
		return totalNonWorkingHours;
	}
	public void setTotalNonWorkingHours(int totalNonWorkingHours) {
		this.totalNonWorkingHours = totalNonWorkingHours;
	}
	

}
