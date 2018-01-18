package br.ufms.facom.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Method extends Container {
	
	
	private ArrayList<Invocation> invocations = new ArrayList<Invocation>();
	private String signature;
	private int parentID;
	private ArrayList<LocalVariable> localVariables = new ArrayList<LocalVariable>();
	private ArrayList<Parameter> parameters = new ArrayList<Parameter>();
	private int declaredTypeID;
	private Entity type;
	
	
	public Set<Entity> calcCBO(){
		
		Set<Entity> hashSet = new HashSet<Entity>();
		
		if(!type.getClass().getSimpleName().equals(PrimitiveType.class.getSimpleName())
				&& !type.getClass().getSimpleName().equals(Constructor.class.getSimpleName())){
			hashSet.add(type);
		}
		
		for(LocalVariable localVariable: this.localVariables){
			if(localVariable.getType().getClass().getSimpleName().equals(PrimitiveType.class.getSimpleName()) == true){
				// DO Nothing
			}
			else if(localVariable.getType().getClass().getSimpleName().equals(ParameterizedType.class.getSimpleName()) == true){
				ParameterizedType aux = (ParameterizedType)localVariable.getType();
				hashSet.addAll(aux.argumentsElegibleForCBO());
			}
			else{
				hashSet.add(localVariable.getType());
			}
		}
		 
		for(Parameter parameter: this.parameters){
			if(parameter.getType().getClass().getSimpleName().equals(PrimitiveType.class.getSimpleName()) == true){
				//do nothing
			}
			else if(parameter.getType().getClass().getSimpleName().equals(ParameterizedType.class.getSimpleName()) == true){
				ParameterizedType aux = (ParameterizedType)parameter.getType();
				hashSet.addAll(aux.argumentsElegibleForCBO());
			}
			else {
				hashSet.add(parameter.getType());
			}
		}
		
		return hashSet;
		
	}
	public ArrayList<Invocation> getListInvocation() {
		return invocations;
	}
	public void setListInvocation(ArrayList<Invocation> listInvocation) {
		this.invocations = listInvocation;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public ArrayList<LocalVariable> getLocalVariables() {
		return localVariables;
	}
	public void setLocalVariables(ArrayList<LocalVariable> localVariables) {
		this.localVariables = localVariables;
	}
	public ArrayList<Parameter> getParameters() {
		return parameters;
	}
	public void setParameters(ArrayList<Parameter> parameters) {
		this.parameters = parameters;
	}
	public Entity getType() {
		return type;
	}
	public void setType(Entity type) {
		this.type = type;
	}
	public int getDeclaredTypeID() {
		return declaredTypeID;
	}
	public void setDeclaredTypeID(int declaredType) {
		this.declaredTypeID = declaredType;
	}
}
