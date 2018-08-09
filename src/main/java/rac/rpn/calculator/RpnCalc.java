package rac.rpn.calculator;

import java.util.Arrays;
import java.util.Stack;

public class RpnCalc {
	
	private Stack<Double> valuesStack = new Stack<Double>();
    private Stack<String> instructionsStack = new Stack<String>();
    private int currentTokenIndex = 0;

    private Double tryParseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    /**
     * Processes a RPN string token
     *
     * @param token           RPN token
     * @param isUndoOperation indicates if the operation is an undo operation.
     * @throws CalculatorException
     */
    private void processToken(String token) throws Exception {
        Double value = tryParseDouble(token);
        if (value == null) {
            processOperator(token);
        } else {
            // it's a digit
          valuesStack.push(Double.parseDouble(token));
       
        }
    }

    /**
     * Executes an operation on the stack
     *
     * @param operatorString  RPN valid operator
     * @param isUndoOperation indicates if the operation is an undo operation.
     * @throws CalculatorException
     */
    private void processOperator(String operatorString) throws Exception {

        // check if there is an empty stack
        if (valuesStack.isEmpty()) {
            throw new Exception("empty stack");
        }

        // searching for the operator
        Operation operator = Operation.getEnum(operatorString);
        if (operator == null) {
            throw new Exception("invalid operator");
        }

        // clear value stack and instructions stack
        if (operator == Operation.CLEAR) {
            clearStacks();
            return;
        }

        // undo evaluates the last instruction in stack
        if (operator == Operation.UNDO) {
            undoLastInstruction();
        	
            return;
        }

        // Checking that there are enough operand for the operation
        if (operator.getOperandsNumber() > valuesStack.size()) {
            throwInvalidOperand(operatorString);
        }

        // getting operands
        Double firstOperand = valuesStack.pop();
        Double secondOperand = (operator.getOperandsNumber() > 1) ? valuesStack.pop() : null;
        
        instructionsStack.clear();        
        instructionsStack.push(firstOperand.toString());
        if(secondOperand !=null) {
        	 instructionsStack.push(secondOperand.toString());
        }
        instructionsStack.push(operator.getSymbol());
        
        // calculate
        Double result = operator.calculate(firstOperand, secondOperand);

        if (result != null) {
            valuesStack.push(result);       
        }

    }

  private void undoLastInstruction() throws Exception {
	  		valuesStack.pop();
	  		
	  		if (!instructionsStack.isEmpty()) {           	
        	instructionsStack.pop(); // remove operation symbol
        	
        	StringBuffer newStr = new StringBuffer(); 
        	if(!instructionsStack.isEmpty()) {
        		newStr.append(instructionsStack.pop());
        		newStr.append(" ");
        	}
        	
        	
        	if(!instructionsStack.isEmpty()) {
        		newStr.append(instructionsStack.pop());
        		//newStr.append(" ");
        	}
        	
        //	String val2 =instructionsStack.pop();
        //	StringBuffer newStr = new StringBuffer();
        	//newStr.append(val1).append(" ");
        //	if(val2 !=null) {
        //		newStr.append(val2);
        //	}
        //	evaluate(lastInstruction.getReverseInstruction(), true);
        	evaluate(newStr.toString());
        }
    }

    private void clearStacks() {
        valuesStack.clear();
        instructionsStack.clear();
    }

    private void throwInvalidOperand(String operator) throws Exception {
        throw new Exception(
                String.format("operator %s (position: %d): insufficient parameters", operator, currentTokenIndex));
    }

    /**
     * Returns the values valuesStack
     */
    public Stack<Double> getValuesStack() {
        return valuesStack;
    }

    /**
     * Helper method to return a specific item in the valuesStack
     *
     * @param index index of the element to return
     */
    public Double getStackItem(int index) {
        return valuesStack.get(index);
    }

  

    /**
     * Evaluates a RPN expression and pushes the result into the valuesStack
     *
     * @param input           valid RPN expression
     * @param isUndoOperation indicates if the operation is an undo operation.
     *                        undo operations use the same evaluation functions as the standard ones
     *                        but they are not pushed into instructionsStack
     * @throws CalculatorException
     */
    public void evaluate(String input) throws Exception {
        if (input == null) {
            throw new Exception("Input cannot be null.");
        }
        currentTokenIndex = 0;
        String[] result = input.split("\\s");
        for (String aResult : result) {
            currentTokenIndex++;
            processToken(aResult);
        }
    }
    
    private void evaluateLambada(String input) throws Exception {
        if (input == null) {
            throw new Exception("Input cannot be null.");
        }
        Arrays.asList(input.split(" ")).stream().forEach(number -> {
        	try {
				processToken(number);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
        });
    }

}
