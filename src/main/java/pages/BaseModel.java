package pages;

import driver.DriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BaseModel {

    protected WebDriver driver = DriverSingleton.getInstance().getDriver();

    protected BaseModel() {
        PageFactory.initElements(driver, this);
    }
}
