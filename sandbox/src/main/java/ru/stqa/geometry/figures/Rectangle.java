package ru.stqa.geometry.figures;

public record Rectangle(double a, double b) {

    public static void printRectangleArea(double a, double b) {
        var text = String.format("Площадь прямоугольника  со сторонами %f и %f = %f", a, b,  reactangleArea(a,b));
        System.out.println(text);
    }

    private static double reactangleArea(double a, double b) {
        return a * b;
    }
}
