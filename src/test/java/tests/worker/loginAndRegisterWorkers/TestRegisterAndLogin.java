package tests.worker.loginAndRegisterWorkers;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class TestRegisterAndLogin {
    private static final String workerUsername = "Test";
    private static final String workerPassword = "123123123";
    private static final String workerEmail = "testSel@abv.bg";
    private static final String workerCv = "This is my first Test With Selenium";
    private static final String firstTrueURL = "http://localhost:8000/career-home-for-noobs";
    private static final String secondTrueURL = "http://localhost:8000/career-home";
    private static final String alertMessage = "Worker successful registered";
    private static final String erorUrl = "http://localhost:8000/career-error";

    WebDriver driver;

    @Before
    public void startUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8000/");
    }


    @Test
    public void register() throws InterruptedException {
        WebElement careerButon = driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(3) > a"));
        careerButon.click();
        WebElement registerButon = driver.findElement(By.className("myButton"));
        registerButon.click();

        driver.findElement(By.id("fullName")).sendKeys(workerUsername);
        driver.findElement(By.id("email")).sendKeys(workerEmail);
        driver.findElement(By.id("password")).sendKeys(workerPassword);
        driver.findElement(By.id("cv")).sendKeys(workerCv);

        WebElement register = driver.findElement(By.cssSelector("body > div.container > div > div > div > form > fieldset > div:nth-child(9) > div > button"));
        register.click();

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();

        assertTrue(alertText.equals(alertMessage));
    }

    @Test
    public void login() throws InterruptedException {
        driver.get("http://localhost:8000/career-login");
        driver.findElement(By.id("email")).sendKeys(workerUsername);
        driver.findElement(By.id("password")).sendKeys(workerPassword);
        driver.findElement(By.cssSelector("body > div:nth-child(2) > div > div > form > fieldset > div:nth-child(8) > div > button")).click();

        assertTrue(driver.getCurrentUrl().equals(firstTrueURL) || driver.getCurrentUrl().equals(secondTrueURL));
    }

    @Test
    public void loginWithoutUsername() throws InterruptedException {
        driver.get("http://localhost:8000/career-login");
        driver.findElement(By.id("password")).sendKeys(workerPassword);
        driver.findElement(By.cssSelector("body > div:nth-child(2) > div > div > form > fieldset > div:nth-child(8) > div > button")).click();

        assertTrue(driver.getCurrentUrl().equals(erorUrl));
    }

    @Test
    public void loginWithoutPassword() throws InterruptedException {
        driver.get("http://localhost:8000/career-login");
        driver.findElement(By.id("email")).sendKeys(workerUsername);
        driver.findElement(By.cssSelector("body > div:nth-child(2) > div > div > form > fieldset > div:nth-child(8) > div > button")).click();

        assertTrue(driver.getCurrentUrl().equals(erorUrl));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
