package jurm;

import java.util.Stack;
import jurm.instance.Macro;

/**
 * Representa la pila de macros que ejecuta la máquina
 */
public class ExecutionStack {
	private static Stack<Macro> stack = new Stack<Macro>();
	private static Macro last;

	/**
	 * Quita el elemento de la cima de la pila, si la pila
	 * tiene solo un elemento este no se quita por tratarse
	 * de la macro principal.
	 */
	public static Macro pop() {
		if (ExecutionStack.stack.size() == 1){
			return ExecutionStack.stack.peek();
		} else {
			ExecutionStack.stack.peek().reset();
			return ExecutionStack.stack.pop();
		}
		
	}

	public static void push(Macro m) {
		ExecutionStack.last = ExecutionStack.peek();
		ExecutionStack.stack.push(m);
	}

	public static Macro peek() {
		return ExecutionStack.stack.peek();
	}

	public static void reset(Macro macro) {
		ExecutionStack.stack.clear();
		ExecutionStack.last = macro;
		ExecutionStack.stack.push(macro);
	}

	public static Macro last() {
		return ExecutionStack.last;
	}

	public static String represent(){
		StringBuilder sb = new StringBuilder();
		for (Macro m : ExecutionStack.stack) {
			sb.append(m.name());
			sb.append("\n");
			sb.append(m.registerList().represent());
			sb.append("\n");
		}
		return sb.toString();
	}
}