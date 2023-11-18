package pages.signIn;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.MyProfilePage;
import pages.components.HeaderComponent;
import pages.signUp.RegistrationPage;
import utils.Waiter;

public class LoginPage extends BasePage<HeaderComponent> {

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;

    @FindBy(xpath = "(//p[@id = 'sign-up-userName-error']) [1]")
    private WebElement emailErrorMessage;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "(//p[@id = 'sign-up-userName-error']) [2]")
    private WebElement passwordErrorMessage;

    @FindBy(xpath = "//img[contains(@id, 'showPassImage')]") //???
    private WebElement passwordEye;

    @FindBy(xpath = "//a[@id = 'sign-in-link-forgot-password']")
    private WebElement forgotPasswordButton;

    @FindBy(xpath = "//input[@id = 'sign-in-submit']")
    private WebElement signInButton;

    @FindBy(xpath = "//a[@id = 'sign-in-link-sign-up']")
    private WebElement signUpButton;

    @FindBy(xpath = "//div[@role='alert']")
    private WebElement errorAlert;

    @FindBy(xpath = "//button[@type='button']")
    private WebElement languageButton;

    @FindBy(xpath = "//div[text() = 'English']")
    private WebElement englishLanguage;

    @FindBy(xpath = "//div[text() = 'Russian']")
    private WebElement russianLanguage;

    public LoginPage fillInEmail(String email) {
        Waiter.waitVisibilityOfElement10Second(emailField).sendKeys(email);
        return this;
    }

    public String getEmailErrorMessage() {
        return emailErrorMessage.getText();
    }

    public LoginPage fillInPassword(String password) {
        Waiter.waitVisibilityOfElement5Second(passwordField).sendKeys(password);
        return this;
    }

    public String getPasswordErrorMessage() {
        return passwordErrorMessage.getText();
    }

    public MyProfilePage clickSignIn() {
        signInButton.click();
        return new MyProfilePage();
    }

    public LoginPage clickForgotPassword() {
        forgotPasswordButton.click();
        return this;
    }

    public String getErrorAlert() {
        return Waiter.waitVisibilityOfElement5Second(errorAlert).getText();
    }

    public RegistrationPage clickSignUp() { //signUp
        signUpButton.click();
        return new RegistrationPage();
    }

    public LoginPage open(String url) {
        driver.get(url);
        return new LoginPage();
    }


    @Override
    public HeaderComponent getHeader() {
        return new HeaderComponent();
    }
}
