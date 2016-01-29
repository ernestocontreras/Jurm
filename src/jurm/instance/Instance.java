package jurm.instance;

import java.util.HashMap;
import java.util.Scanner;

import jurm.instance.RegisterList;
import jurm.instance.InstructionList;
import jurm.instance.MacroList;
import jurm.instance.Instruction;
import jurm.instance.Preprocessor;

import jurm.URMException;
import jurm.ExecutionStack;
import jurm.util.LineRange;

/**
 * Representa una instancia de la máquina.
 */
abstract class Instance {
	private int currentInstruction;
	private int currentLine;

	protected RegisterList registerList;
	protected InstructionList instructionList;
	private Instruction toDoInstruction;

	protected MacroList macroList;
	protected boolean isFinished;
	protected String name;

	protected Instance() {
		this.registerList = new RegisterList();
		this.instructionList = new InstructionList();
		this.macroList = new MacroList();
		
		this.currentInstruction = 1;
		this.currentLine = 0;
		this.toDoInstruction = null;
		this.isFinished = true;
	}

	/**
	 * Ejecuta la instruccion actual. 
	 */
	public final void executeStep() throws URMException {

		if (this.isFinished)this.isFinished = false;

		if (this.currentInstruction <= this.instructionList.last())
		{
			boolean succes = false;
			toDoInstruction = this.instructionList.get(this.currentInstruction);
			succes = toDoInstruction.execute(registerList,macroList);

			currentLine = toDoInstruction.line();

			if (toDoInstruction.isJump() && succes) {
				this.currentInstruction = Integer.valueOf(toDoInstruction.params()[2]);
			} 
			else {
				this.currentInstruction++;
			}
		} else {
			this.endSignal();
			this.isFinished = true;
		}
	}

	/**
	 * Carga los registros, instrucciones y macros leidos en el scanner 
	 */
	public void preprocess(Scanner scanner) throws URMException {
		Preprocessor.load(scanner,
			this.name,
			this.registerList, 
			this.instructionList,
			this.macroList);
	}

	public final int result() {
		return this.registerList.get("1");
	}

	public final int currentLine() {
		return this.currentLine;
	}

	public final LineRange currentLineRange() {
		if (toDoInstruction != null)
			return toDoInstruction.lineRange();
		return new LineRange(0,0);
	}

	public final boolean isFinished() {
		return this.isFinished;
	}
	
	public final RegisterList registerList() {
		return this.registerList;
	}

	public final void reset()
	{
		this.currentInstruction = 1;
		this.currentLine = 0;
		this.toDoInstruction = null;
		this.isFinished = true;
		this.registerList.reset();
	}

	public void print(){
		System.out.println(name + ">");
		System.out.print("\t");
		instructionList.print();

		System.out.print("\t");
		macroList.print();
	}

	/**
	 * Se llama cuando las instrucciones se terminan
	 */
	protected abstract void endSignal();
}