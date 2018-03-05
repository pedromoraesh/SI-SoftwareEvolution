package br.ufms.facom.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ParameterizableClass extends Container {
	
	private ArrayList<ParameterizedType> parameterizedAttributes = new ArrayList<ParameterizedType>();
	private int containerID;
	private ArrayList<Method> listMethods = new ArrayList<Method>();
	private ArrayList<Entity> listInternalClasses = new ArrayList<Entity>();
	private ArrayList<Attribute> listAttributes = new ArrayList<Attribute>();
	private Entity superClass;
	
	public Set<Entity> calculateCBO(){
		
		Set<Entity> hashSet = new HashSet<Entity>();
		
		for(Attribute attribute: listAttributes){
			if(attribute.getType().getClass().getSimpleName().equals(ParameterizedType.class.getSimpleName()) == true){
				ParameterizedType aux = (ParameterizedType)attribute.getType();
				hashSet.addAll(aux.argumentsElegibleForCBO());
			}
			else if(attribute.getType().getClass().getSimpleName().equals(PrimitiveType.class.getSimpleName()) == false
					&& attribute.getType().getClass().getSimpleName().equals(ParameterizedType.class.getSimpleName()) == false) {
				hashSet.add(attribute.getType());
			}
			
		}
		
		for(Entity entity: listInternalClasses){
			if(entity.getClass().getSimpleName().equals(Class.class.getSimpleName())){
				Class temp = (Class)entity;
				hashSet.addAll(temp.calculateCBO());
				hashSet.add(temp);
			}
			else if(entity.getClass().getSimpleName().equals(ParameterizableClass.class.getSimpleName())){
				ParameterizableClass temp = (ParameterizableClass)entity;
				hashSet.addAll(temp.calculateCBO());
				
			}
		}
		
		if(this.superClass != null){
			hashSet.add(superClass);
		}
		
		for(Method method: listMethods){
			hashSet.addAll(method.calcCBOreturnAndParameters());
		}
		
		return hashSet;
	}

	public ArrayList<ParameterizedType> getParameterizedAttributes() {
		return parameterizedAttributes;
	}

	public void setParameterizedAttributes(ArrayList<ParameterizedType> parameterizedAttributes) {
		this.parameterizedAttributes = parameterizedAttributes;
	}

	public int getContainerID() {
		return containerID;
	}

	public void setContainerID(int containerID) {
		this.containerID = containerID;
	}

	public ArrayList<Method> getMethods() {
		return listMethods;
	}
	public void setMethods(ArrayList<Method> listMethods) {
		this.listMethods = listMethods;
	}

	public ArrayList<Attribute> getListAttributes() {
		return listAttributes;
	}

	public void setListAttributes(ArrayList<Attribute> listAttributes) {
		this.listAttributes = listAttributes;
	}

	public ArrayList<Entity> getInternalClasses() {
		return listInternalClasses;
	}

	public void setInternalClasses(ArrayList<Entity> listInternalClasses) {
		this.listInternalClasses = listInternalClasses;
	}

	public Entity getSuperClass() {
		return superClass;
	}

	public void setSuperClass(Entity superClass) {
		this.superClass = superClass;
	}

	

}
