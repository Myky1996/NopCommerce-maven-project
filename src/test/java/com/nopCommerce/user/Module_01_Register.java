package com.nopCommerce.user;

import java.lang.reflect.Method;
import java.util.Random;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import commons.BaseTest;
import commons.PageGeneratorManager;
import enviromentConfig.Enviroment;
import pageObjects.nopCommerce.user.HomePageObject;
import pageObjects.nopCommerce.user.LoginPO;
import pageObjects.nopCommerce.user.registerPO;
import reportConfig.ExtentTestManager;

public class Module_01_Register extends BaseTest {
	Enviroment enviroment;
	WebDriver driver;
	HomePageObject homePage;
	registerPO registerPage;
	LoginPO loginPage;

	String firstName, lastName, email, password, confirmPassword, invalidpassword;

	@Parameters({ "envName", "severName", "browser", "ipAddress", "portNumber", "osName" })
	@BeforeClass
	public void BeforeClass(@Optional("local") String envName, @Optional("testing") String severName,
			@Optional("chrome") String browserName, @Optional("localhost") String ipAddress,
			@Optional("4444") String portNumber, @Optional("Windows 10") String osName, Method method) {

		String enviromentName = System.getProperty("envMaven");
		ConfigFactory.setProperty("envOwner", enviromentName);
		enviroment = ConfigFactory.create(Enviroment.class);

		firstName = "Ky";
		lastName = "My";
		email = "mickey" + getRandomNumber() + "@yopmail.com";
		password = "12345678";
		invalidpassword = "123";

		driver = getBrowserDriver(envName, enviroment.userAppUrl(), browserName, ipAddress, portNumber, osName);
		homePage = PageGeneratorManager.getHompageObject(driver);

		homePage.openHeaderFooterPageByText(driver, "Register");
		registerPage = PageGeneratorManager.getRegisterPageObject(driver);
	}

	@Test
	public void TC_01_Register_With_Empty_Data(Method method) {
		ExtentTestManager.startTest(method.getName(), "Register with empty data");
		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 01: Click 'Register' button");
		registerPage.clickToButtonByText(driver, "Register");

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 01: Verify error msg at mandatory fields");
		verifyEquals(registerPage.getEmptyErrorMessageText(driver, "FirstName-error"), "First name is required.");
		verifyEquals(registerPage.getEmptyErrorMessageText(driver, "LastName-error"), "Last name is required.");
		verifyEquals(registerPage.getEmptyErrorMessageText(driver, "Email-error"), "Email is required.");
		verifyEquals(registerPage.getEmptyErrorMessageText(driver, "Password-error"), "Password is required.");
		verifyEquals(registerPage.getEmptyErrorMessageText(driver, "ConfirmPassword-error"), "Password is required.");

	}

	@Test
	public void TC_02_Register_With_Invalid_Data(Method method) {
		ExtentTestManager.startTest(method.getName(), "Register with invalid data");
		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 01: Fill in email textbox");
		registerPage.enterTextToTextboxByName(driver, "Email", "buimyky");

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 02: Verify 'Wrong email'");
		verifyEquals(registerPage.getEmptyErrorMessageText(driver, "Email-error"), "Wrong email");
	}

	@Test
	public void TC_03_Register_Successfully(Method method) {
		ExtentTestManager.startTest(method.getName(), "Register successfully");
		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 01: Fill in firstname textbox");
		registerPage.enterTextToTextboxByName(driver, "FirstName", firstName);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 02: Fill in last textbox");
		registerPage.enterTextToTextboxByName(driver, "LastName", lastName);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 03: Fill in email textbox");
		registerPage.enterTextToTextboxByName(driver, "Email", email);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 04: Fill in password textbox");
		registerPage.enterTextToTextboxByName(driver, "Password", password);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 05: Fill in confirm password textbox");
		registerPage.enterTextToTextboxByName(driver, "ConfirmPassword", password);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 06: Click 'Register' button");
		registerPage.clickToButtonByText(driver, "Register");

		ExtentTestManager.getTest().log(Status.INFO,
				"Register - Step 07: Verify Register Successfully Message Displayed ");
		verifyTrue(registerPage.isRegisterSuccessfullyMessageDisplayed());

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 08: Click 'Register' button");
		registerPage.openHeaderFooterPageByText(driver, "Log out");
		homePage = PageGeneratorManager.getHompageObject(driver);

	}

	@Test
	public void TC_04_Register_With_Existing_Email(Method method) {
		ExtentTestManager.startTest(method.getName(), "Register with existing email");
		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 01: Open 'Register' page");
		homePage.openHeaderFooterPageByText(driver, "Register");
		registerPage = PageGeneratorManager.getRegisterPageObject(driver);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 02: Fill in firstname textbox");
		registerPage.enterTextToTextboxByName(driver, "FirstName", firstName);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 03: Fill in last textbox");
		registerPage.enterTextToTextboxByName(driver, "LastName", lastName);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 04: Fill in email textbox");
		registerPage.enterTextToTextboxByName(driver, "Email", email);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 05: Fill in password textbox");
		registerPage.enterTextToTextboxByName(driver, "Password", password);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 06: Fill in confirm password textbox");
		registerPage.enterTextToTextboxByName(driver, "ConfirmPassword", password);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 07: Click 'Register' button");
		registerPage.clickToButtonByText(driver, "Register");

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 08: Verify existing email error message");
		verifyEquals(registerPage.getExistingEmailErrorMessageText(driver), "The specified email already exists");

	}

	@Test
	public void TC_05_Register_With_Password_Less_Than_6(Method method) {
		ExtentTestManager.startTest(method.getName(), "Register with password less than 6");
		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 01: Go to 'Register' page");
		homePage.openHeaderFooterPageByText(driver, "Register");
		registerPage = PageGeneratorManager.getRegisterPageObject(driver);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 02: Fill in firstname textbox");
		registerPage.enterTextToTextboxByName(driver, "FirstName", firstName);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 03: Fill in last textbox");
		registerPage.enterTextToTextboxByName(driver, "LastName", lastName);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 04: Fill in email textbox");
		registerPage.enterTextToTextboxByName(driver, "Email", email);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 05: Fill in password textbox");
		registerPage.enterTextToTextboxByName(driver, "Password", invalidpassword);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 06: Fill in confirm password textbox");
		registerPage.enterTextToTextboxByName(driver, "ConfirmPassword", invalidpassword);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 07: Click 'Register' button");
		registerPage.clickToButtonByText(driver, "Register");

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 08: Verify invalid password message");
		verifyEquals(registerPage.getInvalidPasswordMessage(driver),
				"Password must meet the following rules:\nmust have at least 6 characters");

	}

	@Test
	public void TC_06_Confirm_Password_Does_Not_Match_Pw(Method method) {
		ExtentTestManager.startTest(method.getName(), "Wrong confirm pw");
		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 01: Go to 'Register' page");
		homePage.openHeaderFooterPageByText(driver, "Register");
		registerPage = PageGeneratorManager.getRegisterPageObject(driver);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 02: Fill in firstname textbox");
		registerPage.enterTextToTextboxByName(driver, "FirstName", firstName);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 03: Fill in last textbox");
		registerPage.enterTextToTextboxByName(driver, "LastName", lastName);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 04: Fill in email textbox");
		registerPage.enterTextToTextboxByName(driver, "Email", email);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 05: Fill in password textbox");
		registerPage.enterTextToTextboxByName(driver, "Password", password);

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 06: Fill in confirm password textbox");
		registerPage.enterTextToTextboxByName(driver, "ConfirmPassword", "1234567");

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 07: Click 'Register' button");
		registerPage.clickToButtonByText(driver, "Register");

		ExtentTestManager.getTest().log(Status.INFO, "Register - Step 08: Verify incorrect password message");
		verifyEquals(registerPage.getIncorrectConfirmPasswordMessage(driver),
				"The password and confirmation password do not match.");

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	private int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}

}
