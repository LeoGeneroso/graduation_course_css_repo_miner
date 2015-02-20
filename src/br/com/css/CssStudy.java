package br.com.css;

import br.com.metricminer2.MMOptions;
import br.com.metricminer2.Study;
import br.com.metricminer2.persistence.csv.CSVFile;
import br.com.metricminer2.scm.SourceCodeRepositoryNavigator;

public class CssStudy implements Study {

	
	@Override
	public void execute(MMOptions opts) {
		String defaultPath = opts.getCsv();
		
		CSVFile file = new CSVFile(defaultPath, "SelectorCount.csv");
		
		file.write(
				"Data do commit",
				"Arquivo",
				"Seletores de Tipo",
				"Seletores de Classe",
				"Seletores de ID"
			);
		
		new SourceCodeRepositoryNavigator(opts)
			.projectsFromConfig()
			.process(new SelectorCounter(), file)
			.start();		
	}
	
	
}
