package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import main.LoginPage;

public class JUnitTest {
	WebDriver driver;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Owner\\Desktop\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@After
	public void teardown() {
		driver.quit();
	}
	
	@Test
	public void newItemTest() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get("http://35.197.196.128:8080");
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.login("admin", "admin");
		driver.get("http://35.197.196.128:8080/view/all/newJob");
		Thread.sleep(2000);
		driver.findElement(By.id("name")).sendKeys("AutomatedItem");
		driver.findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]")).click();
		driver.findElement(By.id("ok-button")).submit();
		driver.findElement(By.id("yui-gen38-button")).click();
		driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/a[2]")).click();
		String text = driver.findElement(By.id("job_AutomatedItem")).getText();
		assertEquals("Item was not formed properly, or not found!", "AutomatedItem N/A N/A N/A  ", text);
	}
}
