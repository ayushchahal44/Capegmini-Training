package com.capg.java;
import junit.framework.*;

public class CalculationTest extends TestCase{
	Calculation calculation;
	protected void setUp() {
		calculation = new Calculation();
	}
	public void testAdd() {
		int result = calculation.addition(2,3);
		assertEquals(5,result);
	}
	protected void tearDown() {
		calculation = null;
	}
}
