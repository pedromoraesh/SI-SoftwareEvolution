package br.ufms.facom;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import br.ufms.facom.model.FamixEntity;

public class Main {

	
	public static void main(String[] args) throws IOException {
		
		String pathName = args[0];
		Model modelOld = new Model();
		Model modelNew = new Model();
		
		String repository = StringUtils.substringAfterLast(pathName, "/");
		System.out.println(repository);
			
		Map<Integer, FamixEntity> mapOld;
		Map<Integer, FamixEntity> mapNew;
		
		Diff diff = new Diff();
		
		String hashOld = "";
		String hashNew = "";
		
		Scanner in = new Scanner(new FileReader(pathName + "/log.txt"));
		
		hashOld = in.nextLine();
		
		while(in.hasNextLine()){
			
			
			hashNew = in.nextLine();		
				
				//TO Solve problems when JDT2FAMIX don't generate some .mse
				if(canOpen(pathName + "/msefiles/" + repository + "_" + hashNew + ".mse")
					&& canOpen(pathName + "/msefiles/" + repository + "_" + hashOld + ".mse")){
					System.out.println("Getting diff from " + hashOld + " to " + hashNew);
					mapOld = FormatData.mseToMap(pathName + "/msefiles/" + repository + "_" + hashOld + ".mse");
					mapNew = FormatData.mseToMap(pathName + "/msefiles/" + repository + "_" + hashNew + ".mse");
									
					modelOld.setHash(hashOld);
					modelNew.setHash(hashNew);
					
					modelOld.createObjects(mapOld);
					modelNew.createObjects(mapNew);
					
					diff.setPath(pathName + "/diffResults/");
					diff.diff(modelOld, modelNew);
				}
								
			hashOld = hashNew;
			
		}
		
		in.close();
		
		System.out.println("----------Diff done------------");
		
	}
	
	public static boolean canOpen(String path){
		try {
			FileReader f = new FileReader(path);
			f.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	


}
