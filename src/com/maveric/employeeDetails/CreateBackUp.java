package com.maveric.employeeDetails;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

public class CreateBackUp {

	public boolean copyFiles(){
		
		String FilePath=Paths.get(".").toAbsolutePath().normalize().toString();
		System.out.println(FilePath);
		boolean backupflag=false;
		
		File source = new File(FilePath+"\\inputFiles");
		File dest = new File(FilePath+"\\BackUpFiles");
		
		if(!dest.isDirectory()){
			System.out.println("Folder is not present.");
			boolean success=dest.mkdirs();
			if(!success){
				System.out.println("Problem in creating Directory");
			}else{
				
			backupflag=true;
			}
			}
		
		try {
		    FileUtils.copyDirectory(source, dest);
		    System.out.println("--------------------");
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return backupflag;
	}
}
