package br.ufms.facom.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Class extends Container {

	private ArrayList<Method> listMethods = new ArrayList<Method>();
	private ArrayList<Attribute> listAttributes = new ArrayList<Attribute>();
	private Entity superClass;
	private int containerID;
	private ArrayList<Entity> listInternalClasses = new ArrayList<Entity>();
	
	public Set<Entity> calculateCBO(){
		
		Set<Entity> hashSet = new HashSet<Entity>();
		
		for(Entity entity: listInternalClasses){
			if(entity instanceof Class){
				Class temp = (Class)entity;
				hashSet.addAll(temp.calculateCBO());
				hashSet.add(temp);
			}
			else if(entity instanceof ParameterizableClass){
				ParameterizableClass temp = (ParameterizableClass)entity;
				hashSet.addAll(temp.calculateCBO());
				hashSet.add(temp);
				
			}
		}
		
		for(Method method: listMethods){
			hashSet.addAll(method.calcCBOreturnAndParameters());	
		}
		
		if(this.superClass != null){
			hashSet.add(this.superClass);
		}
		
		for(Attribute attribute: listAttributes){

			if(attribute.getType() instanceof ParameterizedType 
					&& (attribute.getType() instanceof PrimitiveType) == false){
				ParameterizedType aux = (ParameterizedType)attribute.getType();
				hashSet.addAll(aux.argumentsElegibleForCBO());
			}
			else if((attribute.getType() instanceof PrimitiveType) == false){
				hashSet.add(attribute.getType());
			}
			
		}

		return hashSet;
	}	
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
	
	public ArrayList<Method> getMethods() {
		return listMethods;
	}
	public void setMethods(ArrayList<Method> listMethods) {
		this.listMethods = listMethods;
	}
	
	public ArrayList<Attribute> getAttributes() {
		return listAttributes;
	}
	public void setAttributes(ArrayList<Attribute> listFields) {
		this.listAttributes = listFields;
	}
	public Entity getSuperClass() {
		return superClass;
	}
	public void setSuperClass(Entity superClass) {
		this.superClass = superClass;
	}
	public int getContainerID() {
		return containerID;
	}
	public void setContainerID(int cointainerID) {
		this.containerID = cointainerID;
	}
	
	public Method findMethod(String fullName){
		for(Method method: this.listMethods){
			if(method.getFullName().equals(fullName)){
				return method;
			}
		}
		return null;
	}
	
	public Attribute findAttribute(String name){
		for(Attribute attribute: this.listAttributes){
			if(attribute.getName().equals(name)){
				return attribute;
			}
		}
		return null;
	}
	
	public ArrayList<Entity> getInternalClasses() {
		return listInternalClasses;
	}
	public void setInternalClasses(ArrayList<Entity> listInternalClasses) {
		this.listInternalClasses = listInternalClasses;
	}

	
}
