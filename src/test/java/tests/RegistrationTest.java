package tests;

import data.TextInfo;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import models.User;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import services.RegistrationPageService;


public class RegistrationTest extends BaseTest {

    RegistrationPageService registrationPageService = new RegistrationPageService();
    Faker faker = new Faker();

    @Description("Успешная регистрация нового пользователя в системе")
    @TmsLink(value = "IN-339")
    @Test(groups = "Regression")
    public void verifySuccessfulUserRegistration() {
        String username = faker.name().username().replace(".", "");
        String email = faker.expression("#{letterify '???@????.???'}");
        String password = faker.internet().password(6, 20, true, true).replaceFirst("[A-Z]", faker.regexify("[a-z]"))
                .replaceFirst("[A-Z]", faker.regexify("[a-z]"));
        User user = User.builder().userName(username).email(email).password(password).passwordConfirm(password).build();

        String actualEmailSentMessage = registrationPageService
                .registration(user)
                .getSuccessfulEmailSentMessage();

        String expectedEmailSentMessage = "We have sent a link to confirm your email to " + email;
        Assert.assertEquals(actualEmailSentMessage, expectedEmailSentMessage);
    }

    @Description("Ошибка регистрации, если email пользователя есть в системе")
    @TmsLink(value = "IN-340")
    @Test(groups = "Regression")
    public void verifyErrorIfEmailInExistInSystem() {
        String email = faker.expression("#{letterify '???@????.???'}");
        String userName = faker.name().username().replace(".", "");
        String username2 = faker.name().username().replace(".", "");
        String password = faker.internet().password(6, 20, true, true).replaceFirst("[A-Z]", faker.regexify("[a-z]"));
        String password2 = faker.internet().password(6, 20, true, true).replaceFirst("[A-Z]", faker.regexify("[a-z]"));

        User user1 = User.builder().userName(userName).email(email).password(password).passwordConfirm(password).build();
        User user2 = User.builder().userName(username2).email(email).password(password2).passwordConfirm(password2).build();

        registrationPageService
                .registration(user1)
                .clickOkButton();

        String actualEmailErrorMessage = registrationPageService
                .registration(user2)
                .getEmailAlreadyExistsMessage();

        Assert.assertEquals(actualEmailErrorMessage, "User with this email is already registered"); //User with this email already exists
    }

    @Description("Ошибка регистрации, если имя пользователя есть в системе")
    @TmsLink(value = "IN-342")
    @Test(groups = "Regression")
    public void verifyErrorIfUsernameExistInSystem() {
        String username = faker.name().username().replace(".", "");
        String email = faker.expression("#{letterify '???@????.???'}");
        String email2 = faker.expression("#{letterify '???@????.???'}");
        String password = faker.internet().password(6, 20, true, true).replaceFirst("[A-Z]", faker.regexify("[a-z]"));
        String password2 = faker.internet().password(6, 20, true, true).replaceFirst("[A-Z]", faker.regexify("[a-z]"));

        User user1 = User.builder().userName(username).email(email).password(password).passwordConfirm(password).build();
        User user2 = User.builder().userName(username).email(email2).password(password2).passwordConfirm(password2).build();

        registrationPageService.registration(user1).clickOkButton();

        String actualErrorMessage = registrationPageService
                .registration(user2)
                .getEmailAlreadyExistsMessage();
        Assert.assertEquals(actualErrorMessage, "User with this username is already registered");
    }

    @Description("Ошибка регистрации пользователя, если пароли (Password и Password confirmation) не совпадают")
    @TmsLink(value = "IN-343")
    @Test(groups = "Regression")
    public void verifyRegistrationErrorWhenPasswordsDontMatch() {
        String userName = faker.name().username().replace(".", "");
        String email = faker.expression("#{letterify '???@????.???'}");
        String password = faker.internet().password(6, 20, true, true).replaceFirst("[A-Z]", faker.regexify("[a-z]"));
        String unmatchingConfirmPassword = faker.internet().password(6, 20, true, true).replaceFirst("[A-Z]", faker.regexify("[a-z]"));

        User user = User.builder().userName(userName).email(email).password(password).passwordConfirm(unmatchingConfirmPassword).build();

        String actualPasswordErrorMessage = registrationPageService
                .registration(user)
                .getPasswordNotMatchDUP();
        Assert.assertEquals(actualPasswordErrorMessage, "Passwords must match");
    }


    @Description("User clicks on the 'Terms of Service' link")
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
}
