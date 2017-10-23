package br.ufms.facom;

import java.util.List;

public class Method {
	private int id;
	private List<Invocation> invocations;
	private String signature;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Invocation> getListInvocation() {
		return invocations;
	}
	public void setListInvocation(List<Invocation> listInvocation) {
		this.invocations = listInvocation;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
}
