package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    @FindBy(xpath = "//div[contains(@class, 'styles__StyledCards-sc-176tmlw-0')]/div[1]//a")
    private WebElement firstProductButton;

    @FindBy(css = "a[title='Koszyk']")
    private WebElement basketIcon;

    @FindBy(css = "div.rounded-full.text-[#e20075].!bg-base-primary-600.!text-white")
    private WebElement itemCount;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void acceptCookies() {
        System.out.println("Checking for cookies popup...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement popUpAgreeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("didomi-notice-agree-button")));

            if (popUpAgreeButton.isDisplayed()) {
                System.out.println("Cookies popup found. Clicking 'Accept'...");
                popUpAgreeButton.click();
                wait.until(ExpectedConditions.invisibilityOf(popUpAgreeButton));
                System.out.println("Cookies popup accepted and closed.");
            }
        } catch (Exception e) {
            System.out.println("Cookies popup not found or another issue: " + e.getMessage());
        }
    }

    public void hoverOverDevicesMenu(String topMenuOption) {
        System.out.println("Hovering over top menu option: " + topMenuOption);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            WebElement devicesMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='" + topMenuOption + "']")));
            Actions actions = new Actions(driver);
            actions.moveToElement(devicesMenu).perform();
            System.out.println("Hovered over top menu option: " + topMenuOption);
        } catch (Exception e) {
            System.out.println("Failed to hover over top menu option: " + e.getMessage());
        }
    }

    public void clickLinkFromColumn(String linkText, String columnText) {
        System.out.println("Attempting to click link: '" + linkText + "' in column: '" + columnText + "'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            WebElement linkElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//p[normalize-space()='" + columnText + "']/ancestor::li//a[contains(@href, 'filter.categoryDevice%5B%5D') and contains(normalize-space(), '" + linkText + "')]")
            ));
            linkElement.click();
            System.out.println("Clicked link: '" + linkText + "' in column: '" + columnText + "'");
        } catch (Exception e) {
            System.out.println("Failed to click link: '" + linkText + "' in column: '" + columnText + "'. Error: " + e.getMessage());
        }
    }

    public ProductPage clickFirstItemInList() {
        System.out.println("Clicking on the first product in the list...");
        try {
            firstProductButton.click();
            System.out.println("Successfully clicked on the first product.");
        } catch (Exception e) {
            System.out.println("Failed to click on the first product. Error: " + e.getMessage());
        }
        return new ProductPage(driver);
    }

    public boolean isBasketIconDisplayed() {
        System.out.println("Checking if the basket icon is displayed...");
        boolean displayed = basketIcon.isDisplayed();
        System.out.println("Basket icon displayed: " + displayed);
        return displayed;
    }

    public int getNumberOfItemsInBasket() {
        System.out.println("Getting the number of items in the basket...");
        try {
            int count = Integer.parseInt(itemCount.getText());
            System.out.println("Number of items in the basket: " + count);
            return count;
        } catch (NumberFormatException e) {
            System.out.println("Failed to get the number of items in the basket. Error: " + e.getMessage());
            return 0;
        }
    }
}
