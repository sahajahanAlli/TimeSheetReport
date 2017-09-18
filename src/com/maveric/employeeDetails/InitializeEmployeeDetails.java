package com.maveric.employeeDetails;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InitializeEmployeeDetails {
	 
	
	public HashMap<String, String> createEmployeeMap(String filePath){
		
		    HashMap<String,String> employeeKey=new HashMap<String,String>();  
	        File file=null;
	        FileInputStream inputStream=null;
	        XSSFWorkbook wbk=null;
	        Sheet sheet=null;
	        
			 try{ 
		          file = new File (filePath);
		          inputStream = new FileInputStream(file);
		           wbk = new XSSFWorkbook(inputStream);
		         
		         
		          sheet = wbk.getSheet("Sheet1");
		         int rowCount = sheet.getLastRowNum();
		         int colCount = sheet.getRow(1).getPhysicalNumberOfCells();

		         
		         for(int i=1;i<=rowCount;i++){
		          Row row = sheet.getRow(i);     
		         

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
			 
			return employeeKey;		 
	}
	
	public ArrayList<Employee> createEmployeeDetails( HashMap<String,String> employeeKey,String filePath){
		
		 	ArrayList<Employee> al=new ArrayList<Employee>();
	        Employee emp=null;
	        File file=null;
	        FileInputStream inputStream=null;
	        XSSFWorkbook wbk=null;
	        Sheet sheet=null;
		
          try{
        	  
        	file = new File (filePath);
	          inputStream = new FileInputStream(file);
	           wbk = new XSSFWorkbook(inputStream);
	           sheet = wbk.getSheet("Sheet1");
		     
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
		        		  emp.setStatus(row.getCell(13).toString());
		        		  empFlag=false;
		       		   
		        		  }
		        		  
		        		  timeWorkdetails.setSubProject(row.getCell(6).toString());
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
			     
	}catch(Exception e){
		 e.printStackTrace();
	 }
          
         return al;

 }
	
	public void displayData(ArrayList<Employee> al){
		
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
					System.out.println(tw.getSubProject()+" "+tw.getActivity()+" "+tw.getWorkLocation()+" "+tw.getCurrentCity()+" "+tw.getActivityDate()+" "+tw.getDuration());
			     
					if( !tw.getActivity().equals("Holiday") || !tw.getActivity().equals("Leave")){
			    	  totalworkinghours+=Double.parseDouble(tw.getDuration());
			      }else{
			    	  totalNonworkinghours += Double.parseDouble(tw.getDuration());
			      }
					
				}
				System.out.println("Total Working Hours : "+totalworkinghours +"   Total Non Working Hours "+totalNonworkinghours);
				e.setTotalWorkingHours(totalworkinghours);
				e.setTotalNonWorkingHours(totalNonworkinghours);
		     }	 	
	}
	
	public void checkAndValidateHours(ArrayList<Employee> al){
		
		HashMap<String,String> subProject=null; 
		HashMap<String,Double> datesOn=null; 
		Iterator<Employee> employeeiterator = al.iterator();
		 Employee e=null;
		 
			while (employeeiterator.hasNext()) {
				subProject=new HashMap<String,String>(); 
				datesOn=new HashMap<String,Double>(); 
				e=new Employee();
				e=employeeiterator.next();
				Iterator<TimeAndWorkLocation> twiterator = e.getTimeAndWorkLocation().iterator();
				TimeAndWorkLocation tw=null;
				
				while (twiterator.hasNext()) {
					tw=new TimeAndWorkLocation();
					tw=twiterator.next();
					subProject.put(tw.getSubProject(), e.getEmployeeId());
					
					if(datesOn.containsKey(tw.getActivityDate())){
					datesOn.put(tw.getActivityDate(), datesOn.get(tw.getActivityDate())+Double.parseDouble(tw.getDuration()));
					}else{
						datesOn.put(tw.getActivityDate(), Double.parseDouble(tw.getDuration()));
					}
				}
				
				System.out.println("For Employee "+e.getEmployeeName()+ "Sub Projects are ");
				 for(@SuppressWarnings("rawtypes") Map.Entry m:subProject.entrySet()){  
		        	   System.out.println(m.getKey()+" "+m.getValue());  
		        	  }
				 
				 for(@SuppressWarnings("rawtypes") Map.Entry<String,Double> m:datesOn.entrySet()){  
		        	  // System.out.println(m.getKey()+" "+m.getValue());  
					 if( m.getValue() <= 8){
						 System.out.println("Valid Values");
					 }else{
						 System.out.println("inValid Values------------------------------------------");
					 }
		        	  }
				 
				
				
		     }
			
		
	}
	
	
	
}
