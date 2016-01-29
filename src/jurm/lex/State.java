package jurm.lex;

import jurm.token.TokenType;
import jurm.lex.Symbol;

/**
 * Encapsula los estados del autómata léxico.
 * Utiliza una matriz para representar los estados del automata.
 */
enum State {
	FAIL,
	WAIT,
	NUMBER (TokenType.NUMBER),
	LETTER (TokenType.LETTER);

	public final byte i;// indice de la enumeración para la matriz
	private final TokenType tokenType;

	/*****************AUTOMATA***********
		S	09	=	:	,	(	)	E  A-Za-z
	0	0	0	0	0	0	0	0	0  0//F
	W	W	N	W	W	W	W	W	0  L

	N	W	N	W	W	W	W	W	0  W
	L	W	W	w	W	w	W	W	0  L
	************************************/
	private static final byte [][] MATRIX = {
		{State.FAIL.i,State.FAIL.i,State.FAIL.i,State.FAIL.i,State.FAIL.i,State.FAIL.i,State.FAIL.i,State.FAIL.i,State.FAIL.i},
		{State.WAIT.i,State.NUMBER.i,State.WAIT.i,State.WAIT.i,State.WAIT.i,State.WAIT.i,State.WAIT.i,State.FAIL.i,State.LETTER.i},

		{State.WAIT.i,State.NUMBER.i,State.WAIT.i,State.WAIT.i,State.WAIT.i,State.WAIT.i,State.WAIT.i,State.FAIL.i,State.WAIT.i},
		{State.WAIT.i,State.WAIT.i,State.WAIT.i,State.WAIT.i,State.WAIT.i,State.WAIT.i,State.WAIT.i,State.FAIL.i,State.LETTER.i},
	};

	State(){
		this(null);	
	}

	/**
	 * @param tokenType el tipo de token que se emite debido a
	 * que dicho estado es final.
	 */
	State(TokenType tokenType) {
		this.i = (byte)this.ordinal();
		this.tokenType = tokenType;
	}

	/**
	 * @return true si el estado actual es final
	 */
	public final boolean isFinal() {
		return this.tokenType != null;
	}

	public TokenType emit() {
		return this.tokenType;
	}

	/**
	 * Obtiene el nuevo estado a partir del
	 * estado y el simbolo actual.
	 */
	public static State get(State state, Symbol symbol) {
		int index = State.MATRIX[state.i][symbol.i];
		for (State st : State.values()) {
			if (st.i == index) return st;
		}
		return State.FAIL;
	}	
}