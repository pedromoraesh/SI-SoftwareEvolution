package br.ufms.facom.model;

public class Attribute extends Container {
	private int id;
	private String name;
	private int declaredTypeID;
	//Type -> cont
	private Container type;
	private int parent;
	
	public String containerName(){
		return type.getName();
	}
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
	public int getDeclaredTypeID() {
		return declaredTypeID;
	}
	public void setDeclaredTypeID(int type) {
		this.declaredTypeID = type;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parentClass) {
		this.parent = parentClass;
	}
	public Container getType() {
		return type;
	}
	public void setType(Container type) {
		this.type = type;
	}
}
