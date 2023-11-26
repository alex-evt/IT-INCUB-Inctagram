package tests;

import API.adapter.MailAdapter;
import API.utils.MailBuilder;
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
import utils.Utility;

public class RegistrationTest extends BaseTest {

    private static final String PASSWORD_FOR_EMAIL = "test1";
    RegistrationPageService registrationPageService = new RegistrationPageService();
    RegistrationPage registrationPage = new RegistrationPage();
    Faker faker = new Faker();


    @Description("Successful registration in the system with email confirmation")
    @TmsLink(value = "IN-353")
    @Test
    public void verifySuccessfulUserRegistrationWithEmailConfirmation() {
        String username = Utility.getUsername();
        String password = Utility.getRandomPassword();

        MailAdapter mailer = MailBuilder.createRandomEmail(PASSWORD_FOR_EMAIL);
        String email = mailer.getEmailAddress();
        User user = User.builder().userName(username).email(email).password(password).passwordConfirm(password).build();
        String actualEmailSentMessage = registrationPageService
                .registrationWithConfirmation(mailer, user).getConfirmationText();
        User.writeUserDataToFile(username, password, email);
        Assert.assertEquals(actualEmailSentMessage, "Your email has been confirmed");
    }


    @Description("Registration failure if email is already in the system")
    @TmsLink(value = "IN-340")
    @Test
    public void verifyErrorIfEmailIsExistInSystem() {
        String userName = Utility.getUsername();
        String username2 = Utility.getUsername();
        String password = Utility.getRandomPassword();
        String password2 = Utility.getRandomPassword();
        MailAdapter mailer = MailBuilder.createRandomEmail(PASSWORD_FOR_EMAIL);
        String email = mailer.getSelf().getEmail();

        User user1 = User.builder().userName(userName).email(email).password(password).passwordConfirm(password).build();
        User user2 = User.builder().userName(username2).email(email).password(password2).passwordConfirm(password2).build();
        registrationPageService
                .registrationWithConfirmation(mailer, user1);

        registrationPageService.wait(15000);

        registrationPageService.
                registrationWithoutConfirmation(user2);

        boolean isSignUpDisabled = registrationPage.isSignUpDisabled();
        String actualEmailErrorMessage = registrationPage.getEmailErrorMessage();

        mailer.deleteEmailAccount();

        Assert.assertEquals(actualEmailErrorMessage, "User with this email is already registered");
        Assert.assertTrue(isSignUpDisabled);
    }

    @Description("Registration failure if username is already in the system")
    @TmsLink(value = "IN-342")
    @Test
    public void verifyErrorIfUsernameExistInSystem() {
        String username = Utility.getRandomUsername();
        String email2 = Utility.getRandomEmail();
        String password = Utility.getRandomPassword();
        String password2 = Utility.getRandomPassword();

        MailAdapter mailer = MailBuilder.createRandomEmail(PASSWORD_FOR_EMAIL);
        String email = mailer.getEmailAddress();

        User user1 = User.builder().userName(username).email(email).password(password).passwordConfirm(password).build();
        User user2 = User.builder().userName(username).email(email2).password(password2).passwordConfirm(password2).build();

        registrationPageService
                .registrationWithConfirmation(mailer, user1);

        registrationPageService.wait(15000);

        String actualErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user2)
                .getUserNameErrorMessage();

        Assert.assertEquals(actualErrorMessage, "User with this username is already registered");
        Assert.assertTrue(registrationPage.isSignUpDisabled());

    }

    @Description("Registration failure if passwords (Password and Password confirmation fields) don't match")
    @TmsLink(value = "IN-343")
    @Test
    public void verifyRegistrationErrorWhenPasswordsDontMatch() {
        String username = Utility.getRandomUsername();
        String email = Utility.getRandomEmail();
        String password = Utility.getRandomPassword();
        String mismatchedPassword = Utility.getRandomPassword();

        User user = User.builder().userName(username).email(email).password(password).passwordConfirm(mismatchedPassword).build();

        String actualPasswordErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getConfirmPasswordErrorMessage();
        Assert.assertEquals(actualPasswordErrorMessage, "Passwords do not match");
    }

    @Description("Error message when username contains more than 30 characters")
    @TmsLink(value = "IN-344")
    @Test
    public void verifyUsernameErrorIfUsernameMoreThan30Characters() {
        String username = faker.regexify("[a-z0-9]{31}");
        String email = Utility.getRandomEmail();
        String password = Utility.getRandomPassword();
        User user = User.builder().userName(username).email(email).password(password).passwordConfirm(password).build();

        String actualUserNameErrorMessage = registrationPageService.registrationWithoutConfirmation(user)
                .getUserNameErrorMessage();
        Assert.assertEquals(actualUserNameErrorMessage, "Username must not exceed 30 characters");
    }


    @Description("Error message when username contains less than 6 characters")
    @TmsLink(value = "IN-351")
    @Test
    public void verifyUsernameErrorIfUsernameLessThan6Characters() {
        String username = faker.regexify("[a-z0-9]{5}");
        String email = Utility.getRandomEmail();
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{2}");
        User user = User.builder().userName(username).email(email).password(password).passwordConfirm(password).build();

        String actualPasswordErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getUserNameErrorMessage();
        Assert.assertEquals(actualPasswordErrorMessage, "Username must be at least 6 characters");
    }

    @Description("Error message when password contains more than 20 characters")
    @TmsLink(value = "IN-345")
    @Test
    public void verifyPasswordErrorIfPasswordMoreThan20Characters() {
        String username = Utility.getRandomUsername();
        String email = Utility.getRandomEmail();
        String password = faker.regexify("[A-Z]{18}[a-z]{2}[0-9]{1}[!]{2}");
        User user = User.builder().userName(username).email(email).password(password).passwordConfirm(password).build();

        String actualPasswordErrorMessage = registrationPageService.registrationWithoutConfirmation(user)
                .getPasswordErrorMessage();
        Assert.assertEquals(actualPasswordErrorMessage, "Password must not exceed 20 characters");
    }

    @Description("Error message when password contains less than 6 characters")
    @TmsLink(value = "IN-349")
    @Test
    public void verifyPasswordErrorIfPasswordLessThan6Characters() {
        String username = Utility.getRandomUsername();
        String email = Utility.getRandomEmail();
        String password = faker.regexify("[A-Z]{1}[a-z]{1}[0-9]{1}[!]{1}");
        User user = User.builder().userName(username).email(email).password(password).passwordConfirm(password).build();

        String actualPasswordErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getPasswordErrorMessage();
        Assert.assertEquals(actualPasswordErrorMessage, "Password must be at least 6 characters");
    }

    @Test
    public void verifyValidUsername() {
        String validUsername = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{2}_-");
        String email = Utility.getRandomEmail();
        String password = Utility.getRandomPassword();
        User user = User.builder().userName(validUsername).email(email).password(password).passwordConfirm(password).build();

        String actualUsernameErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getSuccessfulEmailSentMessage();
        Assert.assertEquals(actualUsernameErrorMessage, "We have sent a link to confirm your email to " + email);
    }

    @DataProvider
    public Object[][] provideInvalidUsername() {
        return new Object[][]{
                {" "}, {"!"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"*"}, {"("}, {")"},
                {"+"}, {"="}, {"{"}, {"}"}, {"["}, {"]"}, {"|"}, {"\\"}, {"/"}, {":"}, {";"},
                {"\""}, {"'"}, {"<"}, {">"}, {"?"}, {"."}, {","}
        };
    }

    @Description("Registration failure when username doesn't meet requirements")
    @TmsLink(value = "IN-346")
    @Test(dataProvider = "provideInvalidUsername")
    public void verifyErrorIfUsernameNotMeetRequirements(String wrongUsername) {
        String username = wrongUsername.repeat(6);
        String email = Utility.getRandomEmail();

        String password = Utility.getRandomPassword();

        User user = User.builder().userName(username).email(email).password(password).passwordConfirm(password).build();

        String actualUsernameErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getUserNameErrorMessage();
        if (wrongUsername.contains(" ")) {
            Assert.assertEquals(actualUsernameErrorMessage, "Username must not contain spaces");
        } else {
            Assert.assertEquals(actualUsernameErrorMessage, "Username must contain a-z, A-Z, 1-9");
        }
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
    @Test(dataProvider = "provideInvalidPassword")
    public void verifyErrorIfPasswordNotMeetRequirements(String invalidPassword, String expectedResult) {
        String username = Utility.getRandomUsername();
        String email = Utility.getRandomEmail();

        User user = User.builder().userName(username).email(email).password(invalidPassword).passwordConfirm(invalidPassword).build();

        String actualPasswordErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getPasswordErrorMessage();
        Assert.assertEquals(actualPasswordErrorMessage, expectedResult);
    }

    @DataProvider
    public Object[][] provideInvalidEmail() {
        return new Object[][]{
                {"юзер@почта.ком", "Email must contain A-Z, a-z, ., @"}, //valid format but cyrillic
                {faker.expression("#{letterify ''}"), "Email is required"}, // nothing
                {faker.expression("#{letterify '   @   .  '}"), "Email must contain A-Z, a-z, ., @"}, // spaces
                {faker.expression("#{letterify '        '}"), "Email must contain A-Z, a-z, ., @"}, //only spaces
                {faker.expression("#{letterify '?'}"), "Email must contain A-Z, a-z, ., @"}, //1
                {faker.expression("#{letterify '????.???????.com'}"), "Email must contain A-Z, a-z, ., @"}, // no @
                {faker.expression("#{letterify '????@????'}"), "Email must contain A-Z, a-z, ., @"},  // no .
                {faker.expression("#{letterify '?@??@?????.com'}"), "Email must contain A-Z, a-z, ., @"}, // more than two @
                {faker.expression("#{letterify '???@??.???.com'}"), "Email must contain A-Z, a-z, ., @"}, // more than two .

                {faker.expression("#{letterify '@?????.???'}"), "Email must contain A-Z, a-z, ., @"}, // missing local part
                {faker.expression("#{letterify '-?????@???.??'}"), "Email must contain A-Z, a-z, ., @"}, //local part starts with -
                {faker.expression("#{letterify '.?????@???.??'}"), "Email must contain A-Z, a-z, ., @"}, //local part starts with .
                {faker.expression("#{letterify '???..??@???.??'}"), "Email must contain A-Z, a-z, ., @"}, //local part with more than one .
                {faker.expression("#{letterify '?????-@???.??'}"), "Email must contain A-Z, a-z, ., @"}, //local part ends with -
                {faker.expression("#{letterify '?????.@???.??'}"), "Email must contain A-Z, a-z, ., @"}, //local part ends with .
                {faker.expression("#{letterify ' ???@????.????'}"), "Email must not contain spaces"}, //local part starts with spaces
                {faker.expression("#{letterify '??? @????.????'}"), "Email must not contain spaces"}, //local part ends with spaces
                {faker.expression("#{letterify '?????@.???'}"), "Email must contain A-Z, a-z, ., @"}, // missing domain part
                {faker.expression("#{letterify '???@??_???.???'}"), "Email must contain A-Z, a-z, ., @"}, //domain part with _
                {faker.expression("#{letterify '???@-.???'}"), "Email must contain A-Z, a-z, ., @"}, //domain part contains only -
                {faker.expression("#{letterify '???@..???'}"), "Email must contain A-Z, a-z, ., @"}, //domain part contains only .
                {faker.expression("#{letterify '???@_.???'}"), "Email must contain A-Z, a-z, ., @"}, //domain part contains only _
                {faker.expression("#{letterify '???@???--???.??'}"), "Email must contain A-Z, a-z, ., @"}, //domain part contains more than two -
                {faker.expression("#{letterify '???@-?????.??'}"), "Email must contain A-Z, a-z, ., @"}, //domain part starts with -
                {faker.expression("#{letterify '?????@?????-.???'}"), "Email must contain A-Z, a-z, ., @"}, //domain part ends with -
                {faker.expression("#{letterify '???@ ????.????'}"), "Email must not contain spaces"}, //domain part starts with spaces
                {faker.expression("#{letterify '???@???? .????'}"), "Email must not contain spaces"}, //domain part ends with spaces

                {faker.expression("#{letterify '?????@???'}"), "Email must contain A-Z, a-z, ., @"}, // missing TLD .
//                {27, faker.expression("#{letterify '?????@?????.?'}"), null}, //TLD part have only 1 letter
//                {28, faker.expression("#{letterify '?????@?????.????'}"), null}, //TLD part have more than 3 letter
                {faker.expression("#{letterify '?????@?????.###'}"), "Email must contain A-Z, a-z, ., @"}, //TLD part have digits
                {faker.expression("#{letterify '?????@?????.??-?'}"), "Email must contain A-Z, a-z, ., @"}, //TLD part have -
                {faker.expression("#{letterify '?????@?????.?_??'}"), "Email must contain A-Z, a-z, ., @"}, //TLD part have _
                {faker.expression("#{letterify '???@???. ???'}"), "Email must contain A-Z, a-z, ., @"}, //TLD part starts with space
                {faker.expression("#{letterify '???@???. ???'}"), "Email must contain A-Z, a-z, ., @"}, //TLD part ends with space

        };
    }

    @Description("Registration failure when email doesn't meet requirements")
    @TmsLink(value = "IN-348")
    @Test(dataProvider = "provideInvalidEmail")
    public void verifyErrorIfEmailNotMeetRequirement(String invalidEmail, String expected) {
        String username = Utility.getRandomUsername();
        String password = Utility.getRandomPassword();
        User user = User.builder().userName(username).email(invalidEmail).password(password).passwordConfirm(password).build();

        String actualEmailErrorMessage = registrationPageService
                .registrationWithoutConfirmation(user)
                .getEmailErrorMessage();

        Assert.assertEquals(actualEmailErrorMessage, expected);
    }


    @Description("User clicks on the 'Terms of Service' link")
    @TmsLink(value = "IN-335")
    @Test(priority = 1, enabled = false)
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
    @Test(priority = 1, enabled = false)
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
    @Test(priority = 2, enabled = false)
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
    @Test(priority = 2, enabled = false)
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
    @Test
    public void verifyEmptyFieldsAfterRegistration() {
        String email = Utility.getRandomEmail();
        String userName = Utility.getRandomUsername();
        String password = Utility.getRandomPassword();

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
