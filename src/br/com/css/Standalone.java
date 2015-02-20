package br.com.css;

import br.com.metricminer2.MMOptions;

public class Standalone {

	public static void main(String[] args) {
		MMOptions opts = new MMOptions();
		opts.setScm("git");
		opts.setProjectPath("D:\\workspace\\css-test");
		opts.setCsv("D:\\workspace\\zTeste\\");
		opts.setSysOut(true);
		new CssStudy().execute(opts);
	}
}
