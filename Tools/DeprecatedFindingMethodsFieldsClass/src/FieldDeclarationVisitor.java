

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class FieldDeclarationVisitor extends ASTVisitor {

	int numberFields = 0;
	int numberDeprecateds = 0;
	int numberDeprecatedsWithAnnotation = 0; 
	int numberDeprecatedsWithMessage = 0;
	int numberDeprecatedWithRelevantMessages = 0;
	
	int numberUse = 0;
	int numberReplace = 0;
	int numberRefer = 0;
	int numberEquivalent = 0;
	int numberLink = 0;
	int numberSee = 0;
	int numberCode = 0;
	
	String path;
	String hash;
	
	
	public FieldDeclarationVisitor(String globalPath, String hash){
		this.path = globalPath;
		this.hash = hash;
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
		boolean privateModifier = false;
		for (Object modifier : node.modifiers()) {
			if (modifier.getClass().equals(Modifier.class) && ((Modifier)modifier).isPrivate()) {
				privateModifier = true;
				break;
			}
			
		}
		
		if (!privateModifier) {
			List<VariableDeclarationFragment> fragments = node.fragments();
			if (fragments != null && !fragments.isEmpty()) {
				for (VariableDeclarationFragment variableDeclarationFragment : fragments) {
					if (variableDeclarationFragment.resolveBinding() != null) {
						if (variableDeclarationFragment.resolveBinding().isDeprecated()) {
							numberDeprecateds++;
							if (containsTagJavaDoc(node, "@deprecated")) {	
								numberDeprecatedsWithMessage++;
								if (node.getJavadoc().toString().toLowerCase().contains("use") ||
										node.getJavadoc().toString().toLowerCase().contains("replace") ||
										node.getJavadoc().toString().toLowerCase().contains("refer") ||
										node.getJavadoc().toString().toLowerCase().contains("equivalent") || 
										node.getJavadoc().toString().toLowerCase().contains("@link") || 
										node.getJavadoc().toString().toLowerCase().contains("@see") ||
										node.getJavadoc().toString().toLowerCase().contains("@code") ||
										node.getJavadoc().toString().toLowerCase().contains("instead") ||
										node.getJavadoc().toString().toLowerCase().contains("should be used")||
										node.getJavadoc().toString().toLowerCase().contains("moved") ||
										node.getJavadoc().toString().toLowerCase().contains("see")) {
									
									printJavaDocWithRecomendation(variableDeclarationFragment, node);
									
								}
								else {
									printJavaDocWithoutRecomendation(variableDeclarationFragment, node);
								}
							}
							else {
								if(containsJavaDoc(node)) {
									if (node.getJavadoc().toString().toLowerCase().contains("use") ||
											node.getJavadoc().toString().toLowerCase().contains("replace") ||
											node.getJavadoc().toString().toLowerCase().contains("refer") ||
											node.getJavadoc().toString().toLowerCase().contains("equivalent") || 
											node.getJavadoc().toString().toLowerCase().contains("@link") || 
											node.getJavadoc().toString().toLowerCase().contains("@see") ||
											node.getJavadoc().toString().toLowerCase().contains("@code") ||
											node.getJavadoc().toString().toLowerCase().contains("instead") ||
											node.getJavadoc().toString().toLowerCase().contains("should be used")||
											node.getJavadoc().toString().toLowerCase().contains("moved") ||
											node.getJavadoc().toString().toLowerCase().contains("see")) {
							
										printJavaDocWithRecomendation(variableDeclarationFragment, node);
										
									}
								}
								else {
									printJavaDocWithoutRecomendation(variableDeclarationFragment, node);
								}
								
							}
							
						}
						
					}
				}
			}
		}
		return super.visit(node);
	}
		

	
	private boolean containsJavaDoc(FieldDeclaration node) {
		if (node.getJavadoc() != null && 
				node.getJavadoc().tags() != null &&
				!node.getJavadoc().tags().isEmpty()) {
			return true;
		} 
		return false;
	}
	
	private boolean containsTagJavaDoc(FieldDeclaration node, String tag) {
		if (node.getJavadoc() != null && 
				node.getJavadoc().tags() != null &&
				!node.getJavadoc().tags().isEmpty()) {
			for (Object tagElement : node.getJavadoc().tags()) {
				if (((TagElement) tagElement).getTagName() != null &&((TagElement) tagElement).getTagName().equals(tag)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void printJavaDocWithRecomendation(VariableDeclarationFragment variable, FieldDeclaration node){
		ExtractData extract = new ExtractData();
		String javadoc = node.getJavadoc().toString().replaceAll("\n", "").replaceAll("\r", "");
		javadoc = javadoc.substring(javadoc.indexOf(" @deprecated")+1);
		String text = variable.resolveBinding().getDeclaringClass().getQualifiedName() + ";" 
					+ variable.getName() + ";" 
					+ "Field;" + javadoc;
		extract.export(this.path, text);
	}
	
	public void printJavaDocWithoutRecomendation(VariableDeclarationFragment variable, FieldDeclaration node){
		ExtractData extract = new ExtractData();
		
		String text = variable.resolveBinding().getDeclaringClass().getQualifiedName() + ";" 
					+ variable.getName() + ";" 
					+ "Field;";
		extract.exportWihoutMsg(this.path, text);
	}


	public int getNumberFields() {
		return numberFields;
	}


	public int getNumberDeprecateds() {
		return numberDeprecateds;
	}


	public int getNumberDeprecatedsWithAnnotation() {
		return numberDeprecatedsWithAnnotation;
	}


	public int getNumberDeprecatedsWithMessage() {
		return numberDeprecatedsWithMessage;
	}


	public int getNumberDeprecatedWithRelevantMessages() {
		return numberDeprecatedWithRelevantMessages;
	}


	public int getNumberUse() {
		return numberUse;
	}


	public int getNumberReplace() {
		return numberReplace;
	}


	public int getNumberRefer() {
		return numberRefer;
	}


	public int getNumberEquivalent() {
		return numberEquivalent;
	}


	public int getNumberLink() {
		return numberLink;
	}


	public int getNumberSee() {
		return numberSee;
	}


	public int getNumberCode() {
		return numberCode;
	}


	public void setPath(String path) {
		this.path = path;
		
	}
	
	
	
	
	/*public List<VariableDeclarationFragment> getFields() {
		return fields;
	}


	public List<VariableDeclarationFragment> getDeprecateds() {
		return deprecateds;
	}


	public List<VariableDeclarationFragment> getDeprecatedsWithoutMessage() {
		return deprecatedsWithoutMessage;
	}*/
	
	

}
