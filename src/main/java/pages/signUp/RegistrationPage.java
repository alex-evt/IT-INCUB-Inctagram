package pages.signUp;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.HeaderComponent;
import pages.signIn.LoginPage;
import utils.Waiter;

public class RegistrationPage extends BasePage<HeaderComponent> {

    @FindBy(xpath = "//input[@id='sign-up-userName']")
    private WebElement userNameField;

    @FindBy(xpath = "//input[@id='sign-up-email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@id='sign-up-password']")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@id='sign-up-passwordConfirm']")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//input[@id='sign-up-agreemets']")
    private WebElement agreementsCheckbox;

    @FindBy(xpath = "//a[@href='./agreemets-page/terms-of-service']")
    private WebElement serviceTermsLink;

    @FindBy(xpath = "//a[@href='./agreemets-page/privacy-policy']")
    private WebElement privacyPolicyLink;

    @FindBy(xpath = "//input[@id='sign-up-submit']")
    private WebElement signUpButton;

    @FindBy(xpath = "//a[@id='sign-up-link-to-sign-in']")
    private WebElement signInButton;

    @FindBy(xpath = "//input[@id='sign-up-password']/following-sibling::img")
    private WebElement passwordFieldEye;

    @FindBy(xpath = "//input[@id='sign-up-passwordConfirm']/following-sibling::img")
    private WebElement confirmPasswordFieldEye;

    //hints
    @FindBy(xpath = "(//p[@id='sign-up-userName-error']) [1]")
    private WebElement userNameErrorMessage;

    @FindBy(xpath = "(//p[@id='sign-up-userName-error']) [2]")
    private WebElement emailErrorMessage;

    @FindBy(xpath = "(//p[@id='sign-up-userName-error']) [3]")
    private WebElement passwordErrorMessage;

    @FindBy(xpath = "(//p[@id='sign-up-userName-error']) [4]")
    private WebElement confirmPasswordErrorMessage;

    @FindBy(xpath = "(//p[@id='sign-up-userName-error']) [5]")
    private WebElement agreementsErrorMessage;

    @FindBy(xpath = ("//p[@id='sign-up-userName-error']")) //!!!!! DUPLICATED
    private WebElement emailAlreadyExistsMessageDUP;

    @FindBy(xpath = "//p[@id='sign-up-userName-error']") //!!!! DUPLICATED
    private WebElement passwordNotMatchDUP;

    //modal popup
    @FindBy(xpath = "//div[@class='modal__body']")
    private WebElement successfulEmailSentMessage;

    @FindBy(xpath = "//div[@class='modal__footer']/button")
    private WebElement emailSentMessageOkButton;

    @Step("Ввести {userName} в поле Username")
    public RegistrationPage fillInUserName(String userName) {
        Waiter.waitVisibilityOfElement5Second(userNameField).sendKeys(userName);
        return this;
    }

    @Step("Ввести {email} в поле Email")
    public RegistrationPage fillInEmail(String email) {
        emailField.sendKeys(email);
        return this;
    }

    @Step("Ввести {password} в поле Password")
    public RegistrationPage fillInPassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Ввести {confirmationPassword} в поле Password confirmation")
    public RegistrationPage fillInConfirmationPassword(String confirmationPassword) {
        confirmPasswordField.sendKeys(confirmationPassword);
        return this;
    }

    @Step("Нажать на чекбокс")
    public RegistrationPage clickAgreementsCheckbox() {
        agreementsCheckbox.click();
        return this;
    }

    public TermsOfServicePage clickServiceTermsLink() {
        Waiter.waitVisibilityOfElement10Second(serviceTermsLink).click();
        return new TermsOfServicePage();
    }

    public PrivacyPolicyPage clickPrivacyPolicyLink() {
        Waiter.waitVisibilityOfElement5Second(privacyPolicyLink).click();
        return new PrivacyPolicyPage();
    }

    @Step("Нажать кнопку Sign Up")
    public RegistrationPage clickSignUp() {
        signUpButton.click();
        return new RegistrationPage();
    }

    public LoginPage clickSignIn() {
        signInButton.click();
        return new LoginPage();
    }

    public RegistrationPage clickPasswordEye() {
        passwordFieldEye.click();
        return this;
    }

    public RegistrationPage clickConfirmPasswordEye() {
        confirmPasswordFieldEye.click();
        return this;
    }

    public String getUserNameErrorMessage() {
        return userNameErrorMessage.getText();
    }

    public String getEmailErrorMessage() {
        return Waiter.waitVisibilityOfElement5Second(emailErrorMessage).getText();
    }

    public String getPasswordErrorMessage() {
        return passwordErrorMessage.getText();
    }

    public String getConfirmPasswordErrorMessage() {
        return confirmPasswordErrorMessage.getText();
    }

    public String getAgreementsErrorMessage() {
        return agreementsErrorMessage.getText();
    }

    public String getEmailAlreadyExistsMessage() {
        return Waiter.waitVisibilityOfElement10Second(emailAlreadyExistsMessageDUP).getText();
    }

    public String getPasswordNotMatchDUP() {
        return Waiter.waitVisibilityOfElement5Second(passwordNotMatchDUP).getText();
    }

    public String getSuccessfulEmailSentMessage() {
        return Waiter.waitVisibilityOfElement20Second(successfulEmailSentMessage).getText();
    }

    @Step("Нажать кнопку OK")
    public RegistrationPage clickOkButton() {
        Waiter.waitVisibilityOfElement5Second(emailSentMessageOkButton).click();
        return this;
    }


    @Step("Открыть страницу {url}")
    public RegistrationPage open(String url) {
        driver.get(url);
        return this;
    }

    @Override
    public HeaderComponent getHeader() {
        return new HeaderComponent();
    }
}
