package br.ufms.facom.model;

public class Namespace extends Entity {
	private int parentScopeID;
	private Entity parent;

	public int getParentScopeID() {
		return parentScopeID;
	}

	public void setParentScopeID(int parentScopeID) {
		this.parentScopeID = parentScopeID;
	}	
	public Entity getContainer(){
		return parent;
	}
	
	public void setContainer(Entity container){
		this.parent = container;
	}
	
}
