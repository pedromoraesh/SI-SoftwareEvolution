import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

//import com.opencsv.CSVWriter;


public class Main {

	public static int[] valuesTotal = {0,0,0,0,0,0};
	static String globalPath;
	static String hash;
	
	public static int[] parse(String str, File source) throws IOException {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		String[] classpath = java.lang.System.getProperty("java.class.path")
				.split(";");
		String[] sources = { source.getParentFile().getAbsolutePath() };

		Hashtable<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM,
				JavaCore.VERSION_1_8);
		 options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
		parser.setUnitName(source.getAbsolutePath());
		
		parser.setCompilerOptions(options);
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8" },
				true);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);

		final CompilationUnit compilationUnit = (CompilationUnit) parser
				.createAST(null);
		

		FieldDeclarationVisitor visitorField = new FieldDeclarationVisitor(globalPath, hash);
		TypeDeclarationVisitor visitorType = new TypeDeclarationVisitor(globalPath, hash);
		MethodDeclarationVisitor visitorMethod = new MethodDeclarationVisitor(globalPath, hash);
		compilationUnit.accept(visitorField);
		compilationUnit.accept(visitorType);
		compilationUnit.accept(visitorMethod);
		
		int[] retorno = {visitorField.getNumberDeprecatedWithRelevantMessages() , 
				visitorType.getNumberDeprecatedWithRelevantMessages() , 
				visitorMethod.getNumberDeprecatedWithRelevantMessages(), visitorField.getNumberDeprecateds() , 
							visitorType.getNumberDeprecateds() , 
							visitorMethod.getNumberDeprecateds()};
		return retorno;
		
		
	}
	
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();

		return fileData.toString();
	}
	
	public static void parseFilesInDir(File file, int[] values) throws IOException {
		if (file.isFile()) {
			if (file.getName().endsWith(".java")) {
				
				int[] line = parse(readFileToString(file.getAbsolutePath()), file);
				values[0] += line[0];
				values[1] += line[1];
				values[2] += line[2];
				values[3] += line[3];
				values[4] += line[4];
				values[5] += line[5];
				
			}
		} else {
			for (File f : file.listFiles()) {
				parseFilesInDir(f, valuesTotal);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		globalPath = args[0];//"C:/Users/Pedro Henrique/Documents/IC/Clones e Scripts/Android-Universal-Image-Loader";
		hash = args[1];//"9da1b1a";
		
		parseFilesInDir(new File(globalPath), valuesTotal);
		System.out.println("Data exported to " + globalPath + " with success");
		

	}

}
