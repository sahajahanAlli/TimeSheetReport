package com.maveric.employeeDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.maveric.writeErrLog.WriteLog;
import com.maveric.employeeDetails.InitializeEmployeeDetails;

public class Validator {
	
	public HashMap<String, String> capitalisationValidator(String capitalisation){
		
		  HashMap<String,String> capitalisationMap=new HashMap<String,String>(); 
		  HashMap<String,String> SubjectMap=new HashMap<String,String>(); 
	        Sheet sheet=null;
	        ArrayList<String> alSubProject= new ArrayList<String>();
	        ArrayList<String> alSubProjectCapitalisation= new ArrayList<String>();
	        
	        try{ 
				 XSSFWorkbook wbk = InitializeEmployeeDetails.readDataExcel(capitalisation);
		          sheet = wbk.getSheet("timesheetdata");
		         int rowCount = sheet.getLastRowNum();
		         int colCount = sheet.getRow(1).getPhysicalNumberOfCells();
		         for(int i=1;i<=rowCount;i++){
		          Row row = sheet.getRow(i);     
		          for (int j=0; j<colCount;j++){     
		           if(row.getCell(6).toString()!=null && row.getCell(6).toString()!=""){
		        	   SubjectMap.put(row.getCell(6).toString(), row.getCell(1).toString());
		           }
		          }
		         }


		          sheet = wbk.getSheet("capitalisation");
		          rowCount = sheet.getLastRowNum();
		          colCount = sheet.getRow(1).getPhysicalNumberOfCells();
		         for(int i=1;i<=rowCount;i++){
		          Row row = sheet.getRow(i);     
		          for (int j=0; j<colCount;j++){     
		           capitalisationMap.put((row.getCell(0)!=null?(row.getCell(0)+"-"):"")+row.getCell(1).toString(), row.getCell(2).toString()); 
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
			 
			 boolean subProjectPresence=false;
			 String txtmissingSubProject="";
			 
			 if(alSubProject.size()>0){
				 subProjectPresence=true;
				 txtmissingSubProject="Missing Project names are below , Please add these projects in the capitalization Sheet : "+"\n";
			 }
			
			 for(String missingProjectname : alSubProject){
				 txtmissingSubProject+=missingProjectname + "\n"; 
			 }
			 
			 if(subProjectPresence){
				 WriteLog wLog=new WriteLog();
				 wLog.createLogText(txtmissingSubProject);
				 System.out.println(txtmissingSubProject);
			 }
			 
			return capitalisationMap;	
		
	}
	
	
	public HashMap<String, String> employeeIdMapValidator(HashMap<String, String> empMap, String filePath){
		
	    HashMap<String,String> employeeInId_name_roleMapping=new HashMap<String,String>(); 
	   // HashMap<String,String> employeeInConnectData=new HashMap<String,String>(); 
        Sheet sheet=null;
        ArrayList<String> alemployeeInConnectData= new ArrayList<String>();
        ArrayList<String> alEmployeeInMappingSheet= new ArrayList<String>();
        
		 try{ 
			 XSSFWorkbook wbk = InitializeEmployeeDetails.readDataExcel(filePath);
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
		
		 for(String missingemplyeeId : alemployeeInConnectData){
			 
			 txtmissingEmp+="Ëmployee Id : "+missingemplyeeId+ "   Name is : "+empMap.get(missingemplyeeId) + "\n"; 
		 }
		 
		 if(empPresence){
			 WriteLog wLog=new WriteLog();
			 wLog.createLogText(txtmissingEmp);
			 System.out.println(txtmissingEmp);
			// System.exit(0);
		 }
		 
		 
		 
		return employeeInId_name_roleMapping;		 
}


}
