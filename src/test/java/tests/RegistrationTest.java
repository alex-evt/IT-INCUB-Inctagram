package tests;

import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import models.User;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.signUp.RegistrationPage;
import services.RegistrationPageService;
import text.data.TextInfo;

import java.util.Objects;

public class RegistrationTest extends BaseTest {

    RegistrationPageService registrationPageService = new RegistrationPageService();
    RegistrationPage registrationPage = new RegistrationPage();
    Faker faker = new Faker();

    @Description("Successful registration in the system with email confirmation")
    @TmsLink(value = "IN-353")
    @Test(groups = {"Regression", "MainFlow"})
    public void verifySuccessfulUserRegistrationWithEmailConfirmation() {
        String username = faker.name().username().replace(".", "");
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{2}");


        String currentTempEmail = registrationPageService
                .openTempMailAndGetCurrentEmail();

        User user = User.builder().userName(username).email(currentTempEmail).password(password).passwordConfirm(password).build();

        User.writeUserDataToFile(username, password, currentTempEmail);

        String actualEmailSentMessage = registrationPageService
                .registrationWithConfirmation(user)
                .getConfirmEmailText();

        Assert.assertEquals(actualEmailSentMessage, "Your email has been confirmed");
    }


    @Description("Registration failure if email is already in the system")
    @TmsLink(value = "IN-340")
    @Test(groups = "Regression")
    public void verifyErrorIfEmailInExistInSystem() {
        String userName = faker.name().username().replace(".", "");
        String username2 = faker.name().username().replace(".", "");
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{2}");
        String password2 = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{2}");

        String currentTempEmail = registrationPageService
                .openTempMailAndGetCurrentEmail();

        User user1 = User.builder().userName(userName).email(currentTempEmail).password(password).passwordConfirm(password).build();
        User user2 = User.builder().userName(username2).email(currentTempEmail).password(password2).passwordConfirm(password2).build();

//        User registeredUser = User.readUserDataFromFile();


        registrationPageService
                .registrationWithConfirmation(user1);

        RegistrationPage registrationPage = registrationPageService
                .registrationWithoutClickSignUp(user2);

        boolean isSignUpDisabled = registrationPage.isSignUpDisabled();
        String actualEmailErrorMessage = registrationPage.getEmailErrorMessage();

        Assert.assertEquals(actualEmailErrorMessage, "User with this email is already registered"); //User with this email already exists
        Assert.assertTrue(isSignUpDisabled);
    }

    @Description("Registration failure if username is already in the system")
    @TmsLink(value = "IN-342")
    @Test(groups = "Regression")
    public void verifyErrorIfUsernameExistInSystem() {
        String username = faker.name().username().replace(".", "");
//        String email = faker.expression("#{letterify '???@????.???'}");
        String email2 = faker.expression("#{letterify '???@????.???'}");
//        String password = faker.internet().password(6, 20, true, true).replaceFirst("[A-Z]", faker.regexify("[a-z]"));
//        String password2 = faker.internet().password(6, 20, true, true).replaceFirst("[A-Z]", faker.regexify("[a-z]"));
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{1}[:-@]{1}[{-~]{1}");
        String password2 = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{1}[:-@]{1}[{-~]{1}");

        String currentTempEmail = registrationPageService
                .openTempMailAndGetCurrentEmail();

        User user1 = User.builder().userName(username).email(currentTempEmail).password(password).passwordConfirm(password).build();
        User user2 = User.builder().userName(username).email(email2).password(password2).passwordConfirm(password2).build();

        registrationPageService
                .registrationWithConfirmation(user1);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String actualErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user2)
                .getUserNameErrorMessage();

        Assert.assertEquals(actualErrorMessage, "User with this username is already registered");
        Assert.assertTrue(registrationPage.isSignUpDisabled());

    }

    @Description("Registration failure if passwords (Password and Password confirmation fields) don't match")
    @TmsLink(value = "IN-343")
    @Test(groups = "Regression")
    public void verifyRegistrationErrorWhenPasswordsDontMatch() {
        String userName = faker.name().username().replace(".", "");
        String email = faker.expression("#{letterify '???@????.???'}");
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{2}");
        String mismatchedPassword = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{2}");

        User user = User.builder().userName(userName).email(email).password(password).passwordConfirm(mismatchedPassword).build();

        String actualPasswordErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getConfirmPasswordErrorMessage();
        Assert.assertEquals(actualPasswordErrorMessage, "Passwords do not match");
    }

    @Description("Error message when username contains more than 30 characters")
    @TmsLink(value = "IN-344")
    @Test(groups = "Regression")
    public void verifyUsernameErrorIfUsernameMoreThan30Characters() {
        String userName = faker.regexify("[a-z0-9]{31}");
        String email = faker.expression("#{letterify '???@????.???'}");
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{2}");
        User user = User.builder().userName(userName).email(email).password(password).passwordConfirm(password).build();

        String actualUserNameErrorMessage = registrationPageService.registrationWithoutConfirmation(user)
                .getUserNameErrorMessage();
        Assert.assertEquals(actualUserNameErrorMessage, "Username must not exceed 30 characters");
    }


    @Description("Error message when username contains less than 6 characters")
    @TmsLink(value = "IN-351")
    @Test(groups = "Regression")
    public void verifyUsernameErrorIfUsernameLessThan6Characters() {
        String userName = faker.regexify("[a-z0-9]{5}");
        String email = faker.expression("#{letterify '???@????.???'}");
//        String password = faker.internet().password(6, 20,true, true).replaceFirst("[A-Z]", faker.regexify("[a-z]"));
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{2}");
        User user = User.builder().userName(userName).email(email).password(password).passwordConfirm(password).build();

        String actualPasswordErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getUserNameErrorMessage();
        Assert.assertEquals(actualPasswordErrorMessage, "Username must be at least 6 characters");
    }

    @Description("Error message when password contains more than 20 characters")
    @TmsLink(value = "IN-345")
    @Test(groups = "Regression")
    public void verifyPasswordErrorIfPasswordMoreThan20Characters() {
        String userName = faker.name().username().replace(".", "");
        String email = faker.expression("#{letterify '???@????.???'}");
        String password = faker.regexify("[A-Z]{18}[a-z]{2}[0-9]{1}[!]{2}");
        User user = User.builder().userName(userName).email(email).password(password).passwordConfirm(password).build();

        String actualPasswordErrorMessage = registrationPageService.registrationWithoutConfirmation(user)
                .getPasswordErrorMessage();
        Assert.assertEquals(actualPasswordErrorMessage, "Password must not exceed 20 characters");
    }

    @Description("Error message when password contains less than 6 characters")
    @TmsLink(value = "IN-349")
    @Test(groups = "Regression")
    public void verifyPasswordErrorIfPasswordLessThan6Characters() {
        String userName = faker.name().username().replace(".", "");
        String email = faker.expression("#{letterify '???@????.???'}");
        String password = faker.regexify("[A-Z]{1}[a-z]{1}[0-9]{1}[!]{1}");
        User user = User.builder().userName(userName).email(email).password(password).passwordConfirm(password).build();

        String actualPasswordErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getPasswordErrorMessage();
        Assert.assertEquals(actualPasswordErrorMessage, "Password must be at least 6 characters");
    }

    @Test
    public void verifyValidUsername() {
        String validUsername = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{2}_-");
        String email = faker.expression("#{letterify '???@????.???'}");
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!-/]{2}[:-@]{1}[[-`]{1}[{-~]{1}");
        User user = User.builder().userName(validUsername).email(email).password(password).passwordConfirm(password).build();

        String actualUsernameErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getSuccessfulEmailSentMessage();
        Assert.assertEquals(actualUsernameErrorMessage, "We have sent a link to confirm your email to " + email);
    }

    @DataProvider
    public Object[][] provideInvalidUsername() {
        return new Object[][]{
                {" "}, {"!"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"*"},
                {"("}, {")"}, {"+"}, {"="}, {"{"}, {"}"}, {"["}, {"]"}, {"|"},
                {"\\"}, {"/"}, {":"}, {";"}, {"\""}, {"'"}, {"<"}, {">"}, {"?"}, {"."}, {","}
        };
    }

    @Description("Registration failure when username doesn't meet requirements")
    @TmsLink(value = "IN-346")
    @Test(dataProvider = "provideInvalidUsername", groups = "Regression")
    public void verifyErrorIfUsernameNotMeetRequirements(String wrongUsername) {
        String userName = wrongUsername.repeat(6);
        String email = faker.expression("#{letterify '???@????.???'}");

        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{2}");
        System.out.println(password);
//        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!-/]{2}[:-@]{1}[[-`]{1}[{-~]{1}"); //valid password!

        User user = User.builder().userName(userName).email(email).password(password).passwordConfirm(password).build();

        String actualUsernameErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getUserNameErrorMessage();
        Assert.assertEquals(actualUsernameErrorMessage, "Username must contain a-z, A-Z, 1-9");
    }


    @DataProvider
    public Object[][] provideInvalidPassword() {
        return new Object[][]{
                {faker.regexify("[A-z]{1}[a-z]{2}[0-9]{1}[!-/]{2} "), "Password must not contain spaces"},
                {faker.regexify(" [A-z]{1}[a-z]{2}[0-9]{1}[!-/]{2}"), "Password must not contain spaces"},
                {faker.regexify("[A-z]{1}[a-z]{2} [0-9]{1}[!-/]{2}"), "Password must not contain spaces"},
                {faker.regexify("[A-z]{1}[a-z]{2}[0-9]{1}[!-/]{2}[А-Я]"), "Password must contain a-z, A-Z, 1-9, special"},
                {faker.regexify("[A-z]{1}[a-z]{2}[0-9]{1}[!-/]{2}[а-я]"), "Password must contain a-z, A-Z, 1-9, special"},
        };
    }

    @Description("Registration failure when password doesn't meet requirements")
    @TmsLink(value = "IN-347")
    @Test(dataProvider = "provideInvalidPassword", groups = "Regression")
    public void verifyErrorIfPasswordNotMeetRequirements(String invalidPassword, String expectedResult) {
//        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!-/]{2}[:-@]{1}[[-`]{1}[{-~]{1}");
        String username = faker.name().username().replace(".", "");
        String email = faker.expression("#{letterify '???@????.???'}");

        User user = User.builder().userName(username).email(email).password(invalidPassword).passwordConfirm(invalidPassword).build();

        String actualPasswordErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getPasswordErrorMessage();
        Assert.assertEquals(actualPasswordErrorMessage, expectedResult);
    }

    @DataProvider
    public Object[][] provideInvalidEmail() {
        return new Object[][]{
                {0, "юзер@почта.ком", null}, //valid format but cyrillic
                {1, faker.expression("#{letterify ''}"), "Email is required"}, // nothing
                {2, faker.expression("#{letterify '   @   .  '}"), null}, // spaces
                {2, faker.expression("#{letterify '        '}"),}, //only spaces
                {3, faker.expression("#{letterify '?'}"), null}, //1
                {4, faker.expression("#{letterify '????.???????.com'}"), null}, // no @
                {5, faker.expression("#{letterify '????@????'}"), null},  // no .
                {6, faker.expression("#{letterify '?@??@?????.com'}"), null}, // more than two @
                {7, faker.expression("#{letterify '???@??.???.com'}"), null}, // more than two .

                {8, faker.expression("#{letterify '@?????.???'}"), null}, // missing local part
                {9, faker.expression("#{letterify '-?????@???.??'}"), null}, //local part starts with -
                {10, faker.expression("#{letterify '?????-@???.??'}"), null}, //local part ends with -
                {11, faker.expression("#{letterify '.?????@???.??'}"), null}, //local part starts with .
                {12, faker.expression("#{letterify '?????.@???.??'}"), null}, //local part ends with .
                {13, faker.expression("#{letterify '???..??@???.??'}"), null}, //local part with more than one .
                {14, faker.expression("#{letterify ' ???@????.????'}"), "Email must not contain spaces"}, //local part starts with spaces
                {15, faker.expression("#{letterify '??? @????.????'}"), "Email must not contain spaces"}, //local part ends with spaces

                {16, faker.expression("#{letterify '?????@.???'}"), null}, // missing domain part
                {17, faker.expression("#{letterify '???@??_???.???'}"), null}, //domain part with _
                {18, faker.expression("#{letterify '???@-.???'}"), null}, //domain part contains only -
                {19, faker.expression("#{letterify '???@..???'}"), null}, //domain part contains only .
                {20, faker.expression("#{letterify '???@_.???'}"), null}, //domain part contains only _
                {21, faker.expression("#{letterify '???@???--???.??'}"), null}, //domain part contains more than two -
                {22, faker.expression("#{letterify '???@-?????.??'}"), null}, //domain part starts with -
                {23, faker.expression("#{letterify '?????@?????-.???'}"), null}, //domain part ends with -
                {24, faker.expression("#{letterify '???@ ????.????'}"), "Email must not contain spaces"}, //domain part starts with spaces
                {25, faker.expression("#{letterify '???@???? .????'}"), "Email must not contain spaces"}, //domain part ends with spaces

                {26, faker.expression("#{letterify '?????@???'}"), null}, // missing TLD .
//                {27, faker.expression("#{letterify '?????@?????.?'}"), null}, //TLD part have only 1 letter
//                {28, faker.expression("#{letterify '?????@?????.????'}"), null}, //TLD part have more than 3 letter
                {29, faker.expression("#{letterify '?????@?????.###'}"), null}, //TLD part have digits
                {30, faker.expression("#{letterify '?????@?????.??-?'}"), null}, //TLD part have -
                {31, faker.expression("#{letterify '?????@?????.?_??'}"), null}, //TLD part have _
                {32, faker.expression("#{letterify '???@???. ???'}"), null}, //TLD part starts with space
                {33, faker.expression("#{letterify '???@???. ???'}"), null}, //TLD part ends with space

        };
    }

    @Description("Registration failure when email doesn't meet requirements")
    @TmsLink(value = "IN-348")
    @Test(dataProvider = "provideInvalidEmail", groups = "Regression")
    public void verifyErrorIfEmailNotMeetRequirement(int number, String invalidEmail, String expected) {
        String username = faker.expression("#{letterify '????????'}");
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}!{2}");
        User user = User.builder().userName(username).email(invalidEmail).password(password).passwordConfirm(password).build();

        String actualEmailErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getEmailErrorMessage();

        System.out.println(number + " " + invalidEmail + " " + actualEmailErrorMessage);
        Assert.assertEquals(actualEmailErrorMessage, Objects.requireNonNullElse(expected, "Email must contain A-Z, a-z, ., @"));

    }


    @Description("User clicks on the 'Terms of Service' link")
    @TmsLink(value = "IN-335")
    @Test(groups = "Extended", priority = 1)
    public void verifyUserClickTermsOfServiceInEnglish() {
        String expectedAgreement = TextInfo.textTermsOfServiceEN;
        String currentLanguage = registrationPageService
                .openRegistrationPage()
                .getCurrentLanguage();

        String agreementText = registrationPageService
                .clickTermsOfService()
                .getAgreementText();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(currentLanguage, "English");
        Assert.assertEquals(agreementText, expectedAgreement);
        softAssert.assertAll();
    }


    @Description("User clicks on the 'Privacy Policy' link")
    @TmsLink(value = "IN-336")
    @Test(groups = "Extended", priority = 1)
    public void verifyUserClickPrivacyPolicyInEnglish() {
        String expectedAgreement = TextInfo.textPrivacyPolicyEN;
        String currentLanguage = registrationPageService
                .openRegistrationPage()
                .getCurrentLanguage();

        String agreementText = registrationPageService
                .clickPrivacyPolicy()
                .getPrivacyText();

        System.out.println("eng " + currentLanguage);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(currentLanguage, "English");
        Assert.assertEquals(agreementText, expectedAgreement);
        softAssert.assertAll();

    }

    @Description("User clicks on the 'Terms of Service' link in Russian")
    @TmsLink(value = "IN-337")
    @Test(groups = "Extended", priority = 2)
    public void verifyUserClickTermsOfServiceInRussian() {
        String expectedAgreement = TextInfo.textTermsOfServiceRU;

        String agreementText = registrationPageService
                .openRegistrationPage()
                .changeLanguageToRussian()
                .clickTermsOfService()
                .getAgreementText();
        String currentLanguage = registrationPageService.getCurrentLanguage();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(currentLanguage, "Russian");
        Assert.assertEquals(agreementText, expectedAgreement);
        softAssert.assertAll();
    }

    @Description("User clicks on the 'Privacy Policy' link in Russian")
    @TmsLink(value = "IN-338")
    @Test(groups = "Extended", priority = 2)
    public void verifyUserClickPrivacyPolicyInRussian() {
        String expectedPrivacyPolicy = TextInfo.textPrivacyPolicyRU;

        String privacyPolicyText = registrationPageService
                .openRegistrationPage()
                .changeLanguageToRussian()
                .clickPrivacyPolicy()
                .getPrivacyText();

        String currentLanguage = registrationPageService.getCurrentLanguage();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(currentLanguage, "Russian");
        Assert.assertEquals(privacyPolicyText, expectedPrivacyPolicy);
        softAssert.assertAll();

    }

    @Description("Registration fields form is empty after submitting the form")
    @TmsLink(value = "IN-352")
    @Test(groups = {"Extended", "Regression"})
    public void verifyEmptyFieldsAfterRegistration() {
        String email = faker.expression("#{letterify '???@????.???'}");
        String userName = faker.name().username().replace(".", "");
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}!{2}");

        User user = User.builder().userName(userName).email(email).password(password).passwordConfirm(password).build();
        RegistrationPage page = registrationPageService
                .registrationWithoutConfirmation(user)
                .clickOkButton();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(page.isUsernameEmpty());
        softAssert.assertTrue(page.isEmailEmpty());
        softAssert.assertTrue(page.isPasswordEmpty());
        softAssert.assertTrue(page.isPasswordConfirmationEmpty());
        softAssert.assertAll();
    }
}
