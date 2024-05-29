public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        String expression = "((3 + 2) * (4 - 1) + 2) / (1 + 2 * (3 - 1))"; // 3.4
        double answer = calculator.calculate(expression);
        System.out.println(answer);
    }
}
