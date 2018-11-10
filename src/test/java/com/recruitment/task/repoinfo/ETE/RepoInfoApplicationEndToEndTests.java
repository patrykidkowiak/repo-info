package com.recruitment.task.repoinfo.ETE;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.TestCase.assertTrue;

public class RepoInfoApplicationEndToEndTests {
    private WebDriver driver;
    private WebDriverWait wait;


    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost:4200/");
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void pageLoadedSuccesfull() {
        String dataOnPage = driver.getPageSource();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mainContainer")));

        assertTrue(dataOnPage.contains("Repository Info"));
    }

    @Test
    public void repositoryInformationLoadedSuccesfull() {
        driver.findElement(By.id("gitHubUser")).sendKeys("patrykidkowiak");
        driver.findElement(By.id("repositoryName")).sendKeys("currency-manager");

        By getRepositorybutton = By.id("getRepository");
        driver.findElement(getRepositorybutton).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("repositoryDetails")));
        String dataOnPage = driver.getPageSource();

        assertTrue(dataOnPage.contains("clone url"));
        assertTrue(dataOnPage.contains("https://github.com/patrykidkowiak/currency-manager.git"));
        assertTrue(dataOnPage.contains("full name"));
        assertTrue(dataOnPage.contains("patrykidkowiak/currency-manager"));
        assertTrue(dataOnPage.contains("created at"));
        assertTrue(dataOnPage.contains("description"));

    }

    @Test
    public void shouldGenerateAlert() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElement(By.id("gitHubUser")).sendKeys("XXXX");
        driver.findElement(By.id("repositoryName")).sendKeys("XXXX");

        By getRepositorybutton = By.id("getRepository");
        driver.findElement(getRepositorybutton).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("alert")));
        String dataOnPage = driver.getPageSource();

        assertTrue(dataOnPage.contains("Something went wrong. Check user or repository name and try again."));
    }

}
