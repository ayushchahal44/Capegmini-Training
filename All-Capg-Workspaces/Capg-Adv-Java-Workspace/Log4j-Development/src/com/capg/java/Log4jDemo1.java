package com.capg.java;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Log4jDemo1{
	
	public static void main(String[] args) {
		 Logger logger = Logger.getLogger(Log4jDemo1.class);
		//if you want print console then use BasicConfigurator.configure();
			BasicConfigurator.configure();
		 BasicConfigurator.configure();
		      logger.info("Info....");
		      logger.warn("This is warn : " );
		      logger.error("This is error : ");
		      logger.fatal("This is fatal : " );
		  
	
		
	}
	
	
}