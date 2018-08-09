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
    private void processToken(String token, boolean isUndoOperation) throws Exception {
        Double value = tryParseDouble(token);
        if (value == null) {
            processOperator(token, isUndoOperation);
        } else {
            // it's a digit
            valuesStack.push(Double.parseDouble(token));
        /*    if (!isUndoOperation) {
                instructionsStack.push(null);
            }*/
        }
    }

    /**
     * Executes an operation on the stack
     *
     * @param operatorString  RPN valid operator
     * @param isUndoOperation indicates if the operation is an undo operation.
     * @throws CalculatorException
     */
    private void processOperator(String operatorString, boolean isUndoOperation) throws Exception {

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
        /*    if (!isUndoOperation) {
                instructionsStack.push(new Command(Operation.getEnum(operatorString), firstOperand));
            }*/
        }

    }

  private void undoLastInstruction() throws Exception {
    /*    if (instructionsStack.isEmpty()) {
            throw new Exception("no operations to undo");
        }

        String lastInstruction = instructionsStack.pop();*/
        if (instructionsStack.isEmpty()) {
            valuesStack.pop();
        } else {
        	 valuesStack.pop();
        	String operationStr=instructionsStack.pop();
        	String val1 =instructionsStack.pop();
        	String val2 =instructionsStack.pop();
        	StringBuffer newStr = new StringBuffer();
        	newStr.append(val1).append(" ");
        	if(val2 !=null) {
        		newStr.append(val2);
        	}
        //	evaluate(lastInstruction.getReverseInstruction(), true);
        	evaluate(newStr.toString(), false);
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
     * evaluates input expression and pushes the result into the valuesStack
     *
     * @param input valid RPN expression
     * @throws CalculatorException
     */
    public void evaluate(String input) throws Exception {
    	evaluate(input, false);
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
    private void evaluate(String input, boolean isUndoOperation) throws Exception {
        if (input == null) {
            throw new Exception("Input cannot be null.");
        }
        currentTokenIndex = 0;
        String[] result = input.split("\\s");
        for (String aResult : result) {
            currentTokenIndex++;
            processToken(aResult, isUndoOperation);
        }
    }
    
    private void evaluateLambada(String input, boolean isUndoOperation) throws Exception {
        if (input == null) {
            throw new Exception("Input cannot be null.");
        }
        Arrays.asList(input.split(" ")).stream().forEach(number -> {
        	try {
				processToken(number, isUndoOperation);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
        });
    }

}
