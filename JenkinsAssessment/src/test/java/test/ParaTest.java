package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import main.LoginPage;

@RunWith(Parameterized.class)
public class ParaTest {
	WebDriver driver;
	
	@Parameters
	public static Collection<Object[]> data() throws Exception {
		FileInputStream file = new FileInputStream("C:\\Users\\Owner\\eclipse-workspace\\JenkinsAssessment\\AssessmentFriday.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Object[][] ob = new Object[sheet.getPhysicalNumberOfRows()-1][6];
		for (int rowCount = 1; rowCount < sheet.getPhysicalNumberOfRows(); rowCount++) {
			for (int colCount = 0; colCount < 5; colCount++) {
				ob[rowCount-1][colCount] = sheet.getRow(rowCount).getCell(colCount).getStringCellValue();
			}
			ob[rowCount-1][5] = rowCount;
		}
		workbook.close();
	     return Arrays.asList(ob);
	}
	
	private String username;
	private String fullname;
	private String password1;
	private String password2;
	private String email;
	private int rowCount;
	
	public ParaTest(String username, String fullname, String password1, String password2, String email, int rowCount) {
		this.username = username;
		this.fullname = fullname;
		this.password1 = password1;
		this.password2 = password2;
		this.email = email;
		this.rowCount = rowCount;
	}
	
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
	public void newUsersTest() throws IOException {
		driver.manage().window().maximize();
		driver.get("http://35.197.196.128:8080");
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.login("admin", "admin");
		driver.get("http://35.197.196.128:8080/manage");
		driver.findElement(By.xpath("//*[@id=\"main-panel\"]/div[16]/a")).click();
		driver.get("http://35.197.196.128:8080/securityRealm/addUser");
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.name("password1")).sendKeys(password1);
		driver.findElement(By.name("password2")).sendKeys(password2);
		driver.findElement(By.name("fullname")).sendKeys(fullname);
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.id("yui-gen1-button")).submit();
		String result;
		if(driver.getPageSource().contains(username))
		{
		    result = "T";
		}

		else
		{
		    result = "F";
		}
		FileInputStream file = new FileInputStream("C:\\\\Users\\\\Owner\\\\eclipse-workspace\\\\JenkinsAssessment\\\\AssessmentFriday.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(rowCount);
		XSSFCell cell;
		cell = row.getCell(5);
		if (cell == null) {
			cell = row.createCell(5);
		}
		cell.setCellValue(result);
		FileOutputStream fileOut = new FileOutputStream("C:\\\\Users\\\\Owner\\\\eclipse-workspace\\\\JenkinsAssessment\\\\AssessmentFriday.xlsx");
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		workbook.close();
		file.close();
		if (result == "F") {
			Assert.fail();
		}
	}

}
