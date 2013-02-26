import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

public class TestYaka extends TestCase {
	
	/**
	 * First test just to intialize the Yaka compiler.
	 */
	public void testFirst() {
		new Yaka(System.in);
	}
	
	/**
	 * Test an empty program.
	 */
	/*public void testEmpty() {
		String program = "PROGRAMME empty FPROGRAMME";
		this.launchAnalyse(program);
		assertEquals("", Yaka.expression.getResult());
	}*/
	
	/**
	 * Test the declaration of the constants.
	 */
	/*public void testDeclarationConstants() {
		String program = "PROGRAMME const " +
				"CONST aa=10, ba=VRAI, cc=aa;" +
				"FPROGRAMME";
		this.launchAnalyse(program);
		assertEquals("", Yaka.expression.getResult());
	}*/
	
	/**
	 * Test the declaration of the variables.
	 */
	public void testDeclarationVariables() {
		String program = "PROGRAMME var " +
				"VAR ENTIER c1,c2; VAR BOOLEEN b1,b2; " +
				"c1 = 5; " +
				"FPROGRAMME";
		String programYVM = "iconst 5\n" +
				"istore -2\n";
		this.launchAnalyse(program);
		assertEquals(programYVM, Yaka.expression.getResult());
	}
	
	/**
	 * Test all types of declarations.
	 */
	public void testDeclaration() {
		String program = "PROGRAMME declar " +
				"CONST aa=10, ba=VRAI, cc=aa; " +
				"VAR ENTIER c1,c2; VAR BOOLEEN b1,b2; " +
				"c1 = 5; " +
				"FPROGRAMME";
		String programYVM = "iconst 5\n" +
				"istore -2\n";
		this.launchAnalyse(program);
		assertEquals(programYVM, Yaka.expression.getResult());
	}
	
	/**
	 * Test the program for expressions part.
	 */
	/*public void testExpression() {
		String program = "PROGRAMME declar " +
				"CONST aa=10, ba=VRAI, cc=aa; " +
				"VAR ENTIER c1,c2; VAR BOOLEEN b1,b2; " +
				"(aa+cc/2)/5; c1+3*c1-aa; c1<=(c2+4); ba OU VRAI;" +
				"FPROGRAMME";
		String programYVM = "iconst 10\n"+
				"iconst 10\n"+
				"iconst 2\n"+
				"idiv\n"+
				"iadd\n"+
				"iconst 5\n"+
				"idiv\n"+
				"iload -2\n"+
				"iconst 3\n"+
				"iload -2\n"+
				"imul\n"+
				"iadd\n"+
				"iconst 10\n"+
				"isub\n"+
				"iload -2\n"+
				"iload -4\n"+
				"iconst 4\n"+
				"iadd\n"+
				"iconst -1\n"+
				"iconst -1\n"+
				"ior\n"+
				"iinfegal\n";
		this.launchAnalyse(program);
		assertEquals(programYVM, Yaka.expression.getResult());
	}*/
	
	/**
	 * Test the program for affectations part.
	 */
	public void testAffectations() {
		String program = "PROGRAMME declar " +
				"VAR ENTIER x,y,z; " +
				"z=(x+y/2)/5; x=y+3*y-4;" +
				"FPROGRAMME";
		String programYVM = "iload -2\n"+
				"iload -4\n"+
				"iconst 2\n"+
				"idiv\n"+
				"iadd\n"+
				"iconst 5\n"+
				"idiv\n"+
				"istore -6\n"+
				"iload -4\n"+
				"iconst 3\n"+
				"iload -4\n"+
				"imul\n"+
				"iadd\n"+
				"iconst 4\n"+
				"isub\n"+
				"istore -2\n";
		this.launchAnalyse(program);
		assertEquals(programYVM, Yaka.expression.getResult());
	}
	
	public void testEntreeSotie() {
		String program = "PROGRAMME entreeSotie" +
				" VAR ENTIER x,y,z;" +
				" ECRIRE(\"x=\"); " +
				"LIRE(x); " +
				"ALALIGNE; " +
				"ECRIRE(\"y=\"); " +
				"LIRE(y); " +
				"ALALIGNE; " +
				"ECRIRE(\"x+y=\"); " +
				"ECRIRE(x+y); " +
				"ALALIGNE; " +
				"z=(x+y/2)/5; " +
				"x=y+3*y-4; " +
				"FINPROGRAMME";
		this.launchAnalyse(program);
		System.out.println(Yaka.expression.getResult());
	}
	
	/**
	 * Compile the program.
	 * @param program The program.
	 */
	private void launchAnalyse(String program) {
		InputStream input = new ByteArrayInputStream(program.getBytes());
		Yaka.ReInit(input);
		Yaka.initVariables();
	    try {
			Yaka.analyse();
		} catch (ParseException e) {
			fail(e.getMessage());
		}
	}
}