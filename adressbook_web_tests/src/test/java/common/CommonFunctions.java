package common;

import java.util.Random;

public class CommonFunctions {
    public static String randomString(int n){
        var rnd = new Random();
        var result = "";
        for (int i = 0; i < n; i++){
            result = result + (char)('a' + rnd.nextInt(26));
        }
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
