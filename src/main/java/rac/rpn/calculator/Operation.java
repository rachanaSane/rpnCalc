package rac.rpn.calculator;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public enum Operation {
	


	    ADDITION("+", "-", 2) {
	        public Double calculate(Double firstOperand, Double secondOperand) throws Exception {
	            return secondOperand + firstOperand;
	        }
	    },

	    SUBTRACTION("-", "+", 2) {
	        public Double calculate(Double firstOperand, Double secondOperand) {
	            return secondOperand - firstOperand;
	        }
	    },

	    MULTIPLICATION("*", "/", 2) {
	        public Double calculate(Double firstOperand, Double secondOperand) {
	            return secondOperand * firstOperand;
	        }
	    },

	    DIVISION("/", "*", 2) {
	        public Double calculate(Double firstOperand, Double secondOperand) throws Exception {
	            if (firstOperand == 0)
	                throw new Exception("Cannot divide by 0.");
	            return secondOperand / firstOperand;
	        }
	    },

	    SQUAREROOT("sqrt", "pow", 1) {
	        public Double calculate(Double firstOperand, Double secondOperand) {
	            return sqrt(firstOperand);
	        }
	    },

	    POWER("pow", "sqrt", 1) {
	        public Double calculate(Double firstOperand, Double secondOperand) {
	            return pow(firstOperand, 2.0);
	        }
	    },

	    UNDO("undo", null, 0) {
	        public Double calculate(Double firstOperand, Double secondOperand) throws Exception {
	            throw new Exception("Invalid operation");
	        }
	    },

	    CLEAR("clear", null, 0) {
	        public Double calculate(Double firstOperand, Double secondOperand) throws Exception {
	            throw new Exception("Invalid operation");
	        }
	    };
	
	
	
	    // using map for a constant lookup cost
	    private static final Map<String, Operation> operationFactory = new HashMap<String, Operation>();

	    static {
	        for (Operation o : values()) {
	        	operationFactory.put(o.getSymbol(), o);
	        }
	    }

	    private String symbol;
	    private String opposite;
	    private int operandsNumber;

	    Operation(String symbol, String opposite, int operandsNumber) {
	        this.symbol = symbol;
	        this.opposite = opposite;
	        this.operandsNumber = operandsNumber;
	    }

	    public static Operation getEnum(String value) {
	        return operationFactory.get(value);
	    }

	    public abstract Double calculate(Double firstOperand, Double secondOperand) throws Exception;

	    public String getSymbol() {
	        return symbol;
	    }

	    public String getOpposite() {
	        return opposite;
	    }

	    public int getOperandsNumber() {
	        return operandsNumber;
	    }

	    @Override
	    public String toString() {
	        return symbol;
	    }


}
