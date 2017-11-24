package br.ufms.facom;

import java.io.IOException;
import java.util.Map;

import br.ufms.facom.model.Entity;

public class Main {

	

	public static void main(String[] args) throws IOException {
		
		String path = "C:/Users/Pedro Henrique/Documents/IC/Clones e Scripts/Android-Universal-Image-Loader/"
				+ "Android-Universal-Image-Loader.mse";
//		String path = "C:/Users/Pedro Henrique/Documents/IC/Clones e Scripts/tomcat70/"
//				+ "tomcat70.mse";
		Map<Integer, Entity> map = FormatData.mseToMap(path);
		Manager manager = new Manager();
		manager.createObjects(map);
		//Passa ID da classe (para descobrir os métodos)
//		manager.findMethods(23);
//		manager.findMethods("ImageViewAware");
		//Passa ID da classe (para descobrir os atributos)
//		manager.findAttributes(23);
//		manager.findAttributes("DefaultThreadFactory");
		//Passa ID do Sender (quem invoca)
//		manager.findInvocations(10);
//		System.out.println(map.size());
	}
	
	

}
