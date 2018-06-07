package com.abhsinh2.aop.aspects;

import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jLogger {
    public Log4jLogger() {
        URL url = Log4jLogger.class.getClassLoader().getResource("test_log4j.xml");
        DOMConfigurator.configure(url);
    }
    
    public Logger getABCLogger() {
        return LogManager.getLogger("com.abhsinh2.abc.test");
    }
    
    public Logger getXYZLogger() {
        return LogManager.getLogger("com.abhsinh2.xyz.test");
    }
}
