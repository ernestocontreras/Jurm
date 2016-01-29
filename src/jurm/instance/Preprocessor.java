package jurm.instance;

import java.util.ArrayList;
import java.util.Scanner;

import jurm.URMException;

import jurm.instance.RegisterList;
import jurm.instance.Instruction;
import jurm.instance.InstructionList;
import jurm.instance.MacroList;
import jurm.instance.Operation;

import jurm.token.Token;
import jurm.lex.Lex;

import jurm.util.LineRange;

/**
 * Se encarga de transformar los tokens leidos del programa 
 * a instrucciones y registros ejecutables por la máquina.
 */
public final class Preprocessor {

	// Linea real del archivo
	public static int currentLine = 0;
	// caracteres leidos en total
	public static int totalCharacters = 0;

	public static void reset(){
		currentLine = 0;
		totalCharacters = 0;
	}

	/**
	 * Recorre secuencialemnte el scann para cargar los tokens leidos
	 * a la máquina, la lectura termina al leer una cadena igual a end.
	 * @param scann flujo de datos a leer
	 * @param end marca del fin de lectura
	 * @param registerList
	 * @param instructionList
	 * @param macroList
	 * @throws URMException si hubo un error al cargar
	 */
	public static void load(Scanner scann,
			String end,
			RegisterList registerList, 
			InstructionList instructionList,
			MacroList macroList) throws URMException {

		Lex lex = new Lex();
		ArrayList<Token> tokens;
		String line;
		Scanner scanner = scann;
		LineRange lineRange;

		while(scanner.hasNext())
		{
			line = scanner.nextLine();

			Preprocessor.currentLine++;
			lineRange = new LineRange(
				totalCharacters, totalCharacters + line.length()
			);
			Preprocessor.totalCharacters += line.length() + 1;
			if (line.equals(end)) {// Se termina la macro
				break;
			}

			tokens = lex.tokens(line);
			if (tokens.size() < Token.MINIMUM) {
				// Si es una Macro hay un solo token de tipo LETTER
				if (tokens.size() == 1 && tokens.get(0).isLetter()) {
					Macro macro = new Macro(tokens.get(0).word());
					macro.preprocess(scanner);
					macroList.put(macro);
				}
			}

			if (tokens.size() == Token.MINIMUM) {
				Preprocessor.createRegister(tokens, registerList);
			}

			if (tokens.size() > Token.MINIMUM) {
				Preprocessor.createInstruction(tokens, 
					instructionList, macroList, lineRange);
			}
		}
	}

	
	private static void createInstruction(ArrayList<Token> tokens, 
						InstructionList instructionList,
						MacroList macroList,
						LineRange range) throws URMException {

		String [] args = Preprocessor.getArguments(tokens);

		String name = tokens.get(1).word();
		Operation operation = Operation.get(name);

		instructionList.put(
			tokens.get(0).number(), // N. instruccion
			new Instruction(
				Preprocessor.currentLine, // La linea real
				range,
				operation,
				name,
				args
			)
		);
	}

	private static void createRegister(ArrayList<Token> tokens,
						RegisterList registerList){
		registerList.putInitial(
			tokens.get(0).word(),
			tokens.get(1).number()
		);
	}

	/**
	 * Obtiene la lista de argumentos de un array de tokens
	 * infiriendo que se trata de una instrucción.
	 * Se considera que los argumentos comienzan despues del número
	 * mínimo de tokens.
	 */
	private static String[] getArguments(ArrayList<Token> tokens) {
		String[] args = new String[ tokens.size() - Token.MINIMUM ];

		for (int i = Token.MINIMUM; i < tokens.size() ; i++) 
			args[i-Token.MINIMUM] = tokens.get(i).word();
		
		return args;
	}
}