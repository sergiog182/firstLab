package com.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class TestProcessor implements Processor{
	final static Logger log = Logger.getLogger(TestProcessor.class);
	
	@Override
	public void process(Exchange ex) throws Exception {
		String test = ex.getIn().getHeader("test", String.class);
		if (test.equals("true")) {
			ex.getIn().setHeader("skipOrder", "Y");
		} else {
			ex.getIn().setHeader("skipOrder", "N");
		}
	}

}
