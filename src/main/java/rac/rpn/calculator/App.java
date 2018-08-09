package rac.rpn.calculator;

import java.io.Console;
import java.text.DecimalFormat;
import java.util.Stack;

/**
 * RPN calculator application
 * @author Rachana Sane
 *
 */
public class App 
{
	
	public static final DecimalFormat tenDecimalFmt = new DecimalFormat("0.##########");
	
	public static void main(String[] args) {

		 Console console = System.console();
	        if (console == null) {
	            System.err.println("No console.");
	            System.exit(1);
	        }

	        RpnCalc calc = new RpnCalc();

	        System.out.println("Enter your expression OR type 'ex' to exit");

	        boolean executing = true;
	        while (executing) {
	            String inputString = console.readLine(" ");
	            if ("ex".equals(inputString)) {
	            	executing = false;
	            } else {
	                runCalculator(calc, inputString);
	            }
	        }
	    }

	/**
	 *  Start running calculator with provided input.
	 * @param calc
	 * @param inputString
	 */
	private static void runCalculator(RpnCalc calc, String inputString) {
		try {
			calc.evaluate(inputString);		
		} catch (Exception e) {
		    System.out.println(e.getMessage());
		}

		
		Stack<Double> stack = calc.getValuesStack();
		System.out.print("Stack: ");
		for (Double value : stack) {
		    System.out.print(tenDecimalFmt.format(value));
		    System.out.print(" ");
		}
		System.out.printf("%n");
	}
}
