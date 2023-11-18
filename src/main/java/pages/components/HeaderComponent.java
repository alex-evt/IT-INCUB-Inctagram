package pages.components;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BaseModel;
import pages.MyProfilePage;
import utils.Waiter;

public class HeaderComponent<Page> extends BaseModel {


    @FindBy(xpath = "//header//button")
    private WebElement languageChanger;

    @FindBy(xpath = "//div[text()='Russian']")
    private WebElement russianLanguage;

    @FindBy(xpath = "//div[text()='English']")
    private WebElement englishLanguage;

    @FindBy(xpath = "//a[text() = 'Inctagram']")
    private WebElement logo;

    public MyProfilePage clickLogo() {
        logo.click();
        return new MyProfilePage();
    }

    public String getCurrentLanguage() {
        return Waiter.waitVisibilityOfElement5Second(languageChanger).getText();
    }

    public HeaderComponent<Page> clickLanguageChanger() { //исправить
        Waiter.waitVisibilityOfElement5Second(languageChanger);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", languageChanger);
//        Waiter.waitVisibilityOfElement5Second(languageChanger).click();
        return this;
    }

    public Page clickRussianLanguage() { //исправить
        Waiter.waitVisibilityOfElement5Second(russianLanguage);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", russianLanguage);
//        Waiter.waitVisibilityOfElement10Second(russianLanguage).click();
        return (Page) this;
    }
}
