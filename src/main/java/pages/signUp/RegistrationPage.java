package pages.signUp;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.components.HeaderComponent;
import pages.signIn.LoginPage;
import utils.Waiter;

@Log4j2
public class RegistrationPage extends BasePage<HeaderComponent> {

    @FindBy(xpath = "//input[@id='sign-up-userName']")
    private WebElement userNameField;

    @FindBy(xpath = "//input[@id='sign-up-email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@id='sign-up-password']")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@id='sign-up-passwordConfirm']")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//input[@id='sign-up-agreements']/following-sibling::*[local-name() = 'svg']")
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
    @FindBy(xpath = "//p[@id='sign-up-userName']")
    private WebElement userNameErrorMessage;

    @FindBy(xpath = "(//p[@id='sign-up-email'])")
    private WebElement emailErrorMessage;

    @FindBy(xpath = "(//p[@id='sign-up-password'])")
    private WebElement passwordErrorMessage;

    @FindBy(xpath = "(//p[@id='sign-up-passwordConfirm'])")
    private WebElement confirmPasswordErrorMessage;

    @FindBy(xpath = "(//p[@id='sign-up-agreemets'])")
    private WebElement agreementsErrorMessage;


    //modal popup
    @FindBy(xpath = "//div[@class='modal__body']")
    private WebElement successfulEmailSentMessage;

    @FindBy(xpath = "//div[contains(@class, 'modal__footer')]/button")
    private WebElement emailSentMessageOkButton;

    @Step("Fill in {username} in Username field")
    public RegistrationPage fillInUserName(String username) {
        log.info("Fill in '{}' in Username field", username);
        Waiter.waitVisibilityOfElement5Second(userNameField).sendKeys(username);
        return this;
    }

    @Step("Fill in {email} in Email field")
    public RegistrationPage fillInEmail(String email) {
        log.info("Fill in '{}' in Email field", email);
        Waiter.waitVisibilityOfElement5Second(emailField).sendKeys(email);
        return this;
    }

    @Step("Fill in {password} in Password field")
    public RegistrationPage fillInPassword(String password) {
        log.info("Fill in '{}' in Password field", password);
        Waiter.waitVisibilityOfElement5Second(passwordField).sendKeys(password);
        return this;
    }

    @Step("Fill in {confirmationPassword} in Password confirmation field")
    public RegistrationPage fillInConfirmationPassword(String confirmationPassword) {
        log.info("Fill in '{}' in Password confirmation field", confirmationPassword);
        confirmPasswordField.sendKeys(confirmationPassword);
        return this;
    }

    @Step("Click on the checkbox")
    public RegistrationPage clickAgreementsCheckbox() {
        log.info("Click on the checkbox");
        agreementsCheckbox.click();
        return this;
    }

    @Step("Click Terms of Service")
    public TermsOfServicePage clickServiceTermsLink() {
        Waiter.waitVisibilityOfElement10Second(serviceTermsLink).click();
        return new TermsOfServicePage();
    }

    @Step("Click Privacy Policy")
    public PrivacyPolicyPage clickPrivacyPolicyLink() {
        Waiter.waitVisibilityOfElement5Second(privacyPolicyLink).click();
        return new PrivacyPolicyPage();
    }

    @Step("Click Sign Up")
    public RegistrationPage clickSignUp() {
        signUpButton.click();
        return new RegistrationPage();
    }

    @Step("Is Sign Up button disabled")
    public boolean isSignUpDisabled() {
        return signUpButton.isDisplayed();
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
        return Waiter.waitVisibilityOfElement5Second(userNameErrorMessage).getText();
    }

    @Step("Get Email field error message")
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


    public String getSuccessfulEmailSentMessage() {
        return Waiter.waitVisibilityOfElement20Second(successfulEmailSentMessage).getText();
    }

    @Step("Click OK")
    public RegistrationPage clickOkButton() {
        log.info("Click OK");
        Waiter.waitVisibilityOfElement10Second(emailSentMessageOkButton).click();
        return this;
    }


    @Step("Open {url}")
    public RegistrationPage open(String url) {
        log.info("Open '{}'", url);
        driver.get(url);
        return this;
    }

//    public TempMailPage switchToTempEmailPage() {
//        String originalWindow = driver.getWindowHandle();
//        for (String winHandle : driver.getWindowHandles()) {
//            if (!originalWindow.contentEquals(winHandle)) {
//                driver.close();
//                driver.switchTo().window(winHandle);
//                break;
//            }
//        }
//        return new TempMailPage();
//    }

//    public TempMailPage closeRegistrationTab() {
//        driver.close();
//        return new TempMailPage();
//    }

    @Step("Username field empty")
    public boolean isUsernameEmpty() {
        return userNameField.getAttribute("value").isEmpty();
    }

    @Step("Email field empty")
    public boolean isEmailEmpty() {
        return emailField.getAttribute("value").isEmpty();
    }

    @Step("Password field empty")
    public boolean isPasswordEmpty() {
        return passwordField.getAttribute("value").isEmpty();
    }

    @Step("Password confirmation field empty")
    public boolean isPasswordConfirmationEmpty() {
        return confirmPasswordField.getAttribute("value").isEmpty();
    }

    @Override
    public HeaderComponent getHeader() {
        return new HeaderComponent();
    }


}
