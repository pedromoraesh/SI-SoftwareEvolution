package a.b.c;

import java.util.Map;
import java.util.Set;

import h.i.j.ClassWithInvocations;
import x.y.z.TestAttributes;
import x.y.z.TestAttributesWithParamaterizableClass;

public class TestMethods {
	
	public String teste1;
	public String teste2;
	public Map<Set<TestAttributesWithParamaterizableClass>, Double> teste3;
	public ClassWithInvocations teste4 = new ClassWithInvocations();
	
	
	private void testPrivateMethodWithoutParameter(){
		teste1 = teste2.intern();
		teste2 = teste1.trim();
		Double localB;
		
	}
	private void testPrivateMethodWithParameters(String a, Double b){
		teste1 = teste2.toLowerCase();
		teste2 = teste1.toUpperCase();
		
	}
	public void testPublicMethodWithoutParameter(){
		
	}
	public void testPublicMethodWithParameter(String c, TestAttributes d){
		
	}	
	private int testPrivateMethodPrimitiveReturnWithNoParameters(){
		
		return 0;
	}
	private int testPrivateMethodPrimitiveReturnWithParameters(Set<InternalClass> e, Double f, int g){
		return 0;
	}
	public int testPublicMethodPrimitiveReturnWithNoParameters(){
		return 0;
	}
	public int testPublicMethodPrimitiveReturnWithParameters(String h, TestAttributes i){
		return 0;
	}
   private Long testPrivateMethodObjectReturnWithNoParameters(){	
		return null;
	}
	private String testPrivateMethodObjectReturnWithParameters(String j, Double k, int l){
		return "";
	}
	public String testPublicMethodObjectReturnWithNoParameters(){
		return "";
	}
	public String testPublicMethodObjectReturnWithParameters(String m, TestAttributes n){
		return "";
	}
}
