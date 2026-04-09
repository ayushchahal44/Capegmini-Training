package com.capg.java;
import junit.framework.TestCase;
import com.capg.java.Calculation;

public class CalculationTest extends TestCase{
	public void testAdd() {
		Calculation cal1 = new Calculation();
		int result = cal1.addition(2,3);
		assertEquals(5,result);
		assertEquals(10,new Calculation().subtraction(15,5));
		assertEquals(4,new Calculation().multiplication(2,2));
	}
}
