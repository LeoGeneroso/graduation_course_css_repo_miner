package br.com.css;

import java.text.SimpleDateFormat;

import br.com.css.parser.CssParser;
import br.com.metricminer2.domain.Commit;
import br.com.metricminer2.domain.Modification;
import br.com.metricminer2.persistence.PersistenceMechanism;
import br.com.metricminer2.scm.CommitVisitor;
import br.com.metricminer2.scm.SCMRepository;

public class SelectorCounter implements CommitVisitor {

	private int commitCount = 0;
	private int cssCommitCount = 0;
	
	private boolean hasACssFile(Commit c){
		
		for (Modification m : c.getModifications()) {
			if (isCss(m) /*&& !m.wasDeleted()*/)
				return true;
		}

		return false;
		
	}
	
	private boolean isCss(Modification m) {
		if (m.wasDeleted())
			return m.getOldPath().toLowerCase().endsWith(".css") || m.getOldPath().toLowerCase().endsWith(".less");
		else
			return m.getNewPath().toLowerCase().endsWith(".css") || m.getNewPath().toLowerCase().endsWith(".less");
	}
	
	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		
		commitCount++;
		
		if (hasACssFile(commit)){
			
			cssCommitCount++;
			
			int typeSelectorQty;
			int classSelectorQty;
			int idSelectorQty;
			String name;
			
			CssParser parser = new CssParser();
			
			for (Modification m : commit.getModifications()) {
				if (isCss(m)){
					
					if (m.wasDeleted()){
						typeSelectorQty = 0;
						classSelectorQty = 0;
						idSelectorQty = 0;
						name = m.getOldPath() + " (removed)";
					}
					else{
						parser.process(m.getSourceCode());
						
						typeSelectorQty = parser.getTypeSelectorQty();
						classSelectorQty = parser.getClassSelectorQty();
						idSelectorQty = parser.getIdSelectorQty();
						name = m.getNewPath();
					}
					
					writer.write(
						new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(commit.getDate().getTime()),
						name,
						typeSelectorQty,
						classSelectorQty,
						idSelectorQty						
					);
				}				
			}

		}
		
	}
	
	@Override
	public String name() {
		return "CSS selectors count";
	}
		
}
