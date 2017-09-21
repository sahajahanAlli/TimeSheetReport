package com.maveric.finalReport;

import java.util.ArrayList;
import java.util.Iterator;

import com.maveric.employeeDetails.Employee;
import com.maveric.employeeDetails.FinalBillingClass;
import com.maveric.employeeDetails.InitializeEmployeeDetails;


public class GenerateReport {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(" Report is Here ");
		
		String filePath=args[0];
		InitializeEmployeeDetails ied=new InitializeEmployeeDetails();
		ArrayList<Employee> al=ied.createEmployeeDetails(ied.createEmployeeMap(filePath), filePath);
		ied.displayData(al);
		ArrayList<FinalBillingClass> fbc=ied.checkAndValidateHours(al);
		Iterator<FinalBillingClass>  billing= fbc.iterator();
		FinalBillingClass fb=null;
		while (billing.hasNext()) {
			
			fb=new FinalBillingClass();
			fb=billing.next();
			System.out.println(fb.getEmployeeId()+ " "+fb.getEmployeeName()+" "+fb.getSubProjectId() +" "+fb.getSubProjectName()+" "+fb.getTotalOnsiteDays()+" "+fb.getTotalOffshoreDays()+" "+fb.getErrMessage());
			
		}
	}

}
