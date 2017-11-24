package br.ufms.facom.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Class extends Container {

	private ArrayList<Method> listMethods = new ArrayList<Method>();
	private ArrayList<Attribute> listAttributes = new ArrayList<Attribute>();
	private Class superClass;
	private int container;
	private boolean isInternalClass = false;
	
	public int calculateCBO(){
		
		
		Set<Container> hashSet = new HashSet<Container>();
		
		for(Method method: listMethods){
			for(LocalVariable localVariable: method.getLocalVariables()){
				if(localVariable.getType().getClass().getSimpleName().equals(PrimitiveType.class.getSimpleName()) == false){
					hashSet.add(localVariable.getType());
				}
					
			}
			
			for(Parameter parameter: method.getParameters()){
				if(parameter.getType().getClass().getSimpleName().equals(PrimitiveType.class.getSimpleName()) == false){
					hashSet.add(parameter.getType());
				}
				
			}
			
		}
		
		hashSet.add(this.superClass);
		
		for(Attribute attributes: listAttributes){
			if(attributes.getType().getClass().getSimpleName().equals(PrimitiveType.class.getSimpleName()) == false){
				hashSet.add(attributes.getType());
			}
			
		}
		for(Container container: hashSet){
			System.out.println(container.getName());
		}
		return hashSet.size();
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
	public Class getSuperClass() {
		return superClass;
	}
	public void setSuperClass(Class superClass) {
		this.superClass = superClass;
	}
	public int getContainer() {
		return container;
	}
	public void setContainer(int container) {
		this.container = container;
	}
	public boolean isInternalClass() {
		return isInternalClass;
	}
	public void setInternalClass(boolean isInternalClass) {
		this.isInternalClass = isInternalClass;
	}
	
}
