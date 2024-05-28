public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        String expression = "7 * 7 - 1.8 * 5 + 10";
        double answer = calculator.calculate(expression);
        System.out.println(answer);
    }
}
