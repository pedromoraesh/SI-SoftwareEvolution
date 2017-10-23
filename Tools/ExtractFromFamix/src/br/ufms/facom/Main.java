package br.ufms.facom;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class Main {

	

	public static void main(String[] args) throws IOException {
		

		Map<Integer, Entity> map = new HashMap<Integer, Entity>();
		String split[];
		ArrayList<String[]> list = new ArrayList<String[]>();
		
//		Scanner in = new Scanner(new FileReader("C:/Users/Pedro Henrique/Documents/IC/Clones e Scripts/Android-Universal-Image-Loader/"
//		   		+ "Android-Universal-Image-Loader.mse"));
		Scanner in = new Scanner(new FileReader("C:/Users/Pedro Henrique/Documents/IC/Clones e Scripts/copycat/"
		   		+ "copycat.mse"));
		
		String line = in.nextLine();		
		
		while(in.hasNextLine()){
			
			Entity entity = new Entity();
			int id = 0;
			//(FAMIX.FileAnchor (id: 1) -> FAMIX.FileAnchor id 1
			list = new ArrayList<String[]>();;
			if(line.contains("FAMIX.")){
				
				split = formatString(line);
				split[0] = StringUtils.strip(split[0]);
				entity.setType(split[0]);
				entity.setId(id);
				
				id = Integer.parseInt(split[2]);
				//System.out.println(split[0] + id);
				line = in.nextLine();
				while(!line.contains("FAMIX.")){
					//(element (ref: 3986)) -> element ref 3986
					if(line.contains("ref")){
						//split = formatString(line);
						line = StringUtils.remove(line, ")");
						line = StringUtils.remove(line, "(");
						line = StringUtils.remove(line, ":");
						line = StringUtils.strip(line);
						split = line.split(" ");
						//System.out.println(line);						
//						split[0] = StringUtils.remove(split[0], "		");	
						list.add(split);
					}
					
					else if(line.contains("modifiers")){
						line = StringUtils.remove(line, "'");
						line = StringUtils.remove(line, ")");
						line = StringUtils.remove(line, "(");
						line = StringUtils.strip(line);
						//System.out.println(line);
						split = line.split(" ");
						list.add(split);
					}
					// Comentários, com mais de uma linha ou só uma linha.
					else if(line.contains("content")){
						if(StringUtils.countMatches(line, "'") == 2){	
							String aux = StringUtils.substringBetween(line, "'", "'");
							line = StringUtils.remove(line, aux);
							line = StringUtils.remove(line, ")");
							line = StringUtils.remove(line, "(");
							line = StringUtils.remove(line, "'");
							line = StringUtils.strip(line);
							line += "(:)";
							line += aux;
							//System.out.println(line);
							split = line.split("(:)");
							list.add(split);
						}
						else{
							line = StringUtils.remove(line, "(content");
							line = StringUtils.remove(line, "'");
							line = StringUtils.strip(line);
							String aux = line + "\n";
							line = in.nextLine();
							while(!line.contains("*/")){
								aux = aux + line + "\n";
								line = in.nextLine();
							}
							aux = aux + line + "\n";
							String toJoin[] = new String[2];
							toJoin[0] = "content";
							toJoin[1] = aux;
							
							//REMOVER -> Mostrar para professor caso com comentários em várias linhas
							//System.out.println(toJoin[0]);
							//System.out.println(toJoin[1]);
							list.add(toJoin);
								
						}
					}
					//(signature '((GridView)listView).setAdapter(new ImageAdapter(getActivity()))'))
					// -> signature ((GridView)listView).setAdapter(new ImageAdapter(getActivity()))
					else if(line.contains("'") && line.contains("signature")){
						if(StringUtils.countMatches(line, "'") == 2){	
							String aux = StringUtils.substringBetween(line, "'", "')");
							line = StringUtils.remove(line, aux);
							line = StringUtils.remove(line, ")");
							line = StringUtils.remove(line, "(");
							line = StringUtils.remove(line, "'");
							line = StringUtils.strip(line);
							line += ";";
							line += aux;
							split = line.split(";");
							//System.out.println(split[0] + split[1]);
							list.add(split);
						}
						else if(StringUtils.countMatches(line, "'") > 2){
							String aux = StringUtils.substringBetween(line, "'", "'))");
							line = StringUtils.remove(line, aux);
							line = StringUtils.remove(line, ")");
							line = StringUtils.remove(line, "(");
							line = StringUtils.remove(line, "'");
							line = StringUtils.strip(line);
							line += ";";
							line += aux;
							split = line.split(";");
							//System.out.println(split[0] + split[1]);
							list.add(split);
						}
						else{
							line = StringUtils.remove(line, "(signature");
							line = StringUtils.remove(line, "'");
							String aux = line + "\n";
							line = in.nextLine();
							while(StringUtils.countMatches(line, "'") == 0 || StringUtils.countMatches(line, "'") >= 2 ){
								aux = aux + line + "\n";
								line = in.nextLine();
							}
							
							String toJoin[] = new String[2];
							toJoin[0] = "signature";
							toJoin[1] = aux;
							//System.out.println(toJoin[0]);
							//System.out.println(toJoin[1]);
							list.add(toJoin);
								
						}
					}
					else if(line.contains("'")){
						line = StringUtils.remove(line, ")");
						line = StringUtils.remove(line, "(");
						line = StringUtils.remove(line, "'");
						line = StringUtils.strip(line);
						//System.out.println(line);
						split = line.split(" ");
						list.add(split);				
					}
					else{
						line = StringUtils.remove(line, ")");
						line = StringUtils.remove(line, "(");			
						split = line.split(" ");
						list.add(split);
					}
					if(in.hasNext()){
						line = in.nextLine();
					}
					else{
						break;
					}
				}
				entity.setList(list);
				map.put(id, entity);
			}
			else{
				line = in.nextLine();
			}
				
				
				

		}
					
		in.close();
		for (Map.Entry<Integer, Entity> entry : map.entrySet())
		{
		    System.out.println(entry.getKey() + "/" + entry.getValue().getType());
		    ArrayList<String[]> temp = entry.getValue().getList();
		    for (String[] dado : temp) {
				for (int i = 0; i < dado.length; i++) {
					System.out.printf(dado[i] + " ");
				}
				System.out.printf("\n");
			}
		    System.out.print("\n");
		    
		}
		
	}
	
	public static String[] formatString(String line){
		
		String formatedString[];
		
		line = StringUtils.remove(line, ")");
		line = StringUtils.remove(line, "(");
		line = StringUtils.remove(line, ":");
		formatedString = line.split(" ");

		return formatedString;
	}


}
