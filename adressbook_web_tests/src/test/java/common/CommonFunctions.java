package common;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonFunctions {
    public static String randomString(int n){
        var rnd = new Random();
        Supplier<Integer> randomNumbers = () -> rnd.nextInt(26);
        var result = Stream.generate(randomNumbers)
                .limit(n)
                .map(i->'a' + i)
                .map(Character::toString)
                .collect(Collectors.joining());
        return result;
    }

    public static String randomPhone() {
        var rnd = new Random();
        var result = "+7";
        for (int i = 0; i < 10; i++) {
            result += rnd.nextInt(10);
        }
        return result;
    }

    public static String randomEmail() {
        var rnd = new Random();
        return randomString(5).toLowerCase() + "@test"
                + rnd.nextInt(10) + rnd.nextInt(10) + ".com";
    }

}
