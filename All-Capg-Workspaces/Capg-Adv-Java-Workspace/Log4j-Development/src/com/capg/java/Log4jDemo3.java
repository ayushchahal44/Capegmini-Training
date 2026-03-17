package com.capg.java;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Log4jDemo3{
	
	final static Logger logger = Logger.getLogger(Log4jDemo3.class);
	
	public static void main(String[] args) {
		//if you want print console then use BasicConfigurator.configure();
		BasicConfigurator.configure();
	
		Log4jDemo3 myobj = new Log4jDemo3();
		
		try{
			logger.info("This is info :I am in side try  " );
			myobj.divide();
			
			
		}catch(ArithmeticException ex){
			logger.error("Sorry, something wrong!", ex);
		}
		
		
	}
	
	private void divide(){
		
		int i = 10 /2;

	}
	
}