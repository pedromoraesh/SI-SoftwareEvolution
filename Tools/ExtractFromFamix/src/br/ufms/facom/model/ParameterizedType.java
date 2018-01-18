package br.ufms.facom.model;

import java.util.ArrayList;

public class ParameterizedType extends Container {

	private int parameterizableClassID;
	private int containerID;
	private ParameterizableClass pClass;
	private ArrayList<Integer> argumentsID;
	private ArrayList<Entity> arguments;
	
	public int getParameterizableClassID() {
		return parameterizableClassID;
	}
	public void setParameterizableClassID(int parameterizedClassID) {
		this.parameterizableClassID = parameterizedClassID;
	}
	public int getContainerID() {
		return containerID;
	}
	public void setContainerID(int containerID) {
		this.containerID = containerID;
	}
	public ArrayList<Integer> getArgumentsIDs() {
		return argumentsID;
	}
	public void setArgumentsIDs(ArrayList<Integer> arguments) {
		this.argumentsID = arguments;
	}
	public ArrayList<Entity> getArguments() {
		return arguments;
	}
	public void setArguments(ArrayList<Entity> arguments) {
		this.arguments = arguments;
	}
	public ArrayList<Entity> argumentsElegibleForCBO(){
		ArrayList<Entity> aux = new ArrayList<Entity>();
		for(Entity entity: this.arguments){
			if(entity.getClass().getSimpleName().equals(PrimitiveType.class.getSimpleName()) == true){
				//do nothing
			}
			else if(entity.getClass().getSimpleName().equals(ParameterizedType.class.getSimpleName()) == true){
				ParameterizedType temp = (ParameterizedType)entity;
				aux.add(this.pClass);
				aux.add(temp.getpClass());
				aux.addAll(temp.argumentsElegibleForCBO());
			}
			else{
				aux.add(entity);
				aux.add(this.pClass);
			}
			
		}
		
		return aux;
	}
	public ParameterizableClass getpClass() {
		return pClass;
	}
	public void setpClass(ParameterizableClass pClass) {
		this.pClass = pClass;
	}
		
	
}
