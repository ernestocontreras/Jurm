package jurm.instance;

import java.util.ArrayList;

import jurm.URMException;

import jurm.instance.Operation;
import jurm.instance.RegisterList;
import jurm.instance.MacroList;
import jurm.instance.Macro;
import jurm.instance.Instance;

import jurm.ExecutionStack;
import jurm.util.LineRange;

final class Instruction {
	private final Operation operation;
	private final String [] params;
	private final String name;
	private final int realLine;
	private final LineRange lineRange;

	public Instruction(int realLine, 
						LineRange lineRange, 
						Operation operation,
						String name, 
						String [] params) {
		this.lineRange = lineRange;
		this.realLine = realLine;
		this.operation = operation;
		this.name = name;
		this.params = params;
	}

	public String name() {
		return this.name;
	}

	public String [] params() {
		return this.params;
	}

	public Operation operation() {
		return this.operation;
	}

	/**
	 * Ejecuta la operación asociada a esta instrucción.
	 * Si la operación es una macro, esta se busca en macroList.
	 * @param registerList los registros que serán afectados por la ejecución.
	 * @param macroList la lista de macros disponibles.
	 * @return true si la operación se realizó correctamente.
	 */
	public boolean execute(RegisterList registerList, 
							MacroList macroList) throws URMException {
		if (operation == Operation.MACRO) {
			// Busca la macro correspondiente a su nombre
			Macro macro = macroList.get(name);
			macro.initRegister(registerList,this.params);
			ExecutionStack.push(macro);
			return true;
		}
		return this.operation.operate(registerList,this.params);
	}

	public boolean isJump() {
		return this.operation == Operation.J;
	}

	/**
	 * @return la linea real del archivo correspondiente a esta instrucción
	 */
	public int line() {
		return this.realLine;
	}

	public LineRange lineRange() {
		return this.lineRange;
	}
}