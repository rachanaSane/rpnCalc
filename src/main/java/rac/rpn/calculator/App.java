package rac.rpn.calculator;

import java.io.Console;
import java.text.DecimalFormat;
import java.util.Stack;

/**
 * 
 * @author Rachana Sane
 *
 */
public class App 
{
	public static void main(String[] args) {

		 Console console = System.console();
	        if (console == null) {
	            System.err.println("No console.");
	            System.exit(1);
	        }

	        RpnCalc calc = new RpnCalc();

	        System.out.println("Enter your expression OR type 'ex' to exit");

	        boolean keepRunning = true;
	        while (keepRunning) {
	            String inputString = console.readLine(" ");
	            if ("ex".equals(inputString)) {
	                keepRunning = false;
	            } else {
	                try {
	                	calc.evaluate(inputString);
	                } catch (Exception e) {
	                    System.out.println(e.getMessage());
	                }

	                DecimalFormat fmt = new DecimalFormat("0.##########");
	                Stack<Double> stack = calc.getValuesStack();
	                System.out.print("Stack: ");
	                for (Double value : stack) {
	                    System.out.print(fmt.format(value));
	                    System.out.print(" ");
	                }
	                System.out.printf("%n");
	            }
	        }
	    }
}
