package com.automationpratice.tests;

import Utils.utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighestPriceItemInBasketTest {

    private WebDriver driver;


    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        String baseUrl = "http://automationpractice.com/index.php";
        driver.manage().window().maximize();
        driver.get(baseUrl);

    }

    @Test(priority = 1)
    public void addMaxPriceDressesToCart() {

        Actions actions = new Actions(driver);

        driver.findElement(By.linkText("DRESSES")).click();
        Assert.assertEquals(getDressesCount().size(), 5);

        List<String> prices = new ArrayList<>();
        driver.findElements(By.cssSelector(".right-block .content_price .price.product-price")).stream().forEach(price -> prices.add(price.getText()));
        List<String> newPrices = new ArrayList<>();
        for (String price : prices) {
            price = price.substring(1);
            newPrices.add(price);
        }
        Collections.sort(newPrices);
        String maxPrice = newPrices.get(newPrices.size() - 1);

        WebElement maxPriceElement = driver.findElement(By.xpath("//*[@id='center_column']/ul/li/div[1]/div[2]/div/span[contains(text()," + maxPrice + ")]"));

        actions.moveToElement(maxPriceElement).click().perform();
        Assert.assertTrue(getProceedToCheckOutBtn().isDisplayed());

        driver.findElement(By.xpath("/html//div[@id='layer_cart']//a[@title='Proceed to checkout']/span")).click();
        Assert.assertTrue(getCartSummaryTable().isDisplayed());
        Assert.assertEquals(getCartSumTotalProductsNum().size(), 1);
        Assert.assertEquals(getCartSumTotalProductsPrice().getText(), "$50.99");

    }


    @AfterClass
    public void closeAll() {
        driver.quit();
    }

    private List<WebElement> getDressesCount() {
        utils.waitForLoad(driver, 10);
        return driver.findElements(By.xpath("//*[@id='center_column']/ul/li"));
    }

    private WebElement getProceedToCheckOutBtn() {
        return utils.waitToBeClickable(driver, By.xpath("//span[contains(text(),'Proceed to checkout')]"), 30);
    }

    private WebElement getCartSummaryTable() {
        return utils.waitForElementToBePresent(driver, By.id("cart_summary"), 30);
    }

    private List<WebElement> getCartSumTotalProductsNum() {
        return driver.findElements(By.xpath("//table[@id='cart_summary']/tbody/tr"));
    }

    private WebElement getCartSumTotalProductsPrice() {
        return utils.waitForElementToBePresent(driver, By.id("total_product"), 30);
    }

}
