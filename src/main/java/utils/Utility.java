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

    public static String getRandomFirstName(){
       return new Faker().name().firstName();
    }

    public static String getRandomLastName(){
        return new Faker().name().lastName();
    }

    public static String getRandomCity(){
        return new Faker().address().city();
    }

    public static String getRandomDateOfBirth(){
        return new Faker().date().birthday(14, 100, "dd.MM.yyyy");
    }

    public static  String getAboutMe(){
        return new Faker().shakespeare().asYouLikeItQuote();
    }
}
