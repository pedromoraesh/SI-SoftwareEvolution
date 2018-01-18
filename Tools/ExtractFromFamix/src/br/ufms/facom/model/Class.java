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
			if(entity.getClass().getSimpleName().equals(Class.class.getSimpleName())){
				Class temp = (Class)entity;
				hashSet.addAll(temp.calculateCBO());
				hashSet.add(temp);
			}
			else if(entity.getClass().getSimpleName().equals(ParameterizableClass.class.getSimpleName())){
				ParameterizableClass temp = (ParameterizableClass)entity;
				hashSet.addAll(temp.calculateCBO());
				hashSet.add(temp);
				
			}
		}
		
//		for(Method method: listMethods){
//			hashSet.addAll(method.calcCBO());	
//		}
		
		if(this.superClass != null){
			hashSet.add(this.superClass);
		}
		
		for(Attribute attribute: listAttributes){
			if(attribute.getType().getClass().getSimpleName().equals(PrimitiveType.class.getSimpleName()) == true){
				//DO nothing
			}
			else if(attribute.getType().getClass().getSimpleName().equals(ParameterizedType.class.getSimpleName()) == true){
				ParameterizedType aux = (ParameterizedType)attribute.getType();
				hashSet.addAll(aux.argumentsElegibleForCBO());
			}
			else{
				hashSet.add(attribute.getType());
			}
			
		}

//		for(Entity entidade: hashSet){
//			System.out.println(entidade.getName());
//		}

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
