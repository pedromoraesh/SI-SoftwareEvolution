import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TagElement;

public class MethodVisitor extends ASTVisitor {
	List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	List<MethodDeclaration> methodsDeprecated = new ArrayList<MethodDeclaration>();
	List<MethodDeclaration> methodsDeprecatedAnnotation = new ArrayList<MethodDeclaration>();
	List<MethodDeclaration> methodsDeprecatedJavaDoc = new ArrayList<MethodDeclaration>();
	List<MethodDeclaration> methodsDeprecatedValidJavaDoc = new ArrayList<MethodDeclaration>();

	@Override
	public boolean visit(MethodDeclaration node) {
		if (node.resolveBinding() != null && node.resolveBinding().isDeprecated()) {
			methodsDeprecated.add(node);
			
			if (containsAnnotation(node, "Deprecated")) {
				methodsDeprecatedAnnotation.add(node);
			}
			
			if (containsTagJavaDoc(node, "@deprecated")) {
				/*if (contendTag(node, "@deprecated").toLowerCase().contains("use")) {
					System.out.println(contendTag(node, "@deprecated"));
				}*/
				//System.out.println(contendTag(node, "@deprecated"));
				if (containsTagJavaDoc(node, "@see") || containsTagJavaDoc(node, "@link") ||
						(contendTag(node, "@deprecated") != null && contendTag(node, "@deprecated").toLowerCase().contains("use"))) {
					//System.out.println("comment: " + contendTag(node, "@deprecated"));
					methodsDeprecatedValidJavaDoc.add(node);
				}
				
				methodsDeprecatedJavaDoc.add(node);
			}	
		}

		methods.add(node);
	
		return super.visit(node);
	}
	
	private String contendTag(MethodDeclaration node, String tag) {
		if (node.getJavadoc() != null && 
				node.getJavadoc().tags() != null &&
				!node.getJavadoc().tags().isEmpty()) {
			for (Object tagElement : node.getJavadoc().tags()) {
				if (((TagElement) tagElement).getTagName() != null &&((TagElement) tagElement).getTagName().equals(tag) &&
						!((TagElement) tagElement).fragments().isEmpty()) {
					return ((TagElement) tagElement).fragments().get(0).toString();
				}
			}
		}
		
		return null;
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


	private boolean containsAnnotation(MethodDeclaration node, String annotation) {
		for (IAnnotationBinding annotationBinding : node.resolveBinding().getAnnotations()) {
			if (annotationBinding.getName().equals(annotation)) {
				return true;
			}
		}
		return false;
	}

	
	
	public List<MethodDeclaration> getMethodsDeprecatedJavaDoc() {
		return methodsDeprecatedJavaDoc;
	}



	public List<MethodDeclaration> getMethodsDeprecatedAnnotation() {
		return methodsDeprecatedAnnotation;
	}
	

	public List<MethodDeclaration> getMethodsDeprecated() {
		return methodsDeprecated;
	}

	public List<MethodDeclaration> getMethods() {
		return methods;
	}

	public List<MethodDeclaration> getMethodsDeprecatedValidJavaDoc() {
		return methodsDeprecatedValidJavaDoc;
	}
	
	
}
