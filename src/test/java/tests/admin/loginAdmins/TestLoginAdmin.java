package tests.admin.loginAdmins;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class TestLoginAdmin {
    private static final String adminEmail = "kurcho@abv.bg";
    private static final String adminPassword = "123123123";
    private static final String trueValidationURL = "http://localhost:8000/admin-home";

    WebDriver driver;

    @Before
    public void startUp(){
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8000/career-login");
    }

    @Test
    public void login() throws InterruptedException {
        driver.findElement(By.id("email")).sendKeys(adminEmail);
        driver.findElement(By.id("password")).sendKeys(adminPassword);
        driver.findElement(By.cssSelector("body > div:nth-child(2) > div > div > form > fieldset > div:nth-child(8) > div > button")).click();

        assertTrue(driver.getCurrentUrl().equals(trueValidationURL));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
