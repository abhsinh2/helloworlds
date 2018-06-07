package com.abhsinh2.aop.aspects;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class Log4jLoggerAspectTest extends AbstractTestNGSpringContextTests {
    private static final Logger LOG = LoggerFactory.getLogger(Log4jLoggerAspectTest.class);
    
    private static final String ABC_LOG_FILE = "abc.log";
    private static final String XYZ_LOG_FILE = "xyz.log";    
    
    @Configuration
    public static class ContextConfiguration {
        @Bean
        Log4jLogger log4jLogger() {
            return new Log4jLogger();
        }
    }
    
    @Autowired
    Log4jLogger loadLog4j;
        
    @BeforeTest
    public void setup() {
        //deleteLogs();
    }
    
    @AfterTest
    public void tearDown() {
        deleteLogs();
    }

    @Test
    public void testPositive() throws Throwable {        
        org.apache.log4j.Logger abclogger = loadLog4j.getABCLogger();
                
        abclogger.debug("123");
        abclogger.info("hello");
        
        List<String> contents = IOUtils.readLines(new FileInputStream(new File(ABC_LOG_FILE)));
        Assert.assertEquals(contents.size(), 1);
        Assert.assertTrue(contents.get(0).contains("hello"));
        
        org.apache.log4j.Logger xyzlogger = loadLog4j.getXYZLogger();
        
        xyzlogger.debug("123");
        xyzlogger.info("hello");
        
        contents = IOUtils.readLines(new FileInputStream(new File(XYZ_LOG_FILE)));
        Assert.assertEquals(contents.size(), 2);
        Assert.assertTrue(contents.get(0).contains("123"));
        Assert.assertTrue(contents.get(1).contains("hello"));
    }
    
    private void deleteLogs() {
        File logFile = new File(ABC_LOG_FILE);
        if (logFile.exists()) {
            LOG.info("Deleting {}", ABC_LOG_FILE);
            logFile.delete();
        }
        
        logFile = new File(XYZ_LOG_FILE);
        if (logFile.exists()) {
            LOG.info("Deleting {}", XYZ_LOG_FILE);
            logFile.delete();
        }
    }
    
}
