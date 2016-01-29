package jurm.token;

import jurm.token.TokenType;

public class Token {
	public static final byte MINIMUM = 2;

	private String word;
	private TokenType type;

	public Token(String word, TokenType type){
		this.word = word;
		this.type = type;
	}

	public final TokenType type(){
		return this.type;
	}

	public final String word(){
		return this.word;
	}

	public final int number()
	{
		return Integer.valueOf(this.word);
	} 

	public final boolean isLetter()
	{
		return this.type == TokenType.LETTER;
	}
}