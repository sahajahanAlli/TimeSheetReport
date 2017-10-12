package com.maveric.employeeDetails;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class LoadProperties {
	
	public String readProperties(){
		Properties prop = new Properties();
		InputStream input = null;
		String FilePath=Paths.get(".").toAbsolutePath().normalize().toString();

		try {

			input = new FileInputStream(FilePath+"\\config.properties");

			// load a properties file
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop.getProperty("Month");


		

	}

}
