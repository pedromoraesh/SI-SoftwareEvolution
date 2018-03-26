package br.ufms.facom;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class Formater {

	public static void main(String[] args) throws FileNotFoundException {
		
		String path = args[0];
		
		Scanner in = new Scanner(new FileReader(path + "/imports/imports.txt"));
		
		ExtractData extractor = new ExtractData();
		
		String line = in.nextLine();
		String java = "";
		String entities[];
		String imports = "";

		while(in.hasNextLine()){
			
			entities = line.split(",");
			String temp[] = entities[0].split("/");
			java = temp[temp.length-1];
			System.out.println(java);
			imports = "";
			while(line.contains(java) && in.hasNextLine()){
				
				imports += entities[entities.length-1];
				if(in.hasNextLine()){
					line = in.nextLine();
				}
				entities = line.split(",");
				
				
			}
			imports = imports.replace("-", "R-");
			imports = imports.replace("+", "A-");
			imports = imports.replace(" ", "");
			imports = StringUtils.removeEnd(imports, ";");
			extractor.export(path + "/imports/", imports);
			
			
		}
		
		in.close();

	}

}
