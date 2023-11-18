package tests;

import io.qameta.allure.Description;
import models.User;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.LoginPageService;
import services.RegistrationPageService;

public class LoginPageTest extends BaseTest {

    Faker faker = new Faker();
    LoginPageService loginPageService = new LoginPageService();
    RegistrationPageService registrationPageService = new RegistrationPageService();


    @Description("Successful login to the system with email confirmation")
    @Test(groups = "Regression")
    public void verifySuccessfulLogin() {
//       User registeredUser = User.readUserDataFromFile();
        String username = faker.name().username().replace(".", "");
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{1}[!]{2}");
        String currentTempEmail = registrationPageService
                .openTempMailAndGetCurrentEmail();

        User user = User.builder().userName(username).email(currentTempEmail).password(password).passwordConfirm(password).build();

        registrationPageService
                .registrationWithConfirmation(user);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String textHomeButton = loginPageService
                .login(user).getTextHomeButton();
        Assert.assertEquals(textHomeButton, "Home");
    }

    @Description("Unsuccessful login to the system without email confirmation")
    @Test(groups = "Regression")
    public void verifyUnsuccessfulLoginEmailUnconfirmed() {
        String userName = faker.name().username().replace(".", "");
        String email = faker.expression("#{letterify '???@????.???'}");
        String password = faker.regexify("[A-Z]{2}[a-z]{2}[0-9]{2}[!]{1}");
        User user = User.builder().userName(userName).email(email).password(password).passwordConfirm(password).build();

        registrationPageService.registrationWithoutConfirmation(user);

        String actualAlertMessage = loginPageService.unsuccessfulLogin(user).getErrorAlert();
        Assert.assertEquals(actualAlertMessage, "Confirmation code is invalid");
    }

    @Description("Unsuccessful authorization for a non-existent user")
    @Test(groups = "Regression")
    public void verifyUnsuccessfulLoginNonexistentUser() {
        String email = "nnn000n@ooooon.ozn";
        String password = "Test1!";
        User user = User.builder().email(email).password(password).build();

        String actualAlertMessage = loginPageService
                .unsuccessfulLogin(user)
                .getErrorAlert();
        Assert.assertEquals(actualAlertMessage, "invalid password or email");
    }

    @Description("Authorization error when invalid email entered")
    @Test(groups = "Regression")
    public void verifyErrorInvalidEmail() {
        String invalidEmail = "Jipeeer@keeeeperz.ol";
        String validPassword = User.readUserDataFromFile().getPassword();
        User user = User.builder().email(invalidEmail).password(validPassword).build();

        String actualAlertMessage = loginPageService.unsuccessfulLogin(user).getErrorAlert();
        Assert.assertEquals(actualAlertMessage, "invalid password or email");
    }

    @Description("Authorization error when invalid password entered")
    @Test(groups = "Regression")
    public void verifyErrorInvalidPassword() {
        String validEmail = User.readUserDataFromFile().getEmail();
        String invalidPassword = "mkgfc@8000";
        User user = User.builder().email(validEmail).password(invalidPassword).build();

        String actualAlertMessage = loginPageService.unsuccessfulLogin(user).getErrorAlert();
        Assert.assertEquals(actualAlertMessage, "invalid password or email");
    }

}
