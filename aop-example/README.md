This project is show how to write AOP.
1. Weaving using aop.xml
Lets say you are using log4j in legacy code and want to switch SLF4j. In your legacy code, you may have many log4j.xml files and they may have debug as default log level. This aspect will change log level to info