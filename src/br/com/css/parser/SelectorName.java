package br.com.css.parser;

public class SelectorName implements ParserState{

	private CssParser parser;	
	
	public SelectorName(CssParser parser) {
		this.parser = parser;
	}

	@Override
	public void processToken(String token) {
		if (token.contains("/*")){
			parser.setState(new Comment(parser));
			parser.setLastState(this);			
			processName(token, "/*");
		}
		else if (token.contains("{")){
			parser.setState(new SelectorBlock(parser));
			parser.setLastState(this);
			processName(token, "{");
		}
		else if (token.contains(","))
		{
			processName(token, ",");
		}
		else
			parser.concatSelectorName(token);
	}

	private void processName(String name, String acronym){
		parser.concatSelectorName(copyName(name, acronym));
		parser.analyseName();
	}
	
	private String copyName(String name, String acronym){
		if ((name.length() == acronym.length()) || (name.startsWith(acronym)))
			return "";
		
		return name.substring(0, name.indexOf(acronym));
	}
}
