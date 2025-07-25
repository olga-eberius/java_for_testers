package ru.stqa.geometry.figures;

public class Rectangle {

    private double a;
    private double b;

    public Rectangle(double a, double b){
        this.a = a;
        this.b = b;
    }

    public static void printRectangleArea(double a, double b) {
        var text = String.format("Площадь прямоугольника  со сторонами %f и %f = %f", a, b,  reactangleArea(a,b));
        System.out.println(text);
    }

    private static double reactangleArea(double a, double b) {
        return a * b;
    }
}
