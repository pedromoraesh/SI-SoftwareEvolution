package br.ufms.facom;

import java.io.IOException;
import java.util.Map;

import br.ufms.facom.model.FamixEntity;

public class Main {

	

	public static void main(String[] args) throws IOException {
		
		String pathToMSE = "C:/Users/Pedro Henrique/Documents/IC/Clones e Scripts/Android-Universal-Image-Loader/"
				+ "Android-Universal-Image-Loader.mse";
		Diff diff = new Diff();
		diff.setPath("C:/Users/Pedro Henrique/Documents/IC/Clones e Scripts/Android-Universal-Image-Loader");
		Map<Integer, FamixEntity> map = FormatData.mseToMap(pathToMSE);
		Model manager = new Model();
		manager.createObjects(map);

	}
	
	

}
