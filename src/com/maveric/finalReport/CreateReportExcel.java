package com.maveric.finalReport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.maveric.employeeDetails.FinalBillingClass;

public class CreateReportExcel {
	
	public void createExcelEmployeeDetails(ArrayList<FinalBillingClass> fbc, HashMap<String, String> roleMappingMap, HashMap<String, String> rateMap, HashMap<String, Double> capitalizarionMap) throws IOException{
		
		String reportPath=Paths.get(".").toAbsolutePath().normalize().toString()+"\\Log";
		System.out.println("Report File path : "+reportPath);
				
		try {
			
			File f = new File(reportPath);
			if(!f.isDirectory()){
				boolean success=f.mkdirs();
				if(!success){
					System.out.println("Problem in creating Directory");
				}
			}else{
				
			}
			
	        FileOutputStream fos = new FileOutputStream(reportPath+"\\report.xlsx");
	        XSSFWorkbook  workbook = new XSSFWorkbook();            

	        XSSFSheet sheet = workbook.createSheet("Hour_Details");  
	        
	        //naming the columns
	        
	        Row row = sheet.createRow(0);
			
			Cell cell0 = row.createCell(0);
	        cell0.setCellValue("Employee ID");
	        
	        Cell cell1 = row.createCell(1);
	        cell1.setCellValue("Employee Name");
	        
	        Cell cell2 = row.createCell(2);
	        cell2.setCellValue("ProjectId");
			
	        Cell cell3 = row.createCell(3);
	        cell3.setCellValue("Sub_Project");
	        
	        Cell cell4 = row.createCell(4);
	        cell4.setCellValue("Invoice Reference");
	        
	        Cell cell5 = row.createCell(5);
	        cell5.setCellValue("Onsite Days");
	        
	        Cell cell6 = row.createCell(6);
	        cell6.setCellValue("Offshore Days");
	        
	        Cell cell7 = row.createCell(7);
	        cell7.setCellValue("Role");
	        
	        Cell cell8 = row.createCell(8);
	        cell8.setCellValue("Rate");
	        
	        Cell cell9 = row.createCell(9);
	        cell9.setCellValue("Total Amount");
	        
	        Cell cell10 = row.createCell(10);
	        cell10.setCellValue("Rate Reduction");
	        
	        Cell cell11 = row.createCell(11);
	        cell11.setCellValue("Total After Rate Reduction");
	        
	        Cell cell12 = row.createCell(12);
	        cell12.setCellValue("Capitalisable %");
	        
	        Cell cell13 = row.createCell(13);
	        cell13.setCellValue("Capitalisable");
	        
	        Cell cell14 = row.createCell(14);
	        cell14.setCellValue("Non-Capitalisable");
	        
	        Cell cell15 = row.createCell(15);
	        cell15.setCellValue("Total without Tax");
	        
	        Cell cell16 = row.createCell(16);
	        cell16.setCellValue("Capitalisable including tax");
	        
	        Cell cell17 = row.createCell(17);
	        cell17.setCellValue("Non-Capitalisable including tax");
	        
	        Cell cell18 = row.createCell(18);
	        cell18.setCellValue("Total Amount 2");
	        
	        Cell cell19 = row.createCell(19);
	        cell19.setCellValue("Error Message");
	        
	        
	        int i=1;
	        Iterator<FinalBillingClass>  billing= fbc.iterator();
			FinalBillingClass fb=null;
			
			while (billing.hasNext()) {
				 row = sheet.createRow(i);
				 fb=new FinalBillingClass();
				 fb=billing.next();
				
				 cell0 = row.createCell(0);
		        cell0.setCellValue(fb.getEmployeeId().toString().contains(".")?fb.getEmployeeId().toString().split("\\.")[0]:fb.getEmployeeId());
		        
		         cell1 = row.createCell(1);
		        cell1.setCellValue(fb.getEmployeeName());
		        
		         cell2 = row.createCell(2);
		        cell2.setCellValue(fb.getSubProjectId());
				
		         cell3 = row.createCell(3);
		        cell3.setCellValue(fb.getSubProjectName().split("\\.")[0]);
		        
		         cell4 = row.createCell(4);
		        cell4.setCellValue(fb.getLocation());
		        
		         cell5 = row.createCell(5);
		        cell5.setCellValue(fb.getTotalOnsiteDays());
		        
		         cell6 = row.createCell(6);
		        cell6.setCellValue(fb.getTotalOffshoreDays());
		        
		        
		          cell7 = row.createCell(7);
		        cell7.setCellValue(roleMappingMap.get(fb.getEmployeeId().toString()));
		        
		          cell8 = row.createCell(8);
		        cell8.setCellValue(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString())+"_"+fb.getLocation()));
		        System.out.println(fb.getEmployeeId().toString());
		        
		        cell9 = row.createCell(9);
		        double totalAmount=0.0;
		        if(fb.getLocation().toString().equals("onsite")){
		        	//totalAmount=fb.getTotalOnsiteDays() * Double.parseDouble(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString()+"_"+fb.getEmployeeName().toString())+"_"+fb.getLocation()));
		        	totalAmount=fb.getTotalOnsiteDays() * Double.parseDouble(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString())+"_"+fb.getLocation()));
		        	 cell9.setCellValue(totalAmount);
		        }else{
		        	System.out.println(fb.getEmployeeId().toString()+"_"+fb.getLocation());
	        	System.out.println(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString())+"_"+fb.getLocation()));
		          //totalAmount=fb.getTotalOffshoreDays() * Double.parseDouble(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString()+"_"+fb.getEmployeeName().toString())+"_"+fb.getLocation()));
		        	totalAmount=fb.getTotalOffshoreDays() * Double.parseDouble(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString())+"_"+fb.getLocation()));
		        	 cell9.setCellValue(totalAmount);
		        }
		        
		        cell10 = row.createCell(10);
		        cell10.setCellValue(0);
		        
		        cell11 = row.createCell(11);
		        cell11.setCellValue((totalAmount*(100-0)/100));
		        
		        cell12 = row.createCell(12);
		        System.out.println(fb.getSubProjectName());
		        System.out.println("888888888888888888888"+fb.getSubProjectName());
		        cell12.setCellValue(capitalizarionMap.get(fb.getSubProjectName().split("\\.")[0]));
		        
		        cell13 = row.createCell(13);
		        cell13.setCellValue( (totalAmount*(100-0)/100)*(capitalizarionMap.get(fb.getSubProjectName().split("\\.")[0])/100));
		        
		        cell14 = row.createCell(14);
		        cell14.setCellValue( (totalAmount*(100-0)/100)*((100-capitalizarionMap.get(fb.getSubProjectName().split("\\.")[0]))/100));
		        
		        // total Capitalisable + Non Capitalisable without tax
		        cell15 = row.createCell(15);
		        cell15.setCellValue((totalAmount*(100-0)/100)*(capitalizarionMap.get(fb.getSubProjectName().split("\\.")[0])/100)+(totalAmount*(100-0)/100)*((100-capitalizarionMap.get(fb.getSubProjectName().split("\\.")[0]))/100));
		        
		        cell16 = row.createCell(16);
		        cell16.setCellValue( (totalAmount*(100-0)/100)*(capitalizarionMap.get(fb.getSubProjectName().split("\\.")[0])/100)*(120.00/100));
		        
		        cell17 = row.createCell(17);
		        cell17.setCellValue( (totalAmount*(100-0)/100)*(100-capitalizarionMap.get(fb.getSubProjectName().split("\\.")[0]))/100*(120.00/100));
		     
		        // total Capitalisable + Non Capitalisable with tax
		        cell18 = row.createCell(18);
		        cell18.setCellValue( (totalAmount*(100-0)/100)*(capitalizarionMap.get(fb.getSubProjectName().split("\\.")[0])/100)*(120.00/100)+ (totalAmount*(100-0)/100)*(100-capitalizarionMap.get(fb.getSubProjectName().split("\\.")[0]))/100*(120.00/100));
		        
		        cell19 = row.createCell(19);
		        cell19.setCellValue(fb.getErrMessage());
		        
		      i++;
		      			
			}
			workbook.write(fos);
			fos.flush();
	        fos.close();
	    } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }	
	}
	
		
	/*	public void CreateOnsiteReportExcel(ArrayList<FinalBillingClass> fbc, HashMap<String, String> roleMappingMap, HashMap<String, String> rateMap, HashMap<String, Double> capitalizarionMap) throws IOException{
			
			String reportPath=Paths.get(".").toAbsolutePath().normalize().toString()+"\\Log";
			System.out.println(reportPath);
			
			
			try {
				
				File f = new File(reportPath);
				if(!f.isDirectory()){
					boolean success=f.mkdirs();
					if(!success){
						System.out.println("Problem in creating Directory");
					}
				}else{
					
				}
				
		        FileOutputStream fos = new FileOutputStream(reportPath+"\\Onsite_report.xlsx");
		        XSSFWorkbook  workbook = new XSSFWorkbook();            

		        XSSFSheet sheet = workbook.createSheet("Hour_Details");  
		        
		        //naming the columns
		        
		        Row row = sheet.createRow(0);
				
				Cell cell0 = row.createCell(0);
		        cell0.setCellValue("Employee ID");
		        
		        Cell cell1 = row.createCell(1);
		        cell1.setCellValue("Employee Name");
		        
		        Cell cell2 = row.createCell(2);
		        cell2.setCellValue("ProjectId");
				
		        Cell cell3 = row.createCell(3);
		        cell3.setCellValue("Sub_Project");
		        
		        Cell cell4 = row.createCell(4);
		        cell4.setCellValue("Invoice Reference");
		        
		        Cell cell5 = row.createCell(5);
		        cell5.setCellValue("Onsite Days");
		        
		        Cell cell6 = row.createCell(6);
		        cell6.setCellValue("Offshore Days");
		        
		        Cell cell7 = row.createCell(7);
		        cell7.setCellValue("Role");
		        
		        Cell cell8 = row.createCell(8);
		        cell8.setCellValue("Rate");
		        
		        Cell cell9 = row.createCell(9);
		        cell9.setCellValue("Total Amount");
		        
		        Cell cell10 = row.createCell(10);
		        cell10.setCellValue("Rate Reduction");
		        
		        Cell cell11 = row.createCell(11);
		        cell11.setCellValue("Total After Rate Reduction");
		        
		        Cell cell12 = row.createCell(12);
		        cell12.setCellValue("Capitalisable %");
		        
		        Cell cell13 = row.createCell(13);
		        cell13.setCellValue("Capitalisable");
		        
		        Cell cell14 = row.createCell(14);
		        cell14.setCellValue("Non-Capitalisable");
		        
		        Cell cell15 = row.createCell(15);
		        cell15.setCellValue("Total without Tax");
		        
		        Cell cell16 = row.createCell(16);
		        cell16.setCellValue("Capitalisable including tax");
		        
		        Cell cell17 = row.createCell(17);
		        cell17.setCellValue("Non-Capitalisable including tax");
		        
		        Cell cell18 = row.createCell(18);
		        cell18.setCellValue("Total Amount 2");
		        
		        Cell cell19 = row.createCell(19);
		        cell19.setCellValue("Error Message");
		        
		        
		        int i=1;
		        Iterator<FinalBillingClass>  billing= fbc.iterator();
				FinalBillingClass fb=null;
				
				while (billing.hasNext()) {
					// row = sheet.createRow(i);
					 fb=new FinalBillingClass();
					 fb=billing.next();
					 
					 if(fb.getLocation().toString().equals("onsite")){
						 row = sheet.createRow(i);
					 cell0 = row.createCell(0);
			        cell0.setCellValue(fb.getEmployeeId().contains(".")?fb.getEmployeeId().split("\\.")[0]:fb.getEmployeeId());
			        
			         cell1 = row.createCell(1);
			        cell1.setCellValue(fb.getEmployeeName());
			        
			         cell2 = row.createCell(2);
			        cell2.setCellValue(fb.getSubProjectId());
					
			         cell3 = row.createCell(3);
			        cell3.setCellValue(fb.getSubProjectName());
			        
			         cell4 = row.createCell(4);
			        cell4.setCellValue(fb.getLocation());
			        
			         cell5 = row.createCell(5);
			        cell5.setCellValue(fb.getTotalOnsiteDays());
			        
			         cell6 = row.createCell(6);
			        cell6.setCellValue(fb.getTotalOffshoreDays());
			        
			        
			          cell7 = row.createCell(7);
			        cell7.setCellValue(roleMappingMap.get(fb.getEmployeeId().toString()+"_"+fb.getEmployeeName().toString()));
			        
			          cell8 = row.createCell(8);
			        cell8.setCellValue(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString()+"_"+fb.getEmployeeName().toString())+"_"+fb.getLocation()));
			        
			        cell9 = row.createCell(9);
			        double totalAmount=0.0;
			        if(fb.getLocation().toString().equals("onsite")){
			        	totalAmount=fb.getTotalOnsiteDays() * Double.parseDouble(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString()+"_"+fb.getEmployeeName().toString())+"_"+fb.getLocation()));
			        	 cell9.setCellValue(totalAmount);
			        }else{
			        	totalAmount=fb.getTotalOffshoreDays() * Double.parseDouble(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString()+"_"+fb.getEmployeeName().toString())+"_"+fb.getLocation()));
			        	 cell9.setCellValue(totalAmount);
			        }
			        
			        cell10 = row.createCell(10);
			        cell10.setCellValue("10");
			        
			        cell11 = row.createCell(11);
			        cell11.setCellValue((totalAmount*(100-10)/100));
			        
			        cell12 = row.createCell(12);
			        cell12.setCellValue(capitalizarionMap.get(fb.getSubProjectName()));
			        
			        cell13 = row.createCell(13);
			        cell13.setCellValue( (totalAmount*(100-10)/100)*(capitalizarionMap.get(fb.getSubProjectName())/100));
			        
			        cell14 = row.createCell(14);
			        cell14.setCellValue( (totalAmount*(100-10)/100)*((100-capitalizarionMap.get(fb.getSubProjectName()))/100));
			        
			        cell15 = row.createCell(15);
			        cell15.setCellValue( (totalAmount*(100-10)/100)*(capitalizarionMap.get(fb.getSubProjectName())/100));
			        
			        cell16 = row.createCell(16);
			        cell16.setCellValue( (totalAmount*(100-10)/100)*(capitalizarionMap.get(fb.getSubProjectName())/100)*(120.00/100));
			        
			        cell17 = row.createCell(17);
			        cell17.setCellValue( (totalAmount*(100-10)/100)*(100-capitalizarionMap.get(fb.getSubProjectName()))/100*(120.00/100));
			        
			        cell18 = row.createCell(18);
			        cell18.setCellValue( (totalAmount*(100-10)/100)*(capitalizarionMap.get(fb.getSubProjectName())/100)*(120.00/100));
			        
			        cell19 = row.createCell(19);
			        cell19.setCellValue(fb.getErrMessage());
			        i++;
					 }		
				}
				workbook.write(fos);
				fos.flush();
		        fos.close();
		    } catch (FileNotFoundException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		}
		
		public void CreateOffshoreReportExcel(ArrayList<FinalBillingClass> fbc, HashMap<String, String> roleMappingMap, HashMap<String, String> rateMap, HashMap<String, Double> capitalizarionMap) throws IOException{
			
			String reportPath=Paths.get(".").toAbsolutePath().normalize().toString()+"\\Log";
			System.out.println(reportPath);
			
			
			try {
				
				File f = new File(reportPath);
				if(!f.isDirectory()){
					boolean success=f.mkdirs();
					if(!success){
						System.out.println("Problem in creating Directory");
					}
				}else{
					
				}
				
		        FileOutputStream fos = new FileOutputStream(reportPath+"\\Offshore_report.xlsx");
		        XSSFWorkbook  workbook = new XSSFWorkbook();            

		        XSSFSheet sheet = workbook.createSheet("Hour_Details");  
		        
		        //naming the columns
		        
		        Row row = sheet.createRow(0);
				
				Cell cell0 = row.createCell(0);
		        cell0.setCellValue("Employee ID");
		        
		        Cell cell1 = row.createCell(1);
		        cell1.setCellValue("Employee Name");
		        
		        Cell cell2 = row.createCell(2);
		        cell2.setCellValue("ProjectId");
				
		        Cell cell3 = row.createCell(3);
		        cell3.setCellValue("Sub_Project");
		        
		        Cell cell4 = row.createCell(4);
		        cell4.setCellValue("Invoice Reference");
		        
		        Cell cell5 = row.createCell(5);
		        cell5.setCellValue("Onsite Days");
		        
		        Cell cell6 = row.createCell(6);
		        cell6.setCellValue("Offshore Days");
		        
		        Cell cell7 = row.createCell(7);
		        cell7.setCellValue("Role");
		        
		        Cell cell8 = row.createCell(8);
		        cell8.setCellValue("Rate");
		        
		        Cell cell9 = row.createCell(9);
		        cell9.setCellValue("Total Amount");
		        
		        Cell cell10 = row.createCell(10);
		        cell10.setCellValue("Rate Reduction");
		        
		        Cell cell11 = row.createCell(11);
		        cell11.setCellValue("Total After Rate Reduction");
		        
		        Cell cell12 = row.createCell(12);
		        cell12.setCellValue("Capitalisable %");
		        
		        Cell cell13 = row.createCell(13);
		        cell13.setCellValue("Capitalisable");
		        
		        Cell cell14 = row.createCell(14);
		        cell14.setCellValue("Non-Capitalisable");
		        
		        Cell cell15 = row.createCell(15);
		        cell15.setCellValue("Total without Tax");
		        
		        Cell cell16 = row.createCell(16);
		        cell16.setCellValue("Capitalisable including tax");
		        
		        Cell cell17 = row.createCell(17);
		        cell17.setCellValue("Non-Capitalisable including tax");
		        
		        Cell cell18 = row.createCell(18);
		        cell18.setCellValue("Total Amount 2");
		        
		        Cell cell19 = row.createCell(19);
		        cell19.setCellValue("Error Message");
		        
		        
		        int i=1;
		        Iterator<FinalBillingClass>  billing= fbc.iterator();
				FinalBillingClass fb=null;
				
				while (billing.hasNext()) {
					
					 fb=new FinalBillingClass();
					 fb=billing.next();
					 
					 if(fb.getLocation().toString().equals("offshore")){
						 row = sheet.createRow(i);
					
					 cell0 = row.createCell(0);
			        cell0.setCellValue(fb.getEmployeeId().contains(".")?fb.getEmployeeId().split("\\.")[0]:fb.getEmployeeId());
			        
			         cell1 = row.createCell(1);
			        cell1.setCellValue(fb.getEmployeeName());
			        
			         cell2 = row.createCell(2);
			        cell2.setCellValue(fb.getSubProjectId());
					
			         cell3 = row.createCell(3);
			        cell3.setCellValue(fb.getSubProjectName());
			        
			         cell4 = row.createCell(4);
			        cell4.setCellValue(fb.getLocation());
			        
			         cell5 = row.createCell(5);
			        cell5.setCellValue(fb.getTotalOnsiteDays());
			        
			         cell6 = row.createCell(6);
			        cell6.setCellValue(fb.getTotalOffshoreDays());
			        
			        
			          cell7 = row.createCell(7);
			        cell7.setCellValue(roleMappingMap.get(fb.getEmployeeId().toString()+"_"+fb.getEmployeeName().toString()));
			        
			          cell8 = row.createCell(8);
			        cell8.setCellValue(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString()+"_"+fb.getEmployeeName().toString())+"_"+fb.getLocation()));
			        
			        cell9 = row.createCell(9);
			        double totalAmount=0.0;
			        if(fb.getLocation().toString().equals("onsite")){
			        	totalAmount=fb.getTotalOnsiteDays() * Double.parseDouble(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString()+"_"+fb.getEmployeeName().toString())+"_"+fb.getLocation()));
			        	 cell9.setCellValue(totalAmount);
			        }else{
			        	totalAmount=fb.getTotalOffshoreDays() * Double.parseDouble(rateMap.get(roleMappingMap.get(fb.getEmployeeId().toString()+"_"+fb.getEmployeeName().toString())+"_"+fb.getLocation()));
			        	 cell9.setCellValue(totalAmount);
			        }
			        
			        cell10 = row.createCell(10);
			        cell10.setCellValue("10");
			        
			        cell11 = row.createCell(11);
			        cell11.setCellValue((totalAmount*(100-10)/100));
			        
			        cell12 = row.createCell(12);
			        cell12.setCellValue(capitalizarionMap.get(fb.getSubProjectName()));
			        
			        cell13 = row.createCell(13);
			        cell13.setCellValue( (totalAmount*(100-10)/100)*(capitalizarionMap.get(fb.getSubProjectName())/100));
			        
			        cell14 = row.createCell(14);
			        cell14.setCellValue( (totalAmount*(100-10)/100)*((100-capitalizarionMap.get(fb.getSubProjectName()))/100));
			        
			        cell15 = row.createCell(15);
			        cell15.setCellValue( (totalAmount*(100-10)/100)*(capitalizarionMap.get(fb.getSubProjectName())/100));
			        
			        cell16 = row.createCell(16);
			        cell16.setCellValue( (totalAmount*(100-10)/100)*(capitalizarionMap.get(fb.getSubProjectName())/100)*(120.00/100));
			        
			        cell17 = row.createCell(17);
			        cell17.setCellValue( (totalAmount*(100-10)/100)*(100-capitalizarionMap.get(fb.getSubProjectName()))/100*(120.00/100));
			        
			        cell18 = row.createCell(18);
			        cell18.setCellValue( (totalAmount*(100-10)/100)*(capitalizarionMap.get(fb.getSubProjectName())/100)*(120.00/100));
			        
			        cell19 = row.createCell(19);
			        cell19.setCellValue(fb.getErrMessage());
			        i++;
					 }  			
				}

		       
				workbook.write(fos);
				fos.flush();
		        fos.close();
		    } catch (FileNotFoundException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
			
			
		}
	
	
	*/
	

}
