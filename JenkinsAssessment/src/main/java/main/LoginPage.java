package main;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	@FindBy(id = "j_username")
	WebElement usernameField;
	
	@FindBy(name = "j_password")
	WebElement passwordField;
	
	@FindBy(className = "submit-button")
	WebElement button;
	
	public void login(String username, String password) {
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		button.submit();
	}
}
