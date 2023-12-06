package pages.signIn;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.MyProfilePage;
import pages.components.HeaderComponent;
import pages.signUp.RegistrationPage;
import utils.Waiter;

@Log4j2
public class LoginPage extends BasePage<HeaderComponent> {

    @FindBy(xpath = "//div[@id='sign-in']/p")
    private WebElement title;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;

    @FindBy(xpath = "//p[@id = 'sign-in-email-input']")
    private WebElement emailErrorMessage;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//p[@id='sign-in-password-input']")
    private WebElement passwordErrorMessage;

    @FindBy(xpath = "//img[contains(@id, 'showPassImage')]") //???
    private WebElement passwordEye;

    @FindBy(xpath = "//a[@id = 'sign-in-link-forgot-password']")
    private WebElement forgotPasswordButton;

    @FindBy(xpath = "//input[@id = 'sign-in-submit']")
    private WebElement signInButton;

    @FindBy(xpath = "//a[@id = 'sign-in-link-sign-up']")
    private WebElement signUpButton;


    @FindBy(xpath = "//button[@type='button']")
    private WebElement languageButton;

    @FindBy(xpath = "//div[text() = 'English']")
    private WebElement englishLanguage;

    @FindBy(xpath = "//div[text() = 'Russian']")
    private WebElement russianLanguage;

    @Step("Fill in {email} in Email field")
    public LoginPage fillInEmail(String email) {
        log.info("Fill in '{}' in Email field", email);
        Waiter.waitVisibilityOfElement10Second(emailField).sendKeys(email);
        return this;
    }

    public String getEmailErrorMessage() {
        return emailErrorMessage.getText();
    }

    @Step("Fill in {password} in Password field")
    public LoginPage fillInPassword(String password) {
        log.info("Fill in '{}' in Password field", password);
        Waiter.waitVisibilityOfElement5Second(passwordField).sendKeys(password);
        return this;
    }

    public String getPasswordErrorMessage() {
        return Waiter.waitVisibilityOfElement5Second(passwordErrorMessage).getText();
    }

    @Step("Click 'Sign In'")
    public MyProfilePage clickSignIn() {
        signInButton.click();
        return new MyProfilePage();
    }

    public LoginPage clickForgotPassword() {
        forgotPasswordButton.click();
        return this;
    }

    public RegistrationPage clickSignUp() { //signUp
        signUpButton.click();
        return new RegistrationPage();
    }

    public LoginPage open(String url) {
        driver.get(url);
        return new LoginPage();
    }

    @Step("Get title 'Sign In'")
    public String getTitle(){
        return Waiter.waitVisibilityOfElement5Second(title).getText();
    }

    @Override
    public HeaderComponent getHeader() {
        return new HeaderComponent();
    }
}
