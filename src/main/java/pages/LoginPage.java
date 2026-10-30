package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import utilities.Log;

public class LoginPage {

	private WebDriver driver;

	@FindBy(id = "username")
	WebElement usernameTextbox;

	@FindBy(id = "password")
	WebElement passwordTextbox;

	@FindBy(id = "submit")
	WebElement loginButton;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void enterUsername(String username) {

		usernameTextbox.sendKeys(username);

	}

	public void enterPassword(String password) {

		passwordTextbox.sendKeys(password);

	}

	public void clickLogin() {

		Log.info("Clicking login button..");
		loginButton.click();

	}

	public void validateUrl() {

		String actualUrl = "https://practicetestautomation.com/logged-in-successfully/";
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, currentUrl);
		Log.info("URL validated...");
		
	}
}