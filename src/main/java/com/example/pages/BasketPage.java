package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasketPage extends BasePage {

    @FindBy(css = "body.page-sklep-basket")
    private WebElement basketPageBody;

    @FindBy(xpath = "//div[@data-qa='BKT_TotalupFront']")
    private WebElement startAmount;

    @FindBy(xpath = "//div[@data-qa='BKT_TotalMonthlySymbol']")
    private WebElement monthlyRateAmount;

    public BasketPage(WebDriver driver) {
        super(driver);
    }

    public boolean isBasketPageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        System.out.println("Checking if the basket page is displayed...");
        try {
            wait.until(ExpectedConditions.visibilityOf(basketPageBody));
            boolean isDisplayed = basketPageBody.isDisplayed();
            System.out.println("Basket page displayed: " + isDisplayed);
            return basketPageBody.isDisplayed();
        } catch (Exception e) {
            System.out.println("Basket page is not displayed. Error: " + e.getMessage());
            return false;
        }
    }

    public String getStartAmount() {
        String amountText = startAmount.getText();
        String numericValue = extractNumericValueAsString(amountText);
        System.out.println("Start amount in basket: " + numericValue);
        return extractNumericValueAsString(amountText);

    }

    public String getMonthlyRateAmount() {
        String amountText = monthlyRateAmount.getText();
        String numericValue = extractNumericValueAsString(amountText);
        System.out.println("Monthly rate amount in basket: " + numericValue);
        return extractNumericValueAsString(amountText);
    }

    private String extractNumericValueAsString(String text) {
        // Usuń wszystkie znaki nie będące cyframi, kropką ani przecinkiem
        String numericText = text.replaceAll("[^\\d.,]", "");
        return numericText;
    }

    public HomePage navigateBackToHomePage() {
        System.out.println("Navigating back to the homepage...");
        driver.get("https://www.t-mobile.pl/");
        System.out.println("Navigated back to the homepage.");
        return new HomePage(driver);
    }
}

