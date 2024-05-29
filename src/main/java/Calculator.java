import java.util.ArrayList;
import java.util.Arrays;

public class Calculator {
    private String[] expression;
    private int skipCount = 0;

    public double calculate(String exp) throws ArithmeticException {
        expression = splitExpression(exp);
        double answer = Double.parseDouble(calculateInParentheses(expression, 0));

        if (answer == Double.NEGATIVE_INFINITY || answer == Double.POSITIVE_INFINITY) {
            throw new ArithmeticException("Division by zero");
        }
        return Double.parseDouble(calculateInParentheses(expression, 0));
    }

    /* Преобразовать выражение в массив */
    private String[] splitExpression(String exp) {
        StringBuilder result = new StringBuilder();
        exp = exp.replaceAll(" ", "");

        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '+' || exp.charAt(i) == '-' || exp.charAt(i) == '*' || exp.charAt(i) == '/') {
                result.append(" ");
                result.append(exp.charAt(i));
                result.append(" ");
            } else if (exp.charAt(i) == '(') {
                result.append(exp.charAt(i));
                result.append(" ");
            } else if (exp.charAt(i) == ')') {
                result.append(" ");
                result.append(exp.charAt(i));
            } else {
                result.append(exp.charAt(i));
            }
        }
        return result.toString().split(" ");
    }

    /* Выполнить указанную операцию над двумя операндами, представленными строкой */
    private String getResult(String left, String right, String operator) {
        return switch (operator) {
            case "*" -> Double.toString(Double.parseDouble(left) * Double.parseDouble(right));
            case "/" -> Double.toString(Double.parseDouble(left) / Double.parseDouble(right));
            case "+" -> Double.toString(Double.parseDouble(left) + Double.parseDouble(right));
            case "-" -> Double.toString(Double.parseDouble(left) - Double.parseDouble(right));
            default -> throw new IllegalArgumentException();
        };
    }

    /* Вычисление выражения в скобках */
    private String calculateInParentheses(String[] exp, int countIndex) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = countIndex; i < exp.length; i++) {
            if (exp[i].equals("(") && skipCount != 0) {
                skipCount++;
            } else if (exp[i].equals("(")) {
                result.add(calculateInParentheses(exp, i + 1));
                skipCount++;
            } else if (exp[i].equals(")") && skipCount != 0) {
                skipCount--;
            } else if (exp[i].equals(")")) {
                return calculateUsualOperation(result.toArray(new String[0]));
            } else if (skipCount != 0) {
                continue;
            } else {
                result.add(exp[i]);
            }
        }
        return calculateUsualOperation(result.toArray(new String[0]));
    }

    /* Вычисляем операции умножения/деления и сложения/вычитания */
    private String calculateUsualOperation(String[] exp) {
        ArrayList<String> result = new ArrayList<>();
        int skipIndex = -1;

        // Вычисляем операции умножения и деления
        for (int i = 0, j = 0; i < exp.length; i++) {
            if (i == skipIndex) {
                continue;
            } else if (exp[i].equals("*")) {
                result.set(
                        j - 1, getResult(result.get(j - 1), exp[i + 1], "*")
                );
                skipIndex = i + 1;
            } else if (exp[i].equals("/")) {
                result.set(
                        j - 1, getResult(result.get(j - 1), exp[i + 1], "/")
                );
                skipIndex = i + 1;
            } else {
                result.add(exp[i]);
                j++;
            }

        }

        // Вычисляем операции сложения и вычитания
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).equals("+")) {
                result.set(
                        i - 1, getResult(result.get(i - 1), result.get(i + 1), "+")
                );
                result.remove(i);
                result.remove(i);
                i--;
            } else if (result.get(i).equals("-")) {
                result.set(
                        i - 1, getResult(result.get(i - 1), result.get(i + 1), "-")
                );
                result.remove(i);
                result.remove(i);
                i--;
            }
        }

        return result.getFirst();
    }
}
