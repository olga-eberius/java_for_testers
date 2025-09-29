package ru.stqa.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CollectionTests {

    @Test
    void ArrayTests(){
        var array = new String[]{"a","b","c"}; //создание массива со значениями
        //var array = new String[3]; //альтернатива - создание пустого массива из трех элементов
        Assertions.assertEquals(3,array.length); //проверка длины массива

        array[0] = "a"; //присвоение значения элементу массива
        Assertions.assertEquals("a",array[0]); //проверка значения первого элемента массива

        array[0] = "d";
        Assertions.assertEquals("d",array[0]);
    }

    @Test
    void listTests(){

        var list = new ArrayList<>(List.of("a", "b", "c")); //создание списка с начальными значениями
        Assertions.assertEquals(3, list.size()); //проверка размера списка
        Assertions.assertEquals("a", list.get(0)); //проверка значения первого элемента списка

        list.set(0, "d");  //обновление первого элемента
        Assertions.assertEquals("d", list.get(0)); //проверка обновленного значения первого элемента
    }

    @Test
    void TestMap(){
        var digits = new HashMap<Character, String>();
        digits.put('1', "one");
        digits.put('2', "two");
        digits.put('3', "three");

        Assertions.assertEquals("one", digits.get('1'));
        digits.put('1',"один");
        Assertions.assertEquals("один", digits.get('1'));
    }

}
