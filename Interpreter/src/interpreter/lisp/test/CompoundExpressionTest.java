package interpreter.lisp.test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import interpreter.lisp.CompoundExpression;
import interpreter.lisp.CompoundFunction;
import interpreter.lisp.Constants;
import interpreter.lisp.Context;
import interpreter.lisp.Expr;
import interpreter.lisp.Num;
import interpreter.lisp.Reader;
import interpreter.lisp.Symbol;

import org.junit.Test;

public class CompoundExpressionTest {

	private static final String fac = "(def fac (x) (if (= x 0) 1 (* x (fac (- x 1)))))";
	private static final String applyFac = "(fac 5)";

	@Test
	public void testEvaluateFac() {
		Expr definition = Reader.read(fac).evaluate(Context.getTopLevelContext());
		assertThat(definition, is(instanceOf(CompoundExpression.class)));
	}
	
	@Test
	public void testEvaluateApplyFac() {
		Reader.read(fac).evaluate(Context.getTopLevelContext());
		Expr value = Reader.read(applyFac).evaluate(Context.getTopLevelContext());
		assertEquals(new Num(120), value);
	}

	@Test
	public void testCompoundExpression() {
		Expr expr3 = Constants.TRUE;
		CompoundExpression ce = new CompoundExpression();
		CompoundFunction cp = new CompoundFunction(new Symbol("t"),ce, expr3);
		assertTrue(Constants.TRUE.toString().equals(cp.toString()));
		assertEquals(ce.evaluate(Context.getTopLevelContext()),Constants.EMPTY_LIST);
		
		
		Expr exprInstance = cp.evaluate(Context.getTopLevelContext());
		assertEquals(Context.getTopLevelContext().get(cp.getName()), exprInstance);
	}
	
	@Test
	public void testCompoundExpression_GetName() {
		String variableName = "test";
		Expr expr3 = Constants.TRUE;
		CompoundExpression ce = new CompoundExpression();
		CompoundFunction cp = new CompoundFunction(new Symbol(variableName),ce, expr3);
		assertTrue(variableName.equals(cp.getName().toString()));
		
	}

	
}
