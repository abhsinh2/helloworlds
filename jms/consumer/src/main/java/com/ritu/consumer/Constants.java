package com.ritu.consumer;

public class Constants {
	// Defines the JNDI context factory.
	public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	
	// Defines the JMS connection factory for the queue.
	public final static String JMS_FACTORY = "jms/TestConnectionFactory";
	
	// Defines the queue.
	public final static String QUEUE = "jms/TestJMSQueue";
	
	public final static String WEBLOGIC_URL = "t3://localhost:7001";
}
