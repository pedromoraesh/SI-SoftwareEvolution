package br.ufms.facom;

import java.util.List;

public class Class {
	private int id;
	private String name;
	private List<Method> listMethods;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Method> getListMethods() {
		return listMethods;
	}
	public void setListMethods(List<Method> listMethods) {
		this.listMethods = listMethods;
	}
	public List<Field> getListFields() {
		return listFields;
	}
	public void setListFields(List<Field> listFields) {
		this.listFields = listFields;
	}
	private List<Field> listFields;
}
