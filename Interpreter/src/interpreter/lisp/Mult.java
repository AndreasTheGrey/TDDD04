package interpreter.lisp;

public class Mult extends BinaryOperator<Num> {

	@Override
	Expr evalBinaryOperator(Num value1, Num value2) {
		return new Num(value1.getValue()*value2.getValue());
	}

}
