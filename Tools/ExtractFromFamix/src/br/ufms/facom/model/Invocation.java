package br.ufms.facom.model;

public class Invocation extends Entity {
	
	private String signature;
	private int senderID;
	private int candidatesID;
	private Entity candidate;

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
	public int getCandidateID() {
		return candidatesID;
	}
	public void setCandidateID(int candidatesID) {
		this.candidatesID = candidatesID;
	}
	public Entity getCandidate() {
		return candidate;
	}
	public void setCandidate(Entity candidate) {
		this.candidate = candidate;
	}
}
