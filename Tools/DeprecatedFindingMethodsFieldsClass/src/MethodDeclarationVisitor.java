import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TagElement;


public class MethodDeclarationVisitor extends ASTVisitor{
	int numberMethods = 0;
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
	
	public MethodDeclarationVisitor(String globalPath, String hash) {
		this.path = globalPath;
		this.hash = hash;
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		
		boolean privateModifier = false;
		for (Object modifier : node.modifiers()) {
			if (modifier.getClass().equals(Modifier.class) && ((Modifier)modifier).isPrivate()) {
				privateModifier = true;
				break;
			}
			
		}
		if (!privateModifier && node.resolveBinding() != null && node.resolveBinding().isDeprecated()) {		
			if (containsTagJavaDoc(node, "@deprecated")) {
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
						
						printJavaDocWithRecomendation(node);
						
					}
					else{
						printJavaDocWithoutRecomendation(node);
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
			
						printJavaDocWithRecomendation(node);
						
					}
				}
				else {
					printJavaDocWithoutRecomendation(node);
				}
				
			}
			
	
		}
		numberMethods++;
		return super.visit(node);
	}
	
//	private boolean containsAnnotation(MethodDeclaration node, String annotation) {
//		System.out.println(node.resolveBinding().getMethodDeclaration().toString());
//		System.out.println(node.resolveBinding().getDeclaringClass().getQualifiedName());
//		for (IAnnotationBinding annotationBinding : node.resolveBinding().getAnnotations()) {	
//			if (annotationBinding.getName().equals(annotation)) {
//				return true;
//			}
//		}
//		
//		return false;
//	}
	
	private boolean containsJavaDoc(MethodDeclaration node) {
		if (node.getJavadoc() != null && 
				node.getJavadoc().tags() != null &&
				!node.getJavadoc().tags().isEmpty()) {
			return true;
		} 
		return false;
	}
	
	private boolean containsTagJavaDoc(MethodDeclaration node, String tag) {
		//"gleiosn".toLowerCase().contains("Use".toLowerCase());
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
	
	public void printJavaDocWithRecomendation(MethodDeclaration node){
		ExtractData extract = new ExtractData();
		String[] temp = node.resolveBinding().getMethodDeclaration().toString().split(" ");
		String javadoc = node.getJavadoc().toString().replaceAll("\n", "").replaceAll("\r", "");
		javadoc = javadoc.substring(javadoc.indexOf(" @deprecated")+1);
		String methodName = temp[2];
		String text = node.resolveBinding().getDeclaringClass().getQualifiedName() + ";" 
						+ methodName + ";" 
						+ "Method;" + javadoc;
		extract.export(this.path, text);
	}
	
	public void printJavaDocWithoutRecomendation(MethodDeclaration node){
		ExtractData extract = new ExtractData();
		String text = node.resolveBinding().getDeclaringClass().getQualifiedName() + ";" 
						+ node.getName() + ";" 
						+ "Method;";
		extract.exportWihoutMsg(this.path, text);
	}
	

	public int getNumberMethods() {
		return numberMethods;
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
	
	public void setPath(String path){
		this.path = path;
	}
}
