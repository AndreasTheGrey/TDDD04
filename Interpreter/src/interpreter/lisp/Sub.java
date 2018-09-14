package interpreter.lisp;

public class Sub extends BinaryOperator<Num> {

	/**
	 * 
	 * We assume that the operands have been evaluated to Num objects
	 * 
	 * @see interpreter.lisp.BinaryOperator#evalBinaryOperator(interpreter.lisp.Expr, interpreter.lisp.Expr)
	 */
	@Override
	Expr evalBinaryOperator(Num value1, Num value2) {
		return new Num(value1.getValue()-value2.getValue());
	}

}
