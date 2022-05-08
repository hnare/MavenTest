package Utils;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class utils {


    public static WebElement waitToBeClickable(WebDriver driver, By selector, int waitInterval) {
        WebElement element = (new WebDriverWait(driver, Duration.ofSeconds(waitInterval))).until(ExpectedConditions.elementToBeClickable(selector));
        return element;
    }


    public static WebElement waitForElementToBePresent(WebDriver driver, By selector, int waitInterval) {
        WebElement element = (new WebDriverWait(driver, Duration.ofSeconds(waitInterval))).until(ExpectedConditions.presenceOfElementLocated(selector));
        return element;
    }


    public static void waitForLoad(WebDriver driver, int waitInterval) {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitInterval));
        wait.until(pageLoadCondition);
    }

}