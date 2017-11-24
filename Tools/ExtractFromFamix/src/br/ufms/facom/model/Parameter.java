package br.ufms.facom.model;

public class Parameter extends Container {
	private int id;
	private String name;
	private Container type;
	private int declaredTypeID;
	private int parentID;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Container getType() {
		return type;
	}
	public void setType(Container type) {
		this.type = type;
	}
	public int getDeclaredTypeID() {
		return declaredTypeID;
	}
	public void setDeclaredTypeID(int declaredTypeID) {
		this.declaredTypeID = declaredTypeID;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
