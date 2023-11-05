package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BaseModel {


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
        return languageChanger.getText();
    }


}
