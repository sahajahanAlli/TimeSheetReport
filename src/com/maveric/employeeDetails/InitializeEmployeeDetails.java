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

import com.maveric.writeErrLog.WriteLog;

public class InitializeEmployeeDetails {
	 
	
	public HashMap<String, String> createEmployeeMap(String filePath,String capitalisationFile){
		
		    HashMap<String,String> employeeKey=new HashMap<String,String>();  
		    HashMap<String,String> subProjectData=new HashMap<String,String>();  
	        Sheet sheet=null;
			 try{ 
				 XSSFWorkbook wbk = readDataExcel(capitalisationFile);
		          sheet = wbk.getSheet("timesheetdata");
		         int rowCount = sheet.getLastRowNum();
		         int colCount = sheet.getRow(1).getPhysicalNumberOfCells();
		         for(int i=1;i<=rowCount;i++){
		          Row row = sheet.getRow(i);     
		          for (int j=0; j<colCount;j++){     
		           employeeKey.put(row.getCell(1).toString(), row.getCell(2).toString()); 
		           if(row.getCell(6).toString()!=null && row.getCell(6).toString()!=""){
		           subProjectData.put(row.getCell(6).toString(), row.getCell(1).toString());
		           }
		          }
		         }

		        }catch(Exception e){
		               e.printStackTrace();
		         }
			 
			// capitalisationValidator(subProjectData,capitalisationFile);
			 
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
	           sheet = wbk.getSheet("timesheetdata");
		     
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
				Iterator<TimeAndWorkLocation> twiterator = e.getTimeAndWorkLocation().iterator();
				TimeAndWorkLocation tw=null;
				while (twiterator.hasNext()) {
					tw=new TimeAndWorkLocation();
					tw=twiterator.next();
					if( !tw.getActivity().equals("Holiday") && !tw.getActivity().equals("Leave") && tw.getActivity().equals("Comp.Off")){
			    	  totalworkinghours+=Double.parseDouble(tw.getDuration());
			      }else{
			    	  totalNonworkinghours += Double.parseDouble(tw.getDuration());
			      }
					
				}
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
				
			//	double totalworkinghours=0;
				String Finalmessage=null;
				
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
					if(!tw.getActivity().equals("Holiday") && !tw.getActivity().equals("Leave") && !tw.getActivity().equals("Comp.Off")){
						if(!(tw.getSubProject()==null || tw.getSubProject()=="")){
							subProject.put(tw.getSubProject(), e.getEmployeeId());
						}else{
							subProject.put("Please Fill the sub project "+tw.getActivity().toString(), e.getEmployeeId());	
						}
					}
					//checking the hours in a day
					if(tw.getStatus().equals("Approved") || tw.getStatus().equals("Submitted") || tw.getActivity().equals("Not Submitted")){
						if(!tw.getActivity().equals("Holiday") && !tw.getActivity().equals("Leave") && !tw.getActivity().equals("Comp.Off")){
					if(datesOn.containsKey(tw.getActivityDate())){
					datesOn.put(tw.getActivityDate(), datesOn.get(tw.getActivityDate())+Double.parseDouble(tw.getDuration()));
					}else{
						datesOn.put(tw.getActivityDate(), Double.parseDouble(tw.getDuration()));
					}
					}
				  }
					
					
					//onsite and offshore hours
					//if(!tw.getSubProject().equals("")){
					if(!tw.getActivity().equals("Holiday") && !tw.getActivity().equals("Leave") && !tw.getActivity().equals("Comp.Off") && !tw.getSubProject().equals("")){
						System.out.println(tw.getActivityDate() + "            "+tw.getDuration()+"   "+tw.getSubProject());
					if(tw.getWorkLocation().equals("Maveric Premises")){
						
						
						if(hourInsubProject.containsKey(tw.getSubProject()+".offshore")){
							hourInsubProject.put(tw.getSubProject()+".offshore", hourInsubProject.get(tw.getSubProject()+".offshore")+Double.parseDouble(tw.getDuration()));
							}else{
								hourInsubProject.put(tw.getSubProject()+".offshore", Double.parseDouble(tw.getDuration()));
							}
						
					}else{
						
						if(hourInsubProject.containsKey(tw.getSubProject()+".onsite")){
							hourInsubProject.put(tw.getSubProject()+".onsite", hourInsubProject.get(tw.getSubProject()+".onsite")+Double.parseDouble(tw.getDuration()));
							}else{
								hourInsubProject.put(tw.getSubProject()+".onsite", Double.parseDouble(tw.getDuration()));
							}
						
					}
					
					//totalworkinghours+=Double.parseDouble(tw.getDuration());
					}
				}
				 
				 for(@SuppressWarnings("rawtypes") Map.Entry<String,Double> m:datesOn.entrySet()){    
					 if( m.getValue() > 8){
						 massage+="Error : "+e.getEmployeeName() +" has an mismatch in effort on "+m.getKey()+" hours is : "+m.getValue() + " is more than actual hours"+"\n";
					 }else if(m.getValue() < 8){
						 massage+="Warning : "+e.getEmployeeName()+" has an mismatch in effort on  "+m.getKey()+" hours is : "+m.getValue()+" is less than actual hours"+"\n";
					 }else{
						 
					 }
					
		        	  }
				 HashMap<String,String> subProjCount=new HashMap<String,String>(); 
				 boolean checkFlag=true;
			
				 
				                  for(@SuppressWarnings("rawtypes") Map.Entry m:hourInsubProject.entrySet()){  		 
					 	        	  fb=new FinalBillingClass();
						        	   fb.setEmployeeId(e.getEmployeeId());
						        	   System.out.println(e.getEmployeeId());
									   fb.setEmployeeName(e.getEmployeeName());
									   if(!(m.getKey().toString().contains("ITCR-") || m.getKey().toString().contains("DEF-"))){
						        	   fb.setSubProjectId(m.getKey().toString().contains("-") && m.getKey().toString().contains("CH")?m.getKey().toString().split("-")[0]:"");
						        	   fb.setSubProjectName(m.getKey().toString().contains("-") && m.getKey().toString().contains("CH")?m.getKey().toString().split("-")[1]:m.getKey().toString());
						        	   System.out.println("++++++++++++++++++++++++++++++++"+m.getKey().toString());
									   }else{
										   fb.setSubProjectId("CH242");
							        	   fb.setSubProjectName(m.getKey().toString().split("-")[0]+"-"+m.getKey().toString().split("-")[1]);
							        	   System.out.println("++++++++++++++++++++++++++++++++"+m.getKey().toString().split("-")[0]+"-"+m.getKey().toString().split("-")[1]);
									   }
						        	   
					 		        	   if(m.getKey().toString().contains("onsite")){
					 		        		   fb.setTotalOnsiteDays((Double.parseDouble(m.getValue().toString()))/8);
					 		        		   fb.setLocation("onsite");
							        	   }else{
					 		        		   fb.setTotalOffshoreDays((Double.parseDouble(m.getValue().toString()))/8);
					 		        		   fb.setLocation("offshore");
					 		        	   }
					 		        	  fb.setErrMessage(massage);
					 		        	   fbc.add(fb);
					  		        	  }
				
		     }
		return fbc;	
		
	}
	
	public HashMap<String, String> createEmployeeRoleMappingMap(String filePath){
		
		 HashMap<String,String> roleMapping=new HashMap<String,String>();  
	        Sheet sheet=null;
			 try{ 
				 XSSFWorkbook wbk = readDataExcel(filePath);
		          sheet = wbk.getSheet("employee-name-id-role_mapping");
		         int rowCount = sheet.getLastRowNum();
		         for(int i=1;i<=rowCount;i++){
		          Row row = sheet.getRow(i);      
		        	 // roleMapping.put(row.getCell(0).toString()+"_"+row.getCell(1).toString(), row.getCell(2).toString());
		          roleMapping.put(row.getCell(0).toString(), row.getCell(2).toString());
		         }
		        }catch(Exception e){
		               e.printStackTrace();
		         }
			 
			return roleMapping;		
		
	}
	
	public HashMap<String, String> createEmployeeRateMap(String filePath){
		
		 HashMap<String,String> roleMapping=new HashMap<String,String>();  
	        Sheet sheet=null;
			 try{ 
				 XSSFWorkbook wbk = readDataExcel(filePath);
		          sheet = wbk.getSheet("employee_rates");
		         int rowCount = sheet.getLastRowNum();
		         for(int i=1;i<=rowCount;i++){
		          Row row = sheet.getRow(i);          
		        	  roleMapping.put(row.getCell(0).toString()+"_"+"onsite", row.getCell(1).toString()); 
		        	  roleMapping.put(row.getCell(0).toString()+"_"+"offshore", row.getCell(2).toString()); 
		         }

		        }catch(Exception e){
		               e.printStackTrace();
		         }
			 
			return roleMapping;		
		
	}
	
	public HashMap<String, Double> createCapitalizationMap(String filePath){
		
		 HashMap<String,Double> capitalizationMapping=new HashMap<String,Double>();  
	        Sheet sheet=null;
			 try{ 
		          XSSFWorkbook wbk = readDataExcel(filePath);
		          sheet = wbk.getSheet("capitalisation");
		         int rowCount = sheet.getLastRowNum();
		       
		         for(int i=1;i<=rowCount;i++){
		          Row row = sheet.getRow(i);      
		          capitalizationMapping.put(row.getCell(1).toString(), Double.parseDouble(row.getCell(2).toString())); 
		        
		         } 

		        }catch(Exception e){
		               e.printStackTrace();
		         }
			 
			return capitalizationMapping;	
		
	}
	
	public static XSSFWorkbook readDataExcel(String filePath){
		
		 File file=null;
	        FileInputStream inputStream=null;
	        XSSFWorkbook wbk=null;
			 try{ 
		          file = new File (filePath);
		          inputStream = new FileInputStream(file);
		          wbk = new XSSFWorkbook(inputStream);
		   
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			return wbk;
	}
	
	/*public HashMap<String, String> fillerProjectId(String filePath){
		
		HashMap<String,String> projectIdFiller=new HashMap<String,String>();  
        Sheet sheet=null;
		 try{ 
	          XSSFWorkbook wbk = readDataExcel(filePath);
	          sheet = wbk.getSheet("ProjectId");
	         int rowCount = sheet.getLastRowNum();
	       
	         for(int i=1;i<=rowCount;i++){
	          Row row = sheet.getRow(i);      
	          projectIdFiller.put(row.getCell(1).toString(), row.getCell(0).toString()); 
	        
	         } 

	        }catch(Exception e){
	               e.printStackTrace();
	         }
		 
		return projectIdFiller;	
		
	}*/
	
	
	
	public  ArrayList<WorkingDaysDetails>  checkWorkingDays(String filePath){
		
		Sheet sheet=null;
		 ArrayList<WorkingDaysDetails> alwdd=new ArrayList<WorkingDaysDetails>();
		 try{ 
	          XSSFWorkbook wbk = readDataExcel(filePath);
	          sheet = wbk.getSheet("WorkingDays");
	         int rowCount = sheet.getLastRowNum();
	         
	        
	         
	         WorkingDaysDetails wdd=null;
	       
	         for(int i=1;i<=rowCount;i++){
	          Row row = sheet.getRow(i);   
	          wdd=new WorkingDaysDetails();
	          wdd.setMonthName(row.getCell(0).toString());
	          wdd.setOnsiteWorkingDays(row.getCell(1).toString());
	          wdd.setOffshoreWorkingDays(row.getCell(2).toString());
	          alwdd.add(wdd);
	         } 

	        }catch(Exception e){
	               e.printStackTrace();
	         }
		return alwdd;
	}
	
}
