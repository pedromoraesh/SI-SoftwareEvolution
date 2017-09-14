
public class Validator {

	
	@Deprecated
	int fielDeprecatedWithAnnotation;
	
	
	/**
	 * @deprecated
	 */
	int fieldDeprecatedWithJavaDocAnnotation;
	
	
	
	
	@Deprecated
	public static void methodDeprecatedOnlyWithAnnotation(String toConvert){
		
		
	}
	/**
	 * @deprecated
	 * @param roman
	 * @return
	 */
	public static void deprecatedWithJavaDocAnnotation(char roman){
		
	}
	
	public static boolean isValidNumber(int number){
		return number > 0 && number <= 1000;
	}
	
	
//	node.getJavadoc().toString().toLowerCase().contains("use") ||
//	node.getJavadoc().toString().toLowerCase().contains("replace") ||
//	node.getJavadoc().toString().toLowerCase().contains("refer") ||
//	node.getJavadoc().toString().toLowerCase().contains("equivalent") || 
//	node.getJavadoc().toString().toLowerCase().contains("@link") || 
//	node.getJavadoc().toString().toLowerCase().contains("@see") ||
//	node.getJavadoc().toString().toLowerCase().contains("@code") ||
//	node.getJavadoc().toString().toLowerCase().contains("instead") ||
//	node.getJavadoc().toString().toLowerCase().contains("should be used")||
//	node.getJavadoc().toString().toLowerCase().contains("moved") ||
//	node.getJavadoc().toString().toLowerCase().contains("see"))

	/**
	 * @deprecated
	 * use that method
	 * @param arabic
	 */
	public static void methodWithUse(String arabic) {
		
		
		
	} 
	/**
	 * @deprecated
	 * replace that method
	 * @param arabic
	 */
	public static void methodWithReplace(String arabic) {
		
		
		
	} 
	/**
	 * @deprecated
	 * refer to another
	 * @param arabic
	 */
	public static void methodWithRefer(String arabic) {
		
		
		
	} 
	/**
	 * @deprecated
	 * the equivalent method -> is another
	 * @param arabic
	 */
	public static void methodWithEquivalent(String arabic) {
		
		
		
	} 
	/**
	 * @deprecated
	 * {@link} methodWithEquivalent is better
	 * @param arabic
	 */
	public static void methodWithLink(String arabic) {
		
		
		
	} 
	/**
	 * @deprecated
	 * @see "otherMethod" this one
	 * @param arabic
	 */
	public static void methodWithSee(String arabic) {
		
		
		
	} 
	/**
	 * @deprecated
	 * you must go to another instead of that
	 * @param arabic
	 */
	public static void methodWithInstead(String arabic) {
		
		
		
	} 
	/**
	 * @deprecated
	 * {@code} code example
	 * @param arabic
	 */
	public static void methodWithCode(String arabic) {
		
		
		
	} 
	/**
	 * @deprecated
	 * should be used another one
	 * @param arabic
	 */
	public static void methodWithShouldBeUsed(String arabic) {
		
		
		
	} 
	/**
	 * @deprecated
	 * this method was moved to another place
	 * @param arabic
	 */
	public static void methodWithMoved(String arabic) {
		
		
		
	} 
	/**
	 * @deprecated
	 * see another method
	 * @param arabic
	 */
	public static void methodWithSee2(String arabic) {
		
		
		
	} 
}
