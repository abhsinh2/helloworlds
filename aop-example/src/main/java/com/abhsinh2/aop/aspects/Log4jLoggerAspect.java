package com.abhsinh2.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This Aspect is to switch off default log level, which is debug, in Log4j.
 * It switch off three places:
 * 
 * 1. <log4j:configuration debug="true"> // prints Log4j internal debug logs
 * 
 * 2. <logger name="">
 *        <level value="debug">
 *    </logger>
 *    
 * 3. <root> 
        <level value="debug" /> 
      </root>
 *
 */
@Aspect
public class Log4jLoggerAspect {
    private static final Logger LOG = LoggerFactory.getLogger(Log4jLoggerAspect.class);

    private static final String CONFIGURATION_TAG = "log4j:configuration";
    private static final String INTERNAL_DEBUG_ATTR = "debug";
    private static final String TAG_LOGGER = "logger";
    private static final String TAG_ROOT = "root";

    private static final String ATTR_NAME = "name";
    private static final String ATTR_VALUE = "value";
    private static final String LOG_LEVEL_INFO = "info";

    private static final String LOGGER_PREFIX = "com.abhsinh2.abc"; // only switching off for logger with this prefix 

    @Pointcut("execution(* org.apache.log4j.xml.DOMConfigurator.parseLevel(org.w3c.dom.Element,org.apache.log4j.Logger,boolean))")
    //@Pointcut("execution(* org.apache.log4j.xml.DOMConfigurator.parseLevel(..))")
    public void makeLoggerLevelInfo() {
    }

    @Pointcut("execution(* org.apache.log4j.xml.DOMConfigurator.parse(org.w3c.dom.Element))")
    public void disableDebugForConfiguration() {
    }

    @Around("makeLoggerLevelInfo()")
    public Object parseLogLevelElement(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = joinPoint.getArgs()[0];
        if (obj instanceof Element) {
            Element el = (Element) obj;
            Node parentNode = el.getParentNode();

            if (parentNode.getNodeName().equals(TAG_LOGGER)) { // <logger>
                Element loggerElement = (Element) parentNode;
                String loggerName = loggerElement.getAttribute(ATTR_NAME);

                if (loggerName.startsWith(LOGGER_PREFIX)) {
                    LOG.info("Changing log level from {} to INFO for logger {}.", el.getAttribute(ATTR_VALUE),
                            loggerName);
                    el.setAttribute(ATTR_VALUE, LOG_LEVEL_INFO);
                }
            } else if (parentNode.getNodeName().equals(TAG_ROOT)) { // <root>
                LOG.info("Changing root level from {} to INFO.", el.getAttribute(ATTR_VALUE));
                el.setAttribute(ATTR_VALUE, LOG_LEVEL_INFO);
            }
        }
        return joinPoint.proceed();
    }

    @Around("disableDebugForConfiguration()")
    public Object parseLog4jConfigurationElement(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = joinPoint.getArgs()[0];
        if (obj instanceof Element) {
            Element el = (Element) obj;
            if (el.getNodeName().equals(CONFIGURATION_TAG)) {
                if (el.hasAttribute(INTERNAL_DEBUG_ATTR)) {
                    LOG.info("Changing debug to false for log4j:configuration.");
                    el.setAttribute(INTERNAL_DEBUG_ATTR, "false");
                }                
            }
        }
        return joinPoint.proceed();
    }
}
