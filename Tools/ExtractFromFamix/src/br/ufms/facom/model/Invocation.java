package br.ufms.facom.model;

public class Invocation extends Entity {
	
	private String signature;
	private int senderID;
	private int recieverID;
	private Entity reciever;

	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public int getSenderID() {
		return senderID;
	}
	public void setSenderID(int sender) {
		this.senderID = sender;
	}
	public int getRecieverID() {
		return recieverID;
	}
	public void setRecieverID(int recieverID) {
		this.recieverID = recieverID;
	}
	public Entity getReciever() {
		return reciever;
	}
	public void setReciever(Entity reciever) {
		this.reciever = reciever;
	}
}
