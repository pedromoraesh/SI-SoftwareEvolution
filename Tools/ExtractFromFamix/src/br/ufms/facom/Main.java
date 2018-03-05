package br.ufms.facom;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.LogManager;

import org.apache.logging.log4j.core.Logger;

import br.ufms.facom.model.FamixEntity;

public class Main {


	public static void main(String[] args) throws IOException {
		
		String pathName = args[0];
		
		Model modelOld = new Model();
		Model modelNew = new Model();
		
		Map<Integer, FamixEntity> mapOld;
		Map<Integer, FamixEntity> mapNew;
		
		Diff diff = new Diff();
		File directory = new File(pathName + "/msefiles");
		String[] files = directory.list();
		
		
		for (int i = 0; i < files.length; i++ ){
			
			String oldMSEFile = files[i];
			if(i+1 < files.length){
				String newMSEFile = files[i+1];
				mapOld = FormatData.mseToMap(pathName + "/msefiles/" + oldMSEFile);
				mapNew = FormatData.mseToMap(pathName + "/msefiles/" + newMSEFile);
				
				modelOld.createObjects(mapOld);
				modelNew.createObjects(mapNew);
				diff.setPath(pathName + "/diffResults/");
				diff.diff(modelOld, modelNew);
			}
			
			
			
			
			
		}
		
	}


}
