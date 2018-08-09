package rac.rpn.calculator;
import java.util.HashMap;
import java.util.Map;

import rac.rpn.exception.RPNCalculatorException;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * 
 * @author Rachana Sane
 *
 */
public enum Command {	


	    ADDITION("+",  2) {
	        public Double calculate(Double firstOperand, Double secondOperand) throws RPNCalculatorException {
	            return secondOperand + firstOperand;
	        }
	    },

	    SUBTRACTION("-",  2) {
	        public Double calculate(Double firstOperand, Double secondOperand) {
	            return secondOperand - firstOperand;
	        }
	    },

	    MULTIPLICATION("*", 2) {
	        public Double calculate(Double firstOperand, Double secondOperand) {
	            return secondOperand * firstOperand;
	        }
	    },

	    DIVISION("/",  2) {
	        public Double calculate(Double firstOperand, Double secondOperand) throws RPNCalculatorException {
	            if (firstOperand == 0)
	                throw new RPNCalculatorException("Cannot divide by 0.");
	            return secondOperand / firstOperand;
	        }
	    },

	    SQUAREROOT("sqrt",  1) {
	        public Double calculate(Double firstOperand, Double secondOperand) {
	            return sqrt(firstOperand);
	        }
	    },

	  
	    UNDO("undo",  0) {
	        public Double calculate(Double firstOperand, Double secondOperand) throws RPNCalculatorException {
	            throw new RPNCalculatorException("Invalid operation");
	        }
	    },

	    CLEAR("clear",  0) {
	        public Double calculate(Double firstOperand, Double secondOperand) throws RPNCalculatorException {
	            throw new RPNCalculatorException("Invalid operation");
	        }
	    };
	
	
	    private static final Map<String, Command> commandFactory = new HashMap<>();

	    static {
	        for (Command o : values()) {
	        	commandFactory.put(o.getSymbol(), o);
	        }
	    }

	    private String symbol;	
	    private int noOfOperands;

	    Command(String symbol,int noOfOperands) {
	        this.symbol = symbol;	 
	        this.noOfOperands = noOfOperands;
	    }

	    public static Command getEnum(String value) {
	        return commandFactory.get(value);
	    }

	    public abstract Double calculate(Double firstOperand, Double secondOperand) throws RPNCalculatorException;

	    public String getSymbol() {
	        return symbol;
	    }

	  

	    public int getNoOfOperands() {
	        return noOfOperands;
	    }

	    @Override
	    public String toString() {
	        return symbol;
	    }


}
