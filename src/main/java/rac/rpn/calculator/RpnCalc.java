package rac.rpn.calculator;


import java.util.Stack;

import rac.rpn.exception.RPNCalculatorException;



/**
 * @author Rachana Sane
 *
 */
public class RpnCalc {
	
	private Stack<Double> valuesStack = new Stack<>();
    private Stack<String> undoStack = new Stack<>();
    private int pos = 0;

    
    /**
     * Handle parse exception
     * if could not parse double value, return null instead of exception.
     * @param str
     * @return
     */
    private Double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    /**
     *  Process input string
     * @param input
     * @throws RPNCalculatorException
     */
    private void processInputString(String input) throws RPNCalculatorException {
        Double value = parseDouble(input);
        if (value == null) { // this means it is operator
            processOperator(input);
        } else {           
          valuesStack.push(Double.parseDouble(input));       
        }
    }

    
   /**
    *  Take correct action as per operation provided
    * @param operatorString
    * @throws RPNCalculatorException
    */
    private void processOperator(String operatorString) throws RPNCalculatorException {

        // check if there is an empty stack
        if (valuesStack.isEmpty()) {
            throw new RPNCalculatorException("empty stack");
        }

        // searching for the operator
        Command operator = Command.getEnum(operatorString);
        
        if (operator == null) {
            throw new RPNCalculatorException("invalid operator");
        }

        // clear value stack and instructions stack
        if (operator == Command.CLEAR) {
            clearStacks();
            return;
        }

        // undo evaluates the last instruction in stack
        if (operator == Command.UNDO) {
            undoLastInstruction();        	
            return;
        }

        // Checking that there are enough operand for the operation
        if (operator.getNoOfOperands() > valuesStack.size()) {
            throwInvalidOperand(operatorString);
        }

        performOperation(operator);

    }

    /**
     * Perform arithmatic operation on operands
     * @param operator
     * @throws RPNCalculatorException
     */
	private void performOperation(Command command) throws RPNCalculatorException {
		// getting operands
        Double firstOperand = valuesStack.pop();
        Double secondOperand = (command.getNoOfOperands() > 1) ? valuesStack.pop() : null;
        
        backupValuesForUndo(command, firstOperand, secondOperand);
        
        // calculate
        Double result = command.calculate(firstOperand, secondOperand);

        if (result != null) {
            valuesStack.push(result);       
        }
	}


	/**
	 * maintain separate  stack for undo operation
	 * Backup 2 operands whenever you get operation string
	 * 
	 * @param operator
	 * @param firstOperand
	 * @param secondOperand
	 */
	private void backupValuesForUndo(Command operator, Double firstOperand, Double secondOperand) {
		undoStack.clear();        
        undoStack.push(firstOperand.toString());
        if(secondOperand !=null) {
        	undoStack.push(secondOperand.toString());
        }
        undoStack.push(operator.getSymbol());
	}

	/**
	 * perform undo operation
	 * 
	 * @throws RPNCalculatorException
	 */
  private void undoLastInstruction() throws RPNCalculatorException {
	  		valuesStack.pop();
	  		
	  		if (!undoStack.isEmpty()) {           	
	  			undoStack.pop(); // remove operation symbol
        	
        	StringBuilder newStr = new StringBuilder(); 
        	if(!undoStack.isEmpty()) {
        		newStr.append(undoStack.pop());
        		newStr.append(" ");
        	}
        	
        	
        	if(!undoStack.isEmpty()) {
        		newStr.append(undoStack.pop());        	
        	}        	
      
        	evaluate(newStr.toString());        
        }
    }

    private void clearStacks() {
        valuesStack.clear();
        undoStack.clear();
    }

    private void throwInvalidOperand(String operator) throws RPNCalculatorException {
        throw new RPNCalculatorException(
                String.format("operator %s (position: %d): insufficient parameters", operator, pos));
    }

    
    public Stack<Double> getValuesStack() {
        return valuesStack;
    }

   
    public Double getStackItem(int index) {
        return valuesStack.get(index);
    }

  
    /**
     *   evaluate input string
     * @param input
     * @throws RPNCalculatorException
     */
 
   public void evaluate(String input) throws RPNCalculatorException {
        if (input == null) {
            throw new RPNCalculatorException("Input cannot be null.");
        }
        pos = 0;
        String[] result = input.split("\\s");
        for (String aResult : result) {
            pos++;
            processInputString(aResult);
        }
    }
    
 

}
