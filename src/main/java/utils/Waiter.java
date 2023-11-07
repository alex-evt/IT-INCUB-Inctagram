package utils;

import driver.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Waiter {

    private Waiter() {
    }

    private static final int WAIT_FIVE_SECONDS = 5;
    private static final int WAIT_TEN_SECONDS = 10;
    private static final int WAIT_TWENTY_SECONDS = 20;

    public static WebElement waitVisibilityOfElement5Second(WebElement webElement) {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_FIVE_SECONDS))
                .until(ExpectedConditions.visibilityOf(webElement));
    }

    public static WebElement waitVisibilityOfElement10Second(WebElement webElement) {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_TEN_SECONDS))
                .until(ExpectedConditions.visibilityOf(webElement));
    }

    public static WebElement waitVisibilityOfElement20Second(WebElement webElement) {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_TWENTY_SECONDS))
                .until(ExpectedConditions.visibilityOf(webElement));
    }

    public static WebElement waitElementToBeClickable10Second(WebElement webElement) {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_TEN_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static WebElement waitVisibilityOfElementLocated10Second(String xpathLocator) {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_TEN_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLocator)));
    }

    public static List<WebElement> waitVisibilityOfElements5Seconds(List<WebElement> webElementList) {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_FIVE_SECONDS))
                .until(ExpectedConditions.visibilityOfAllElements());
    }

}
