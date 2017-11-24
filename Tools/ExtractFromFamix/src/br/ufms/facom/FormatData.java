package br.ufms.facom;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import br.ufms.facom.model.Entity;

public class FormatData {

	public static Map<Integer, Entity> mseToMap(String path) throws IOException {
		
		Map<Integer, Entity> map = new HashMap<Integer, Entity>();
		String split[];
		ArrayList<String[]> list = new ArrayList<String[]>();
		

		Scanner in = new Scanner(new FileReader(path));
		//		Scanner in = new Scanner(new FileReader("C:/Users/Pedro Henrique/Documents/IC/Clones e Scripts/copycat/"
		//		   		+ "copycat.mse"));

		String line = in.nextLine();		

		while(in.hasNextLine()){

			Entity entity = new Entity();
			int id = 0;
			list = new ArrayList<String[]>();;
			if(line.contains("FAMIX.") && !line.contains("FAMIX.Comment") ){

				split = formatString(line);
				split[0] = StringUtils.strip(split[0]);
				entity.setType(split[0]);
				entity.setId(id);

				id = Integer.parseInt(split[2]);
				line = in.nextLine();
				while(!line.contains("FAMIX.")){
					if(line.contains("ref")){
						//split = formatString(line);
						line = StringUtils.remove(line, ")");
						line = StringUtils.remove(line, "(");
						line = StringUtils.remove(line, ":");
						line = StringUtils.strip(line);
						split = line.split(" ");
						list.add(split);
					}

					else if(line.contains("modifiers")){
						line = StringUtils.remove(line, "'");
						line = StringUtils.remove(line, ")");
						line = StringUtils.remove(line, "(");
						line = StringUtils.strip(line);
						split = line.split(" ");
						list.add(split);
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
							list.add(toJoin);

						}
					}
					else if(line.contains("'")){
						line = StringUtils.remove(line, ")");
						line = StringUtils.remove(line, "(");
						line = StringUtils.remove(line, "'");
						line = StringUtils.strip(line);
						split = line.split(" ");
						list.add(split);				
					}
					else{
						line = StringUtils.remove(line, ")");
						line = StringUtils.remove(line, "(");
						line = StringUtils.strip(line);
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
		
		return map;
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
