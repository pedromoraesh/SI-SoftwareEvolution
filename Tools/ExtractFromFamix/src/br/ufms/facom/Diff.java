package br.ufms.facom;

import java.util.HashSet;
import java.util.Set;

import br.ufms.facom.model.Class;
import br.ufms.facom.model.Entity;
import br.ufms.facom.model.Method;
import br.ufms.facom.model.ParameterizableClass;

public class Diff {
	
	private String path;
	private ExtractData extractor = new ExtractData();

	
	public void diffCBO(Model oldVersion, Model newVersion){
		
		Set<Entity> diff = new HashSet<Entity>();
		
		for(Class classFromNewVersion: newVersion.getClasses()){
			Class classFromOldVersion = oldVersion.findClass(classFromNewVersion.getFullName());
			if(classFromOldVersion != null){
				diff = detectAdd(classFromOldVersion, classFromNewVersion);
				String contentToExport = "";
				if(diff.size() != 0){
					
					contentToExport = extractDiff(diff, classFromNewVersion, "A");;
				}
				
				diff = detectRemove(classFromNewVersion, classFromOldVersion);
				if(diff.size() != 0){
					
					contentToExport = contentToExport + extractDiff(diff, classFromNewVersion, "R");	
				}
				if(!contentToExport.equals("")){
					extractor.export(path, contentToExport);
				}
			}
		}
		
		for(ParameterizableClass classFromNewVersion: newVersion.getPClasses()){
			ParameterizableClass classFromOldVersion = oldVersion.findParameterizableClass(classFromNewVersion.getFullName());
			if(classFromOldVersion != null){
				diff = detectAdd(classFromOldVersion, classFromNewVersion);
				String contentToExportParameterizable = "";
				if(diff.size() != 0){
					contentToExportParameterizable = extractDiff(diff, classFromNewVersion, "A");				
				}
				
				diff = detectRemove(classFromNewVersion, classFromOldVersion);
				if(diff.size() != 0){
					contentToExportParameterizable += extractDiff(diff, classFromNewVersion, "R");		
				}			
				
				extractor.export(path, contentToExportParameterizable);	
			}
		}
		
		
		
	}
	
	public void diffMethods(Model oldVersion, Model newVersion){
		
		Set<Entity> diff = new HashSet<Entity>();
		
		for(Class classFromNewVersion: newVersion.getClasses()){
			Class classFromOldVersion = oldVersion.findClass(classFromNewVersion.getFullName());
			
			
			
			if(classFromOldVersion != null){
				for(Method methodFromNewVersion: classFromNewVersion.getMethods()){
					Method methodFromOldVersion = classFromOldVersion.findMethod(methodFromNewVersion.getFullName());
					
					if(methodFromOldVersion != null){
						
						diff = detectAdd(methodFromOldVersion, methodFromNewVersion);
						String contentToExport = "";
						if(diff.size() != 0){
							contentToExport = extractDiff(diff, methodFromNewVersion, "A");
						}
						
						diff = detectRemove(methodFromNewVersion, methodFromOldVersion);
						if(diff.size() != 0){
							
							contentToExport = contentToExport + extractDiff(diff, methodFromNewVersion, "R");	
						}
						if(!contentToExport.equals("")){
							extractor.export(path, contentToExport);
						}
					}
				}
			}
			
		}
	}
	
	private Set<Entity> detectRemove(ParameterizableClass neww, ParameterizableClass old) {
		
		Set<Entity> cboOldClass = new HashSet<Entity>(old.calculateCBO());
		Set<Entity> cboNewClass = new HashSet<Entity>(neww.calculateCBO());
		
		Set<Entity> diff = new HashSet<Entity>(cboOldClass);
			
		diff.removeAll(cboNewClass);
				
		return diff;
	}
	
	private Set<Entity> detectRemove(Class neww, Class old) {
		
		Set<Entity> cboOldClass = new HashSet<Entity>(old.calculateCBO());
		Set<Entity> cboNewClass = new HashSet<Entity>(neww.calculateCBO());
		
		Set<Entity> diff = new HashSet<Entity>(cboOldClass);
			
		diff.removeAll(cboNewClass);
				
		return diff;
	}
	
	private Set<Entity> detectRemove(Method neww, Method old) {
		
		Set<Entity> cboOldClass = new HashSet<Entity>(old.calcCBO());
		Set<Entity> cboNewClass = new HashSet<Entity>(neww.calcCBO());
		
		Set<Entity> diff = new HashSet<Entity>(cboOldClass);
			
		diff.removeAll(cboNewClass);
				
		return diff;
	}
	
	public Set<Entity> detectAdd(ParameterizableClass old, ParameterizableClass neww){
		
		Set<Entity> cboOldClass = new HashSet<Entity>(old.calculateCBO());
		Set<Entity> cboNewClass = new HashSet<Entity>(neww.calculateCBO());
		
		Set<Entity> diff = new HashSet<Entity>(cboNewClass);
			
		diff.removeAll(cboOldClass);
				
		return diff;
	}

	public Set<Entity> detectAdd(Class old, Class neww){
		
		Set<Entity> cboOldClass = new HashSet<Entity>(old.calculateCBO());
		Set<Entity> cboNewClass = new HashSet<Entity>(neww.calculateCBO());
		
		Set<Entity> diff = new HashSet<Entity>(cboNewClass);
			
		diff.removeAll(cboOldClass);
				
		return diff;
	}
	
	public Set<Entity> detectAdd(Method old, Method neww){
		
		Set<Entity> cboOldClass = new HashSet<Entity>(old.calcCBO());
		Set<Entity> cboNewClass = new HashSet<Entity>(neww.calcCBO());
		
		Set<Entity> diff = new HashSet<Entity>(cboNewClass);
			
		diff.removeAll(cboOldClass);
		
//		for(Entity entity: diff){
//			System.out.println(entity.getFullName());
//		}
				
		return diff;
	}
	
	public String extractDiff(Set<Entity> diff, Class classe, String addOrRemove){
		String content = "";
		for(Entity entity: diff){		
			content = content + addOrRemove + ";" + entity.getFullName() + ";" + classe.getFullName() + ";";
		}
		
		return content;
	}
	
	public String extractDiff(Set<Entity> diff, ParameterizableClass classe, String addOrRemove){
		String content = "";
		for(Entity entity: diff){		
			content = content + addOrRemove + ";" + entity.getFullName() + ";" + classe.getFullName() + ";";
		}
		
		return content;
	}
	
	public String extractDiff(Set<Entity> diff, Method method, String addOrRemove){
		String content = "";
		for(Entity entity: diff){		
			content = content + addOrRemove + ";" + entity.getFullName() + ";" + method.getFullName() + ";";
		}
		
		return content;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
