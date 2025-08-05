package ru.stqa.geometry.figures;

public record Rectangle(double a, double b) {

    public Rectangle{
        if (a<0 || b<0){
            throw new IllegalArgumentException("Rectangle side should be non-negative");
        }
    }

    public static void printRectangleArea(Rectangle r) {
        var text = String.format("Площадь прямоугольника  со сторонами %f и %f = %f", r.a(), r.b(),  r.area());
        System.out.println(text);
    }

    public double area() {
        return a * b;
    }

    public double perimeter() {
        return 2 * (a + b);
    }
}
