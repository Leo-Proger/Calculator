import java.util.ArrayList;
import java.util.Arrays;

public class Calculator {
    private String[] exp;

    public double calculate(String expression) {
        exp = splitExpression(expression);

        System.out.println(Arrays.toString(exp));

        return Double.parseDouble(calculateInParentheses());
    }

    /* Преобразовать выражение в массив */
    private String[] splitExpression(String expression) {
        StringBuilder result = new StringBuilder();
        expression = expression.replaceAll(" ", "");

        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == '*' || expression.charAt(i) == '/') {
                result.append(" ");
                result.append(expression.charAt(i));
                result.append(" ");
            } else {
                result.append(expression.charAt(i));
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

    /* Вычисление операций в скобках */
    private String calculateInParentheses() {
        boolean flag;
        ArrayList<String> result = new ArrayList<>();

        int openParentheses = 0;
        int closeParentheses = -2;

        for (int i = 0; i < exp.length; i++) {
            if (result.get(i).equals("(")) {
                openParentheses++;
                flag = true;
            } else if (result.get(i).equals(")")) {
                closeParentheses++;
            }
        }

        return "";
    }

    /* Вычисляем операции умножения/деления и сложения/вычитания */
    private String calculateUsualOperation(String[] simpleExp) {
        ArrayList<String> result = new ArrayList<>();
        int skipIndex = -1;

        // Вычисляем операции умножения и деления
        for (int i = 0, j = 0; i < simpleExp.length; i++) {
            if (i == skipIndex) {
                continue;
            } else if (simpleExp[i].equals("*")) {
                result.set(
                        j - 1, getResult(result.get(j - 1), simpleExp[i + 1], "*")
                );
                skipIndex = i + 1;
            } else if (simpleExp[i].equals("/")) {
                result.set(
                        j - 1, getResult(result.get(j - 1), simpleExp[i + 1], "/")
                );
                skipIndex = i + 1;
            } else {
                result.add(simpleExp[i]);
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
