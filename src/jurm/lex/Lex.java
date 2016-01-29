package jurm.lex;

import java.util.ArrayList;

import jurm.URMException;

import jurm.lex.State;
import jurm.lex.Symbol;
import jurm.token.Token;


/**
 * Analizador Léxico para URM
 */
public final class Lex 
{
	private State STATE; //estado actual

	/**
	 * @param line cadena para ser analizada
	 * @return una lista de Tokens obtenidos a partir de la cadena
	 * @throws URMExeption si hubo un error de lectura
	 */
	public ArrayList<Token> tokens(final String line) throws URMException {
		ArrayList<Token> arrayTokens = new ArrayList<Token>();
		
		String word = "";
		Symbol symbol = Symbol.ERROR;
		State newState = State.WAIT;
		this.STATE = State.WAIT;

		for (char character : line.toCharArray()) 
		{
			if (character == '#'){break;}//Comentario

			symbol = Symbol.get(character);
			if (symbol == Symbol.ERROR) {
				throw new URMException(this,"No se reconoce " + character);
			}

			newState = State.get(this.STATE,symbol);
			if (newState != this.STATE && this.STATE.isFinal()) {
				arrayTokens.add(
					new Token(word,this.STATE.emit())
				);
				word = "";
			}
			
			this.STATE = newState;
			if (this.STATE.isFinal()) {
				word += character;
			}

			if (this.STATE == State.FAIL) {
				throw new URMException(this,"Error de estado"); 
			}
		}

		if (this.STATE.isFinal()) {
			arrayTokens.add(
				new Token(word,this.STATE.emit())
			);
		}

		return arrayTokens;
	}
}
