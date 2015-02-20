package br.com.css.parser;

public class SelectorBlock implements ParserState{

	private CssParser parser;
		
	public SelectorBlock(CssParser parser) {
		this.parser = parser;
	}

	@Override
	public void processToken(String token) {
		if (token.contains("/*")){
			parser.setState(new Comment(parser));
			parser.setLastState(this);
		}
		else if (token.contains("}")){
			parser.setState(new SelectorName(parser));
			parser.setLastState(this);
		}		
	}

}
