package com.maveric.writeErrLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;

public class WriteLog {

	public void createLogText(String errText){
		try {
            //Whatever the file path is.
			String excelFilePath=Paths.get(".").toAbsolutePath().normalize().toString();
			File f = new File(excelFilePath+"\\ErrorLog");
			if(!f.isDirectory()){
			boolean success=f.mkdirs();
			if(!success){
				System.out.println("Problem in creating Error log Directory");
			}else{
			}
			System.exit(1);
			}
			
            File statText = new File(excelFilePath+""+"\\ErrorLog\\errLog.txt");
            FileOutputStream is = new FileOutputStream(statText,true);
            OutputStreamWriter osw = new OutputStreamWriter(is);    
            Writer w = new BufferedWriter(osw);
            w.write(errText);
            w.flush();
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file error Log file");
        }
	}
}
