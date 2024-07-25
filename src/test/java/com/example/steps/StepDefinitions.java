package com.example.steps;

import com.example.pages.BasketPage;
import com.example.pages.HomePage;
import com.example.pages.ProductPage;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.*;

public class StepDefinitions {

    private WebDriver driver;
    private HomePage homePage;
    private ProductPage productPage;
    private BasketPage basketPage;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\piotr\\Desktop\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-site-isolation-trials");
        driver = new ChromeDriver(options);
    }

    @Given("the user is on the T-Mobile homepage")
    public void theUserIsOnTheTMobileHomepage() {
        driver.get("https://www.t-mobile.pl/");
        homePage = new HomePage(driver);
        homePage.acceptCookies();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("didomi-notice-agree-button")));

    }


    @When("the user selects {string} from the top menu")
    public void theUserSelectsFromTheTopMenu(String topMenuOption) {
        homePage.hoverOverDevicesMenu(topMenuOption);
    }

    @When("the user clicks {string} from the {string} column")
    public void theUserClicksFromTheColumn(String bezAbonamentu, String innerMenuColumn) {
        homePage.clickLinkFromColumn(bezAbonamentu, innerMenuColumn);
    }

    @When("the user clicks the first item in the list")
    public void theUserClicksTheFirstItemInTheList() {
        productPage = homePage.clickFirstItemInList();
    }

    @When("the user adds the product to the basket")
    public void theUserAddsTheProductToTheBasket() {
        basketPage = productPage.addToBasket();
    }

    @Then("the user should see the basket page with correct prices")
    public void theUserShouldSeeTheBasketPageWithCorrectPrices() {
        assertTrue(basketPage.isBasketPageDisplayed());
        assertEquals(productPage.getStartAmount(), basketPage.getStartAmount());
        assertEquals(productPage.getMonthlyRateAmount(), basketPage.getMonthlyRateAmount());
    }

    @When("the user navigates back to the homepage")
    public void theUserNavigatesBackToTheHomepage() {
        homePage = basketPage.navigateBackToHomePage();
    }

    @Then("the basket icon with correct number of items should be displayed")
    public void theBasketIconWithCorrectNumberOfItemsShouldBeDisplayed() {
        assertTrue(homePage.isBasketIconDisplayed());
        assertEquals(1, homePage.getNumberOfItemsInBasket());
    }

    @After
    public void tearDown() {
        // Zamknięcie przeglądarki po zakończeniu testu
        if (driver != null) {
            driver.quit();
        }
    }
}
