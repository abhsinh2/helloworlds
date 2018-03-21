package com.ritu.consumer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyAppServletContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("ServletContextListener destroyed");
	}

	public void contextInitialized(ServletContextEvent event) {
		System.out.println("ServletContextListener starting");
		
		Runnable myRunnable  = new Runnable() {			
			public void run() {
				System.out.println("Starting Thread");
				try {
					QueueConsumer.main(null);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e);
				}
				System.out.println("Stopping Thread");
			}
		};
		
		Thread th = new Thread(myRunnable);
		th.start();		
		
		System.out.println("ServletContextListener started");
	}
}
