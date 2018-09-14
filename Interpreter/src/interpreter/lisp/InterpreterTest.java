package interpreter.lisp;

public class InterpreterTest {

	public static void main(String[] args) {
		Expr expr = Reader.read("(def fac (x) (if (= x 0) 1 (* x (fac (- x 1)))))");
		Expr result = expr.evaluate(Context.getTopLevelContext());
		Expr expr2 = Reader.read("(fac 5)");
		result = expr2.evaluate(Context.getTopLevelContext());
		System.out.println(result);
	}

}
