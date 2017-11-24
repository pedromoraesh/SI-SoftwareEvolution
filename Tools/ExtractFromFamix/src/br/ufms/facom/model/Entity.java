package br.ufms.facom.model;

import java.util.ArrayList;

public class Entity {
	
	private int id;
	private String type;
	private ArrayList<String[]> list;
	
	public Entity(){
		
	}
	public ArrayList<String[]> getList() {
		return list;
	}
	public void setList(ArrayList<String[]> list2) {
		this.list = list2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
