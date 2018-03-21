package com.abhsinh2.calculator;

import java.util.List;

public class GenericExample {
	public <T extends First> List<T> someMethod(String namespace, Class<T> clazz ) {
		List<T> deviceConfigStatusList = null;
		return deviceConfigStatusList;
    }
	
	class First {
		
	}
	
	class Second {
		
	}
	
	class Third extends First {
		
	}
	
	class Fourth extends Second {
		
	}
	
	public static void main(String args[]) {
		GenericExample ex = new GenericExample();
		ex.someMethod("", First.class);
		ex.someMethod("", Third.class);
	}

}
