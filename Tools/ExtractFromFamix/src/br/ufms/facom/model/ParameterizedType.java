package br.ufms.facom.model;

public class ParameterizedType extends Container {

	private int parameterizedClassID;
	
	public int getId() {
		return super.getId();
	}
	public void setId(int id) {
		super.setId(id);
	}
	public String getName() {
		return super.getName();
	}
	public void setName(String name) {
		super.setName(name);
	}
	public int getParameterizedClassID() {
		return parameterizedClassID;
	}
	public void setParameterizedClassID(int parameterizedClassID) {
		this.parameterizedClassID = parameterizedClassID;
	}
}
