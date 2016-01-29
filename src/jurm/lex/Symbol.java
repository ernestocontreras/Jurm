package jurm.lex;

/**
 * Encapsula los simbolos admitidos por URM.
 */
enum Symbol {
	SPACE,
	NUMBER,

	EQUAL,
	COLON,
	COMMA,

	PLEFT,
	PRIGHT,

	ERROR,

	LETTER;
	
	public final byte i;
	Symbol() {
		this.i = (byte)this.ordinal();
	}

	/**
	 * Obtiene el simbolo que representa al
	 * caracter c
	 * @return Symbol.ERROR si la entrada no se reconoció
	 */
	public static Symbol get(final char c) {
		switch(c) {
			case ' ' : return Symbol.SPACE;
			case '\n' : return Symbol.SPACE;
			case '\t' : return Symbol.SPACE;

			case '=' : return Symbol.EQUAL;
			case ':' : return Symbol.COLON;
			case ',' : return Symbol.COMMA;
			case '(' : return Symbol.PLEFT;
			case ')' : return Symbol.PRIGHT;
		}

		if (Character.isDigit(c)) {
			return Symbol.NUMBER;
		}

		if (Character.isLetter(c)) {
			return Symbol.LETTER;
		}
		return Symbol.ERROR;
	}
}