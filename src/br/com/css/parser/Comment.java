package br.com.css.parser;

public class Comment implements ParserState {

	private CssParser parser;
	
	public Comment(CssParser parser){
		this.parser = parser;
	}
	
	@Override
	public void processToken(String token) {
		if (token.contains("*/")) {
			parser.setState(parser.getLastState());
			parser.setLastState(this);
		}		
	}

}
