package interpreter;

import java.util.Map;

/**
 * 
 * Adapted from http://en.wikipedia.org/wiki/Interpreter_pattern
 * 
 * @author olale
 * 
 */
public interface Expression {
	public int interpret(Map<String, Expression> variables);
}