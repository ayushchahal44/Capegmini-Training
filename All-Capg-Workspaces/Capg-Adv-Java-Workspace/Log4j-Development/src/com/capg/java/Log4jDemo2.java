package com.capg.java;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Log4jDemo2{
	
	
	final static Logger logger = Logger.getLogger(Log4jDemo2.class);
	public static void main(String[] args) {
		//if you want print console then use BasicConfigurator.configure();
				BasicConfigurator.configure();
		BasicConfigurator.configure();
		Log4jDemo2 mylog = new Log4jDemo2();
		mylog.runMe("Jai");
		
	}
	
	private void runMe(String parameter){
		
		if(logger.isDebugEnabled()){
			logger.debug("This is debug : " + parameter);
		}
		
		if(logger.isInfoEnabled()){
			logger.info("This is info : " + parameter);
		}
		
		logger.warn("This is warn : " + parameter);
		logger.error("This is error : " + parameter);
		logger.fatal("This is fatal : " + parameter);
		
	}
	
}