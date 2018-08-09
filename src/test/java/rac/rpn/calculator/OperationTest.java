package rac.rpn.calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rac.rpn.exception.RPNCalculatorException;

class CommandTest {

	@BeforeEach
	void setUp() throws Exception {
		
	}
	

	@Test
	void testCalculate() throws RPNCalculatorException {
		Double op1 = new Double(10);
		Double op2 = new Double(2);
		Double op3 = new Double(25);
		assertEquals(new Double(12) ,Command.ADDITION.calculate(op1, op2));	
		assertEquals(new Double(8) ,Command.SUBTRACTION.calculate(op2, op1));
		assertEquals(new Double(20) ,Command.MULTIPLICATION.calculate(op2, op1));
		assertEquals(new Double(5) ,Command.DIVISION.calculate(op2, op1));
		assertEquals(new Double(5) ,Command.SQUAREROOT.calculate(op3, op1));
	}

	
	@Test
	void testCalculateUndo()  {
		Double op1 = new Double(10);
		Double op2 = new Double(2);
		try {
			Command.UNDO.calculate(op2, op1);
		} catch (RPNCalculatorException e) {
			assertEquals("Invalid operation" ,e.getMessage());
		}
	}
	

}
