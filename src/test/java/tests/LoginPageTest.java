package tests;

import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import models.User;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.LoginPageService;
import services.MyProfileService;
import services.RegistrationPageService;
import utils.Utility;

public class LoginPageTest extends BaseTest {

    private final String expectedErrorEmailPassword = "The email or password are incorrect. Try again please";
    private LoginPageService loginPageService;
    private MyProfileService myProfileService;
    private RegistrationPageService registrationPageService;
    @BeforeClass
    public void init() {
        loginPageService = new LoginPageService();
        myProfileService = new MyProfileService();
        registrationPageService = new RegistrationPageService();
    }

    @Description("Successful login to the system with email confirmation")
    @TmsLink("IN-354")
    @Test(groups = "SuccessfulLogin")
    public void verifySuccessfulLogin() {
        User registeredUser = User.readUserDataFromFile();
        String textHomeButton = loginPageService
                .login(registeredUser).getTextHomeButton();
        Assert.assertEquals(textHomeButton, "Home");
    }

    @Description("Unsuccessful login to the system without email confirmation")
    @TmsLink("IN-357")
    @Test
    public void verifyUnsuccessfulLoginEmailUnconfirmed() {
        String userName = Utility.getRandomUsername();
        String email = Utility.getRandomEmail();
        String password = Utility.getRandomPassword();
        User user = User.builder().userName(userName).email(email).password(password).passwordConfirm(password).build();

        registrationPageService.registrationWithoutConfirmation(user);

        String actualErrorMessage = loginPageService
                .unsuccessfulLogin(user)
                .getPasswordErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorEmailPassword);
    }

    @Description("Unsuccessful authorization for a non-existent user")
    @TmsLink("IN-358")
    @Test
    public void verifyUnsuccessfulLoginNonexistentUser() {
        String email = "nnn000n@ooooon.ozn";
        String password = "Test1!";
        User user = User.builder().email(email).password(password).build();

        String actualErrorMessage = loginPageService
                .unsuccessfulLogin(user)
                .getPasswordErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorEmailPassword);
    }

    @Description("Authorization error when entering an invalid email address")
    @TmsLink("IN-355")
    @Test
    public void verifyErrorInvalidEmail() {
        String invalidEmail = "Jipeeer@keeeeperz.ol";
        String validPassword = User.readUserDataFromFile().getPassword();
        User user = User.builder().email(invalidEmail).password(validPassword).build();

        String actualErrorMessage = loginPageService
                .unsuccessfulLogin(user)
                .getPasswordErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorEmailPassword);
    }

    @Description("Authorization error when entering an invalid password")
    @TmsLink("IN-356")
    @Test
    public void verifyErrorInvalidPassword() {
        String validEmail = User.readUserDataFromFile().getEmail();
        String invalidPassword = "mkgfc@8000";
        User user = User.builder().email(validEmail).password(invalidPassword).build();

        String actualErrorMessage = loginPageService
                .unsuccessfulLogin(user)
                .getPasswordErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorEmailPassword);
    }

    @Description("Successful log out from account of the system")
    @TmsLink("IN-365")
    @Test
    public void verifySuccessfulLogout() {
        User registeredUser = User.readUserDataFromFile();
        loginPageService
                .login(registeredUser);
        String actualTitle = myProfileService
                .logOut()
                .getTitle();
        Assert.assertEquals(actualTitle, "Sign In");
    }

    @Description("Successful log out from account of the system")
    @TmsLink("IN-366")
    @Test(groups = "SuccessfulLogin")
    public void verifyCancelLogout() {
        User registeredUser = User.readUserDataFromFile();
        loginPageService
                .login(registeredUser);
        String textHomeButton = myProfileService.cancelLogout().getTextHomeButton();
        Assert.assertEquals(textHomeButton, "Home");
    }

    @AfterMethod(onlyForGroups = "SuccessfulLogin")
    public void logout() {
        myProfileService.logOut().getTitle();
    }
}
