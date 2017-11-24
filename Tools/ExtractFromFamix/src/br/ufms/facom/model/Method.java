package br.ufms.facom.model;

import java.util.ArrayList;

public class Method extends Container {
	private int id;
	private ArrayList<Invocation> invocations = new ArrayList<Invocation>();
	private String signature;
	private int parent;
	private ArrayList<LocalVariable> localVariables = new ArrayList<LocalVariable>();
	private ArrayList<Parameter> parameters = new ArrayList<Parameter>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
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
}
