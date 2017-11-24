package br.ufms.facom.model;

import java.util.ArrayList;

public class ParameterizableClass extends Container {
	
	private int container;
	private ArrayList<ParameterizedType> parameterizedAttributes = new ArrayList<ParameterizedType>();
	private boolean isInternalClass = false;

	public int getContainer() {
		return container;
	}

	public void setContainer(int container) {
		this.container = container;
	}

	public ArrayList<ParameterizedType> getParameterizedAttributes() {
		return parameterizedAttributes;
	}

	public void setParameterizedAttributes(ArrayList<ParameterizedType> parameterizedAttributes) {
		this.parameterizedAttributes = parameterizedAttributes;
	}

	public boolean isInternalClass() {
		return isInternalClass;
	}

	public void setInternalClass(boolean isInternalClass) {
		this.isInternalClass = isInternalClass;
	}
	

}
