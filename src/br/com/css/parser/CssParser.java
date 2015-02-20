package br.com.css.parser;

import java.util.Scanner;

public class CssParser {

	private int typeSelectorQty;
	private int classSelectorQty;
	private int idSelectorQty;
	
	private String selectorName;
	
	private ParserState state;
	private ParserState lastState;
	
	
	public int getTypeSelectorQty() {
		return typeSelectorQty;
	}
	
	public int getClassSelectorQty() {
		return classSelectorQty;
	}
	
	public int getIdSelectorQty() {
		return idSelectorQty;
	}
	
	public ParserState getState() {
		return state;
	}

	public void setState(ParserState state) {
		this.state = state;
	}

	public ParserState getLastState() {
		return lastState;
	}

	public void setLastState(ParserState lastState) {
		this.lastState = lastState;
	}	
	
	public void concatSelectorName(String name){
		if (!name.isEmpty())
			selectorName = name;
	}
	
	public void process(String cssHash){
		
		clearAll();
		
		String token;
		String hash = cssHash.replace(",", ", ");
		hash = hash.replace("}", " }");
		Scanner scanner = new Scanner(hash);
		
		while (scanner.hasNext()){
			token = scanner.next();
			state.processToken(token);
		}
			
	}
	
	private void clearAll(){
		typeSelectorQty = 0;
		classSelectorQty = 0;
		idSelectorQty = 0;
		selectorName = "";
		state = new SelectorName(this);
		lastState = new SelectorName(this);
	}

	public void analyseName(){
		if (selectorName.isEmpty())
			return;
		
		if (selectorName.contains("."))
			classSelectorQty++;
		else if (selectorName.contains("#"))
			idSelectorQty++;
		else
			typeSelectorQty++;
		
		selectorName = "";
	}
	
}
