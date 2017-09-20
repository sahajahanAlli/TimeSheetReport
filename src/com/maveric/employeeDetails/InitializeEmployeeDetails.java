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
	        System.out.println(filePath);
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
		        		  empFlag=false;
		       		   
		        		  }
		        		  
		        		  timeWorkdetails.setSubProject(row.getCell(6).toString());
		        		  timeWorkdetails.setActivity(row.getCell(8).toString());
		        		  timeWorkdetails.setWorkLocation(row.getCell(9).toString());
		        		  timeWorkdetails.setCurrentCity(row.getCell(10).toString());
		        		  timeWorkdetails.setActivityDate(row.getCell(11).toString());
		        		  timeWorkdetails.setDuration(row.getCell(12).toString());
		        		  timeWorkdetails.setStatus(row.getCell(13).toString());
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
				System.out.println(e.getEmployeeId() + " "+e.getEmployeeName()+ " "+e.getClientName()+" "+e.getProjectName());
			
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
	
	public ArrayList<FinalBillingClass> checkAndValidateHours(ArrayList<Employee> al){
		
		ArrayList<FinalBillingClass> fbc=new ArrayList<FinalBillingClass>();
		HashMap<String,String> subProject=null; 
		HashMap<String,Double> hourInsubProject=null;
		HashMap<String,Double> datesOn=null; 
		Iterator<Employee> employeeiterator = al.iterator();
		Employee e=null;
		FinalBillingClass fb=null;
		 
			while (employeeiterator.hasNext()) {
				
				String Finalmessage=null;
				fb=new FinalBillingClass();
				subProject=new HashMap<String,String>(); 
				hourInsubProject=new HashMap<String,Double>(); 
				datesOn=new HashMap<String,Double>(); 
				e=new Employee();
				e=employeeiterator.next();
				Iterator<TimeAndWorkLocation> twiterator = e.getTimeAndWorkLocation().iterator();
				TimeAndWorkLocation tw=null;
				String massage="";
				Finalmessage+=e.getEmployeeId()+"  "+e.getEmployeeName();
				
				
				while (twiterator.hasNext()) {
					tw=new TimeAndWorkLocation();
					tw=twiterator.next();
					subProject.put(tw.getSubProject(), e.getEmployeeId());
					
					//checking the hours in a day
					if(tw.getStatus().equals("Approved") || tw.getStatus().equals("Submitted") ){
						if(!tw.getActivity().equals("Holiday")){
					if(datesOn.containsKey(tw.getActivityDate())){
					datesOn.put(tw.getActivityDate(), datesOn.get(tw.getActivityDate())+Double.parseDouble(tw.getDuration()));
					}else{
						datesOn.put(tw.getActivityDate(), Double.parseDouble(tw.getDuration()));
					}
					}
				  }
					
					//onsite and offshore hours
					if(!tw.getSubProject().equals("")){
					if(tw.getWorkLocation().equals("Maveric Premises")){
						
						if(hourInsubProject.containsKey(tw.getSubProject()+"-offshore")){
							hourInsubProject.put(tw.getSubProject()+"-offshore", hourInsubProject.get(tw.getSubProject()+"-offshore")+Double.parseDouble(tw.getDuration()));
							}else{
								hourInsubProject.put(tw.getSubProject()+"-offshore", Double.parseDouble(tw.getDuration()));
							}
						
					}else{
						
						if(hourInsubProject.containsKey(tw.getSubProject()+"-onsite")){
							hourInsubProject.put(tw.getSubProject()+"-onsite", hourInsubProject.get(tw.getSubProject()+"-onsite")+Double.parseDouble(tw.getDuration()));
							}else{
								hourInsubProject.put(tw.getSubProject()+"-onsite", Double.parseDouble(tw.getDuration()));
							}
						
					}
					}
				}
				
				System.out.println("For Employee "+e.getEmployeeName()+ "Sub Projects are ");
				 for(@SuppressWarnings("rawtypes") Map.Entry m:subProject.entrySet()){  
		        	   System.out.println(m.getKey()+" "+m.getValue());  
		        	  }
				 
				 for(@SuppressWarnings("rawtypes") Map.Entry<String,Double> m:datesOn.entrySet()){  
					//  System.out.println(m.getKey()+" "+m.getValue());  
					 if( m.getValue() > 8){
						 massage+="Error : "+e.getEmployeeName() +" has an mismatch in effort on "+m.getKey()+" hours is : "+m.getValue() + " is more than actual hours"+"\n";
						// System.out.println("Error : "+e.getEmployeeName() +" has an mismatch in effort on "+m.getKey()+" hours is : "+m.getValue() + " is more than actual hours");
					 }else if(m.getValue() < 8){
						 massage+="Warning : "+e.getEmployeeName()+" has an mismatch in effort on  "+m.getKey()+" hours is : "+m.getValue()+" is less than actual hours"+"\n";
						// System.out.println("Warning : "+e.getEmployeeName()+" has an mismatch in effort on  "+m.getKey()+" hours is : "+m.getValue()+" is less than actual hours");
					 }else{
						 
					 }
					fb.setErrMessage(massage);
		        	  }
				 System.out.println(massage);
				 
				 for(@SuppressWarnings("rawtypes") Map.Entry m:hourInsubProject.entrySet()){  
					 
		        	   System.out.println(m.getKey()+" "+m.getValue()); 
		        	   Finalmessage+= m.getKey()+" "+m.getValue()+" Hours ";
		        	   fb.setEmployeeId(e.getEmployeeId());
					   fb.setEmployeeName(e.getEmployeeName());
		        	   fb.setSubProjectId(m.getKey().toString().split("-")[0]);
		        	   fb.setSubProjectName(m.getKey().toString().split("-")[1]);
		        	   if(m.getKey().toString().contains("onsite")){
		        		   fb.setTotalOnsiteDays((Double.parseDouble(m.getValue().toString()))/8);
		        	   }else{
		        		   fb.setTotalOffshoreDays((Double.parseDouble(m.getValue().toString()))/8); 
		        	   }
		        	   fbc.add(fb);
		        	  }
				System.out.println(Finalmessage);
				
		     }
		return fbc;	
		
	}
	
	
	
}
