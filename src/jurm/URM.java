package jurm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import jurm.URMException;
import jurm.ExecutionStack;

import jurm.instance.Macro;
import jurm.instance.GlobalMacros;
import jurm.instance.Preprocessor;

import jurm.util.LineRange;

/**
 * Emula una máquina de registros ilimitados
 */
public final class URM
{
	private Macro mainMacro;
	public URM() {
		GlobalMacros.init();
		this.reset();
	}

	public void reset() {
		Preprocessor.reset();

		this.mainMacro = null;
		this.mainMacro = new Macro("_Main_");
		ExecutionStack.reset(this.mainMacro);
	}

	/**
	 * Inicia el análisis del programa desde un String
	 */
	public void init(final String code) throws URMException {
		this.reset();
		Scanner scanner = new Scanner(code);
		this.mainMacro.preprocess(scanner);
	}

	/**
	 * Ejecuta todo el programa cargado actualmente
	 */
	public void run() throws URMException {
		ExecutionStack.peek().executeStep();
		while (!mainMacro.isFinished()){
			ExecutionStack.peek().executeStep();
		}
	}

	/**
	 * Ejecuta la siguiente instrucción del programa actual
	 */
	public void runStep() throws URMException {
			ExecutionStack.peek().executeStep();
	}

	/**
	 * Devuelve la linea leida actualmente
	 */
	public int currentLine() {
		return ExecutionStack.peek().currentLine();
	}

	/**
	 * @return Rango de caracteres de la linea actual
	 */
	public LineRange currentLineRange() {
		if (ExecutionStack.peek().currentLine() == 0){
			return ExecutionStack.last().currentLineRange();
		}
		return ExecutionStack.peek().currentLineRange();
	}

	/**
	 * @return true si el porgrama ya terminó
	 */
	public boolean isFinished() {
		return this.mainMacro.isFinished();
	}

	public int result() {
		return this.mainMacro.result();
	}

	public String represent(){
		return ExecutionStack.represent();
	}
}