package com.maveric.employeeDetails;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.maveric.writeErrLog.WriteLog;
import com.maveric.employeeDetails.InitializeEmployeeDetails;

public class Validator {
	
	
	
	public HashMap<String, String> capitalisationValidator(String capitalisation) throws IOException{
		
		  HashMap<String,String> capitalisationMap=new HashMap<String,String>(); 
		  HashMap<String,String> SubjectMap=new HashMap<String,String>(); 
	        Sheet sheet=null;
	        ArrayList<String> alSubProject= new ArrayList<String>();
	        ArrayList<String> alSubProjectCapitalisation= new ArrayList<String>();
	        XSSFWorkbook wbk=null;
	        
	        try{ 
				  wbk = InitializeEmployeeDetails.readDataExcel(capitalisation);
		          sheet = wbk.getSheet("timesheetdata");
		         int rowCount = sheet.getLastRowNum();
		         int colCount = sheet.getRow(1).getPhysicalNumberOfCells();
		         for(int i=1;i<=rowCount;i++){
		          Row row = sheet.getRow(i);     
		          for (int j=0; j<colCount;j++){     
		           if(row.getCell(6).toString()!=null && row.getCell(6).toString()!=""){
		        	   SubjectMap.put(row.getCell(6).toString().contains("CH") && row.getCell(6).toString().contains("-")?row.getCell(6).toString().split("-")[1]:row.getCell(6).toString(), row.getCell(1).toString());
		           }
		          }
		         }


		          sheet = wbk.getSheet("capitalisation");
		          rowCount = sheet.getLastRowNum();
		          colCount = sheet.getRow(1).getPhysicalNumberOfCells();
		         for(int i=1;i<=rowCount;i++){
		          Row row = sheet.getRow(i);     
		          for (int j=0; j<colCount;j++){     
		         //  capitalisationMap.put((row.getCell(0)!=null && row.getCell(0).toString()!="" ?(row.getCell(0)+"-"):"")+row.getCell(1).toString(), row.getCell(2).toString()); 
		        	  capitalisationMap.put(row.getCell(1).toString(), row.getCell(2).toString());
		          }
		         }

		        }catch(Exception e){
		               e.printStackTrace();
		         }
			 
			 alSubProject.addAll(SubjectMap.keySet());
			// System.out.println(alSubProject);
			 alSubProjectCapitalisation.addAll(capitalisationMap.keySet());
			 /*System.out.println(alSubProjectCapitalisation);
			 
			 System.out.println("Differenece is : ");
			 
			 System.out.println();
			 
			 System.out.println(alSubProject);*/
			 
			 alSubProject.removeAll(alSubProjectCapitalisation);
			 System.out.println(alSubProject);
			 
			 boolean subProjectPresence=false;
			 String txtmissingSubProject="";
			 
			 if(alSubProject.size()>0){
				 subProjectPresence=true;
				 txtmissingSubProject="Missing Project names are below , Please add these projects in the capitalization Sheet : "+"\n";
			 }
			int count=0;
			int rowcount=sheet.getLastRowNum();
			 for(String missingProjectname : alSubProject){
				 txtmissingSubProject+=missingProjectname + "\n"; 
				String subProjectId= missingProjectname.contains("-") && missingProjectname.contains("CH")?missingProjectname.split("-")[0]:"";
				String subProjectName= missingProjectname.contains("-") && missingProjectname.contains("CH")?missingProjectname.split("-")[1]:missingProjectname;
				 Row row=sheet.createRow(rowcount+count+1);
				 Cell cell=row.createCell(0);
				 cell.setCellValue(subProjectId);
				 cell=row.createCell(1);
				 cell.setCellValue(subProjectName);
				 
				 count++;
			 }
			 
			 if(subProjectPresence){
				 WriteLog wLog=new WriteLog();
				 wLog.createLogText(txtmissingSubProject);
				 System.out.println(txtmissingSubProject);
			 }
			 
			 FileOutputStream os = new FileOutputStream(capitalisation);
             wbk.write(os);
             os.close();
			 
			return capitalisationMap;	
		
	}
	
	
	public HashMap<String, String> employeeIdMapValidator(HashMap<String, String> empMap, String filePath) throws IOException{
		
	    HashMap<String,String> employeeInId_name_roleMapping=new HashMap<String,String>(); 
	   // HashMap<String,String> employeeInConnectData=new HashMap<String,String>(); 
        Sheet sheet=null;
        ArrayList<String> alemployeeInConnectData= new ArrayList<String>();
        ArrayList<String> alEmployeeInMappingSheet= new ArrayList<String>();
        XSSFWorkbook wbk=null;
        
		 try{ 
			  wbk = InitializeEmployeeDetails.readDataExcel(filePath);
	          sheet = wbk.getSheet("employee-name-id-role_mapping");
	         int rowCount = sheet.getLastRowNum();
	         int colCount = sheet.getRow(1).getPhysicalNumberOfCells();
	         for(int i=1;i<=rowCount;i++){
	          Row row = sheet.getRow(i);     
	          for (int j=0; j<colCount;j++){     
	        	  employeeInId_name_roleMapping.put(row.getCell(0).toString(), row.getCell(1).toString()); 
	          }
	         }

	        }catch(Exception e){
	               e.printStackTrace();
	         }
		 
		 alemployeeInConnectData.addAll(empMap.keySet());
		 alEmployeeInMappingSheet.addAll(employeeInId_name_roleMapping.keySet());
		 boolean empPresence=false;
		 String txtmissingEmp="";
		
		 
		/* System.out.println(alemployeeInConnectData);
		 System.out.println(alEmployeeInMappingSheet);
		 
		 System.out.println("Differenece is : ");
		 
		 System.out.println();
		 
		 System.out.println(alemployeeInConnectData);*/
		 
		 alemployeeInConnectData.removeAll(alEmployeeInMappingSheet);
		 
		 if(alemployeeInConnectData.size()>0){
			 empPresence=true;
			 txtmissingEmp="Missing Employee names are below , Please add these projects in the id-role mapping Sheet : "+"\n";
		 }
		
		 int count=0;
		int rowcount=sheet.getLastRowNum();
		 for(String missingemplyeeId : alemployeeInConnectData){
			 
			 txtmissingEmp+="Employee Id : "+missingemplyeeId+ "   Name is : "+empMap.get(missingemplyeeId) + "\n"; 
			 Row row=sheet.createRow(rowcount+count+1);
			 Cell cell=row.createCell(0);
			 cell.setCellValue(missingemplyeeId);
			 cell=row.createCell(1);
			 cell.setCellValue(empMap.get(missingemplyeeId));
			 count++;
			 
		 }
		 
		 FileOutputStream os = new FileOutputStream(filePath);
         wbk.write(os);
         os.close();
		 
		 if(empPresence){
			 WriteLog wLog=new WriteLog();
			 wLog.createLogText(txtmissingEmp);
			 System.out.println(txtmissingEmp);
			// System.exit(0);
		 }
		 
		 
		 
		return employeeInId_name_roleMapping;		 
}

	
public ArrayList<String> rateRoleMappingValidator( String filePath) throws IOException{
		
	    HashMap<String,Integer> rolesInEmployeeIdRoleMappingSheet=new HashMap<String,Integer>();
	    HashMap<String,Integer> roleInRoleRateMappingSheet=new HashMap<String,Integer>(); 
        Sheet sheet=null;
        ArrayList<String> alrolesInEmployeeIdRoleMappingSheet= new ArrayList<String>();
        ArrayList<String> alroleInRoleRateMappingSheet= new ArrayList<String>();
        XSSFWorkbook wbk=null;
        
		 try{ 
			  wbk = InitializeEmployeeDetails.readDataExcel(filePath);
	          sheet = wbk.getSheet("employee-name-id-role_mapping");
	         int rowCount = sheet.getLastRowNum();
	         int colCount = sheet.getRow(1).getPhysicalNumberOfCells();
	         for(int i=1;i<=rowCount;i++){
	          Row row = sheet.getRow(i);     
	          for (int j=0; j<colCount;j++){     
	        	  rolesInEmployeeIdRoleMappingSheet.put(row.getCell(2).toString(), 0); 
	          }
	         }

	        }catch(Exception e){
	               e.printStackTrace();
	         }
		 
		 
		 try{ 
			  wbk = InitializeEmployeeDetails.readDataExcel(filePath);
	          sheet = wbk.getSheet("employee_rates");
	         int rowCount = sheet.getLastRowNum();
	         int colCount = sheet.getRow(1).getPhysicalNumberOfCells();
	         for(int i=1;i<=rowCount;i++){
	          Row row = sheet.getRow(i);     
	          for (int j=0; j<colCount;j++){     
	        	  roleInRoleRateMappingSheet.put(row.getCell(0).toString(), 0); 
	          }
	         }

	        }catch(Exception e){
	               e.printStackTrace();
	         }
		 
		 alrolesInEmployeeIdRoleMappingSheet.addAll(rolesInEmployeeIdRoleMappingSheet.keySet());
		 alroleInRoleRateMappingSheet.addAll(roleInRoleRateMappingSheet.keySet());
		 boolean rolePeresence=false;
		 String txtmissingEmp="";
		 
		 System.out.println(alrolesInEmployeeIdRoleMappingSheet);
		 System.out.println(alroleInRoleRateMappingSheet);
		 
		 alrolesInEmployeeIdRoleMappingSheet.removeAll(alroleInRoleRateMappingSheet);
		 
		 if(alrolesInEmployeeIdRoleMappingSheet.size()>0){
			 rolePeresence=true;
			 txtmissingEmp="Missing Employee Roles are below , Please add these projects in the employee rates sheet : "+"\n";
		 }
		
		 int count=0;
		int rowcount=sheet.getLastRowNum();
		 for(String missingemplyeerole : alrolesInEmployeeIdRoleMappingSheet){
			 Row row=sheet.createRow(rowcount+count+1);
			 Cell cell=row.createCell(0);
			 cell.setCellValue(missingemplyeerole);
			 count++;
			 
		 }
		 
		 FileOutputStream os = new FileOutputStream(filePath);
         wbk.write(os);
         os.close();
		 
		 if(rolePeresence){
			 WriteLog wLog=new WriteLog();
			 wLog.createLogText(txtmissingEmp);
			 System.out.println(txtmissingEmp);
		 }
		 
		 
		 
		return alrolesInEmployeeIdRoleMappingSheet;		 
}

public ArrayList<String> subprojectValidator(String filePath){
	
	  Sheet sheet=null;
      ArrayList<String> txtMissingProject= new ArrayList<String>();
      XSSFWorkbook wbk=null;
      
		 try{ 
			  wbk = InitializeEmployeeDetails.readDataExcel(filePath);
	          sheet = wbk.getSheet("timesheetdata");
	         int rowCount = sheet.getLastRowNum();
	         for(int i=1;i<=rowCount;i++){
	          Row row = sheet.getRow(i);       
	        	  if(!(row.getCell(8).toString().equals("Holiday") || row.getCell(8).toString().equals("Leave") || row.getCell(8).toString().equals("Comp.Off") || row.getCell(8).toString().equals("Bench") ||row.getCell(8).toString().equals("Knowledge transfer") || row.getCell(8).toString().equals("Travel Arrival") || row.getCell(8).toString().equals("Travel Departure"))){
	        		  if(row.getCell(6).toString() == null || row.getCell(6).toString()== ""){
	        			  txtMissingProject.add(" Employee ID : "+row.getCell(1).toString()+ " Name : "+row.getCell(2).toString() +" has missing subproject on date "+row.getCell(11).toString()+"\n");
	        		  }
	          }
	         }

	        }catch(Exception e){
	               e.printStackTrace();
	         }
		 
		WriteLog.createLogTextArray(txtMissingProject,"NullSubProjectReport");
	
	return txtMissingProject;
	
}

public ArrayList<String> rejectTimeSheetValidator(String filePath){
	
	 Sheet sheet=null;
     ArrayList<String> txtRejectedTimedata= new ArrayList<String>();
     XSSFWorkbook wbk=null;
     
		 try{ 
			  wbk = InitializeEmployeeDetails.readDataExcel(filePath);
	          sheet = wbk.getSheet("timesheetdata");
	         int rowCount = sheet.getLastRowNum();
	         for(int i=1;i<=rowCount;i++){
	          Row row = sheet.getRow(i);         
	        	  if(!(row.getCell(8).toString().equals("Holiday") || row.getCell(8).toString().equals("Leave") || row.getCell(8).toString().equals("Comp.Off") || row.getCell(8).toString().equals("Bench") ||row.getCell(8).toString().equals("Knowledge transfer") || row.getCell(8).toString().equals("Travel Arrival") || row.getCell(8).toString().equals("Travel Departure"))){
	        		  if(row.getCell(13).toString().equals("Rejected")){
	        			  txtRejectedTimedata.add(" Employee ID : "+row.getCell(1).toString()+ " Name : "+row.getCell(2).toString() +" timesheet has been rejected on "+row.getCell(11).toString()+"\n");
	        		  }
	          }
	         }

	        }catch(Exception e){
	               e.printStackTrace();
	         }
		 
		WriteLog.createLogTextArray(txtRejectedTimedata ,"RejectTimeData");
	
	return txtRejectedTimedata;
}

public ArrayList<String> EffortHoursValidator(String filePath){
	
	 Sheet sheet=null;
    ArrayList<String> txtEffortHoursValidator= new ArrayList<String>();
    XSSFWorkbook wbk=null;
    
		 try{ 
			  wbk = InitializeEmployeeDetails.readDataExcel(filePath);
	          sheet = wbk.getSheet("timesheetdata");
	         int rowCount = sheet.getLastRowNum();
	         for(int i=1;i<=rowCount;i++){
	          Row row = sheet.getRow(i);         
	        	  if(!(row.getCell(8).toString().equals("Holiday") || row.getCell(8).toString().equals("Leave") || row.getCell(8).toString().equals("Comp.Off") || row.getCell(8).toString().equals("Bench") ||row.getCell(8).toString().equals("Knowledge transfer") || row.getCell(8).toString().equals("Travel Arrival") || row.getCell(8).toString().equals("Travel Departure"))){
	        		  if(Double.parseDouble(row.getCell(12).toString()) % 2 != 0){
	        			  txtEffortHoursValidator.add(" Employee ID : "+row.getCell(1).toString()+ " Name : "+row.getCell(2).toString() +" Filled odd hours on "+row.getCell(11).toString()+ " Entered effor hour is "+row.getCell(12).toString()+"\n");
	        		  }
	          }
	         }

	        }catch(Exception e){
	               e.printStackTrace();
	         }
		 
		WriteLog.createLogTextArray(txtEffortHoursValidator,"EvenOddHoursValidator");
	
	return txtEffortHoursValidator;
}
}
