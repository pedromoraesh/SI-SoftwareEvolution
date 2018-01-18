package br.ufms.facom.model;

public class LocalVariable extends Entity {
	private int declaredTypeID;
	private int parentID;
	private Entity type;
	
	public Entity getType() {
		return type;
	}
	public void setType(Entity type) {
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
	
	
}
