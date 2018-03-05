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
	public Long testeDiff1;
	public Double testeDiff2;
	
	
	private void testPrivateMethodWithoutParameter(){
		teste1 = teste2.intern();
		Long abc;
		String abcd = "";
		Double localB = null;
		localB.doubleValue();
		Integer testDiffA;
		Float testDiffB = null;
		int a = testDiffB.hashCode();
		abcd = abcd.trim();
		
	}
	private void testPrivateMethodWithParameters(String a, Float b, Long c){
		teste1 = teste2.toLowerCase();
		teste2 = teste1.toUpperCase();
		String testDiffC;
		Long testDiffD;
		ClassWithInvocations testDiffE;
		a = a.trim();
		
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
