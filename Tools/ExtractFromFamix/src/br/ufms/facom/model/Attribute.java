package br.ufms.facom.model;

public class Attribute extends Entity {
	private int declaredTypeID;
	private Entity type;
	private int parentID;
	private String modifier;
	
	public String containerName(){
		return type.getName();
	}
	public int getDeclaredTypeID() {
		return declaredTypeID;
	}
	public void setDeclaredTypeID(int type) {
		this.declaredTypeID = type;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentClass) {
		this.parentID = parentClass;
	}
	public Entity getType() {
		return type;
	}
	public void setType(Entity type) {
		this.type = type;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
}
