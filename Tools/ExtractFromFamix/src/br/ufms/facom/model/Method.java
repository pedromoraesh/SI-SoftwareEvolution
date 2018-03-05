package br.ufms.facom.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_COLOR_BURNPeer;

public class Method extends Container {
	
	
	private ArrayList<Invocation> invocations = new ArrayList<Invocation>();
	private String signature;
	private int parentID;
	private ArrayList<LocalVariable> localVariables = new ArrayList<LocalVariable>();
	private ArrayList<Parameter> parameters = new ArrayList<Parameter>();
	private int declaredTypeID;
	private Entity type;
	
	
	public Set<Entity> calcCBOWithLocalVariables(){
		
		Set<Entity> hashSet = new HashSet<Entity>();
		

		for(LocalVariable localVariable: this.localVariables){
			
			if(localVariable.getType() instanceof ParameterizedType){
				ParameterizedType aux = (ParameterizedType)localVariable.getType();
				hashSet.addAll(aux.argumentsElegibleForCBO());
			}
			else if((localVariable.getType() instanceof PrimitiveType) == false){
				hashSet.add(localVariable.getType());
			}
		}
		 
		
		return hashSet;
		
	}
	
	public Set<Entity> calcCBOreturnAndParameters(){
		
		Set<Entity> hashSet = new HashSet<Entity>();
		
		if((type instanceof PrimitiveType) == false
				&& (type instanceof Constructor) == false){
			hashSet.add(type);
		}
		
		for(Parameter parameter: this.parameters){
			if(parameter.getType() instanceof ParameterizedType){
				ParameterizedType aux = (ParameterizedType)parameter.getType();
				hashSet.addAll(aux.argumentsElegibleForCBO());
			}
			else if((parameter.getType() instanceof PrimitiveType) == false) {
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
