package ru.stqa.geometry;

import ru.stqa.geometry.figures.Rectangle;
import ru.stqa.geometry.figures.Square;
import ru.stqa.geometry.figures.Triangle;

public class Geometry {
    public static void main(String[] args) {
        Square.printSquareArea(new Square(7.0));
        Rectangle.printRectangleArea(new Rectangle(3.0, 5.));
        Triangle.printTriangleArea(new Triangle(4.0, 3.0, 5.0));
    }

}
