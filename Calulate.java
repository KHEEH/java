import java.io.*;
import java.net.*;

public class Calulate implements Runnable {
    private final Socket clientSocket;

    public Calulate (Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            String expression = reader.readLine();
            System.out.println("Received expression: " + expression);

            // Evaluate the expression using a simple method
            double result = evaluateExpression(expression);

            // Send the result back to the client
            writer.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double evaluateExpression(String expression) {
        try {
            // Find the operator (+, -, *, /) and split the expression
            char operator = findOperator(expression);
            String[] parts = expression.split("\\" + operator);

            // Perform the calculation based on the operator
            double operand1 = Double.parseDouble(parts[0].trim());
            double operand2 = Double.parseDouble(parts[1].trim());

            switch (operator) {
                case '+':
                    return operand1 + operand2;
                case '-':
                    return operand1 - operand2;
                case '*':
                    return operand1 * operand2;
                case '/':
                    if (operand2 != 0) {
                        return operand1 / operand2;
                    } else {
                        System.err.println("Division by zero error");
                        return 0.0;
                    }
                default:
                    System.err.println("Invalid operator: " + operator);
                    return 0.0;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Handle invalid expressions with an error message
            System.err.println("Expression evaluation error: " + e.getMessage());
            return 0.0;  // Return a default value (0.0) in case of an error
        }
    }

    private char findOperator(String expression) {
        for (char operator : "+-*/".toCharArray()) {
            if (expression.indexOf(operator) != -1) {
                return operator;
            }
        }
        return '+';
    }
}
