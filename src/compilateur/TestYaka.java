package compilateur;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;

/**
 * Unit tests.
 * @author Paul Chaignon
 * @author Damien Le Guen
 * @author Antoine Lejeune
 * @author Benoit Travers
 */
public class TestYaka extends TestCase {
	
	/**
	 * First test just to intialize the Yaka compiler.
	 */
	public void testFirst() {
		new Yaka(System.in);
	}
	
	/**
	 * Test the declaration of the variables.
	 */
	public void testVariablesDeclaration() {
		this.testProgram("tests/declaration_variables", false, false);
	}
	
	/**
	 * Test all types of declarations.
	 */
	public void testDeclaration() {
		this.testProgram("tests/declaration", false, false);
	}
	
	/**
	 * Test the program for affectations part.
	 */
	public void testAffectations() {
		this.testProgram("tests/affectations", false, false);
	}
	
	/**
	 * Test the inputs and outputs.
	 */
	public void testInputOutput() {
		this.testProgram("tests/input_output", false, false);
	}
	
	/**
	 * Test a simple iteration.
	 */
	public void testSimpleIteration() {
		this.testProgram("tests/simple_iteration", false, false);
	}
	
	/**
	 * Test nested interations.
	 */
	public void testNestedIterations() {
		this.testProgram("tests/nested_iterations", false, false);
	}
	
	/**
	 * Test an expression.
	 * Written by the teachers.
	 */
	public void testTeachersExpr() {
		for(int i=1 ; i<6 ; i++) {
			this.testProgram("tests/tests_prof/expr"+i, false, false);
		}
	}
	
	/**
	 * Test a simple condition bloc.
	 */
	public void testSimpleCondition() {
		this.testProgram("tests/simple_condition", false, false);
	}
	
	/**
	 * Test the functions example from the handout.
	 */
	public void testHandoutFunctions() {
		this.testProgram("tests/simple_functions", true, false);
	}
	
	/**
	 * Compile a Yaka program and compare it to the YVM and ASM programs.
	 * @param file The path to the files. They differ in their extensions.
	 * @param showYVMCode True to show the YVM code.
	 * @param showASMCode True to show the ASM code.
	 */
	private void testProgram(String file, boolean showYVMCode, boolean showASMCode) {
		String program = getContentOfFile(file+".yaka");
		
		String programYVM = getContentOfFile(file+".yvm");
		compileToYVM(program);
		if(showYVMCode) {
			System.out.println("YVM:");
			System.out.println(Yaka.yvm.getProgram());
		}
		assertEquals(programYVM, Yaka.yvm.getProgram());
		
		String programASM = getContentOfFile(file+".asm");
		compileToASM(program);
		if(showASMCode) {
			System.out.println("ASM:");
			System.out.println(Yaka.yvm.getProgram());
		}
		assertEquals(programASM, Yaka.yvm.getProgram());
	}
	
	/**
	 * Compile the program to YVM code.
	 * @param program The Yaka program.
	 */
	private static void compileToYVM(String program) {
		InputStream input = new ByteArrayInputStream(program.getBytes());
		Yaka.ReInit(input);
		Yaka.initVariables(true);
	    try {
			Yaka.analyse();
		} catch (ParseException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Compile the program to ASM code.
	 * @param program The Yaka program.
	 */
	private static void compileToASM(String program) {
		InputStream input = new ByteArrayInputStream(program.getBytes());
		Yaka.ReInit(input);
		Yaka.initVariables(false);
	    try {
			Yaka.analyse();
		} catch (ParseException e) {
			fail(e.getMessage());
		}
	}
	
	private static String getContentOfFile(String nameFile) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nameFile)));
			String content = "";
			String line;
			while ((line = br.readLine()) != null) {
				content += line+"\n";
			}
			br.close();
			return content;
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return null;
	}
}