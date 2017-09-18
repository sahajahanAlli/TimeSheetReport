package com.maveric.finalReport;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.maveric.employeeDetails.Employee;
import com.maveric.employeeDetails.TimeAndWorkLocation;


public class GenerateReport {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(" Report is Here ");
		
		 //hash map of the employee
        HashMap<String,String> employeeKey=new HashMap<String,String>();  
        ArrayList<Employee> al=new ArrayList<Employee>();
        Employee emp=null;
        File file=null;
        FileInputStream inputStream=null;
        XSSFWorkbook wbk=null;
        Sheet sheet=null;
        
		 try{ 
	          file = new File ("D:\\NewProject\\TimeSheetReport\\excelFiles\\report.xlsx");
	          inputStream = new FileInputStream(file);
	           wbk = new XSSFWorkbook(inputStream);
	         
	         
	          sheet = wbk.getSheet("Sheet1");
	         int rowCount = sheet.getLastRowNum();
	         int colCount = sheet.getRow(1).getPhysicalNumberOfCells();

	         
	         for(int i=1;i<=rowCount;i++){
	          Row row = sheet.getRow(i);     
	          emp=new Employee();

	          for (int j=0; j<colCount;j++){     
	           employeeKey.put(row.getCell(1).toString(), row.getCell(2).toString()); 
	          }
	         }
	         for(Map.Entry m:employeeKey.entrySet()){  
	        	   System.out.println(m.getKey()+" "+m.getValue());  
	        	  } 

	        }catch(Exception e){
	               e.printStackTrace();
	         }
		 
		 
		 try{
			 
			 for(Map.Entry m:employeeKey.entrySet()){ 
				 
				 ArrayList<TimeAndWorkLocation> timeWork=new ArrayList<TimeAndWorkLocation>();
				 int rowCount = sheet.getLastRowNum();
		         emp=new Employee();
		         Boolean empFlag=true;
		         for(int i=1;i<=rowCount;i++){
		          Row row = sheet.getRow(i);
		          TimeAndWorkLocation timeWorkdetails=null; 
		         
		        	  if( m.getKey().equals(row.getCell(1).toString())){
		        		  
		        		  timeWorkdetails = new TimeAndWorkLocation();
		        		  
		        		  if(empFlag){
		        		  emp.setEmployeeId(row.getCell(1).toString());
		        		  emp.setEmployeeName(row.getCell(2).toString());
		        		  emp.setClientName(row.getCell(4).toString());
		        		  emp.setProjectName(row.getCell(5).toString());
		        		  emp.setSubProject(row.getCell(6).toString());
		        		  emp.setStatus(row.getCell(13).toString());
		        		  
		        		  timeWorkdetails.setActivity(row.getCell(8).toString());
		        		  timeWorkdetails.setWorkLocation(row.getCell(9).toString());
		        		  timeWorkdetails.setCurrentCity(row.getCell(10).toString());
		        		  timeWorkdetails.setActivityDate(row.getCell(11).toString());
		        		  timeWorkdetails.setDuration(row.getCell(12).toString());
		        		  
		        		  empFlag=false;
		        		  timeWork.add(timeWorkdetails);
		        		   
		        		  }
		        		  
		        		  timeWorkdetails.setActivity(row.getCell(8).toString());
		        		  timeWorkdetails.setWorkLocation(row.getCell(9).toString());
		        		  timeWorkdetails.setCurrentCity(row.getCell(10).toString());
		        		  timeWorkdetails.setActivityDate(row.getCell(11).toString());
		        		  timeWorkdetails.setDuration(row.getCell(12).toString());
		        		  timeWork.add(timeWorkdetails);
		        	  }
		         }
		         emp.setTimeAndWorkLocation(timeWork);
		         al.add(emp);
	        	  }
			 
			 if(employeeKey.size() == al.size()){
				 System.out.println("Matched");
			 }else{
				 System.out.println("Not Matched");
			 }
			 
			 Iterator<Employee> employeeiterator = al.iterator();
			 Employee e=null;
				while (employeeiterator.hasNext()) {
					e=new Employee();
					e=employeeiterator.next();
					int totalworkinghours=0;
					int totalNonworkinghours=0;
					System.out.println(e.getEmployeeId() + " "+e.getEmployeeName()+ " "+e.getClientName()+" "+e.getProjectName()+" "+e.getStatus());
				
					Iterator<TimeAndWorkLocation> twiterator = e.getTimeAndWorkLocation().iterator();
					TimeAndWorkLocation tw=null;
					while (twiterator.hasNext()) {
						tw=new TimeAndWorkLocation();
						tw=twiterator.next();
						System.out.println(tw.getActivity()+" "+tw.getWorkLocation()+" "+tw.getCurrentCity()+" "+tw.getActivityDate()+" "+tw.getDuration());
				      if( tw.getActivity() != "Holiday" && tw.getActivity() != "Leave"){
				    	  totalworkinghours+=Integer.parseInt(tw.getDuration());
				      }else{
				    	  totalNonworkinghours = Integer.parseInt(tw.getDuration());
				      }
						
					}
					e.setTotalWorkingHours(totalworkinghours);
					e.setTotalNonWorkingHours(totalNonworkinghours);
			     }
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }

	}

}
