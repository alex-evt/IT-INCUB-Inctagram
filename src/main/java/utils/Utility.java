package utils;

import net.datafaker.Faker;

public class Utility {


    public static String getRandomEmail(){
      return new Faker().expression("#{letterify '?????@????.???'}");
    }

    public static String getUsername() {
        return new Faker().name().username().replace(".", "");
    }

    public static String getRandomUsername() {
        return new Faker().regexify("[A-Z]{1}[a-z]{8}[1-9]{1}");
    }

    public static String getRandomPassword() {
        return new Faker().regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!-/]{2}[:-@]{1}[{-~]{1}");
//        return new Faker().regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!-/]{2}[:-@]{1}[[-`]{1}[{-~]{1}"); //should be valid!
    }
}
