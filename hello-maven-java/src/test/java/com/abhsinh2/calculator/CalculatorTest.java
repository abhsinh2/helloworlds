package com.abhsinh2.calculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
	private Calculator calc;
	
	@Before
	public void beforeTest() {
		calc = new Calculator();
	}
	
	@Test
	public void testAddition() {
		Assert.assertEquals(10, calc.add(5, 5));
		Assert.assertNotEquals(11, calc.add(5, 5));
	}
}
