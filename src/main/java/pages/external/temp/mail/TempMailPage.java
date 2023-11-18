package pages.external.temp.mail;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.FindBy;
import pages.BaseModel;
import pages.signUp.CongratulationPage;
import pages.signUp.RegistrationPage;
import utils.Waiter;

public class TempMailPage extends BaseModel {

    @FindBy(xpath = "//input[@id='eposta_adres']")
    private WebElement currentEmail;

    @FindBy(xpath = "//li[@class='mail ']/a")
    private WebElement receivedMail;

    @FindBy(xpath = "//a[contains(@href, 'inctagram')]")
    private WebElement setUpAccountButtonInEmail;

    @FindBy(xpath = "(//button[@data-clipboard-target='#mail'])[2]")
    private WebElement copyToClipboard;

    @FindBy(xpath = "//a[@class='yenile-link']")
    private WebElement refreshButton;

    @FindBy(xpath = "//iframe[@id='iframe']")
    private WebElement emailFrame;

    @Step
    public TempMailPage clickRefreshButton() {
        Waiter.waitVisibilityOfElement5Second(refreshButton).click();
        return this;
    }

    public TempMailPage switchToEmailFrame() {
        driver.switchTo().frame(emailFrame);
        return this;
    }

    public TempMailPage clickCopyToClipboard() {
        copyToClipboard.click();
        return this;
    }

    @Step("Get {currentEmail} email from temp mail")
    public String getCurrentEmail() {
        return Waiter.waitVisibilityOfElement5Second(currentEmail).getDomProperty("value");
    }


    public TempMailPage getReceivedMail() {
        Waiter.waitVisibilityOfElement20Second(receivedMail).click();
        return this;
    }


    public CongratulationPage getSetUpAccountButtonInEmail() {
        Waiter.waitVisibilityOfElement10Second(setUpAccountButtonInEmail).click();
        return new CongratulationPage();
    }


    public CongratulationPage switchToCongratulationPage() {
        String originalWindow = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(winHandle)) {
                driver.close();
                driver.switchTo().window(winHandle);
                break;
            }
        }
        return new CongratulationPage();
    }

    @Step("Open {url}")
    public TempMailPage open(String url) {
        driver.get(url);
        return new TempMailPage();
    }

    public RegistrationPage openNewTabToRegistrationPage(String url) {
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
        return new RegistrationPage();
    }

}
