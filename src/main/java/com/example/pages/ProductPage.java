package com.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage extends BasePage {

    @FindBy(css = "button[data-qa='PRD_AddToBasket']")
    private WebElement addToBasketButton;

    @FindBy(xpath = "//div[@data-qa='PRD_TotalUpfront']")
    private WebElement startAmount;

    @FindBy(xpath = "//div[@class='sc-gzzPqb jkzHzl dt_typography variant_h4 value']//div[@class='dt_price_change']//div[contains(@class, 'sc-gzzPqb') and contains(@class, 'jkzHzl') and contains(@class, 'dt_typography') and contains(@class, 'variant_h4')]")
    private WebElement monthlyRateAmount;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickUsingJavaScript(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public BasketPage addToBasket() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Sprawdzenie, czy element jest obecny w DOM
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[data-qa='PRD_AddToBasket']")));

        // Przewinięcie strony do przycisku za pomocą JavaScript
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", addToBasketButton);
        try {
            Thread.sleep(2000); // Krótkie opóźnienie, aby upewnić się, że przewinięcie się zakończyło
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        // Wypisanie informacji o rodzicu przycisku
        WebElement parentElement = addToBasketButton.findElement(By.xpath(".."));
        String parentTagName = parentElement.getTagName();
        String parentOuterHTML = parentElement.getAttribute("outerHTML");
        System.out.println("Tag rodzica: " + parentTagName);
        System.out.println("Pełny HTML rodzica: " + parentOuterHTML);

        // Kliknięcie przycisku za pomocą JavaScript
        clickUsingJavaScript(addToBasketButton);
        System.out.println("Wywołanie metody clickUsingJavaScript(addToBasketButton) przebiegło pomyślnie.");

        return new BasketPage(driver);
    }

    public boolean isElementVisible(WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getStartAmount() {
        if (isElementVisible(startAmount, 5)) {
            String amountText = startAmount.getText();
            return extractNumericValueAsString(amountText);
        } else {
            throw new RuntimeException("Element 'startAmount' is not visible on the page.");
        }
    }

    public String getMonthlyRateAmount() {
        if (isElementVisible(monthlyRateAmount, 5)) {
            String amountText = monthlyRateAmount.getText();
            return extractNumericValueAsString(amountText);
        } else {
            throw new RuntimeException("Element 'monthlyRateAmount' is not visible on the page.");
        }
    }

    private String extractNumericValueAsString(String text) {
        // Usuń wszystkie znaki nie będące cyframi, kropką ani przecinkiem
        String numericText = text.replaceAll("[^\\d.,]", "");
        return numericText;
    }
}