package com.maveric.finalReport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.maveric.employeeDetails.Employee;
import com.maveric.employeeDetails.FinalBillingClass;
import com.maveric.employeeDetails.InitializeEmployeeDetails;
import com.maveric.finalReport.CreateReportExcel;
import com.maveric.employeeDetails.Validator;
import com.maveric.employeeDetails.CreateBackUp;



public class GenerateReport {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(" Report is Here ");
		CreateReportExcel ce=new CreateReportExcel();
		String excelFilePath=Paths.get(".").toAbsolutePath().normalize().toString();
		
		if(args.length == 0 ){
			System.out.println("Type --help to se the details");
			System.exit(1);
		}else if( args[0].equals("--help")){
			System.out.println(" Required parameters are : " + "Connect timesheet with all sub Project fields, "+"Name of the sheet : "+"TimeSheetData"+"\n"+" employee-name-id-role_mapping sheet ,"+"Name of the sheet : "+"employee-name-id-role_mapping"+"\n"+" roles and onsite/offshore rates :  "+" Name of the sheet : "+"employee_rates"+"\n"+" capitalization, "+" Name of the sheet : "+"capitalization"+"\n");
			System.out.println("Command : java -jar timesheet.jar TimeSheetData.xlsx employee-name-id-role_mapping.xlsx employee_rates.xlsx capitalization.xlsx"+"\n");
			System.out.println("Column details for TimeSheetData.xlsx : "+"\n"+"1. Week"+"\n"+"2. Employee ID"+"\n"+"3. Employee Name"+"\n"+"4. Employee Current Status"+"\n"+"5. Client Name"+"\n"+"6. Project Name"+"\n"+"7. Sub Project"+"\n"+"8. Project Current Status"+"\n"+"9. Activity"+"\n"+"10. Work Location"+"\n"+"11. Current City"+"\n"+"12. Activity Date"+"\n"+"13. Duration"+"\n"+"14. Status"+"\n"+"15. Status On"+"\n"+"16. Approver Employee ID"+"\n"+"17. Approved By"+"\n");
			System.out.println("Column details for employee-name-id-role_mapping.xlsx : "+"\n"+"1. Emp Id"+"\n"+"2. Resource "+"\n"+"3. Role "+"\n");
			System.out.println("Column details for employee_rates.xlsx : "+"\n"+"1. Role "+"\n"+"2. onsite "+"\n"+"3. offshore "+"\n");
			System.out.println("Column details for capitalization.xlsx : "+"\n"+"1. Project Code "+"\n"+"2. QC/ITCR/Project Ref"+"\n"+"3. Capitalization "+"\n");
			System.exit(1);
		}
		
		File errorLogFile=new File(excelFilePath+"\\ErrorLog\\errLog.txt");
		if(errorLogFile.exists()){
			errorLogFile.delete();
		}
		File f = new File(excelFilePath+"\\BackUpFiles");
		if(!f.isDirectory()){
		System.out.println("Folder is not present.");
		boolean success=f.mkdirs();
		if(!success){
			System.out.println("Problem in creating Directory");
		}else{
		System.out.println("'inputFile' Folder is created ,Please place all the required excel.");	
		}
		System.exit(1);
		}
		
		
		CreateBackUp cb=new CreateBackUp();
		System.out.println("Back up Status : "+cb.copyFiles());
		String filePath=excelFilePath+"\\BackUpFiles\\"+args[0];
		InitializeEmployeeDetails ied=new InitializeEmployeeDetails();
		Validator validateDataExcel=new Validator();
		HashMap<String, String> empMap = ied.createEmployeeMap(filePath, filePath);
		validateDataExcel.capitalisationValidator(filePath);
		validateDataExcel.employeeIdMapValidator(empMap,filePath);
		ArrayList<Employee> al=ied.createEmployeeDetails(empMap, filePath);
		ied.displayData(al);
		ArrayList<FinalBillingClass> fbc=ied.checkAndValidateHours(al);
		HashMap<String, String> projectIdFiller = ied.fillerProjectId( filePath);
		//Iterator<FinalBillingClass>  billing= fbc.iterator();
		//FinalBillingClass fb=null;
		HashMap<String, Double> capitalizarionMap = ied.createCapitalizationMap(filePath);
		HashMap<String, String> roleMappingMap = ied.createEmployeeRoleMappingMap(filePath);
		HashMap<String, String> rateMap = ied.createEmployeeRateMap(filePath);
		ce.createExcelEmployeeDetails(fbc,roleMappingMap,rateMap,capitalizarionMap);
	//	ce.CreateOnsiteReportExcel(fbc,roleMappingMap,rateMap,capitalizarionMap);
	//	ce.CreateOffshoreReportExcel(fbc,roleMappingMap,rateMap,capitalizarionMap);
	}

}
