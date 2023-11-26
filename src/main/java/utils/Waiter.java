package utils;

import driver.DriverSingleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waiter {

    private Waiter() {
    }

    private static final int WAIT_FIVE_SECONDS = 5;
    private static final int WAIT_TEN_SECONDS = 10;
    private static final int WAIT_TWENTY_SECONDS = 20;

    public static WebElement waitVisibilityOfElement5Second(WebElement element) {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_FIVE_SECONDS))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitVisibilityOfElement10Second(WebElement element) {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_TEN_SECONDS))
                .until(ExpectedConditions.visibilityOf(element));
    }


    public static WebElement waitVisibilityOfElement20Second(WebElement element) {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_TWENTY_SECONDS))
                .until(ExpectedConditions.visibilityOf(element));
    }

}
