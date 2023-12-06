package API.tempmail.utils;

import java.util.Random;

public class ApiUtility {

    private static final String regex = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    public ApiUtility() {
    }

    public static String createRandomString(int length) {
//        String randomString = "";
        StringBuilder randomString = new StringBuilder(length);

//        for(int i = 0; i < length; ++i) {
//            randomString = randomString + regex.charAt(random.nextInt(regex.length()));
//        }

        for (int i = 0; i < length; i++) {
            randomString.append(regex.charAt(random.nextInt(regex.length())));
        }

//        return randomString;
        return randomString.toString();
    }

}
