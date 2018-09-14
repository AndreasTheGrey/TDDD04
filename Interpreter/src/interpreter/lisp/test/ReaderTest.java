package interpreter.lisp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import interpreter.Evaluator;
import interpreter.Expression;
import interpreter.Number;
import interpreter.InterpreterExample;
import interpreter.Variable;
import interpreter.lisp.CompoundExpression;
import interpreter.lisp.CompoundFunction;
import interpreter.lisp.Conditional;
import interpreter.lisp.Constants;
import interpreter.lisp.Expr;
import interpreter.lisp.Num;
import interpreter.lisp.Reader;
import interpreter.lisp.Symbol;
import interpreter.lisp.InterpreterTest;
import interpreter.lisp.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReaderTest {

	private final PrintStream originalOut = System.out;
	private final ByteArrayOutputStream outConsoleStream = new ByteArrayOutputStream();
	
	@Before
	public void setupConsoleStream() {
		System.setOut(new PrintStream(outConsoleStream));
	}
	
	@After
	public void tearDownConsoleStream() {
		System.setOut(originalOut);
	}
	
	
	@Test
	public void testReadFunctionDefinition() {
		String functionDefinition = "(def fac (x) (if (= x 0) 1 (* x (fac (- x 1)))))";
		assertEquals(CompoundExpression.class, Reader.read(functionDefinition).getClass());
	}
	
	@Test
	public void testReadSimpleExpression() {
		assertEquals(new Num(1), Reader.read("1"));
	}

	@Test
	public void testReadVariable() {
		assertEquals(new Symbol("x"), Reader.read("x"));
	}
	
	@Test
	public void testReaderInstance() {
		Reader reader = new Reader();
	}
	
	
	@Test
	public void testInterpreter() {
	    String expression = "w 20 z + /";
        Evaluator sentence = new Evaluator(Evaluator.read(expression));
        Map<String,Expression> variables = new HashMap<String,Expression>();
        variables.put("w", new Number(5));
        variables.put("x", new Number(10));
        variables.put("z", new Number(42));
        variables.put("k", null);
        // The variables are our simple evaluation context
        int result = sentence.interpret(variables);
        
        String expression2 = "x 20 x - /";
        Evaluator sentence2 = new Evaluator(Evaluator.read(expression2));
        // The variables are our simple evaluation context
        int result2 = sentence2.interpret(variables);
        
        String expression3 = "10 10 *";
        Evaluator sentence3 = new Evaluator(Evaluator.read(expression3));
        // The variables are our simple evaluation context
        int result3 = sentence3.interpret(null);
        
        String expression4 = "10 k +";
        Evaluator sentence4 = new Evaluator(Evaluator.read(expression4));
        // The variables are our simple evaluation context
        int result4 = sentence4.interpret(variables);
        
        assertEquals(new Num(12).getValue(), result);
        assertEquals(new Num(-1).getValue(), result2);
        assertEquals(new Num(100).getValue(), result3);
	}
	
	@Test
	public void testVariableNull()
	{
	    Map<String,Expression> variables = new HashMap<String,Expression>();
        variables.put("w", new Number(5));
        variables.put("x", new Number(10));
        variables.put("z", new Number(42));
        variables.put("k", null);
        Variable test1 = new Variable("l");
        Variable test2 = new Variable("w");
        
        int result1 = test1.interpret(variables);
        int result2 = test2.interpret(variables);
        assertEquals(result1, 0);
        assertEquals(result2, 5);
	}
	
	@Test
	public void testSymbolToString() {
		Symbol s = new Symbol("h");
		String toString = s.toString();
		assertTrue("h".equals(toString));
	}
	
	@Test
	public void testSymbolEquals() {
		Symbol s1 = new Symbol("s");
		Symbol s2 = new Symbol("s");
		Symbol h1 = new Symbol("h");
		assertTrue(s1.equals(s2));
		assertFalse(s1.equals(h1));
	}
	
	
	@Test
	public void testNum() {
		int number = 1;
		Num n = new Num(number);
		assertTrue(String.valueOf(number).equals(n.toString()));
		assertEquals(number, n.hashCode());
	}
	
	@Test
	public void testInterpreterTest() {
		InterpreterTest it = new InterpreterTest();
		InterpreterTest.main(null);
		assertTrue(outConsoleStream.toString().contains("120"));
	}
	
	@Test
	public void testInterpreterExample() {
		InterpreterExample ie = new InterpreterExample();
		InterpreterExample.main(null);
		assertTrue(outConsoleStream.toString().contains("12"));
	}
	
	
	@Test
	public void testConstants() {
		Expr c = Constants.TRUE;
		assertEquals(c, c.evaluate(null));
	}
	
	@Test
	public void testConditional() {
		Symbol a1 = new Symbol("a");
		Symbol a2 = new Symbol("b");
		Symbol a3 = new Symbol("c");
		Conditional c1 = new Conditional(a1,a2,a3);
		assertTrue(a1.equals(c1.getCondition()));
		assertTrue(a2.equals(c1.getConsequent()));
		assertTrue(a3.equals(c1.getAntecedent()));
		
	}
	
	@Test
	public void testContext()
	{
		Context c = Context.getTopLevelContext();
		Symbol a1 = new Symbol("k");
		
		assertFalse(c.hasParent());
		assertEquals(c.get(a1),null);
		c.extend(c);
		assertEquals(c.getParent(), Context.getTopLevelContext());
		System.setOut(originalOut);
		c.toString();
		
	}
	
	@Test
	public void testCompoundExpressionEval()
	{
		CompoundExpression ce = new CompoundExpression();
		ce.evaluate(null);
	} 
	
	
	
	
	
}
