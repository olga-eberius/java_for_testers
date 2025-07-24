public class Geometry {
    public static void main(String[] args) {
        printSquareArea(7.0);
        printSquareArea(5.0);
        printSquareArea(2.0);

        printRectangleArea(3.0, 5.0);
    }

    private static void printRectangleArea(double a, double b) {
        System.out.println("Площадь прямоугольника  со сторонами" + a + "и" + b + "и" + reactangleArea(a,b));
    }

    private static double reactangleArea(double a, double b) {
        return a * b;
    }

    static void printSquareArea(double a) {
        System.out.println("Площадь квадрата со стороной " + a + "=" + squareArea(a));
    }

    private static double squareArea(double a) {
        return a * a;
    }
}
