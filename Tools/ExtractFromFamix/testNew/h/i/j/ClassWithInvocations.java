package h.i.j;

import a.b.c.TestMethods;
import x.y.z.TestAttributes;
import x.y.z.TestAttributesWithParamaterizableClass;

public class ClassWithInvocations {
	TestMethods methods = new TestMethods();
	TestAttributesWithParamaterizableClass pClass = new TestAttributesWithParamaterizableClass();
	TestAttributes tAttributes = new TestAttributes();
	String string = "";
	String otherString = string.trim();
	Long forNewCBO;
	Double forNewwCBO;
	Integer testDiff;
	
	int a = methods.testPublicMethodPrimitiveReturnWithParameters(" ", tAttributes);
		
}
