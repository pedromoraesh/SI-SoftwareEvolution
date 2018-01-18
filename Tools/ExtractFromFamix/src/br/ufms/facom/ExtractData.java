package br.ufms.facom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ExtractData {
	
	public void export(String path, String content){
		File dir = new File(path);
        File arq = new File(dir, "DiffBetweenVersions.txt");
        
        try {
			if (dir.exists()) {			
			    if (arq.exists()) {
			    	arq.createNewFile();
			    }
			}
			else{
				System.out.println("Directory don't exists");
			}	
			FileWriter fileWriter = new FileWriter(arq, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			
			printWriter.println(content);
			printWriter.flush();
			
            printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}