package services;

import io.qameta.allure.Step;
import models.User;
import pages.MyProfilePage;
import pages.signIn.ForgotPasswordPage;
import pages.signIn.LoginPage;
import pages.signUp.RegistrationPage;

import static text.data.PagesURL.LOGIN_PAGE_URL;

public class LoginPageService {

    private final LoginPage loginPage = new LoginPage();

    @Step("Successful login")
    public MyProfilePage login(User user) {
        loginPage
                .open(LOGIN_PAGE_URL)
                .fillInEmail(user.getEmail())
                .fillInPassword(user.getPassword())
                .clickSignIn();
        return new MyProfilePage();
    }

    @Step("Unsuccessful login")
    public LoginPage unsuccessfulLogin(User user) {
        loginPage
                .open(LOGIN_PAGE_URL)
                .fillInEmail(user.getEmail())
                .fillInPassword(user.getPassword())
                .clickSignIn();
        return new LoginPage();
    }

    public RegistrationPage clickSignUp() {
        loginPage
                .clickSignUp();
        return new RegistrationPage();
    }

    public ForgotPasswordPage clickForgotPassword() {
        loginPage
                .clickForgotPassword();
        return new ForgotPasswordPage();
    }

    public LoginPage clickHeader() {
        loginPage.open(LOGIN_PAGE_URL)
                .getHeader()
                .clickLogo();
        return new LoginPage();
    }

}
