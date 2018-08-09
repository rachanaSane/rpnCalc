package rac.rpn.calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rac.rpn.exception.RPNCalculatorException;

class RpnCalcTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testEvaluateExample1() throws RPNCalculatorException {
		RpnCalc calc = new RpnCalc();
		calc.evaluate("5 2");
		assertEquals(calc.getValuesStack().size(), 2);
		assertEquals(calc.getValuesStack().get(0), new Double(5));
		assertEquals(calc.getValuesStack().get(1), new Double(2));
	}
	
	@Test
	void testEvaluateExample2() throws RPNCalculatorException {
		RpnCalc calc = new RpnCalc();
		calc.evaluate("2 sqrt");
		assertEquals(calc.getValuesStack().size(), 1);
		assertEquals(calc.getValuesStack().get(0), new Double(1.4142135623730951));
		calc.evaluate("clear 9 sqrt");
		assertEquals(calc.getValuesStack().get(0), new Double(3));
		
	}
	
	@Test
	void testEvaluateExample3() throws RPNCalculatorException {
		RpnCalc calc = new RpnCalc();
		calc.evaluate("5 2 -");		
		assertEquals(calc.getValuesStack().get(0), new Double(3));
		calc.evaluate("3 -");
		assertEquals(calc.getValuesStack().get(0), new Double(0));
		calc.evaluate("clear");
		assertEquals(calc.getValuesStack().size(), 0);		
	}
	
	@Test
	void testEvaluateExample4() throws RPNCalculatorException {
		RpnCalc calc = new RpnCalc();
		calc.evaluate("5 4 3 2");		
		assertEquals(calc.getValuesStack().size(), 4);	
		calc.evaluate("undo undo *");
		assertEquals(calc.getValuesStack().get(0), new Double(20));
		calc.evaluate("5 *");
		assertEquals(calc.getValuesStack().get(0), new Double(100));
		calc.evaluate("undo");
		assertEquals(calc.getValuesStack().get(0), new Double(20));	
		assertEquals(calc.getValuesStack().get(1), new Double(5));	
	}
	
	@Test
	void testEvaluateExample5() throws RPNCalculatorException {
		RpnCalc calc = new RpnCalc();
		calc.evaluate("7 12 2 /");		
		assertEquals(calc.getValuesStack().size(), 2);	
		assertEquals(calc.getValuesStack().get(0), new Double(7));	
		assertEquals(calc.getValuesStack().get(1), new Double(6));	
		calc.evaluate("*");
		assertEquals(calc.getValuesStack().get(0), new Double(42));
		calc.evaluate("4 /");
		assertEquals(calc.getValuesStack().get(0), new Double(10.5));		
	}
	
	@Test
	void testEvaluateExample6() throws RPNCalculatorException {
		RpnCalc calc = new RpnCalc();
		calc.evaluate("1 2 3 4 5");		
		assertEquals(calc.getValuesStack().size(), 5);	
		calc.evaluate("*");
		assertEquals(calc.getValuesStack().get(0), new Double(1));	
		assertEquals(calc.getValuesStack().get(1), new Double(2));	
		assertEquals(calc.getValuesStack().get(2), new Double(3));	
		assertEquals(calc.getValuesStack().get(3), new Double(20));	
		
		calc.evaluate("clear 3 4 -");
		
		assertEquals(calc.getValuesStack().get(0), new Double(-1));
			
	}
	
	@Test
	void testEvaluateExample7() throws RPNCalculatorException {
		RpnCalc calc = new RpnCalc();
		calc.evaluate("1 2 3 4 5");		
		assertEquals(calc.getValuesStack().size(), 5);	
		calc.evaluate("* * * *");
		assertEquals(calc.getValuesStack().get(0), new Double(120));			
			
	}

	
	@Test
	void testEvaluateExample8() {
		RpnCalc calc = new RpnCalc();
		try {
			calc.evaluate("1 2 3 * 5 + * * 6 5");
		} catch (RPNCalculatorException e) {
			assertEquals("operator * (position: 8): insufficient parameters" , e.getMessage());		
		}				
			
	}


}
