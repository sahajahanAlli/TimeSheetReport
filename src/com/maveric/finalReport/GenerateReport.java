package com.maveric.finalReport;

import java.util.ArrayList;

import com.maveric.employeeDetails.Employee;
import com.maveric.employeeDetails.InitializeEmployeeDetails;


public class GenerateReport {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(" Report is Here ");
		
		String filePath=args[0];
		InitializeEmployeeDetails ied=new InitializeEmployeeDetails();
		ArrayList<Employee> al=ied.createEmployeeDetails(ied.createEmployeeMap(filePath), filePath);
		ied.displayData(al);
		ied.checkAndValidateHours(al);
		
	}

}
