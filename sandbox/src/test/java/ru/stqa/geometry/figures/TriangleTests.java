package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TriangleTests {

    @Test
    void canCalculateArea() {
        var t = new Triangle(3.0, 4.0, 5.0);
        double result = t.area();
        Assertions.assertEquals(6.0, result);
    }

    @Test
    void canCalculatePerimeter() {
        var t = new Triangle(3.0, 4.0, 5.0);
        double result = t.perimeter();
        Assertions.assertEquals(12.0, result);
    }

    //Невозможно создать треугольник с отрицательной стороной А
    @Test
    void cannotCreateTriangleWithNegativeSideA() {
        try {
            new Triangle(-3.0,4.0, 5.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            //ok
        }
    }

    //Невозможно создать треугольник с отрицательной стороной B
    @Test
    void cannotCreateTriangleWithNegativeSideB() {
        try {
            new Triangle(3.0,-4.0, 5.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            //ok
        }
    }

    //Невозможно создать треугольник с отрицательной стороной С
    @Test
    void cannotCreateTriangleWithNegativeSideC() {
        try {
            new Triangle(3.0,4.0, -5.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            //ok
        }
    }

    //Невозможно создать треугольник когда сумма двух сторон равна третьей
    @Test
    void cannotCreateTriangleWhenSumOfTwoSidesEqualsThird() {
        try {
            new Triangle(3, 4, 7);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // ok
        }
    }

    //Невозможно создать треугольник когда сумма двух сторон меньше третьей
    @Test
    void cannotCreateTriangleWhenSumOfTwoSidesLessThanThird() {
        try {
            new Triangle(3, 4, 8);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // ok
        }
    }

    //Проверка равенства одинаковых треугольников, a=a, b=b, c=c
    @Test
    void testEquality(){
        var t1 = new Triangle (5.0, 4.0, 3.0);
        var t2 = new Triangle (5.0, 4.0, 3.0);
        Assertions.assertTrue(t1.equals(t2));
    }

    //Проверка равенства треугольников, где a=a, b=c, c=b
    @Test
    void testEquality2(){
        var t1 = new Triangle (5.0, 4.0, 3.0);
        var t2 = new Triangle (5.0, 3.0, 4.0);
        Assertions.assertTrue(t1.equals(t2));
    }

    //Проверка равенства треугольников, где a=b, b=a, c=c
    @Test
    void testEquality3(){
        var t1 = new Triangle (5.0, 4.0, 3.0);
        var t2 = new Triangle (4.0, 5.0, 3.0);
        Assertions.assertTrue(t1.equals(t2));
    }

    //Проверка равенства треугольников, где a=b, b=c, c=a
    @Test
    void testEquality4(){
        var t1 = new Triangle (5.0, 4.0, 3.0);
        var t2 = new Triangle (4.0, 3.0, 5.0);
        Assertions.assertTrue(t1.equals(t2));
    }

    //Проверка равенства треугольников, где a=c, b=a, c=b
    @Test
    void testEquality5(){
        var t1 = new Triangle (5.0, 4.0, 3.0);
        var t2 = new Triangle (3.0, 5.0, 4.0);
        Assertions.assertTrue(t1.equals(t2));
    }

    //Проверка равенства треугольников, где a=c, b=b, c=a
    @Test
    void testEquality6(){
        var t1 = new Triangle (5.0, 4.0, 3.0);
        var t2 = new Triangle (3.0, 4.0, 5.0);
        Assertions.assertTrue(t1.equals(t2));
    }

    //Проверка неравенства треугольников
    @Test
    void testNotEquality(){
        var t1 = new Triangle (5.0, 4.0, 3.0);
        var t2 = new Triangle (5.0, 4.0, 5.0);
        Assertions.assertFalse(t1.equals(t2));
    }

}

