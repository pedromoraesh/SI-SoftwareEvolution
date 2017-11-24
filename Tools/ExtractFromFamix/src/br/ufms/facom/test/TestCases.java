package br.ufms.facom.test;
import br.ufms.facom.*;
import br.ufms.facom.model.Entity;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

public class TestCases {
	
	

	@Test
	public void testAndroid() throws IOException {
		String path = "C:/Users/Pedro Henrique/Documents/IC/Clones e Scripts/Android-Universal-Image-Loader/"
				+ "Android-Universal-Image-Loader.mse";
		Map<Integer, Entity> map = FormatData.mseToMap(path);
		Manager manager = new Manager();
		manager.createObjects(map);
		
		assertEquals(manager.findMethods(5270), 8);
		
		assertEquals(manager.findAttributes(112), 4);
		
		assertEquals(manager.findInvocations(2984), 3);
		
		//Não Classe
		assertEquals(manager.findCBO(5), -1);
		
		assertEquals(manager.findCBO(109), 5);
	}
	
//	@Test
//	public void testTomcat() throws IOException {
//		String path = "C:/Users/Pedro Henrique/Documents/IC/Clones e Scripts/tomcat70/"
//				+ "tomcat70.mse";
//		Map<Integer, Entity> map = FormatData.mseToMap(path);
//		Manager manager = new Manager();
//		manager.createObjects(map);
//		
//		assertEquals(manager.findMethods(23), 16);
//		
//		assertEquals(manager.findAttributes(23), 7);
//		
//		assertEquals(manager.findInvocations(10), 0);
//	}

}
