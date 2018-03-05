package br.ufms.facom.model;

public class Entity {
	private String name;
	private String fullName;
	private int id;
	private Entity container;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Entity getContainer(){
		return container;
	}
	public void setContainer(Entity container){
		this.container = container;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(obj instanceof Entity){
			Entity entity = (Entity)obj;
			if(entity.getFullName() != null){
				return entity.getFullName().equals(getFullName());
			}
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		if(this.fullName == null){
			return 0;
		}
		return this.fullName.hashCode();
	}
	
}
