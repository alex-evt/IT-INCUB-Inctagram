package driver;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;

@Log4j2
public class DriverSingleton {

    private final static ThreadLocal<DriverSingleton> instance = new ThreadLocal<>();
    private WebDriver driver;

    private DriverSingleton() {
        driver = WebDriverFactory.getWebDriver();
    }

    public static synchronized DriverSingleton getInstance() {
        if (instance.get() == null) {
            instance.set(new DriverSingleton());
            log.info("Instance of the driver was created");
        }
        return instance.get();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void closeDriver() {
        try {
            driver.quit();
            driver = null;
            log.info("Driver closed successfully");
        } catch (Exception e) {
            log.fatal("Driver wasn't closed" + e.getMessage());
        } finally {
            instance.remove();
        }
    }
}
