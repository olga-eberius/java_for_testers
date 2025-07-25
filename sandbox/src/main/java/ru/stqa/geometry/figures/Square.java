package ru.stqa.geometry.figures;

public record Square(double side){

    public static void printSquareArea(Square s) {
        String text = String.format("Площадь квадрата со стороной %f = %f", s.side, s.area());
        System.out.println(text);
    }


    public double area() {
        return side * side;
    }

    public double perimeter() {
        return 4 * side;
    }
}
