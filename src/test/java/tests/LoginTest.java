package tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utilities.ExcelUtils;
import utilities.ExtentReportManager;
import utilities.Log;

public class LoginTest extends BaseTest {
	
	@DataProvider(name = "LoginData")
	public Object[][] getLoginData(Method method)
	        throws IOException {

	    String filePath =
	        System.getProperty("user.dir")
	        + "/testData/TestData.xlsx";

	    ExcelUtils.loadExcel(filePath, "Sheet1");

	    List<HashMap<String,String>> dataList =
	            ExcelUtils.getTestData(method.getName());

	    ExcelUtils.closeExcel();

	    Object[][] data =
	            new Object[dataList.size()][1];

	    for (int i = 0; i < dataList.size(); i++) {
	        data[i][0] = dataList.get(i);
	    }

	    return data;
	}
	
	@DataProvider(name="LoginData2")
	public Object[][] getData(){
		
		return new Object[][] {
			{"user1","pass1"},
			{"user2","pass2"},
			{"user3","pass3"}
		};
	}
	
//	@Test(dataProvider = "LoginData2")
//	@Parameters({"username","password"})
	
	@Test(dataProvider = "LoginData")
	public void testValidLogin(HashMap<String, String> data) {

		Log.info("Starting login test...");
		test = ExtentReportManager.createTest("Login Test - ");

		test.info("Navigating to URL");
		LoginPage loginPage = new LoginPage(driver);

		Log.info("Adding credentials");
		test.info("Adding Credentails");
		loginPage.enterUsername(data.get("username"));
		loginPage.enterPassword(data.get("password"));
		test.info("Clicking on Login button");
		loginPage.clickLogin();

		loginPage.validateUrl();
		test.info("Verifying page title");

		test.pass("Login Successful");

	}

}