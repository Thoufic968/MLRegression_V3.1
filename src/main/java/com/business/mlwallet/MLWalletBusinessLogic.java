package com.business.mlwallet;


import com.driverInstance.CommandBase;
import com.driverInstance.DriverInstance;
import com.driverInstance.DriverManager;
import com.mlwallet.pages.*;
import com.propertyfilereader.PropertyFileReader;
import com.utility.ExtentReporter;
import com.utility.LoggingUtils;
import com.utility.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

import static com.utility.Utilities.*;
// HI


public class MLWalletBusinessLogic {

	static LoggingUtils logger = new LoggingUtils();
	private int timeout;
	private int retryCount;

	public static SoftAssert softAssert = new SoftAssert();


	public static PropertyFileReader prop = new PropertyFileReader(".\\properties\\testdata.properties");

	public MLWalletBusinessLogic(String Application, String deviceName, String portno) throws InterruptedException {
		new CommandBase(Application, deviceName, portno);
		init();
	}

	public void init() {
		PropertyFileReader handler = new PropertyFileReader("properties/Execution.properties");
		setTimeout(Integer.parseInt(handler.getproperty("TIMEOUT")));
		setRetryCount(Integer.parseInt(handler.getproperty("RETRY_COUNT")));
		logger.info("Loaded the following properties" + " TimeOut :" + getTimeout() + " RetryCount :" + getRetryCount());
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public void tearDown() {
		softAssert.assertAll();
		logger.info("Session ID: " + ((RemoteWebDriver) DriverManager.getAppiumDriver()).getSessionId());
		ExtentReporter.extentLogger("", "Session ID: " + ((RemoteWebDriver) DriverManager.getAppiumDriver()).getSessionId());
		logger.info("Session is quit");
		ExtentReporter.extentLogger("", "Session is quit");

		setScreenshotSource();
		DriverManager.getAppiumDriver().quit();
	}

	//================================ LOG IN==============================================//
	public void mlWalletLogin(String sTier) throws Exception {
		explicitWaitVisible(MLWalletLoginPage.objMobileNumberTextField, 10);
		click(MLWalletLoginPage.objMobileNumberTextField, "Mobile Number Text Field");
		type(MLWalletLoginPage.objMobileNumberTextField, sTier, "Mobile Number Text Field");
		click(MLWalletLoginPage.objLoginBtn, "Login Button");
		enterOTP(prop.getproperty("Valid_OTP"));
		explicitWaitVisible(MLWalletLoginPage.objAvailableBalance, 10);
		if (verifyElementPresent(MLWalletLoginPage.objAvailableBalance, getTextVal(MLWalletLoginPage.objAvailableBalance, "Text"))) {
			logger.info("Application Logged In Successfully");
		} else {
			logger.info("Application not get Logged In Successfully");
		}
	}

	//===================================LOG OUT=============================================================//
	public void mlWalletLogout() throws Exception {
		if (verifyElementPresent(MLWalletLogOutPage.objHamburgerMenu, "Hamburger Menu")) {
			click(MLWalletLogOutPage.objHamburgerMenu, "Hamburger Menu");
			click(MLWalletLogOutPage.objLogoutBtn, getTextVal(MLWalletLogOutPage.objLogoutBtn, "Button"));
			Thread.sleep(2000);
			click(MLWalletLogOutPage.objLogoutBtn, getTextVal(MLWalletLogOutPage.objLogoutBtn, "Button"));
		}
		if (verifyElementPresent(MLWalletLogOutPage.objChangeNumber, getTextVal(MLWalletLogOutPage.objChangeNumber, "Link"))) {
			logger.info("Application Logged Out Successfully");
		} else {
			logger.info("Application not get Logged Out Successfully");
		}

	}
//================================== Enter OTP ===================================================//

	public void enterOTP(String OTP) throws Exception {
		explicitWaitVisible(MLWalletLoginPage.objOneTimePin, 5);
		verifyElementPresent(MLWalletLoginPage.objOneTimePin, getTextVal(MLWalletLoginPage.objOneTimePin, "Page"));
		verifyElementPresent(MLWalletLoginPage.objOtpTextField, "OTP text Field");
		Thread.sleep(3000);
		type(MLWalletLoginPage.objOtpTextField, OTP, "OTP Text Field");
	}

	public void backArrowBtn(int nNumber) throws Exception {
		for (int i = 1; i <= nNumber; i++) {
			click(SendTransferPage.objBackArrow, "Back Arrow Button");
			Thread.sleep(2000);
		}
	}


	public void enterMLPin() throws Exception {
		explicitWaitVisible(MLWalletLoginPage.objMLPin, 5);
		verifyElementPresent(MLWalletLoginPage.objMLPin, getTextVal(MLWalletLoginPage.objMLPin, "Page"));
		Thread.sleep(3000);
		click(MLWalletLoginPage.objOneBtn, getTextVal(MLWalletLoginPage.objOneBtn, "Button"));
		click(MLWalletLoginPage.objOneBtn, getTextVal(MLWalletLoginPage.objOneBtn, "Button"));
		click(MLWalletLoginPage.objOneBtn, getTextVal(MLWalletLoginPage.objOneBtn, "Button"));
		click(MLWalletLoginPage.objOneBtn, getTextVal(MLWalletLoginPage.objOneBtn, "Button"));
	}


	public void enableLocation_PopUp() throws Exception {
		String loc = getText(MLWalletLoginPage.objLocationPopup);
		if (loc.contains("Allow")) {
			logger.info(loc + " Pop Up is Displayed");
			ExtentReporter.extentLoggerPass("pop up", loc + " Pop Up is Displayed");
			click(MLWalletCashOutPage.objLocationPermission, "Allow Button");
		} else {
			logger.info(" Location Pop Up is not Displayed");
			ExtentReporter.extentLoggerPass("pop up", "Location Pop Up is not Displayed");
		}
	}


//========================================= LOGIN SCENARIOS======================================//

	public void logInScenarioWithValidMobNumber_Lgn_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With Valid Mobile Number");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		if (verifyElementPresent(MLWalletLoginPage.objAvailableBalance, getTextVal(MLWalletLoginPage.objAvailableBalance, "Text"))) {
			logger.info("Lgn_TC_01, Logged In Successfully and redirected to Dashboard");
			ExtentReporter.extentLoggerPass("Lgn_TC_01", "Lgn_TC_01, Logged In Successfully and redirected to Dashboard");
			System.out.println("-----------------------------------------------------------");

		}
	}

	public void logInScenarioWithInvalidMobNumber_Lgn_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With Invalid Mobile Number");
		explicitWaitVisibility(MLWalletLoginPage.objMobileNumberTextField, 10);
		click(MLWalletLoginPage.objMobileNumberTextField, "Mobile Number Text Field");
		type(MLWalletLoginPage.objMobileNumberTextField, prop.getproperty("Invalid_MobileNumber"), "Mobile Number Text Field");
		click(MLWalletLoginPage.objLoginBtn, "Login Button");
		if (verifyElementPresent(MLWalletLoginPage.objInvalidMobNumberTxt, getTextVal(MLWalletLoginPage.objInvalidMobNumberTxt, "Error Message"))) {
			logger.info("Lgn_TC_02, Mobile number is Invalid Error Message is Displayed");
			ExtentReporter.extentLoggerPass("Lgn_TC_02", "Lgn_TC_02, Mobile number is Invalid Error Message is Displayed");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void logInScenarioWithValidOTP_Lgn_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With Valid OTP");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		if (verifyElementPresent(MLWalletLoginPage.objAvailableBalance, getTextVal(MLWalletLoginPage.objAvailableBalance, "Text"))) {
			logger.info("Lgn_TC_03, Logged In Successfully and redirected to Dashboard");
			ExtentReporter.extentLoggerPass("Lgn_TC_03", "Lgn_TC_03, Logged In Successfully and redirected to Dashboard");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void LogInScenarioWithInValidOTP() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With InValid OTP");
		explicitWaitVisibility(MLWalletLoginPage.objMobileNumberTextField, 10);
		click(MLWalletLoginPage.objMobileNumberTextField, "Mobile Number Text Field");
		type(MLWalletLoginPage.objMobileNumberTextField, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
		click(MLWalletLoginPage.objLoginBtn, "Login Button");
		type(MLWalletLoginPage.objOtpTextField, prop.getproperty("InValid_OTP"), "OTP Text Field");
	}


//========================================== Phase 2==========================================//

	public void appLaunch_Lgn_TC_05() throws Exception {
		ExtentReporter.HeaderChildNode("App Launch");
		if(verifyElementPresent(MLWalletLoginPage.objLoginBtn,"Login Button")){
			logger.info("Lgn_TC_05, Application Launched Successfully");
			ExtentReporter.extentLoggerPass("Lgn_TC_05", "Lgn_TC_05, Application Launched Successfully");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void loginPageUIValidation_Lgn_TC_06() throws Exception {
		ExtentReporter.HeaderChildNode("Login Page UI Validation");
		if(verifyElementPresent(MLWalletLoginPage.objLoginBtn,"Login Button")){
			verifyElementPresent(MLWalletLoginPage.objMobileNumberTextField,  "Mobile Number Text Field");
			verifyElementPresent(MLWalletLoginPage.objTroubleSigningIn,getTextVal(MLWalletLoginPage.objTroubleSigningIn,"Button"));
			verifyElementPresent(MLWalletLoginPage.objHaveAnAccountMsg,getTextVal(MLWalletLoginPage.objHaveAnAccountMsg,"Message"));
			verifyElementPresent(MLWalletLoginPage.objCreateOneBtn,getTextVal(MLWalletLoginPage.objCreateOneBtn,"Button"));
			verifyElementPresent(MLWalletLoginPage.objBranchLocator,getTextVal(MLWalletLoginPage.objBranchLocator,"Button"));
			verifyElementPresent(MLWalletLoginPage.objAppVersion,getTextVal(MLWalletLoginPage.objAppVersion,"App Version"));
			logger.info("Lgn_TC_06, Login Page UI Validated");
			ExtentReporter.extentLoggerPass("Lgn_TC_06", "Lgn_TC_06,  Login Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void loginTroubleSigningIn_Lgn_TC_07() throws Exception {
		ExtentReporter.HeaderChildNode("LogIn Trouble Signing In Validation");
		if(verifyElementPresentAndClick(MLWalletLoginPage.objTroubleSigningIn,getTextVal(MLWalletLoginPage.objTroubleSigningIn,"Button"))){
			verifyElementPresent(MLWalletLoginPage.objTroubleSigningPage,getTextVal(MLWalletLoginPage.objTroubleSigningPage,"Page"));
			verifyElementPresent(MLWalletLoginPage.objMLWalletSupport,getTextVal(MLWalletLoginPage.objMLWalletSupport,"Header"));
			logger.info("Lgn_TC_07, Navigated to Trouble Signing In Page");
			ExtentReporter.extentLoggerPass("Lgn_TC_07", "Lgn_TC_07,  Navigated to Trouble Signing In Page");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void loginCreateOne_Lgn_TC_08() throws Exception {
		ExtentReporter.HeaderChildNode("LogIn Create One");
		if(verifyElementPresentAndClick(MLWalletLoginPage.objCreateOneBtn,getTextVal(MLWalletLoginPage.objCreateOneBtn,"Button"))){
			verifyElementPresent(MLWalletLoginPage.objRegistrationNumber,getTextVal(MLWalletLoginPage.objRegistrationNumber,"Page"));
			logger.info("Lgn_TC_08, Navigated to Create One Page");
			ExtentReporter.extentLoggerPass("Lgn_TC_08", "Lgn_TC_08, Navigated to Create One Page");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void loginBranchLocator_Lgn_TC_09() throws Exception {
		ExtentReporter.HeaderChildNode("LogIn Branch Locator");
		if(verifyElementPresentAndClick(MLWalletLoginPage.objBranchLocator,getTextVal(MLWalletLoginPage.objBranchLocator,"Button"))){
			enableLocation_PopUp();
			verifyElementPresent(MLWalletLoginPage.objBranchLocator,getTextVal(MLWalletLoginPage.objBranchLocator,"Page"));
			logger.info("Lgn_TC_09, Navigated to Branch Locator Page");
			ExtentReporter.extentLoggerPass("Lgn_TC_09", "Lgn_TC_09, Navigated to Branch Locator Page");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void loginOTPPageUIValidation_Lgn_TC_10() throws Exception {
		ExtentReporter.HeaderChildNode("LogIn OTP Page UI Validation");
		click(MLWalletLoginPage.objMobileNumberTextField, "Mobile Number Text Field");
		type(MLWalletLoginPage.objMobileNumberTextField, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
		click(MLWalletLoginPage.objLoginBtn, "Login Button");
		explicitWaitVisible(MLWalletLoginPage.objOneTimePin, 5);
		if (verifyElementPresent(MLWalletLoginPage.objOneTimePin, getTextVal(MLWalletLoginPage.objOneTimePin, "Page"))) {
			verifyElementPresent(MLWalletLoginPage.objOtpTextField, "OTP text Field");
			verifyElementPresent(MLWalletLoginPage.objResendCode, getTextVal(MLWalletCashOutPage.objResendCode, "Seconds"));
			logger.info("Lgn_TC_10, LogIn OTP Page UI Validated");
			ExtentReporter.extentLoggerPass("Lgn_TC_10", "Lgn_TC_10, LogIn OTP Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}









//========================================CASH OUT / WITHDRAW===============================================//
//======================================= Generalized methods =============================================//


	public void cashOutSelectBank(String sBank) throws Exception {
		if (verifyElementPresent(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button")) {
			click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
			if (verifyElementPresent(MLWalletCashOutPage.objToAnyBank, getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"))) {
				click(MLWalletCashOutPage.objToAnyBank, getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"));
				type(MLWalletCashOutPage.objSearchBank, sBank, "Search Bank Text Field");
				click(MLWalletCashOutPage.BogusBank, getTextVal(MLWalletCashOutPage.BogusBank, "Bank"));
			}
		}
	}

	public void enterBankDetails(String sAccountNumber) throws Exception {
		if (verifyElementPresent(MLWalletCashOutPage.objBankInformation, getTextVal(MLWalletCashOutPage.objBankInformation, "Button"))) {
			type(MLWalletCashOutPage.objAccountNumberField, sAccountNumber, "Account Number Field");
			type(MLWalletCashOutPage.objFirstname, prop.getproperty("First_Name"), "Account Holder First Name");
			type(MLWalletCashOutPage.objMiddleName, prop.getproperty("Middle_Name"), "Account Holder Middle Name");
			click(MLWalletCashOutPage.objCheckBox, "Check Box");
			type(MLWalletCashOutPage.objLastName, prop.getproperty("Last_Name"), "Account Holder Last Name");
			Swipe("UP", 1);
			type(MLWalletCashOutPage.objEmailAddress, prop.getproperty("Email"), "Account Holder Email Address");
			click(MLWalletCashOutPage.objConfirmBtn, getTextVal(MLWalletCashOutPage.objConfirmBtn, "Button"));
		}

	}

	public void enterAmountMLBranch(String nAmount) throws Exception {
		if (verifyElementPresent(MLWalletCashOutPage.objToAnyMLBranch, getTextVal(MLWalletCashOutPage.objToAnyMLBranch, "Button"))) {
			click(MLWalletCashOutPage.objToAnyMLBranch, getTextVal(MLWalletCashOutPage.objToAnyMLBranch, "Button"));
			verifyElementPresent(MLWalletCashOutPage.objCashOutToBranch, getTextVal(MLWalletCashOutPage.objCashOutToBranch, "Page"));
			type(MLWalletCashOutPage.objAmountField, nAmount, "Amount to Send");
			click(MLWalletCashOutPage.objNextBtn, getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
			click(MLWalletCashOutPage.objContinueBtn, getTextVal(MLWalletCashOutPage.objContinueBtn, "Button"));
			Thread.sleep(3000);
		}
	}

	public void enterAmountBank(String sAmount) throws Exception {
		if(verifyElementPresent(MLWalletCashOutPage.objAmountField,"Bank Cash Out Amount Field")){
			type(MLWalletCashOutPage.objAmountField, sAmount, "Amount to Send");
			click(MLWalletCashOutPage.objNextBtn, getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
			Thread.sleep(3000);
			String sDragonPopUpMsg = getText(MLWalletCashOutPage.objDragonPayPopUpMsg);
			String sExpectedMsg = "Dragon Pay charges a fee of 35.00 pesos for this transaction. Do you wish to continue with your transaction?";
			assertionValidation(sDragonPopUpMsg, sExpectedMsg);
			click(MLWalletCashOutPage.objContinueBtn, getTextVal(MLWalletCashOutPage.objContinueBtn, "Button"));
			Swipe("UP", 2);
			click(MLWalletCashOutPage.objNextBtn, getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
			Thread.sleep(5000);
	 	}
	}

	//===================================================================================================================//
	public void cashOutWithdrawBank_WM_TC_01(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		enterAmountBank(Amount);
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(MLWalletCashOutPage.objTransactionReceipt, getTextVal(MLWalletCashOutPage.objTransactionReceipt, "Text"))) {
			verifyElementPresent(MLWalletCashOutPage.objTransactionSuccessMessage, getTextVal(MLWalletCashOutPage.objTransactionSuccessMessage, "Message"));
			String sTransactionSuccess = getText(MLWalletCashOutPage.objTransactionSuccessMessage);
			assertionValidation(sTransactionSuccess, "Transaction Successful");
			verifyElementPresent(MLWalletCashOutPage.objTransactionNo,getTextVal(MLWalletCashOutPage.objTransactionNo, "Transaction Number"));
			String sTransactionNumber = getText(MLWalletCashOutPage.objTransactionNo);
			System.out.println(sTransactionNumber);
			scrollToVertical("Back To Home");
			click(MLWalletCashOutPage.objBackToHomeBtn, getTextVal(MLWalletCashOutPage.objBackToHomeBtn, "Button"));
			Thread.sleep(3000);
			Swipe("DOWN", 2);
			Swipe("UP", 1);
			verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			click(MLWalletHomePage.objCashOutButton, getTextVal(MLWalletHomePage.objCashOutButton, "Text"));
			if (verifyElementPresent(MLWalletCashOutPage.objTransactionDetails, getTextVal(MLWalletCashOutPage.objTransactionDetails, "Page"))) {
				String sReferenceNumberInCashOut = getText(MLWalletCashOutPage.objReferenceNumberInCashOut);
				System.out.println(sReferenceNumberInCashOut);
				assertionValidation(sReferenceNumberInCashOut, sTransactionNumber);
				logger.info("WM_TC_01, Successfully Withdraw Money to Bank");
				ExtentReporter.extentLoggerPass("WM_TC_01", "WM_TC_01, Successfully Withdraw Money to Bank");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void cashOutWithInvalidAccNumber_WM_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("cashOut With Invalid Account Number");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("Invalid_AccountNumber"));
		Thread.sleep(3000);
		if (verifyElementPresent(MLWalletCashOutPage.objAccInvalidErrorMsg, getTextVal(MLWalletCashOutPage.objAccInvalidErrorMsg, "Text Message"))) {
			String sInvalidAccTxt = getText(MLWalletCashOutPage.objAccInvalidErrorMsg);
			String ExpectedTxt = "Bank Account provided not valid. Please check the account details and try again.";
			assertionValidation(sInvalidAccTxt, ExpectedTxt);
			logger.info("WM_TC_02, Bank Account provided not valid. Error Message is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_02", "WM_TC_02, Bank Account provided not valid. Error Message is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void cashOutWithdrawBankMaxAmount_WM_TC_03(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch Max Amount");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		enterAmountBank(Amount);
		if (verifyElementPresent(MLWalletCashOutPage.objBankMaxLimitTxt, getTextVal(MLWalletCashOutPage.objBankMaxLimitTxt, "Error Message"))) {
			String sErrorMsg = getText(MLWalletCashOutPage.objBankMaxLimitTxt);
			String sExpectedErrorMsg = "The maximum Bank Cash-out per transaction set for your verification level is P50,000.00. Please try again.";
			assertionValidation(sErrorMsg, sExpectedErrorMsg);
			logger.info("WM_TC_03, The Maximum Bank Cash-out per transaction Msg is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_03", "WM_TC_03, The Maximum Bank Cash-out per transaction Msg is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void cashOutWithdrawMinTransactionLimit_WM_TC_04(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch Max Amount");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		explicitWaitVisible(MLWalletCashOutPage.objAmountField, 5);
		type(MLWalletCashOutPage.objAmountField, Amount, "Amount to Send");
		click(MLWalletCashOutPage.objNextBtn, getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
		Thread.sleep(5000);
		if (verifyElementPresent(MLWalletCashOutPage.objMinimumTransactionErrorMsg, getTextVal(MLWalletCashOutPage.objMinimumTransactionErrorMsg, "Error message"))) {
			String sMinimumTransactionErrorMsg = getText(MLWalletCashOutPage.objMinimumTransactionErrorMsg);
			String sExpectedMsg = "The supplied amount is less than the required minimum transaction limit";
			assertionValidation(sMinimumTransactionErrorMsg, sExpectedMsg);
			logger.info("WM_TC_04, The supplied amount is less than the required minimum transaction limit Error Msg is validated");
			ExtentReporter.extentLoggerPass("WM_TC_04", "WM_TC_04, The supplied amount is less than the required minimum transaction limit Error Msg is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutWithdrawBranch_WM_TC_05() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("10");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(MLWalletCashOutPage.objCashOutToBranch, getTextVal(MLWalletCashOutPage.objCashOutToBranch, "Page"))) {
			verifyElementPresent(MLWalletCashOutPage.objCreatedDate, getTextVal(MLWalletCashOutPage.objCreatedDate, "Date"));
			verifyElementPresent(MLWalletCashOutPage.objReferenceNumber, getTextVal(MLWalletCashOutPage.objReferenceNumber, "Reference Number"));
			String nReference = getText(MLWalletCashOutPage.objReferenceNumber);
			System.out.println(nReference);
			String sReferenceNumber = nReference.substring(5, 16);
			System.out.println(sReferenceNumber);
			click(MLWalletCashOutPage.objBackToHomeBtn, getTextVal(MLWalletCashOutPage.objBackToHomeBtn, "Button"));
			Swipe("DOWN", 2);
			Swipe("UP", 1);
			verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			click(MLWalletHomePage.objCashOutButton, getTextVal(MLWalletHomePage.objCashOutButton, "Text"));
			if (verifyElementPresent(MLWalletCashOutPage.objTransactionDetails, getTextVal(MLWalletCashOutPage.objTransactionDetails, "Page"))) {
				String sReferenceNumberInCashOut = getText(MLWalletCashOutPage.objReferenceNumberInCashOut);
				System.out.println(sReferenceNumberInCashOut);
				assertionValidation(sReferenceNumberInCashOut, sReferenceNumber);
				logger.info("Reference Number is matching with recent Transaction");
				logger.info("WM_TC_05, Successfully Withdraw Money to ML Branch");
				ExtentReporter.extentLoggerPass("WM_TC_05", "WM_TC_05, Successfully Withdraw Money to ML Branch");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void cashOutMaxLimit_WM_TC_06() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("100000");
		if (verifyElementPresent(MLWalletCashOutPage.objMaxLimitTxt, getTextVal(MLWalletCashOutPage.objMaxLimitTxt, "Text Message"))) {
			String sMaxLimitTxt = getText(MLWalletCashOutPage.objMaxLimitTxt);
			String ExpectedTxt = "The maximum Branch Cash-out per set for your verification level is P40,000.00. Please try again. ";
			assertionValidation(sMaxLimitTxt, ExpectedTxt);
			logger.info("WM_TC_06, The supplied amount us less than the required minimum transaction limit. Error Message is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_06", "WM_TC_06, The supplied amount us less than the required minimum transaction limit. Error Message is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutInsufficientBalance_WM_TC_07() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Insufficient Balance");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("35000");
		if (verifyElementPresent(MLWalletCashOutPage.objInsufficientBalance, getTextVal(MLWalletCashOutPage.objInsufficientBalance, "Text Message"))) {
			String sInsufficientBalancePopupTxt = getText(MLWalletCashOutPage.objInsufficientBalance);
			String ExpectedTxt = "There is insufficient balance to proceed with this transaction. Please try again.";
			assertionValidation(sInsufficientBalancePopupTxt, ExpectedTxt);
			logger.info("WM_TC_07, Insufficient Balance pop up validated");
			ExtentReporter.extentLoggerPass("WM_TC_07", "WM_TC_07, Insufficient Balance pop up validated");
			System.out.println("-----------------------------------------------------------");
		}

	}

	public void cashOutBuyerTierLevelAcc_WM_TC_08() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Buyer_Tier"));
		click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("100");
		if (verifyElementPresent(MLWalletCashOutPage.objMaxLimitTxt, getTextVal(MLWalletCashOutPage.objMaxLimitTxt, "Text Message"))) {
			String sErrorMessage = getText(MLWalletCashOutPage.objMaxLimitTxt);
			String ExpectedTxt = "Branch Cash-out is not allowed for customers at this verification level. Please upgrade your account to use this service.";
			assertionValidation(sErrorMessage, ExpectedTxt);
			logger.info("WM_TC_08, Branch Cash-out is not allowed for customers at this verification level. Error Message is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_08", "WM_TC_08, Branch Cash-out is not allowed for customers at this verification level. Error Message is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


//=================================== Cash Out Phase2==================================================//

	public void cashOutInvalidBank_WM_TC_10() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Invalid Bank Details");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		if (verifyElementPresent(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button")) {
			click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
			if (verifyElementPresent(MLWalletCashOutPage.objToAnyBank, getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"))) {
				click(MLWalletCashOutPage.objToAnyBank,getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"));
				type(MLWalletCashOutPage.objSearchBank, prop.getproperty("Invalid_BankName"), "Search Bank Text Field");
				if (verifyElementPresent(MLWalletCashOutPage.objNoRecentTransactionTxt, getTextVal(MLWalletCashOutPage.objNoRecentTransactionTxt, "Text"))) {
					logger.info("WM_TC_10, No Recent Transaction message Validated after entering invalid Bank Name");
					ExtentReporter.extentLoggerPass("WM_TC_10", "WM_TC_10, No Recent Transaction message Validated after entering invalid Bank Name");
					System.out.println("-----------------------------------------------------------");
				}
			}
		}
	}

	public void searchAndSelectBank_WM_TC_11() throws Exception {
		ExtentReporter.HeaderChildNode("Search And Select Bank");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		if (verifyElementPresent(MLWalletCashOutPage.objBankInformation, getTextVal(MLWalletCashOutPage.objBankInformation, "Page"))) {
			verifyElementPresent(MLWalletCashOutPage.BogusBank, getTextVal(MLWalletCashOutPage.BogusBank, "Bank Name"));
			logger.info("WM_TC_11, Bank Name auto-displayed after searching and selecting the particular Bank");
			ExtentReporter.extentLoggerPass("WM_TC_11", "WM_TC_11, Bank Name auto-displayed after searching and selecting the particular Bank");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutInvalidAmount_WM_TC_12() throws Exception {
		ExtentReporter.HeaderChildNode("Search And Select Bank");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		type(MLWalletCashOutPage.objAmountField, "", "Amount to Send");
		click(MLWalletCashOutPage.objNextBtn, getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
		if (verifyElementPresent(MLWalletCashOutPage.objAmountRequiredErrorMsg, getTextVal(MLWalletCashOutPage.objAmountRequiredErrorMsg, "Error Message"))) {
			String sAmountRequiredErrorMsg = getText(MLWalletCashOutPage.objAmountRequiredErrorMsg);
			String sExceptedErrorMsg = "Amount is required";
			assertionValidation(sAmountRequiredErrorMsg, sExceptedErrorMsg);
			logger.info("WM_TC_12, Amount is required - Error message is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_12", "WM_TC_12, Amount is required - Error message is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutSaveRecipient_WM_TC_13(String sAmount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash out Save Recipient");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		enterAmountBank(sAmount);
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		scrollToVertical("Back To Home");
		type(MLWalletCashOutPage.objNickName, prop.getproperty("Nick_Name"), "Nick Name Input Text Field");
		click(MLWalletCashOutPage.objSaveRecipientBtn, getTextVal(MLWalletCashOutPage.objSaveRecipientBtn, "Button"));
		explicitWaitVisible(MLWalletCashOutPage.objToAnyBank, 5);
		verifyElementPresentAndClick(MLWalletCashOutPage.objToAnyBank, getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"));
		click(MLWalletCashOutPage.BogusBank, getTextVal(MLWalletCashOutPage.BogusBank, "Bank"));
		verifyElementPresent(MLWalletCashOutPage.objSavedBackAccount, getTextVal(MLWalletCashOutPage.objSavedBackAccount, "Page"));
		if (verifyElementPresent(MLWalletCashOutPage.objNickNameInSavedBankAcc(prop.getproperty("Nick_Name")), Utilities.getTextVal(MLWalletCashOutPage.objNickNameInSavedBankAcc(prop.getproperty("Nick_Name")), "Nick Name for Saved Bank Account"))) {
			logger.info("WM_TC_13, Saved Recipient is displayed under Saved Bank Account");
			ExtentReporter.extentLoggerPass("WM_TC_13", "WM_TC_13, Saved Recipient is displayed under Saved Bank Account");
			System.out.println("-----------------------------------------------------------");

		}
	}

	public void cashOutRecipientDuplicate_WM_TC_14(String sAmount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash out Recipient Duplicate");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		enterAmountBank(sAmount);
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		scrollToVertical("Back To Home");
		type(MLWalletCashOutPage.objNickName, prop.getproperty("Nick_Name"), "Nick Name Input Text Field");
		click(MLWalletCashOutPage.objSaveRecipientBtn, getTextVal(MLWalletCashOutPage.objSaveRecipientBtn, "Button"));
		if (verifyElementPresent(MLWalletCashOutPage.objRecipientExistMsg, getTextVal(MLWalletCashOutPage.objRecipientExistMsg, "Popup Message"))) {
			String sRecipientExistMsg = getText(MLWalletCashOutPage.objRecipientExistMsg);
			String sExpectedMsg = "Recipient already exists.";
			assertionValidation(sRecipientExistMsg, sExpectedMsg);
			logger.info("WM_TC_14, Recipient already exists pop up message Validated");
			ExtentReporter.extentLoggerPass("WM_TC_14", "WM_TC_14, Recipient already exists pop up message Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutUIValidation_WM_TC_16() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Page UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		verifyElementPresentAndClick(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		if (verifyElementPresent(MLWalletCashOutPage.objCashOutWithdraw, getTextVal(MLWalletCashOutPage.objCashOutWithdraw, "Page"))) {
			verifyElementPresent(MLWalletCashOutPage.objCashOutOptions, getTextVal(MLWalletCashOutPage.objCashOutOptions, "Header"));
			verifyElementPresent(MLWalletCashOutPage.objToAnyBank, getTextVal(MLWalletCashOutPage.objToAnyBank, "Option"));
			verifyElementPresent(MLWalletCashOutPage.objToAnyMLBranch, getTextVal(MLWalletCashOutPage.objToAnyMLBranch, "Option"));
			verifyElementPresent(MLWalletCashOutPage.objCashOutOngoingTransaction, getTextVal(MLWalletCashOutPage.objCashOutOngoingTransaction, "Header"));
			logger.info("WM_TC_16, Cash Out Page UI Validation");
			ExtentReporter.extentLoggerPass("WM_TC_16", "WM_TC_16, Cash Out Page UI Validation");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutWithdrawBackBtnValidation_WM_TC_17() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Page back arrow Button Validation");
		cashOutUIValidation_WM_TC_16();
		click(MLWalletCashOutPage.cashOutBackArrowBtn, "Cash Out Page Back Arrow Button");
		explicitWaitVisible(MLWalletLoginPage.objAvailableBalance, 10);
		if (verifyElementPresent(MLWalletLoginPage.objAvailableBalance,getTextVal(MLWalletLoginPage.objAvailableBalance, "Text"))) {
			logger.info("WM_TC_17, Cash Out Page back arrow Button Validation");
			ExtentReporter.extentLoggerPass("WM_TC_17", "WM_TC_17, Cash Out Page back arrow Button Validation");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutToBranchUIValidation_WM_TC_18() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out To Branch UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		verifyElementPresentAndClick(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		verifyElementPresentAndClick(MLWalletCashOutPage.objToAnyMLBranch, getTextVal(MLWalletCashOutPage.objToAnyMLBranch, "Button"));
		if (verifyElementPresent(MLWalletCashOutPage.objCashOutPage, getTextVal(MLWalletCashOutPage.objCashOutPage, "Page"))) {
			verifyElementPresent(MLWalletCashOutPage.ObjCashOutToBranch, getTextVal(MLWalletCashOutPage.objCashOutToBranch, "Header"));
			verifyElementPresent(MLWalletCashOutPage.objUserName, getTextVal(MLWalletCashOutPage.objUserName, "User Name"));
			verifyElementPresent(MLWalletCashOutPage.objUserMobileNumber, getTextVal(MLWalletCashOutPage.objUserMobileNumber, "User Mobile Number"));
			verifyElementPresent(MLWalletCashOutPage.objMLWalletBalance, getTextVal(MLWalletCashOutPage.objMLWalletBalance, "Balance"));
			verifyElementPresent(MLWalletCashOutPage.objCashOutInstructions, "Instructions Icon");
			logger.info("WM_TC_18, Cash Out to Branch Page Validation");
			ExtentReporter.extentLoggerPass("WM_TC_18", "WM_TC_18, Cash Out to Branch Page Validation");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutToBranchBackBtnValidation_WM_TC_19() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out To Branch Page back arrow Button Validation");
		cashOutToBranchUIValidation_WM_TC_18();
		click(MLWalletCashOutPage.objCashOutToBranchBackBtn, "Cash Out Page Back Arrow Button");
		if (verifyElementPresent(MLWalletCashOutPage.objCashOutWithdraw, getTextVal(MLWalletCashOutPage.objCashOutWithdraw, "Page"))) {
			logger.info("WM_TC_19, Cash Out To Branch Page back arrow Button Validation");
			ExtentReporter.extentLoggerPass("WM_TC_19", "WM_TC_19, Cash Out To Branch Page back arrow Button Validation");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void cashOutOTPPageUIValidation_WM_TC_20(String sAmount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out OTP Page UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		enterAmountBank(sAmount);
		enableLocation_PopUp();
		explicitWaitVisible(MLWalletLoginPage.objOneTimePin, 5);
		if (verifyElementPresent(MLWalletLoginPage.objOneTimePin, getTextVal(MLWalletLoginPage.objOneTimePin, "Page"))) {
			verifyElementPresent(MLWalletLoginPage.objOtpTextField, "OTP text Field");
			verifyElementPresent(MLWalletCashOutPage.objResendCode, getTextVal(MLWalletCashOutPage.objResendCode, "Seconds"));
			logger.info("WM_TC_20, One Time Pin page UI Validation");
			ExtentReporter.extentLoggerPass("WM_TC_20", "WM_TC_20, One Time Pin page UI Validation");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutOTPPageBackBtnValidation_WM_TC_21(String sAmount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out OTP Page Back Button Validation");
		cashOutOTPPageUIValidation_WM_TC_20(sAmount);
		click(MLWalletCashOutPage.objOneTimePinBackBtn, "OTP Back Arrow Button");
		if (verifyElementPresent(MLWalletCashOutPage.objReviewTransaction,getTextVal(MLWalletCashOutPage.objReviewTransaction, "Page"))) {
			logger.info("WM_TC_21, OTP page back arrow Button Validation");
			ExtentReporter.extentLoggerPass("WM_TC_21", "WM_TC_21, OTP page back arrow Button Validation");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void cashOutMlBranchQRPageUIValidation_WM_TC_22() throws Exception {
		ExtentReporter.HeaderChildNode("CashOut ML Branch QR Code Page UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("10");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(MLWalletCashOutPage.objCashOutToBranch, getTextVal(MLWalletCashOutPage.objCashOutToBranch, "Page"))) {
			verifyElementPresent(MLWalletCashOutPage.objCreatedDate, getTextVal(MLWalletCashOutPage.objCreatedDate, "Date"));
			verifyElementPresent(MLWalletCashOutPage.objReferenceNumber, getTextVal(MLWalletCashOutPage.objReferenceNumber, "Reference Number"));
			verifyElementPresent(MLWalletCashOutPage.objPHP,getTextVal(MLWalletCashOutPage.objPHP,"Amount"));
			verifyElementPresent(MLWalletCashOutPage.objBackToHomeBtn,getTextVal(MLWalletCashOutPage.objBackToHomeBtn,"Button"));
			verifyElementPresent(MLWalletCashOutPage.objNewTransaction,getTextVal(MLWalletCashOutPage.objNewTransaction,"Button"));
			logger.info("WM_TC_22, CashOut ML Branch QR Code Page UI Validation");
			ExtentReporter.extentLoggerPass("WM_TC_22", "WM_TC_22, CashOut ML Branch QR Code Page UI Validation");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutCancelIconValidation_WM_TC_23() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Cancel Icon Validation");
		cashOutMlBranchQRPageUIValidation_WM_TC_22();
		verifyElementPresentAndClick(MLWalletCashOutPage.objCancelIcon, "Cancel Icon");
		if (verifyElementPresent(MLWalletLoginPage.objAvailableBalance, getTextVal(MLWalletLoginPage.objAvailableBalance, "Text"))) {
			logger.info("WM_TC_23, Cash Out Cancel Icon Validated");
			ExtentReporter.extentLoggerPass("WM_TC_23", "WM_TC_23, Cash Out Cancel Icon Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutPendingTransactionValidation_WM_TC_24(String sAmount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Pending Transaction Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		enterAmountBank(sAmount);
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(MLWalletCashOutPage.objTransactionReceipt, getTextVal(MLWalletCashOutPage.objTransactionReceipt, "Text"))) {
			verifyElementPresent(MLWalletCashOutPage.objTransactionSuccessMessage, getTextVal(MLWalletCashOutPage.objTransactionSuccessMessage, "Message"));
			scrollToVertical("Back To Home");
			click(MLWalletCashOutPage.objBackToHomeBtn, getTextVal(MLWalletCashOutPage.objBackToHomeBtn, "Button"));
			Thread.sleep(3000);
			Swipe("DOWN", 2);
			Swipe("UP", 1);
			verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			verifyElementPresent(MLWalletHomePage.objCashOutButton, getTextVal(MLWalletHomePage.objCashOutButton, "Text"));
			if(verifyElementPresent(MLWalletHomePage.objPendingStatusForCashOut,getTextVal(MLWalletHomePage.objPendingStatusForCashOut,"Status"))){
				String sActualStatus = getText(MLWalletHomePage.objPendingStatusForCashOut);
				String sExceptedStatus = "Pending";
				assertionValidation(sActualStatus,sExceptedStatus);
				logger.info("WM_TC_24, Cash Out Pending Transaction Validated");
				ExtentReporter.extentLoggerPass("WM_TC_24", "WM_TC_24, Cash Out Pending Transaction Validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void cashOutMLBankBuyerTier_WM_TC_27(String sAmount) throws Exception {
		ExtentReporter.HeaderChildNode("CashOut ML Bank Buyer Tier");
		mlWalletLogin(prop.getproperty("Buyer_Tier"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		enterAmountBank(sAmount);
		if (verifyElementPresent(MLWalletCashOutPage.objMaxLimitTxt, getTextVal(MLWalletCashOutPage.objMaxLimitTxt, "Text Message"))) {
			String sErrorMessage = getText(MLWalletCashOutPage.objMaxLimitTxt);
			String ExpectedTxt = "Bank Cash-out is not allowed for customers at this verification level. Please upgrade your account to use this service.";
			assertionValidation(sErrorMessage, ExpectedTxt);
			verifyElementPresent(MLWalletCashOutPage.objUpgradeNowBtn,getTextVal(MLWalletCashOutPage.objUpgradeNowBtn,"Button"));
			logger.info("WM_TC_27, CashOut ML Bank Buyer Tier Validated");
			ExtentReporter.extentLoggerPass("WM_TC_27", "WM_TC_27, CashOut ML Bank Buyer Tier Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void cashOutSemiVerifiedTier_WM_TC_28(String sAmount) throws Exception {
		ExtentReporter.HeaderChildNode("CashOut Withdraw Semi-Verified Tier Account");
		mlWalletLogin(prop.getproperty("Semi_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		enterAmountBank(sAmount);
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(MLWalletCashOutPage.objTransactionReceipt, getTextVal(MLWalletCashOutPage.objTransactionReceipt, "Text"))) {
			verifyElementPresent(MLWalletCashOutPage.objTransactionSuccessMessage, getTextVal(MLWalletCashOutPage.objTransactionSuccessMessage, "Message"));
			verifyElementPresent(MLWalletCashOutPage.objReferenceNumberCashOutBranch,getTextVal(MLWalletCashOutPage.objReferenceNumberCashOutBranch,"Reference NUmber"));
			verifyElementPresent(MLWalletCashOutPage.objTransactionNo,getTextVal(MLWalletCashOutPage.objTransactionNo,"Transaction Number"));
			logger.info("WM_TC_28, CashOut Withdraw Semi-Verified Tier Account Validated");
			ExtentReporter.extentLoggerPass("WM_TC_28", "WM_TC_28, CashOut Withdraw Semi-Verified Tier Account Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutFullyVerifiedTier_WM_TC_29(String sAmount) throws Exception {
		ExtentReporter.HeaderChildNode("CashOut Withdraw Semi-Verified Tier Account");
		mlWalletLogin(prop.getproperty("Fully_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		enterAmountBank(sAmount);
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(MLWalletCashOutPage.objTransactionReceipt, getTextVal(MLWalletCashOutPage.objTransactionReceipt, "Text"))) {
			verifyElementPresent(MLWalletCashOutPage.objTransactionSuccessMessage, getTextVal(MLWalletCashOutPage.objTransactionSuccessMessage, "Message"));
			verifyElementPresent(MLWalletCashOutPage.objReferenceNumberCashOutBranch,getTextVal(MLWalletCashOutPage.objReferenceNumberCashOutBranch,"Reference NUmber"));
			verifyElementPresent(MLWalletCashOutPage.objTransactionNo,getTextVal(MLWalletCashOutPage.objTransactionNo,"Transaction Number"));
			logger.info("WM_TC_29, CashOut Withdraw Fully_Verified Tier Account Validated");
			ExtentReporter.extentLoggerPass("WM_TC_29", "WM_TC_29, CashOut Withdraw Fully_Verified Tier Account Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutMLBranchSemiVerifiedTier_WM_TC_31() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Branch Semi-Verified Tier");
		mlWalletLogin(prop.getproperty("Semi_Verified"));
		click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("10");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(MLWalletCashOutPage.objCashOutToBranch, getTextVal(MLWalletCashOutPage.objCashOutToBranch, "Page"))) {
			verifyElementPresent(MLWalletCashOutPage.objCreatedDate, getTextVal(MLWalletCashOutPage.objCreatedDate, "Date"));
			verifyElementPresent(MLWalletCashOutPage.objReferenceNumber, getTextVal(MLWalletCashOutPage.objReferenceNumber, "Reference Number"));
			String nReference = getText(MLWalletCashOutPage.objReferenceNumber);
			System.out.println(nReference);
			String sReferenceNumber = nReference.substring(5, 16);
			System.out.println(sReferenceNumber);
			click(MLWalletCashOutPage.objBackToHomeBtn, getTextVal(MLWalletCashOutPage.objBackToHomeBtn, "Button"));
			Swipe("DOWN", 2);
			Swipe("UP", 1);
			verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			click(MLWalletHomePage.objCashOutButton, getTextVal(MLWalletHomePage.objCashOutButton, "Text"));
			if (verifyElementPresent(MLWalletCashOutPage.objTransactionDetails, getTextVal(MLWalletCashOutPage.objTransactionDetails, "Page"))) {
				String sReferenceNumberInCashOut = getText(MLWalletCashOutPage.objReferenceNumberInCashOut);
				System.out.println(sReferenceNumberInCashOut);
				assertionValidation(sReferenceNumberInCashOut, sReferenceNumber);
				logger.info("Reference Number is matching with recent Transaction");
				logger.info("WM_TC_31, Successfully Withdraw Money to ML Branch for Semi-verified Tier Account");
				ExtentReporter.extentLoggerPass("WM_TC_31", "WM_TC_31, Successfully Withdraw Money to ML Branch for Semi-verified Tier Account");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void cashOutMLBranchFullyVerifiedTier_WM_TC_32() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Branch Fully-Verified Tier");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("10");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(MLWalletCashOutPage.objCashOutToBranch, getTextVal(MLWalletCashOutPage.objCashOutToBranch, "Page"))) {
			verifyElementPresent(MLWalletCashOutPage.objCreatedDate, getTextVal(MLWalletCashOutPage.objCreatedDate, "Date"));
			verifyElementPresent(MLWalletCashOutPage.objReferenceNumber, getTextVal(MLWalletCashOutPage.objReferenceNumber, "Reference Number"));
			String nReference = getText(MLWalletCashOutPage.objReferenceNumber);
			System.out.println(nReference);
			String sReferenceNumber = nReference.substring(5, 16);
			System.out.println(sReferenceNumber);
			click(MLWalletCashOutPage.objBackToHomeBtn, getTextVal(MLWalletCashOutPage.objBackToHomeBtn, "Button"));
			Swipe("DOWN", 2);
			Swipe("UP", 1);
			verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			click(MLWalletHomePage.objCashOutButton, getTextVal(MLWalletHomePage.objCashOutButton, "Text"));
			if (verifyElementPresent(MLWalletCashOutPage.objTransactionDetails, getTextVal(MLWalletCashOutPage.objTransactionDetails, "Page"))) {
				String sReferenceNumberInCashOut = getText(MLWalletCashOutPage.objReferenceNumberInCashOut);
				System.out.println(sReferenceNumberInCashOut);
				assertionValidation(sReferenceNumberInCashOut, sReferenceNumber);
				logger.info("Reference Number is matching with recent Transaction");
				logger.info("WM_TC_32, Successfully Withdraw Money to ML Branch for Fully-Verified Tier Account");
				ExtentReporter.extentLoggerPass("WM_TC_32", "WM_TC_32, Successfully Withdraw Money to ML Branch for Fully-Verified Tier Account");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void cashOutMaxLimitSemiVerifiedTier_WM_TC_33() throws Exception {
		ExtentReporter.HeaderChildNode("CashOut Bank Maximum Limit For Semi-verified Tier");
		mlWalletLogin(prop.getproperty("Semi_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		enterAmountBank("30000");
		if (verifyElementPresent(MLWalletCashOutPage.objBankMaxLimitTxt, getTextVal(MLWalletCashOutPage.objBankMaxLimitTxt, "Error Message"))) {
			String sErrorMsg = getText(MLWalletCashOutPage.objBankMaxLimitTxt);
			String sExpectedErrorMsg = "The maximum Bank Cash-out per transaction set for your verification level is P5,000.00. Please try again.";
			assertionValidation(sErrorMsg, sExpectedErrorMsg);
			verifyElementPresent(MLWalletCashOutPage.objUpgradeNowBtn,getTextVal(MLWalletCashOutPage.objUpgradeNowBtn,"Button"));
			logger.info("WM_TC_33, The Maximum Bank Cash-out per transaction Msg for Semi-verified tier Account is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_33", "WM_TC_33, The Maximum Bank Cash-out per transaction Msg for Semi-verified tier Account is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutMaxLimitFullyVerifiedTier_WM_TC_36() throws Exception {
		ExtentReporter.HeaderChildNode("CashOut Bank Maximum Limit For Fully-verified Tier");
		mlWalletLogin(prop.getproperty("Fully_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		enterBankDetails(prop.getproperty("AccountNumber"));
		Thread.sleep(3000);
		enterAmountBank("60000");
		if (verifyElementPresent(MLWalletCashOutPage.objBankMaxLimitTxt, getTextVal(MLWalletCashOutPage.objBankMaxLimitTxt, "Error Message"))) {
			String sErrorMsg = getText(MLWalletCashOutPage.objBankMaxLimitTxt);
			String sExpectedErrorMsg = "The maximum Bank Cash-out per transaction set for your verification level is P50,000.00. Please try again.";
			assertionValidation(sErrorMsg, sExpectedErrorMsg);
			verifyElementPresent(MLWalletCashOutPage.objUpgradeNowBtn,getTextVal(MLWalletCashOutPage.objUpgradeNowBtn,"Button"));
			logger.info("WM_TC_36, The Maximum Bank Cash-out per transaction Msg for Fully-verified tier Account is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_36", "WM_TC_36, The Maximum Bank Cash-out per transaction Msg for Fully-verified tier Account is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void cashOutMLBranchMaxLimitSemiVerifiedTier_WM_TC_39() throws Exception {
		ExtentReporter.HeaderChildNode("CashOut ML Branch Maximum Limit For Semi-verified Tier");
		mlWalletLogin(prop.getproperty("Semi_Verified"));
		click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("20000");
		if (verifyElementPresent(MLWalletCashOutPage.objBankMaxLimitTxt, getTextVal(MLWalletCashOutPage.objBankMaxLimitTxt, "Error Message"))) {
			String sErrorMsg = getText(MLWalletCashOutPage.objBankMaxLimitTxt);
			String sExpectedErrorMsg = "The maximum Branch Cash-out per transaction set for your verification level is P5,000.00. Please try again.";
			assertionValidation(sErrorMsg, sExpectedErrorMsg);
			verifyElementPresent(MLWalletCashOutPage.objUpgradeNowBtn,getTextVal(MLWalletCashOutPage.objUpgradeNowBtn,"Button"));
			logger.info("WM_TC_39, The Maximum Branch Cash-out per transaction Msg for Semi-verified tier Account is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_39", "WM_TC_39, The Maximum Bank Cash-out per transaction Msg for Semi-verified tier Account is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void cashOutMLBranchMaxLimitFullyVerifiedTier_WM_TC_42() throws Exception {
		ExtentReporter.HeaderChildNode("CashOut ML Branch Maximum Limit For Fully-verified Tier");
		mlWalletLogin(prop.getproperty("Fully_Verified"));
		click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("60000");
		if (verifyElementPresent(MLWalletCashOutPage.objBankMaxLimitTxt, getTextVal(MLWalletCashOutPage.objBankMaxLimitTxt, "Error Message"))) {
			String sErrorMsg = getText(MLWalletCashOutPage.objBankMaxLimitTxt);
			String sExpectedErrorMsg = "The maximum Branch Cash-out per transaction set for your verification level is P40,000.00. Please try again.";
			assertionValidation(sErrorMsg, sExpectedErrorMsg);
			verifyElementPresent(MLWalletCashOutPage.objUpgradeNowBtn,getTextVal(MLWalletCashOutPage.objUpgradeNowBtn,"Button"));
			logger.info("WM_TC_42, The Maximum Branch Cash-out per transaction Msg for Fully-verified tier Account is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_42", "WM_TC_42, The Maximum Bank Cash-out per transaction Msg for Fully-verified tier Account is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutWithDrawBankRequiredDetails() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Bank Required Details Validation");
		mlWalletLogin(prop.getproperty("Fully_Verified"));
		cashOutSelectBank(prop.getproperty("Valid_BankName"));
		verifyElementPresent(MLWalletCashOutPage.objBankInformation, getTextVal(MLWalletCashOutPage.objBankInformation, "Button"));
		Swipe("UP",1);
		verifyElementPresentAndClick(MLWalletCashOutPage.objConfirmBtn,getTextVal(MLWalletCashOutPage.objConfirmBtn,"Button"));

		if (verifyElementPresent(MLWalletCashOutPage.objFirstNameRequiredMsg, getTextVal(MLWalletCashOutPage.objFirstNameRequiredMsg, "Error Message"))) {
			String sAccountNumberErrorMsg = getText(MLWalletCashOutPage.objFirstNameRequiredMsg);
			String sExpectedMsg = "Account Number is required";
			assertionValidation(sAccountNumberErrorMsg, sExpectedMsg);
		}
		type(MLWalletCashOutPage.objAccountNumberField, prop.getproperty("AccountNumber"), "Account Number Text Field");

		if (verifyElementPresent(MLWalletCashOutPage.objFirstNameRequiredMsg, getTextVal(MLWalletCashOutPage.objFirstNameRequiredMsg, "Error Message"))) {
			String sFirstNameErrorMsg = getText(MLWalletCashOutPage.objFirstNameRequiredMsg);
			String sExpectedMsg = "First name is required";
			assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
		}
		type(MLWalletCashOutPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
		Swipe("UP",1);
		verifyElementPresentAndClick(MLWalletCashOutPage.objConfirmBtn,getTextVal(MLWalletCashOutPage.objConfirmBtn,"Button"));



		type(MLWalletCashOutPage.objMiddleName, prop.getproperty("Middle_Name"), "Account Holder Middle Name");
		click(MLWalletCashOutPage.objCheckBox, "Check Box");
		type(MLWalletCashOutPage.objLastName, prop.getproperty("Last_Name"), "Account Holder Last Name");
		Swipe("UP", 1);
		type(MLWalletCashOutPage.objEmailAddress, prop.getproperty("Email"), "Account Holder Email Address");
		click(MLWalletCashOutPage.objConfirmBtn, getTextVal(MLWalletCashOutPage.objConfirmBtn, "Button"));

		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objMiddleNameRequiredMsg, Utilities.getTextVal(MLWalletCashOutPage.objMiddleNameRequiredMsg, "Error Message"))) {
			String sMiddleNameRequiredMsg = Utilities.getText(MLWalletCashOutPage.objMiddleNameRequiredMsg);
			String sExpectedMsg = "Middle name is required";
			Utilities.assertionValidation(sMiddleNameRequiredMsg, sExpectedMsg);
		}
		Thread.sleep(3000);
		Utilities.type(MLWalletCashOutPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");








		if (Utilities.verifyElementPresent(SendTransferPage.objFirstNameRequiredMsg, Utilities.getTextVal(SendTransferPage.objFirstNameRequiredMsg, "Error Message"))) {
			String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objFirstNameRequiredMsg);
			String sExpectedMsg = "First name is required";
			Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
		}
		Utilities.hideKeyboard();
		Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
		Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
		Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
		if (Utilities.verifyElementPresent(SendTransferPage.objMiddleNameRequiredMsg, Utilities.getTextVal(SendTransferPage.objMiddleNameRequiredMsg, "Error Message"))) {
			String sMiddleNameRequiredMsg = Utilities.getText(SendTransferPage.objMiddleNameRequiredMsg);
			String sExpectedMsg = "Middle name is required";
			Utilities.assertionValidation(sMiddleNameRequiredMsg, sExpectedMsg);
		}
		Thread.sleep(3000);
		Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
		Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
		if (Utilities.verifyElementPresent(SendTransferPage.objLastNameRequiredMsg, Utilities.getTextVal(SendTransferPage.objLastNameRequiredMsg, "Error Message"))) {
			String sLastNameRequiredMsg = Utilities.getText(SendTransferPage.objLastNameRequiredMsg);
			String sExpectedMsg = "Last name is required";
			Utilities.assertionValidation(sLastNameRequiredMsg, sExpectedMsg);
		}
		Thread.sleep(3000);
		Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
		Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
		Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
		if (Utilities.verifyElementPresent(SendTransferPage.objMobileNumberRequiredMsg, Utilities.getTextVal(SendTransferPage.objMobileNumberRequiredMsg, "Error Message"))) {
			String sMobileNumberRequiredMsg = Utilities.getText(SendTransferPage.objMobileNumberRequiredMsg);
			String sExpectedMsg = "Mobile number is required";
			Utilities.assertionValidation(sMobileNumberRequiredMsg, sExpectedMsg);
		}
		Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Last Name Text Field");
		Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));

	}
















//================================ Send/Transfer To any ML Branch ============================================//
//=============================== General methods For send transfer ============================================//

	public void sendMoneyToAnyMLBranch() throws Exception {
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		click(SendTransferPage.objSendTransferBtn, getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		verifyElementPresent(SendTransferPage.objSendMoney, getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (verifyElementPresent(SendTransferPage.objToAnyMLBranch, getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			click(SendTransferPage.objToAnyMLBranch, getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
		}
	}

	public void enterMLBranchDetails() throws Exception {
		Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
		if (Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"))) {
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
			Utilities.waitTime(3000);
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
		}
	}

	public void enterAmountToKwartaPadala(String nAmount) throws Exception {
		explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
		verifyElementPresent(SendTransferPage.objKwartaPadala, getTextVal(SendTransferPage.objKwartaPadala, "Page"));
		type(SendTransferPage.objAmountTxtField, nAmount, "Amount text Field");
		click(SendTransferPage.objNextBtn, getTextVal(SendTransferPage.objNextBtn, "Button"));
		verifyElementPresent(SendTransferPage.objSelectPaymentMethod, getTextVal(SendTransferPage.objSelectPaymentMethod, "Page"));
		Thread.sleep(3000);
		click(SendTransferPage.objMLWalletBalance,getTextVal(SendTransferPage.objMLWalletBalance, "Button"));
		verifyElementPresent(SendTransferPage.objConfirmDetails, getTextVal(SendTransferPage.objConfirmDetails, "Page"));
		click(SendTransferPage.objConfirmBtn, getTextVal(SendTransferPage.objConfirmBtn, "Button"));
	}

	public void selectSavedRecipient() throws Exception {
		explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
		if (verifyElementPresent(SendTransferPage.objKwartaPadala, getTextVal(SendTransferPage.objKwartaPadala, "Page"))) {
			click(SendTransferPage.objSavedRecipients, getTextVal(SendTransferPage.objSavedRecipients, "Button"));
			explicitWaitVisible(SendTransferPage.objSavedRecipients, 5);
			click(SendTransferPage.objSavedRecipients, getTextVal(SendTransferPage.objSavedRecipients, "Page"));
			type(SendTransferPage.objSearchRecipient, prop.getproperty("Last_Name"), "Search Recipient Text Field");
			verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
			click(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
			Thread.sleep(3000);
		}
	}

	public void addRecipient() throws Exception {
		if (verifyElementPresent(SendTransferPage.objSavedRecipients, getTextVal(SendTransferPage.objSavedRecipients, "Button"))) {
			click(SendTransferPage.objSavedRecipients, getTextVal(SendTransferPage.objSavedRecipients, "Button"));
			click(SendTransferPage.objAddRecipient, getTextVal(SendTransferPage.objAddRecipient, "Button"));
			explicitWaitVisible(SendTransferPage.objAddRecipient, 5);
			type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
			click(SendTransferPage.objCheckBox, "Check Box");
			type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Last Name Text Field");
			type(SendTransferPage.objNickName, prop.getproperty("Nick_Name"), "Nick Name Text Field");
			click(SendTransferPage.ObjSaveRecipient, getTextVal(SendTransferPage.ObjSaveRecipient, "Button"));
		}
	}


	//===============================================================================================================//
	public void sendMoneyToMLBranch_STB_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		sendMoneyToAnyMLBranch();
		enterMLBranchDetails();
		enterAmountToKwartaPadala("100");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(SendTransferPage.objSendMoneySuccessful, getTextVal(SendTransferPage.objSendMoneySuccessful, "Message"))) {
			verifyElementPresent(SendTransferPage.objPHPAmount, getTextVal(SendTransferPage.objPHPAmount, "Amount"));
			verifyElementPresent(SendTransferPage.objDate, getTextVal(SendTransferPage.objDate, "Date"));
			verifyElementPresent(SendTransferPage.objReferenceNumber, getTextVal(SendTransferPage.objReferenceNumber, "Reference Number"));
			String sReference = getText(SendTransferPage.objReferenceNumber);
			System.out.println(sReference);
			String sReferenceNumber = sReference.substring(9, 20);
			System.out.println(sReferenceNumber);
			Swipe("UP", 2);
			click(SendTransferPage.objBackToHomeBtn, getTextVal(SendTransferPage.objBackToHomeBtn, "Button"));
			Thread.sleep(3000);
			Swipe("DOWN", 2);
			Swipe("UP", 1);
			verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			click(MLWalletHomePage.objKwartaPadala, getTextVal(MLWalletHomePage.objKwartaPadala, "Text"));
			if (verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
				String sReferenceNumberInCashOut = getText(SendTransferPage.objReferenceNumberInTransactionDetails);
				System.out.println(sReferenceNumberInCashOut);
				assertionValidation(sReferenceNumberInCashOut, sReferenceNumber);
				logger.info("STB_TC_01, Successfully sent Amount to ML Branch and Transaction Details is validated");
				ExtentReporter.extentLoggerPass("STB_TC_01", "STB_TC_01, Successfully sent Amount to ML Branch and Transaction Details is validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void sendMoneyRequiredDetails_STB_TC_08() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Invalid Bank Details");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		click(SendTransferPage.objSendTransferBtn, getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		verifyElementPresent(SendTransferPage.objSendMoney, getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (verifyElementPresent(SendTransferPage.objToAnyMLBranch, getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objFirstNameRequiredMsg, Utilities.getTextVal(SendTransferPage.objFirstNameRequiredMsg, "Error Message"))) {
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objFirstNameRequiredMsg);
				String sExpectedMsg = "First name is required";
				Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			}
			Utilities.hideKeyboard();
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objMiddleNameRequiredMsg, Utilities.getTextVal(SendTransferPage.objMiddleNameRequiredMsg, "Error Message"))) {
				String sMiddleNameRequiredMsg = Utilities.getText(SendTransferPage.objMiddleNameRequiredMsg);
				String sExpectedMsg = "Middle name is required";
				Utilities.assertionValidation(sMiddleNameRequiredMsg, sExpectedMsg);
			}
			Thread.sleep(3000);
			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objLastNameRequiredMsg, Utilities.getTextVal(SendTransferPage.objLastNameRequiredMsg, "Error Message"))) {
				String sLastNameRequiredMsg = Utilities.getText(SendTransferPage.objLastNameRequiredMsg);
				String sExpectedMsg = "Last name is required";
				Utilities.assertionValidation(sLastNameRequiredMsg, sExpectedMsg);
			}
			Thread.sleep(3000);
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objMobileNumberRequiredMsg, Utilities.getTextVal(SendTransferPage.objMobileNumberRequiredMsg, "Error Message"))) {
				String sMobileNumberRequiredMsg = Utilities.getText(SendTransferPage.objMobileNumberRequiredMsg);
				String sExpectedMsg = "Mobile number is required";
				Utilities.assertionValidation(sMobileNumberRequiredMsg, sExpectedMsg);
			}
			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Last Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			if (Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "page"))) {
				logger.info("STB_TC_08, Prompt msg for Receiver's Details required is validated");
				ExtentReporter.extentLoggerPass("STB_TC_08", "STB_TC_08, Prompt msg for Receiver's Details required is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void sendMoneyInvalidDetails_STB_TC_07() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Invalid Bank Details");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("Invalid_First_Name"), "First Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objFirstNameErrorMsg, Utilities.getTextVal(SendTransferPage.objFirstNameErrorMsg, "Error Message"))) {
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objFirstNameErrorMsg);
				String sExpectedMsg = "First name must only contain letters and spaces";
				Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			}
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");


			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Invalid_Middle_Name"), "Middle Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objMiddleNameErrorMsg, Utilities.getTextVal(SendTransferPage.objMiddleNameErrorMsg, "Error Message"))) {
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objMiddleNameErrorMsg);
				String sExpectedMsg = "Middle name must only contain letters and spaces";
				Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			}
			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
			Utilities.Swipe("UP", 1);

			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Invalid_Last_Name"), "Last Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objLastNameErrorMsg, Utilities.getTextVal(SendTransferPage.objLastNameErrorMsg, "Error Message"))) {
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objLastNameErrorMsg);
				String sExpectedMsg = "Last name must only contain letters and spaces";
				Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			}
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");


			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Invalid_MobileNumber"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objMobileNumberErrorMsg, Utilities.getTextVal(SendTransferPage.objMobileNumberErrorMsg, "Error Message"))) {
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objMobileNumberErrorMsg);
				String sExpectedMsg = "Mobile number is invalid";
				Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			}
			Utilities.clearField(SendTransferPage.objMobileNumber, "Mobile Number Text Field");
			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));

			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			if (Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "page"))) {
				logger.info("STB_TC_07, Prompt msg for Receiver's Details Invalid is validated");
				ExtentReporter.extentLoggerPass("STB_TC_07", "STB_TC_07, Prompt msg for Receiver's Details Invalid is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void sendMoneyAddRecipient_STB_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			addRecipient();
			Utilities.type(SendTransferPage.objSearchRecipient, prop.getproperty("Last_Name"), "Search Recipient Text Field");
			if (Utilities.verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"))) {
				logger.info("STB_TC_03, The Added Recipient is displayed in Saved Recipient Page");
				ExtentReporter.extentLoggerPass("STB_TC_03", "STB_TC_03, The Added Recipient is displayed in Saved Recipient Page");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void sendMoneyToSavedRecipient_STB_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			selectSavedRecipient();
			Thread.sleep(3000);
			Utilities.click(SendTransferPage.objSelectRecipient, Utilities.getTextVal(SendTransferPage.objSelectRecipient, "Button"));
			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
			Utilities.Swipe("UP", 1);
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Thread.sleep(5000);
			enterAmountToKwartaPadala("100");
			enableLocation_PopUp();
			enterOTP(prop.getproperty("Valid_OTP"));
			if (Utilities.verifyElementPresent(SendTransferPage.objSendMoneySuccessful, Utilities.getTextVal(SendTransferPage.objSendMoneySuccessful, "Message"))) {
				Utilities.verifyElementPresent(SendTransferPage.objPHPAmount, Utilities.getTextVal(SendTransferPage.objPHPAmount, "Amount"));
				Utilities.verifyElementPresent(SendTransferPage.objDate, Utilities.getTextVal(SendTransferPage.objDate, "Date"));
				Utilities.verifyElementPresent(SendTransferPage.objReferenceNumber, Utilities.getTextVal(SendTransferPage.objReferenceNumber, "Reference Number"));
				String sReference = Utilities.getText(SendTransferPage.objReferenceNumber);
				System.out.println(sReference);
				String sReferenceNumber = sReference.substring(9, 20);
				System.out.println(sReferenceNumber);
				Utilities.Swipe("UP", 2);
				Utilities.click(SendTransferPage.objBackToHomeBtn, Utilities.getTextVal(SendTransferPage.objBackToHomeBtn, "Button"));
				Thread.sleep(3000);
				Utilities.Swipe("DOWN", 2);
				Utilities.Swipe("UP", 1);
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.click(MLWalletHomePage.objKwartaPadala, Utilities.getTextVal(MLWalletHomePage.objKwartaPadala, "Text"));
				if (Utilities.verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, Utilities.getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
					String sReferenceNumberInCashOut = Utilities.getText(SendTransferPage.objReferenceNumberInTransactionDetails);
					System.out.println(sReferenceNumberInCashOut);
					Utilities.assertionValidation(sReferenceNumberInCashOut, sReferenceNumber);
					logger.info("STB_TC_02, Successfully sent Amount to saved Recipient and Transaction Details is validated");
					ExtentReporter.extentLoggerPass("STB_TC_02", "STB_TC_02, Successfully sent Amount to saved Recipient and Transaction Details is validated");
					Utilities.setScreenshotSource();
					System.out.println("-----------------------------------------------------------");
				}
			}
		}
	}


	public void sendMoneyContactDuplicate_STB_TC_04() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Contact Duplicate");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			addRecipient();
			if (Utilities.verifyElementPresent(SendTransferPage.objContactAlreadyExistMsg, Utilities.getTextVal(SendTransferPage.objContactAlreadyExistMsg, "Error Message"))) {
				String sContactDuplicatePopupMsg = Utilities.getText(SendTransferPage.objContactAlreadyExistMsg);
				String sExpectedPopupMsg = "Contact already exists.";
				Utilities.assertionValidation(sContactDuplicatePopupMsg, sExpectedPopupMsg);
				logger.info("STB_TC_04, Contact already exists popup message Validated");
				ExtentReporter.extentLoggerPass("STB_TC_04", "STB_TC_04, Contact already exists popup message Validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void sendMoneyEditRecipient_STB_TC_06() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			selectSavedRecipient();
			Utilities.click(SendTransferPage.objEditRecipient, Utilities.getTextVal(SendTransferPage.objEditRecipient, "Button"));
			Utilities.type(SendTransferPage.objEditRecipientLastName, prop.getproperty("Edited_Last_name"), "Last Name Text Field");
			Utilities.click(SendTransferPage.ObjSaveRecipient, Utilities.getTextVal(SendTransferPage.ObjSaveRecipient, "Button"));
			Utilities.type(SendTransferPage.objSearchRecipient, prop.getproperty("Edited_Last_name"), "Search Recipient Text Field");
			if (Utilities.verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"), prop.getproperty("First_Name")), "Recipient"))) {
				logger.info("STB_TC_06, Successfully edited the Saved Recipient");
				ExtentReporter.extentLoggerPass("STB_TC_06", "STB_TC_06, Successfully edited the Saved Recipient");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void sendMoneyDeleteRecipient_STB_TC_05() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			selectSavedRecipient();
			Utilities.click(SendTransferPage.objDeleteRecipient, Utilities.getTextVal(SendTransferPage.objDeleteRecipient, "Button"));
			Utilities.verifyElementPresent(SendTransferPage.objPopupMsg, Utilities.getTextVal(SendTransferPage.objPopupMsg, "Pop Up message"));
			String sDeleteConfirmationPopup = Utilities.getText(SendTransferPage.objPopupMsg);
			String sExceptedMsg = "Are you sure you want to remove this saved recipient?";
			Utilities.assertionValidation(sDeleteConfirmationPopup, sExceptedMsg);
			Utilities.click(SendTransferPage.objRemoveBtn, Utilities.getTextVal(SendTransferPage.objRemoveBtn, "Button"));
			Utilities.clearField(SendTransferPage.objSearchRecipient, "Search Field");
			Thread.sleep(3000);
			if (Utilities.verifyElementNotPresent(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"), prop.getproperty("First_Name")), 5)) {
				logger.info("STB_TC_05, Saved Recipient from Saved Recipients page not got deleted Successfully");
			} else {
				logger.info("STB_TC_05, Saved Recipient from Saved Recipients page deleted Successfully");
				ExtentReporter.extentLoggerPass("STB_TC_05", "STB_TC_05, Saved Recipient from Saved Recipients page deleted Successfully");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}

		}

	}

	public void sendMoneyInvalidAmount_STB_TC_09(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			enterMLBranchDetails();
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objAmountTxtField, Amount, "Amount text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objInvalidAmountMsg, Utilities.getTextVal(SendTransferPage.objInvalidAmountMsg, "Error Message"))) {
				String sInvalidAmountErrorMsg = Utilities.getText(SendTransferPage.objInvalidAmountMsg);
				String sExpectedErrorMsg = "The amount should not be less than 1";
				Utilities.assertionValidation(sInvalidAmountErrorMsg, sExpectedErrorMsg);
				logger.info("STB_TC_09, The amount should not be less than 1 - Error Message is validated");
				ExtentReporter.extentLoggerPass("STB_TC_09", "STB_TC_09, The amount should not be less than 1 - Error Message is validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void sendMoneyInsufficientAmount_STB_TC_10() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified_LowBalance"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			enterMLBranchDetails();
			enterAmountToKwartaPadala("35000");
			if (Utilities.verifyElementPresent(SendTransferPage.objInsufficientAmountMsg, Utilities.getTextVal(SendTransferPage.objInsufficientAmountMsg, "Error Message"))) {
				String sInsufficientBalanceErrorMsg = Utilities.getText(SendTransferPage.objInsufficientAmountMsg);
				String sExpectedErrorMsg = "There is insufficient balance to proceed with this transaction. Please try again.";
				Utilities.assertionValidation(sInsufficientBalanceErrorMsg, sExpectedErrorMsg);
				logger.info("STB_TC_10, Insufficient Balance - Error Message is validated");
				ExtentReporter.extentLoggerPass("STB_TC_10", "STB_TC_10, Insufficient Balance - Error Message is validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void sendMoneyMaximumAmount_STB_TC_12() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			enterMLBranchDetails();
			enterAmountToKwartaPadala("100000");
			if (Utilities.verifyElementPresent(SendTransferPage.objMaxLimitErrorMsg, getTextVal(SendTransferPage.objMaxLimitErrorMsg, "Error Message"))) {
				String sMaximumLimitErrorMsg = Utilities.getText(SendTransferPage.objMaxLimitErrorMsg);
				String sExpectedErrorMsg = "The maximum Send Money per transaction set for your verification level is P50,000.00. Please try again.";
				Utilities.assertionValidation(sMaximumLimitErrorMsg, sExpectedErrorMsg);
				logger.info("STB_TC_12, The maximum send money per transaction - Error Message is validated");
				ExtentReporter.extentLoggerPass("STB_TC_12", "STB_TC_12, The maximum send money per transaction - Error Message is validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

//================================= Phase 2 ==================================================================//


	public void sendTransferUIValidation_STB_TC_13() throws Exception {
		ExtentReporter.HeaderChildNode("Send/Transfer page UI Validation");
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		if (Utilities.verifyElementPresent(SendTransferPage.objSendMoney, getTextVal(SendTransferPage.objSendMoney, "Page"))) {
			Utilities.verifyElementPresent(SendTransferPage.objSendWalletOptions, getTextVal(SendTransferPage.objSendWalletOptions, "Header"));
			verifyElementPresent(SendTransferPage.objToAnyMLBranch, getTextVal(SendTransferPage.objToAnyMLBranch, "option"));
			verifyElementPresent(SendTransferPage.objToAMLWalletUser, getTextVal(SendTransferPage.objToAMLWalletUser, "option"));
			logger.info("STB_TC_13, Send/Transfer page UI Validated");
			ExtentReporter.extentLoggerPass("STB_TC_13", "STB_TC_13, Send/Transfer page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyToBranchUIValidation_STB_TC_14() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to ML Branch page UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		click(SendTransferPage.objSendTransferBtn, getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		verifyElementPresentAndClick(SendTransferPage.objToAnyMLBranch, getTextVal(SendTransferPage.objToAnyMLBranch, "Option"));
		explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
		if (verifyElementPresent(SendTransferPage.objKwartaPadala, getTextVal(SendTransferPage.objKwartaPadala, "Page"))) {
			verifyElementPresent(SendTransferPage.objSavedRecipients, getTextVal(SendTransferPage.objSavedRecipients, "Button"));
			verifyElementPresent(SendTransferPage.objFirstname, "First Name Input Field");
			verifyElementPresent(SendTransferPage.objMiddleName, "Middle Name Input Field");
			verifyElementPresent(SendTransferPage.objCheckBox, "Check box to Receiver legally does not have middle Name");
			verifyElementPresent(SendTransferPage.objLastName, "Last Name Input Field");
			verifyElementPresent(SendTransferPage.objMobileNumber, "Mobile Number Input Field");
			verifyElementPresent(SendTransferPage.objNextBtn, getTextVal(SendTransferPage.objNextBtn, "Button"));
			logger.info("STB_TC_14, Send Money to ML Branch page UI Validation");
			ExtentReporter.extentLoggerPass("STB_TC_14", "STB_TC_14, Send Money to ML Branch page UI Validation");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyToBranchSaveRecipientPageUIValidation_STB_TC_15() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money To Branch Save Recipient Page UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		click(SendTransferPage.objSendTransferBtn, getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		verifyElementPresentAndClick(SendTransferPage.objToAnyMLBranch, getTextVal(SendTransferPage.objToAnyMLBranch, "Option"));
		explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
		verifyElementPresentAndClick(SendTransferPage.objSavedRecipients, getTextVal(SendTransferPage.objSavedRecipients, "Button"));
		if (verifyElementPresent(SendTransferPage.objSavedRecipients, getTextVal(SendTransferPage.objSavedRecipients, "Page"))) {
			verifyElementPresent(SendTransferPage.objAddRecipient, getTextVal(SendTransferPage.objAddRecipient, "Button"));
			verifyElementPresent(SendTransferPage.objSelectRecipient, getTextVal(SendTransferPage.objSelectRecipient, "Header"));
			verifyElementPresent(SendTransferPage.objSearchRecipient, "Search Recipient Input Field");
			if (verifyElementDisplayed(SendTransferPage.objSavedRecipientsList)) {
				List<WebElement> values = findElements(SendTransferPage.objSavedRecipientsList);
				for (int i = 0; i < values.size(); i++) {
					String savedRecipientName = values.get(i).getText();
					logger.info("Saved Recipient : " + savedRecipientName + " is displayed");
					ExtentReporter.extentLogger(" ", "Saved Recipient : " + savedRecipientName + " is displayed");
				}
			} else if (verifyElementPresent(SendTransferPage.objNoRecentTransactionTxt, getTextVal(SendTransferPage.objNoRecentTransactionTxt, "Text"))) {
				logger.info("No Saved Recipients are present");
			}
			logger.info("STB_TC_15, Send Money To Branch Save Recipient Page UI Validated");
			ExtentReporter.extentLoggerPass("STB_TC_15", "STB_TC_15, Send Money To Branch Save Recipient Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void sendMoneyToBranchSuccessUIValidation_STB_TC_16() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money To Branch Success UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		click(SendTransferPage.objSendTransferBtn, getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		verifyElementPresent(SendTransferPage.objSendMoney, getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (verifyElementPresent(SendTransferPage.objToAnyMLBranch, getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			click(SendTransferPage.objToAnyMLBranch, getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			enterMLBranchDetails();
			enterAmountToKwartaPadala("100");
			enableLocation_PopUp();
			enterOTP(prop.getproperty("Valid_OTP"));
			if (verifyElementPresent(SendTransferPage.objSendMoneySuccessful, getTextVal(SendTransferPage.objSendMoneySuccessful, "Message"))) {
				verifyElementPresent(SendTransferPage.objPHPAmount, getTextVal(SendTransferPage.objPHPAmount, "Amount"));
				verifyElementPresent(SendTransferPage.objDate, getTextVal(SendTransferPage.objDate, "Date"));
				verifyElementPresent(SendTransferPage.objReferenceNumber, getTextVal(SendTransferPage.objReferenceNumber, "Reference Number"));
				verifyElementPresent(SendTransferPage.objTransactionDetails, getTextVal(SendTransferPage.objTransactionDetails, "Header"));
				verifyElementPresent(SendTransferPage.objReceiverName, getTextVal(SendTransferPage.objReceiverName, "Receiver's Name"));
				verifyElementPresent(SendTransferPage.objReceiverMobileNo, getTextVal(SendTransferPage.objReceiverMobileNo, "Receiver's Mobile Number"));
				verifyElementPresent(SendTransferPage.objPaymentMethod, getTextVal(SendTransferPage.objPaymentMethod, "Payment Method"));
				verifyElementPresent(SendTransferPage.objAmount, getTextVal(SendTransferPage.objAmount, "Amount"));
				verifyElementPresent(SendTransferPage.objServiceFee, getTextVal(SendTransferPage.objServiceFee, "Service Fee"));
				verifyElementPresent(SendTransferPage.objTotalAmount, getTextVal(SendTransferPage.objTotalAmount, "Total Amount"));
				Swipe("UP", 1);
				verifyElementPresent(SendTransferPage.objBackToHomeBtn, getTextVal(SendTransferPage.objBackToHomeBtn, "Button"));
				verifyElementPresent(SendTransferPage.objNewTransaction, getTextVal(SendTransferPage.objNewTransaction, "Button"));
				logger.info("STB_TC_16, Send Money To Branch Success page UI Validated");
				ExtentReporter.extentLoggerPass("STB_TC_16", "STB_TC_16, Send Money To Branch Success page UI Validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void sendMoneyToBranchConfirmDetailsPageUIValidation_STB_TC_17(String nAmount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money To Branch Confirm Details Page UI Validation");
		sendMoneyToAnyMLBranch();
		enterMLBranchDetails();
		explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
		verifyElementPresent(SendTransferPage.objKwartaPadala, getTextVal(SendTransferPage.objKwartaPadala, "Page"));
		type(SendTransferPage.objAmountTxtField, nAmount, "Amount text Field");
		click(SendTransferPage.objNextBtn, getTextVal(SendTransferPage.objNextBtn, "Button"));
		verifyElementPresent(SendTransferPage.objSelectPaymentMethod, getTextVal(SendTransferPage.objSelectPaymentMethod, "Page"));
		Thread.sleep(3000);
		click(SendTransferPage.objMLWalletBalance, getTextVal(SendTransferPage.objMLWalletBalance, "Button"));
		if (verifyElementPresent(SendTransferPage.objConfirmDetails, getTextVal(SendTransferPage.objConfirmDetails, "Page"))) {
			verifyElementPresent(SendTransferPage.objTransactionDetails, getTextVal(SendTransferPage.objTransactionDetails, "Header"));
			verifyElementPresent(SendTransferPage.objReceiverName, getTextVal(SendTransferPage.objReceiverName, "Receiver's Name"));
			verifyElementPresent(SendTransferPage.objReceiverMobileNo, getTextVal(SendTransferPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(SendTransferPage.objPaymentMethod, getTextVal(SendTransferPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(SendTransferPage.objAmount, getTextVal(SendTransferPage.objAmount, "Amount"));
			verifyElementPresent(SendTransferPage.objServiceFee, getTextVal(SendTransferPage.objServiceFee, "Service Fee"));
			verifyElementPresent(SendTransferPage.objTotalAmount, getTextVal(SendTransferPage.objTotalAmount, "Total Amount"));
			verifyElementPresent(SendTransferPage.objConfirmBtn, getTextVal(SendTransferPage.objConfirmBtn, "Button"));
			logger.info("STB_TC_17, Send Money To Branch Confirm Details Page UI Validated");
			ExtentReporter.extentLoggerPass("STB_TC_17", "STB_TC_17, Send Money To Branch Confirm Details Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyToBranchSelectPaymentMethodPageUIValidation_STB_TC_18(String nAmount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money To Branch Select Payment Method Page UI Validation");
		sendMoneyToAnyMLBranch();
		enterMLBranchDetails();
		explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
		verifyElementPresent(SendTransferPage.objKwartaPadala, getTextVal(SendTransferPage.objKwartaPadala, "Page"));
		type(SendTransferPage.objAmountTxtField, nAmount, "Amount text Field");
		click(SendTransferPage.objNextBtn, getTextVal(SendTransferPage.objNextBtn, "Button"));
		if (verifyElementPresent(SendTransferPage.objSelectPaymentMethod, getTextVal(SendTransferPage.objSelectPaymentMethod, "Page"))) {
			verifyElementPresent(SendTransferPage.objMLWalletBalance, getTextVal(SendTransferPage.objMLWalletBalance, "Button"));
			verifyElementPresent(SendTransferPage.objAvailableBalance, getTextVal(SendTransferPage.objAvailableBalance, "Available PHP"));
			logger.info("STB_TC_18, Send Money To Branch Select Payment Method Page UI Validated");
			ExtentReporter.extentLoggerPass("STB_TC_18", "STB_TC_18, Send Money To Branch Select Payment Method Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyToBranchEnterAmountPageUIValidation_STB_TC_19() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money To Branch Enter Amount Page UI Validation");
		sendMoneyToAnyMLBranch();
		enterMLBranchDetails();
		explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
		if (verifyElementPresent(SendTransferPage.objKwartaPadala, getTextVal(SendTransferPage.objKwartaPadala, "Page"))) {
			verifyElementPresent(SendTransferPage.objAmountToSend, getTextVal(SendTransferPage.objAmountToSend, "Header"));
			verifyElementPresent(SendTransferPage.objAmountTxtField, "Amount Input Field");
			verifyElementPresent(SendTransferPage.objNextBtn, getTextVal(SendTransferPage.objNextBtn, "Button"));
			logger.info("STB_TC_19, Send Money To Branch Enter Amount Page UI Validated");
			ExtentReporter.extentLoggerPass("STB_TC_19", "STB_TC_19, Send Money To Branch Enter Amount Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void kwartaPadalaTransactionDetailsUIValidation_STB_TC_20() throws Exception {
		ExtentReporter.HeaderChildNode("Kwarta Padala Transaction Details UI Validation");
		sendMoneyToMLBranch_STB_TC_01();
		if (verifyElementPresent(SendTransferPage.objTransactionDetails, getTextVal(SendTransferPage.objTransactionDetails, "Header"))) {
			verifyElementPresent(SendTransferPage.objKwartaPadala, getTextVal(SendTransferPage.objKwartaPadala, "Text"));
			verifyElementPresent(SendTransferPage.objKwartaPadalaDate, getTextVal(SendTransferPage.objKwartaPadalaDate, "Kwarta Padala Date"));
			verifyElementPresent(SendTransferPage.objReceiverName, getTextVal(SendTransferPage.objReceiverName, "Receiver's Name"));
			verifyElementPresent(SendTransferPage.objReceiverMobileNo, getTextVal(SendTransferPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(SendTransferPage.objPaymentMethod, getTextVal(SendTransferPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(SendTransferPage.objAmount, getTextVal(SendTransferPage.objAmount, "Amount"));
			verifyElementPresent(SendTransferPage.objServiceFee, getTextVal(SendTransferPage.objServiceFee, "Service Fee"));
			verifyElementPresent(SendTransferPage.objTotalAmount, getTextVal(SendTransferPage.objTotalAmount, "Total Amount"));
			logger.info("STB_TC_20, Kwarta Padala Transaction Details page UI Validated");
			ExtentReporter.extentLoggerPass("STB_TC_20", "STB_TC_20, Kwarta Padala Transaction Details page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyToBranchAddRecipientPageUIValidation_STB_TC_21() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money To Branch Add Recipient Page UI Validation");
		sendMoneyToAnyMLBranch();
		if (verifyElementPresent(SendTransferPage.objSavedRecipients, getTextVal(SendTransferPage.objSavedRecipients, "Button"))) {
			click(SendTransferPage.objSavedRecipients, getTextVal(SendTransferPage.objSavedRecipients, "Button"));
			click(SendTransferPage.objAddRecipient, getTextVal(SendTransferPage.objAddRecipient, "Button"));
			explicitWaitVisible(SendTransferPage.objAddRecipient, 5);
			if (verifyElementPresent(SendTransferPage.objAddRecipient, getTextVal(SendTransferPage.objAddRecipient, "Page"))) {
				verifyElementPresent(SendTransferPage.objFirstname, "First Name Text Field");
				verifyElementPresent(SendTransferPage.objMiddleName, "Middle Name Text Field");
				verifyElementPresent(SendTransferPage.objCheckBox, "Check Box");
				verifyElementPresent(SendTransferPage.objLastName, "Last Name Text Field");
				verifyElementPresent(SendTransferPage.objMobileNumber, "Last Name Text Field");
				verifyElementPresent(SendTransferPage.objNickName, "Nick Name Text Field");
				verifyElementPresent(SendTransferPage.ObjSaveRecipient, getTextVal(SendTransferPage.ObjSaveRecipient, "Button"));
				logger.info("STB_TC_21, Send Money To Branch Add Recipient Page UI Validated");
				ExtentReporter.extentLoggerPass("STB_TC_21", "STB_TC_21, Send Money To Branch Add Recipient Page UI Validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}










//===============================================Send/Transfer To a ML Wallet User=============================//
//========================== Generalized methods for Send/Transfer To a ML Wallet User========================//

	public void sendMoneyMLWallet(String sTier) throws Exception {
		mlWalletLogin(sTier);
		click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		verifyElementPresentAndClick(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
	}


	public void enterMobileNumberMLWallet(String nMobileNumber) throws Exception {
			explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			verifyElementPresent(SendTransferPage.objSendMoney,getTextVal(SendTransferPage.objSendMoney, "Page"));
			type(SendTransferPage.objMobileNumberField, nMobileNumber, "Mobile Number Text Field");
			click(SendTransferPage.objNextBtn, getTextVal(SendTransferPage.objNextBtn, "Button"));

	}

	public void enterAmountAndSendToMLWallet(String nAmount) throws Exception {
		Utilities.explicitWaitVisible(SendTransferPage.objAmountTxtField, 5);
		if (Utilities.verifyElementPresent(SendTransferPage.objToMLWalletUser, Utilities.getTextVal(SendTransferPage.objToMLWalletUser, "Page"))) {
			Utilities.type(SendTransferPage.objAmountTxtField, nAmount, "Amount Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.click(SendTransferPage.objMLWalletBalance, Utilities.getTextVal(SendTransferPage.objMLWalletBalance, "Button"));
			Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails, Utilities.getTextVal(SendTransferPage.objConfirmDetails, "Page"));
			Utilities.Swipe("UP", 1);
			Utilities.click(SendTransferPage.objSendPHPBtn, Utilities.getTextVal(SendTransferPage.objSendPHPBtn, "Button"));
		}
	}





//======================================================================================================================//
	public void sendToMLWalletUser_STW_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Wallet");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		enterAmountAndSendToMLWallet("10");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWallet, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWallet, "Message"))) {
			Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletPHP, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletPHP, "Amount"));
			Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletDate, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletDate, "Date"));
			Utilities.verifyElementPresent(SendTransferPage.objMLWalletReferenceNumber, Utilities.getTextVal(SendTransferPage.objMLWalletReferenceNumber, "Reference Number"));
			String sReferenceNumber = Utilities.getText(SendTransferPage.objMLWalletReferenceNumber);
			System.out.println(sReferenceNumber);
			Swipe("UP", 2);
			click(SendTransferPage.objBackToHomeBtn, getTextVal(SendTransferPage.objBackToHomeBtn, "Button"));
			Thread.sleep(3000);
			Swipe("DOWN", 2);
			Swipe("UP", 1);
			verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			verifyElementPresent(MLWalletHomePage.objWalletToWallet, getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
			click(MLWalletHomePage.objWalletToWallet, getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
			if (verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
				String sReferenceNumberInWalletToWallet = getText(SendTransferPage.objReferenceNumberInTransactionDetails);
				System.out.println(sReferenceNumberInWalletToWallet);
				assertionValidation(sReferenceNumberInWalletToWallet, sReferenceNumber);
				logger.info("STW_TC_01, Successfully Amount sent from Wallet to Wallet and Transaction Details is validated");
				ExtentReporter.extentLoggerPass("STW_TC_01", "STW_TC_01, Successfully Amount sent from Wallet to Wallet and Transaction Details is validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void sendMoneyAddToFavorites_STW_TC_12() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Add To Favorites");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		enterAmountAndSendToMLWallet("10");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(SendTransferPage.objSendMoneyMLWallet, getTextVal(SendTransferPage.objSendMoneyMLWallet, "Message"))) {
			verifyElementPresent(SendTransferPage.objSendMoneyMLWalletPHP, getTextVal(SendTransferPage.objSendMoneyMLWalletPHP, "Amount"));
			verifyElementPresent(SendTransferPage.objSendMoneyMLWalletDate, getTextVal(SendTransferPage.objSendMoneyMLWalletDate, "Date"));
			verifyElementPresent(SendTransferPage.objMLWalletReferenceNumber, getTextVal(SendTransferPage.objMLWalletReferenceNumber, "Reference Number"));
			String sReferenceNumber =getText(SendTransferPage.objMLWalletReferenceNumber);
			System.out.println(sReferenceNumber);
			Swipe("UP", 1);
			click(SendTransferPage.objSaveToMyFavorite, getTextVal(SendTransferPage.objSaveToMyFavorite, "Button"));
			if (verifyElementPresent(SendTransferPage.objAddedToFavoritesMsg, getTextVal(SendTransferPage.objAddedToFavoritesMsg, "Message"))) {
				click(SendTransferPage.objOkBtn, getTextVal(SendTransferPage.objOkBtn, "Button"));
			}
			if(verifyElementPresent(SendTransferPage.objSendMoney,getTextVal(SendTransferPage.objSendMoney,"Page"))) {
				verifyElementPresent(SendTransferPage.objFavoriteReceiver,"Added Favorites");
				logger.info("STW_TC_12, Added to favorites and displayed in Recent Favorites");
				ExtentReporter.extentLoggerPass("STW_TC_12", "STW_TC_12, Added to favorites and displayed in Recent Favorites");
				System.out.println("-----------------------------------------------------------");
			}

		}
	}


	public void sendMoneyMLWalletToExistingReceiver_STW_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet To Existing Receiver");
//		sendMoneyAddToFavorites();
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		verifyElementPresent(SendTransferPage.objSelectFavorite, getTextVal(SendTransferPage.objSelectFavorite, "Text"));
		click(SendTransferPage.objSelectFavorite, getTextVal(SendTransferPage.objSelectFavorite, "Text"));
		click(SendTransferPage.objNextBtn, getTextVal(SendTransferPage.objNextBtn, "Button"));
		enterAmountAndSendToMLWallet("10");
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(SendTransferPage.objSendMoneyMLWallet, getTextVal(SendTransferPage.objSendMoneyMLWallet, "Message"))) {
			verifyElementPresent(SendTransferPage.objSendMoneyMLWalletPHP, getTextVal(SendTransferPage.objSendMoneyMLWalletPHP, "Amount"));
			verifyElementPresent(SendTransferPage.objSendMoneyMLWalletDate, getTextVal(SendTransferPage.objSendMoneyMLWalletDate, "Date"));
			verifyElementPresent(SendTransferPage.objMLWalletReferenceNumber, getTextVal(SendTransferPage.objMLWalletReferenceNumber, "Reference Number"));
			String sReferenceNumber = getText(SendTransferPage.objMLWalletReferenceNumber);
			System.out.println(sReferenceNumber);
			Swipe("UP", 2);
			click(SendTransferPage.objBackToHomeBtn, getTextVal(SendTransferPage.objBackToHomeBtn, "Button"));
			Thread.sleep(3000);
			Swipe("DOWN", 2);
			Swipe("UP", 1);
			verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			verifyElementPresent(MLWalletHomePage.objWalletToWallet, getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
			click(MLWalletHomePage.objWalletToWallet, getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
			if (verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
				String sReferenceNumberInWalletToWallet = getText(SendTransferPage.objReferenceNumberInTransactionDetails);
				System.out.println(sReferenceNumberInWalletToWallet);
				assertionValidation(sReferenceNumberInWalletToWallet, sReferenceNumber);
				logger.info("STW_TC_02, Successfully Amount sent from Wallet to Wallet to Recently added favorite and Transaction Details is validated");
				ExtentReporter.extentLoggerPass("STW_TC_02", "STW_TC_02, Successfully Amount sent from Wallet to Wallet to Recently added favorite and Transaction Details is validated");
				System.out.println("-----------------------------------------------------------");
			}
		}

	}


	public void sendToMLWalletInvalidMobNumber_STW_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Send To ML Wallet to Invalid Mobile Number");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Invalid_MobileNumber"));
		explicitWaitVisible(SendTransferPage.objMobileNumberErrorMsg,5);
		if (verifyElementPresent(SendTransferPage.objMobileNumberErrorMsg, getTextVal(SendTransferPage.objMobileNumberErrorMsg, "Error Message"))) {
			String sFirstNameErrorMsg = getText(SendTransferPage.objMobileNumberErrorMsg);
			String sExpectedMsg = "Mobile number is invalid";
			assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			logger.info("STW_TC_03, Mobile number is invalid - Error Message is validated");
			ExtentReporter.extentLoggerPass("STW_TC_03", "STW_TC_03, Mobile number is invalid - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void sendToMLWalletUnRegisteredNumber_STW_TC_04() throws Exception {
		ExtentReporter.HeaderChildNode("Send To ML Wallet to Invalid Mobile Number");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Unregistered_MobileNumber"));
		explicitWaitVisible(SendTransferPage.objUnRegisteredMobNumber,10);
		if (verifyElementPresent(SendTransferPage.objUnRegisteredMobNumber, getTextVal(SendTransferPage.objUnRegisteredMobNumber, "Error Message"))) {
			String sFirstNameErrorMsg = getText(SendTransferPage.objUnRegisteredMobNumber);
			String sExpectedMsg = "Receiver not Found!";
			assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			logger.info("STW_TC_04, Receiver not Found - Error Message is validated");
			ExtentReporter.extentLoggerPass("STW_TC_04", "STW_TC_04, Receiver not Found - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void sendToMLWalletInvalidAmount_STW_TC_05(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		Utilities.explicitWaitVisible(SendTransferPage.objAmountTxtField, 5);
		Utilities.type(SendTransferPage.objAmountTxtField, Amount, "Amount Text Field");
		Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
		if (Utilities.verifyElementPresent(SendTransferPage.objInvalidAmountMsg, Utilities.getTextVal(SendTransferPage.objInvalidAmountMsg, "Error Message"))) {
			String sInvalidAmountErrorMsg = Utilities.getText(SendTransferPage.objInvalidAmountMsg);
			String sExpectedErrorMsg = "The amount should not be less than 1";
			Utilities.assertionValidation(sInvalidAmountErrorMsg, sExpectedErrorMsg);
			logger.info("STW_TC_05, The amount should not be less than 1 - Error Message is validated");
			ExtentReporter.extentLoggerPass("STW_TC_05", "STW_TC_05, The amount should not be less than 1 - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void sendToMLWalletInsufficientAmount_STW_TC_06() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		sendMoneyMLWallet("9999999912");
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		enterAmountAndSendToMLWallet("35000");
		Utilities.explicitWaitVisible(SendTransferPage.objInsufficientAmountMsg,5);
		if (Utilities.verifyElementPresent(SendTransferPage.objInsufficientAmountMsg, Utilities.getTextVal(SendTransferPage.objInsufficientAmountMsg, "Error Message"))) {
			String sInsufficientBalanceErrorMsg = Utilities.getText(SendTransferPage.objInsufficientAmountMsg);
			String sExpectedErrorMsg = "There is insufficient balance to proceed with this transaction. Please try again.";
			Utilities.assertionValidation(sInsufficientBalanceErrorMsg, sExpectedErrorMsg);
			logger.info("STW_TC_06, Insufficient Balance - Error Message is validated");
			ExtentReporter.extentLoggerPass("STW_TC_06", "STW_TC_06, Insufficient Balance - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void sendMoneyMLWalletMaximumAmount_STW_TC_07() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet Maximum Amount");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		enterAmountAndSendToMLWallet("100000");
		if (verifyElementPresent(SendTransferPage.objMaxLimitErrorMsg, getTextVal(SendTransferPage.objMaxLimitErrorMsg, "Error Message"))) {
			String sMaximumLimitErrorMsg = getText(SendTransferPage.objMaxLimitErrorMsg);
			String sExpectedErrorMsg = "The maximum Send Money per transaction set for your verification level is P50,000.00. Please try again.";
			assertionValidation(sMaximumLimitErrorMsg, sExpectedErrorMsg);
			logger.info("STW_TC_07, The maximum send money per transaction - Error Message is validated");
			ExtentReporter.extentLoggerPass("STW_TC_07", "STW_TC_07, The maximum send money per transaction - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyDeleteFromFavorites_STW_TC_09() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Delete From Favorites");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		explicitWaitVisible(SendTransferPage.objSendMoney, 5);
		verifyElementPresent(SendTransferPage.objSendMoney, getTextVal(SendTransferPage.objSendMoney, "Page"));
		click(SendTransferPage.objViewAllBtn, getTextVal(SendTransferPage.objViewAllBtn, "Text"));
		click(SendTransferPage.objEllipsisBtn, "Ellipsis Button");
		click(SendTransferPage.objDeleteBtn, getTextVal(SendTransferPage.objDeleteBtn, "Button"));
		click(SendTransferPage.objConfirmBtn, getTextVal(SendTransferPage.objConfirmBtn, "Button"));
		if (verifyElementPresent(SendTransferPage.objFavRemovedMsg, getTextVal(SendTransferPage.objFavRemovedMsg, "Pop up Message"))) {
			String sRemovedSuccessfulMsg = getText(SendTransferPage.objFavRemovedMsg);
			String sExpectedMsg = "Successfully Removed";
			assertionValidation(sRemovedSuccessfulMsg, sExpectedMsg);
			logger.info("STW_TC_09, Successfully removed Favorite Contact from favorites list is validated");
			ExtentReporter.extentLoggerPass("STW_TC_09", "STW_TC_09, Successfully removed Favorite Contact from favorites list is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void sendMoneyMLWalletUIValidation_STW_TC_10() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet Page UI Validation");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		explicitWaitVisible(SendTransferPage.objSendMoney, 5);
		if(verifyElementPresent(SendTransferPage.objSendMoney, getTextVal(SendTransferPage.objSendMoney, "Page"))){
			verifyElementPresent(SendTransferPage.objViewAllBtn,getTextVal(SendTransferPage.objViewAllBtn,"Button"));
			verifyElementPresent(SendTransferPage.objRecentFavorites,getTextVal(SendTransferPage.objRecentFavorites,"Header"));
			verifyElementPresent(SendTransferPage.objReceiver,getTextVal(SendTransferPage.objReceiver,"Header"));
			verifyElementPresent(SendTransferPage.objMobileNumberField,"Mobile number input text field");
			verifyElementPresent(SendTransferPage.objNextBtn,getTextVal(SendTransferPage.objNextBtn,"Button"));
			logger.info("STW_TC_10, Send Money ML Wallet Page UI validated");
			ExtentReporter.extentLoggerPass("STW_TC_10", "STW_TC_10, Send Money ML Wallet Page UI validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyFavoritesUIValidation_STW_TC_11() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Saved Favorites UI Validation");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		explicitWaitVisible(SendTransferPage.objSendMoney, 5);
		verifyElementPresentAndClick(SendTransferPage.objViewAllBtn,getTextVal(SendTransferPage.objViewAllBtn,"Button"));
		if(verifyElementPresent(SendTransferPage.ObjFavorites,getTextVal(SendTransferPage.ObjFavorites,"Page"))){
			verifyElementPresent(SendTransferPage.objSearchFavoriteField,"Search Favorites Field");
			logger.info("STW_TC_11, Send Money ML Wallet Page UI validated");
			ExtentReporter.extentLoggerPass("STW_TC_11", "STW_TC_11, Send Money ML Wallet Page UI validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyMLWalletCancelTransaction_STW_TC_13(String nAmount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet Cancel Transaction");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		explicitWaitVisible(SendTransferPage.objAmountTxtField, 5);
		if (verifyElementPresent(SendTransferPage.objToMLWalletUser, getTextVal(SendTransferPage.objToMLWalletUser, "Page"))) {
			type(SendTransferPage.objAmountTxtField, nAmount, "Amount Text Field");
			click(SendTransferPage.objNextBtn, getTextVal(SendTransferPage.objNextBtn, "Button"));
			click(SendTransferPage.objMLWalletBalance, getTextVal(SendTransferPage.objMLWalletBalance, "Button"));
			verifyElementPresent(SendTransferPage.objConfirmDetails, getTextVal(SendTransferPage.objConfirmDetails, "Page"));
			Swipe("UP", 1);
			verifyElementPresentAndClick(SendTransferPage.objCancelTransaction,getTextVal(SendTransferPage.objCancelTransaction,"Button"));
			if(verifyElementPresent(SendTransferPage.objSendMoney,getTextVal(SendTransferPage.objSendMoney,"Page"))){
				logger.info("STW_TC_13, Cancelled the current Transaction");
				ExtentReporter.extentLoggerPass("STW_TC_13", "STW_TC_13, Cancelled the current Transaction");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void sendMoneyMLWalletSearchUnFavoriteUser_STW_TC_14() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet Search UnFavorite User");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		explicitWaitVisible(SendTransferPage.objSendMoney, 5);
		verifyElementPresentAndClick(SendTransferPage.objViewAllBtn,getTextVal(SendTransferPage.objViewAllBtn,"Button"));
		verifyElementPresent(SendTransferPage.ObjFavorites,getTextVal(SendTransferPage.ObjFavorites,"Page"));
		type(SendTransferPage.objSearchFavoriteField,"ABCD","Search Favorite Field");
		if(verifyElementPresent(SendTransferPage.objNoRecentTransactionTxt,getTextVal(SendTransferPage.objNoRecentTransactionTxt,"Added Favorite"))){
			logger.info("STW_TC_14, Send Money ML Wallet Search UnFavorite User Validated");
			ExtentReporter.extentLoggerPass("STW_TC_14", "STW_TC_14, Send Money ML Wallet Search UnFavorite User Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyMLWalletSearchFavoriteUser_STW_TC_15() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet Search Favorite User");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		explicitWaitVisible(SendTransferPage.objSendMoney, 5);
		verifyElementPresentAndClick(SendTransferPage.objViewAllBtn,getTextVal(SendTransferPage.objViewAllBtn,"Button"));
		verifyElementPresent(SendTransferPage.ObjFavorites,getTextVal(SendTransferPage.ObjFavorites,"Page"));
		type(SendTransferPage.objSearchFavoriteField,prop.getproperty("Last_Name"),"Search Favorite Field");
		if(verifyElementPresent(SendTransferPage.objAddedFavorite,getTextVal(SendTransferPage.objAddedFavorite,"Added Favorite"))){
			logger.info("STW_TC_15, Send Money ML Wallet Search Favorite User Validated");
			ExtentReporter.extentLoggerPass("STW_TC_15", "STW_TC_15, Send Money ML Wallet Search Favorite User Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyMLWalletSuccessPageUIValidation_STW_TC_16() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet Success Page UI Validation");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		enterAmountAndSendToMLWallet("10");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (verifyElementPresent(SendTransferPage.objSendMoneyMLWallet, getTextVal(SendTransferPage.objSendMoneyMLWallet, "Message"))) {
			verifyElementPresent(SendTransferPage.objSendMoneyMLWalletPHP, getTextVal(SendTransferPage.objSendMoneyMLWalletPHP, "Amount"));
			verifyElementPresent(SendTransferPage.objSendMoneyMLWalletDate, getTextVal(SendTransferPage.objSendMoneyMLWalletDate, "Date"));
			verifyElementPresent(SendTransferPage.objMLWalletReferenceNumber, getTextVal(SendTransferPage.objMLWalletReferenceNumber, "Reference Number"));
			verifyElementPresent(SendTransferPage.objReceiverName,getTextVal(SendTransferPage.objReceiverName,"Receiver Name"));
			verifyElementPresent(SendTransferPage.objReceiverMobileNo, getTextVal(SendTransferPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(SendTransferPage.objPaymentMethod, getTextVal(SendTransferPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(SendTransferPage.objAmount, getTextVal(SendTransferPage.objAmount, "Amount"));
			verifyElementPresent(SendTransferPage.objServiceFee, getTextVal(SendTransferPage.objServiceFee, "Service Fee"));
			verifyElementPresent(SendTransferPage.objTotalAmount, getTextVal(SendTransferPage.objTotalAmount, "Total Amount"));
			Swipe("UP",2);
			verifyElementPresent(SendTransferPage.objSaveToMyFavorite,getTextVal(SendTransferPage.objSaveToMyFavorite,"Button"));
			verifyElementPresent(SendTransferPage.objBackToHomeBtn,getTextVal(SendTransferPage.objBackToHomeBtn,"Button"));
			verifyElementPresent(SendTransferPage.objNewTransaction,getTextVal(SendTransferPage.objNewTransaction,"Button"));
			logger.info("STW_TC_16,Send Money ML Wallet Success Page UI Validated");
			ExtentReporter.extentLoggerPass("STW_TC_16", "STW_TC_16, Send Money ML Wallet Success Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyMLWalletOTPPageUIValidation_STW_TC_17() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet OTP PageUI Validation");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		enterAmountAndSendToMLWallet("10");
		enableLocation_PopUp();
		explicitWaitVisible(MLWalletLoginPage.objOneTimePin, 5);
		if (verifyElementPresent(MLWalletLoginPage.objOneTimePin, getTextVal(MLWalletLoginPage.objOneTimePin, "Page"))) {
			verifyElementPresent(MLWalletLoginPage.objOtpTextField, "OTP text Field");
			verifyElementPresent(MLWalletCashOutPage.objResendCode, getTextVal(MLWalletCashOutPage.objResendCode, "Seconds"));
			logger.info("STW_TC_17, One Time Pin page UI Validated");
			ExtentReporter.extentLoggerPass("STW_TC_17", "STW_TC_16, One Time Pin page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyMLWalletConfirmDetailsPageUIValidation_STW_TC_18(String nAmount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet Confirm Details Page UI Validation");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		explicitWaitVisible(SendTransferPage.objAmountTxtField, 5);
		verifyElementPresent(SendTransferPage.objToMLWalletUser, getTextVal(SendTransferPage.objToMLWalletUser, "Page"));
		type(SendTransferPage.objAmountTxtField, nAmount, "Amount Text Field");
		click(SendTransferPage.objNextBtn, getTextVal(SendTransferPage.objNextBtn, "Button"));
		click(SendTransferPage.objMLWalletBalance, getTextVal(SendTransferPage.objMLWalletBalance, "Button"));
		if(verifyElementPresent(SendTransferPage.objConfirmDetails, getTextVal(SendTransferPage.objConfirmDetails, "Page"))){
			verifyElementPresent(SendTransferPage.objReceiverName,getTextVal(SendTransferPage.objReceiverName,"Receiver Name"));
			verifyElementPresent(SendTransferPage.objReceiverMobileNo, getTextVal(SendTransferPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(SendTransferPage.objPaymentMethod, getTextVal(SendTransferPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(SendTransferPage.objAmount, getTextVal(SendTransferPage.objAmount, "Amount"));
			verifyElementPresent(SendTransferPage.objServiceFee, getTextVal(SendTransferPage.objServiceFee, "Service Fee"));
			verifyElementPresent(SendTransferPage.objTotalAmount, getTextVal(SendTransferPage.objTotalAmount, "Total Amount"));
			verifyElementPresent(SendTransferPage.objCancelTransaction,getTextVal(SendTransferPage.objCancelTransaction,"Button"));
			Swipe("UP", 1);
			verifyElementPresent(SendTransferPage.objSendPHPBtn,getTextVal(SendTransferPage.objSendPHPBtn,"Button"));
			logger.info("STW_TC_18, Send Money ML Wallet Confirm Details Page UI Validated");
			ExtentReporter.extentLoggerPass("STW_TC_18", "STW_TC_18, Send Money ML Wallet Confirm Details Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyToMlWalletEnterAmountPageUIValidation_STW_TC_19() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money To Ml Wallet Enter Amount Page UI Validation");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		explicitWaitVisible(SendTransferPage.objAmountTxtField, 5);
		if(verifyElementPresent(SendTransferPage.objToMLWalletUser, getTextVal(SendTransferPage.objToMLWalletUser, "Page"))) {
			verifyElementPresent(SendTransferPage.objAmountToSend, getTextVal(SendTransferPage.objAmountToSend, "Header"));
			verifyElementPresent(SendTransferPage.objAmountTxtField, "Amount Text Field");
			verifyElementPresent(SendTransferPage.objNextBtn, getTextVal(SendTransferPage.objNextBtn, "Button"));
			logger.info("STW_TC_19, Send Money To Ml Wallet Enter Amount Page UI Validated");
			ExtentReporter.extentLoggerPass("STW_TC_19", "STW_TC_19, Send Money To Ml Wallet Enter Amount Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyToMLWalletPageUIValidation_STW_TC_20() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money To ML Wallet Page UI Validation");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		explicitWaitVisible(SendTransferPage.objSendMoney, 5);
		if(verifyElementPresent(SendTransferPage.objSendMoney,getTextVal(SendTransferPage.objSendMoney, "Page"))) {
			verifyElementPresent(SendTransferPage.objRecentFavorites,getTextVal(SendTransferPage.objRecentFavorites,"Header"));
			verifyElementPresent(SendTransferPage.objViewAllBtn,getTextVal(SendTransferPage.objViewAllBtn,"Button"));
			verifyElementPresent(SendTransferPage.objReceiver,getTextVal(SendTransferPage.objReceiver,"Header"));
			verifyElementPresent(SendTransferPage.objMobileNumberField, "Mobile Number Text Field");
			verifyElementPresent(SendTransferPage.objNextBtn, getTextVal(SendTransferPage.objNextBtn, "Button"));
			logger.info("STW_TC_20, Send Money To ML Wallet Page UI Validated");
			ExtentReporter.extentLoggerPass("STW_TC_20", "STW_TC_20, Send Money To ML Wallet Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void sendMoneyMlWalletTransactionDetailsUIValidation_STW_TC_21() throws Exception {
		ExtentReporter.HeaderChildNode("Transaction Details of Wallet To Wallet Page UI Validation");
		sendMoneyMLWallet(prop.getproperty("Fully_verified"));
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		enterAmountAndSendToMLWallet("10");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWallet, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWallet, "Message"))) {
			Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletPHP, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletPHP, "Amount"));
			Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletDate, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletDate, "Date"));
			Utilities.verifyElementPresent(SendTransferPage.objMLWalletReferenceNumber, Utilities.getTextVal(SendTransferPage.objMLWalletReferenceNumber, "Reference Number"));
			Swipe("UP", 2);
			click(SendTransferPage.objBackToHomeBtn, getTextVal(SendTransferPage.objBackToHomeBtn, "Button"));
			Thread.sleep(3000);
			Swipe("DOWN", 2);
			Swipe("UP", 1);
			verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			verifyElementPresent(MLWalletHomePage.objWalletToWallet, getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
			click(MLWalletHomePage.objWalletToWallet, getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
			if(verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objTransactionDetails, "Page"))) {
				verifyElementPresent(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, "Reference Number"));
				verifyElementPresent(MLWalletTransactionHistoryPage.objDate, getTextVal(MLWalletTransactionHistoryPage.objDate, "Date"));
				verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverName, getTextVal(MLWalletTransactionHistoryPage.objReceiverName, "Receiver Name"));
				verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverMobileNo, getTextVal(MLWalletTransactionHistoryPage.objReceiverMobileNo, "Receiver's Mobile Number"));
				verifyElementPresent(MLWalletTransactionHistoryPage.objPaymentMethod, getTextVal(MLWalletTransactionHistoryPage.objPaymentMethod, "Payment Method"));
				verifyElementPresent(MLWalletTransactionHistoryPage.objSenderName, getTextVal(MLWalletTransactionHistoryPage.objSenderName, "Sender Name"));
				verifyElementPresent(MLWalletTransactionHistoryPage.objAmount, getTextVal(MLWalletTransactionHistoryPage.objAmount, "Amount"));
				verifyElementPresent(MLWalletTransactionHistoryPage.objServiceFee, getTextVal(MLWalletTransactionHistoryPage.objServiceFee, "Service Fee"));
				verifyElementPresent(MLWalletTransactionHistoryPage.objTotalAmount, getTextVal(MLWalletTransactionHistoryPage.objTotalAmount, "Total Amount"));
				logger.info("STW_TC_21, Transaction Details of Wallet To Wallet Page UI Validation Validated");
				ExtentReporter.extentLoggerPass("STW_TC_21", "STW_TC_21, Transaction Details of Wallet To Wallet Page UI Validation Validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}











//================================================ Transaction History ===========================================//


	public void mlWallet_TransactionHistory_Generic_Steps(String billModule, String transaction) throws Exception {
		String PayBillsHistory = Utilities.getText(MLWalletTransactionHistoryPage.objBillHistory(billModule, transaction));
		if (PayBillsHistory.equals(billModule))// "Pay Bills"
		{
			List<WebElement> values = Utilities
					.findElements(MLWalletTransactionHistoryPage.objPayBillsTransctionList1(billModule));
			for (int i = 0; i < values.size(); i++) {
				String billPayTransaction = values.get(i).getText();
				logger.info(billModule + " All Transactions : " + billPayTransaction);
				ExtentReporter.extentLogger(" ", billModule + " All Transactions : " + billPayTransaction);
			}
		} else if (PayBillsHistory.equals(transaction))// "No Recent Transaction"
		{
			logger.info("No Recent Transactions Are Available for " + billModule + " Module");
			ExtentReporter.extentLogger("", "No Recent Transactions Are Available for " + billModule + " Module");
		}
	}

	public void mlWallet_TransactionHistory_TH_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("MLWallet_TransactionHistory_Scenario");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.verifyElementPresent(MLWalletTransactionHistoryPage.objRecentTransaction, Utilities.getText(MLWalletTransactionHistoryPage.objRecentTransaction));
		Utilities.Swipe("UP", 2);
		Utilities.click(MLWalletTransactionHistoryPage.objSeeMoreBtn, "See More Button");
		if(Utilities.verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionHistory,Utilities.getTextVal(MLWalletTransactionHistoryPage.objTransactionHistory,"Page"))) {
			logger.info("TH_TC_01, All Transactions are displayed");
			ExtentReporter.extentLoggerPass("TH_TC_01", "'TH_TC_01', All Transactions are displayed");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void billsPayTransactionHHistory_TH_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Bills Pay Transaction History");
		mlWallet_TransactionHistory_TH_TC_01();
		Utilities.click(MLWalletTransactionHistoryPage.objBillsPayTab, "Bills Pay");
		mlWallet_TransactionHistory_Generic_Steps("Pay Bills", "No Recent Transaction");
		logger.info("TH_TC_02, Bills pay Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_02", "'TH_TC_02', Bills pay Transactions are displayed");
		System.out.println("-----------------------------------------------------------");
	}

	public void buyLoadTransactionHistory_TH_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Buy Eload Transaction History");
		mlWallet_TransactionHistory_TH_TC_01();
		Utilities.click(MLWalletTransactionHistoryPage.objeLoadTab, "eLoad");
		mlWallet_TransactionHistory_Generic_Steps("Buy Eload", "No Recent Transaction");
		logger.info("TH_TC_03, eLoad Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_03", "'TH_TC_03', eLoad Transactions Transactions are displayed");
		System.out.println("-----------------------------------------------------------");
	}

	public void sendMoneyTransactionHistory_TH_TC_04() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Transaction History");
		mlWallet_TransactionHistory_TH_TC_01();
		Utilities.click(MLWalletTransactionHistoryPage.objSendMoneyTab, "Send Money");
		mlWallet_TransactionHistory_Generic_Steps("Kwarta Padala", "No Recent Transaction"); // Kwarta Padala
		logger.info("TH_TC_04, Send Money Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_04", "'TH_TC_04', Send Money Transactions are displayed");
		System.out.println("-----------------------------------------------------------");
	}

	public void cashInTransactionHistory_TH_TC_05() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Transaction History");
		mlWallet_TransactionHistory_TH_TC_01();
		scrollToFirstHorizontalScrollableElement("Cash In");
		click(MLWalletTransactionHistoryPage.objCashInTab, "Cash In");
		Thread.sleep(3000);
		mlWallet_TransactionHistory_Generic_Steps("Cash In", "No Recent Transaction");
		logger.info("TH_TC_05, Cash In Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_05", "'TH_TC_05',  Cash In Transactions are displayed");
		System.out.println("-----------------------------------------------------------");
	}

	public void cashOutTransactionHistory_TH_TC_06() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Transaction History");
		mlWallet_TransactionHistory_TH_TC_01();
		Utilities.click(MLWalletTransactionHistoryPage.objCashOutTab, "Cash Out");
		Thread.sleep(3000);
		mlWallet_TransactionHistory_Generic_Steps("Cash Out", "No Recent Transaction");
		logger.info("TH_TC_06,  Cash Out Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_06", "'TH_TC_06', Cash Out Transactions are displayed");
		System.out.println("-----------------------------------------------------------");
	}

	public void receiveMoneyTransactionHistory_TH_TC_07() throws Exception {
		ExtentReporter.HeaderChildNode("Receive Money Transaction History");
		mlWallet_TransactionHistory_TH_TC_01();
		Utilities.click(MLWalletTransactionHistoryPage.objReceiveMoneyTab, "Receive Money");
		Thread.sleep(3000);
		mlWallet_TransactionHistory_Generic_Steps("Receive Money", "No Recent Transaction");
		logger.info("TH_TC_07, Receive Money Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_07", "'TH_TC_07', Receive Money Transactions are displayed");
		System.out.println("-----------------------------------------------------------");
	}

	public void balanceAdjustmentTransactionHistory_TH_TC_08() throws Exception {
		ExtentReporter.HeaderChildNode("Balance Adjustment Transaction History");
		mlWallet_TransactionHistory_TH_TC_01();
		Utilities.scrollToFirstHorizontalScrollableElement("ML Shop");
		Utilities.click(MLWalletTransactionHistoryPage.objBalanceAdjustmentTab, "Balance Adjustment");
		Thread.sleep(2000);
		mlWallet_TransactionHistory_Generic_Steps("Balance Adjustment", "No Recent Transaction");
		logger.info("TH_TC_08, Balance Adjustment Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_08", "'TH_TC_08', Balance Adjustment Transactions are displayed");
		System.out.println("-----------------------------------------------------------");
	}

	public void mlShopTransactionHistory_TH_TC_09() throws Exception {
		ExtentReporter.HeaderChildNode("ML Shop Transaction History");
		mlWallet_TransactionHistory_TH_TC_01();
		Utilities.click(MLWalletTransactionHistoryPage.objMlShopTab, "ML Shop");
		Thread.sleep(2000);
		mlWallet_TransactionHistory_Generic_Steps("ML Shop", "No Recent Transaction");
		logger.info("TH_TC_09, ML Shop Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_09", "'TH_TC_09', ML Shop Transactions are displayed");
		System.out.println("-----------------------------------------------------------");
	}


	public void transactionHistoryUIValidation_TH_TC_10() throws Exception {
		ExtentReporter.HeaderChildNode("Transaction History UI Validation");
		mlWallet_TransactionHistory_TH_TC_01();
		if(verifyElementPresent(MLWalletTransactionHistoryPage.objAllTab,getTextVal(MLWalletTransactionHistoryPage.objAllTab,"Tab"))){
			verifyElementPresent(MLWalletTransactionHistoryPage.objBillsPayTab,getTextVal(MLWalletTransactionHistoryPage.objBillsPayTab,"Tab"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objeLoadTab,getTextVal(MLWalletTransactionHistoryPage.objeLoadTab,"Tab"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objSendMoneyTab,getTextVal(MLWalletTransactionHistoryPage.objSendMoneyTab,"Tab"));
			scrollToFirstHorizontalScrollableElement("Cash Out");
			verifyElementPresent(MLWalletTransactionHistoryPage.objCashInTab,getTextVal(MLWalletTransactionHistoryPage.objCashInTab,"Tab"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objCashOutTab,getTextVal(MLWalletTransactionHistoryPage.objCashOutTab,"Tab"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiveMoneyTab,getTextVal(MLWalletTransactionHistoryPage.objReceiveMoneyTab,"Tab"));
			scrollToFirstHorizontalScrollableElement("ML Shop");
			verifyElementPresent(MLWalletTransactionHistoryPage.objBalanceAdjustmentTab,getTextVal(MLWalletTransactionHistoryPage.objBalanceAdjustmentTab,"Tab"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objMlShopTab,getTextVal(MLWalletTransactionHistoryPage.objMlShopTab,"Tab"));
			logger.info("TH_TC_10,Transaction History UI Validated");
			ExtentReporter.extentLoggerPass("TH_TC_10", "'TH_TC_10',Transaction History UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void transactionHistoryBillsPayTransactionDetailsValidation_TH_TC_13() throws Exception {
		ExtentReporter.HeaderChildNode("Transaction History BillsPay Transaction Details Validation");
		mlWallet_TransactionHistory_TH_TC_01();
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objBillsPayTab,getTextVal(MLWalletTransactionHistoryPage.objBillsPayTab,"Tab"));
		if (verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objFirstTransaction, "First Transaction")) {
			verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objTransactionDetails, "Page"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, "Reference Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objDate,getTextVal(MLWalletTransactionHistoryPage.objDate,"Date"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverName,getTextVal(MLWalletTransactionHistoryPage.objReceiverName,"Receiver Name"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverMobileNo, getTextVal(MLWalletTransactionHistoryPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objBiller,getTextVal(MLWalletTransactionHistoryPage.objBiller,"Biller"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objPaymentMethod, getTextVal(MLWalletTransactionHistoryPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objAmount, getTextVal(MLWalletTransactionHistoryPage.objAmount, "Amount"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objServiceFee, getTextVal(MLWalletTransactionHistoryPage.objServiceFee, "Service Fee"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objTotalAmount, getTextVal(MLWalletTransactionHistoryPage.objTotalAmount, "Total Amount"));
			logger.info("TH_TC_13,Transaction History BillsPay Transaction Details Validated");
			ExtentReporter.extentLoggerPass("TH_TC_13", "'TH_TC_13',Transaction History BillsPay Transaction Details Validated");
			System.out.println("-----------------------------------------------------------");
		}
		else {
			logger.info("No recent Transaction");
		}
	}

	public void transactionHistoryELoadTransactionDetailsValidation_TH_TC_14() throws Exception {
		ExtentReporter.HeaderChildNode("Transaction History ELoad Transaction Details Validation");
		mlWallet_TransactionHistory_TH_TC_01();
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objeLoadTab,getTextVal(MLWalletTransactionHistoryPage.objeLoadTab,"Tab"));
		if (verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objFirstTransaction, "First Transaction")) {
			verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objTransactionDetails, "Page"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, "Reference Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objDate,getTextVal(MLWalletTransactionHistoryPage.objDate,"Date"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverMobileNo, getTextVal(MLWalletTransactionHistoryPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objLoadType,getTextVal(MLWalletTransactionHistoryPage.objLoadType,"Load Type"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objPaymentMethod, getTextVal(MLWalletTransactionHistoryPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objAmount, getTextVal(MLWalletTransactionHistoryPage.objAmount, "Amount"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objServiceFee, getTextVal(MLWalletTransactionHistoryPage.objServiceFee, "Service Fee"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objTotalAmount, getTextVal(MLWalletTransactionHistoryPage.objTotalAmount, "Total Amount"));
			logger.info("TH_TC_14,Transaction History ELoad Transaction Details Validated");
			ExtentReporter.extentLoggerPass("TH_TC_14", "'TH_TC_14',Transaction History ELoad Transaction Details Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void transactionHistorySendMoneyWalletToWalletTransactionDetailsValidation_TH_TC_15() throws Exception {
		ExtentReporter.HeaderChildNode("Transaction History Send Money Wallet To Wallet Transaction Details Validation");
		mlWallet_TransactionHistory_TH_TC_01();
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objSendMoneyTab,getTextVal(MLWalletTransactionHistoryPage.objSendMoneyTab,"Tab"));
		swipeDownUntilElementVisible("Wallet to Wallet");
		if(verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objWalletToWallet,getTextVal(MLWalletTransactionHistoryPage.objWalletToWallet,"Transaction"))){

			verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objTransactionDetails, "Page"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, "Reference Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objDate,getTextVal(MLWalletTransactionHistoryPage.objDate,"Date"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverName,getTextVal(MLWalletTransactionHistoryPage.objReceiverName,"Receiver Name"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverMobileNo, getTextVal(MLWalletTransactionHistoryPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objPaymentMethod, getTextVal(MLWalletTransactionHistoryPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objSenderName,getTextVal(MLWalletTransactionHistoryPage.objSenderName,"Sender Name"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objAmount, getTextVal(MLWalletTransactionHistoryPage.objAmount, "Amount"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objServiceFee, getTextVal(MLWalletTransactionHistoryPage.objServiceFee, "Service Fee"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objTotalAmount, getTextVal(MLWalletTransactionHistoryPage.objTotalAmount, "Total Amount"));
			logger.info("TH_TC_15,Transaction History Send Money Wallet To Wallet Transaction Details Validated");
			ExtentReporter.extentLoggerPass("TH_TC_15", "'TH_TC_15',Transaction History Send Money Wallet To Wallet Transaction Details Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void transactionHistorySendMoneyKwartaPadalaTransactionDetailsValidation_TH_TC_16() throws Exception {
		ExtentReporter.HeaderChildNode("Transaction History Send Money Kwarta Padala Transaction Details Validation");
		mlWallet_TransactionHistory_TH_TC_01();
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objSendMoneyTab,getTextVal(MLWalletTransactionHistoryPage.objSendMoneyTab,"Tab"));
		swipeDownUntilElementVisible("Kwarta Padala");
		if(verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objKwartaPadala,getTextVal(MLWalletTransactionHistoryPage.objKwartaPadala,"Transaction"))){
			verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objTransactionDetails, "Page"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, "Reference Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objDate,getTextVal(MLWalletTransactionHistoryPage.objDate,"Date"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverName,getTextVal(MLWalletTransactionHistoryPage.objReceiverName,"Receiver Name"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverMobileNo, getTextVal(MLWalletTransactionHistoryPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objPaymentMethod, getTextVal(MLWalletTransactionHistoryPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objAmount, getTextVal(MLWalletTransactionHistoryPage.objAmount, "Amount"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objServiceFee, getTextVal(MLWalletTransactionHistoryPage.objServiceFee, "Service Fee"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objTotalAmount, getTextVal(MLWalletTransactionHistoryPage.objTotalAmount, "Total Amount"));
			logger.info("TH_TC_16,Transaction History Send Money Kwarta Padala Transaction Details Validated");
			ExtentReporter.extentLoggerPass("TH_TC_16", "'TH_TC_16',Transaction History Send Money Kwarta Padala Transaction Details Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void transactionHistoryCashInTransactionDetailsValidation_TH_TC_17() throws Exception {
		ExtentReporter.HeaderChildNode("Transaction History Cash In Transaction Details Validation");
		mlWallet_TransactionHistory_TH_TC_01();
		scrollToFirstHorizontalScrollableElement("Cash In");
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objCashInTab, getTextVal(MLWalletTransactionHistoryPage.objCashInTab,"Tab"));
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objFirstTransaction, "First Transaction");
		if (verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objTransactionDetails, "Page"))) {
			verifyElementPresent(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, "Reference Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objDate,getTextVal(MLWalletTransactionHistoryPage.objDate,"Date"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverMobileNo, getTextVal(MLWalletTransactionHistoryPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objPaymentMethod, getTextVal(MLWalletTransactionHistoryPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionType, getTextVal(MLWalletTransactionHistoryPage.objTransactionType, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objBank,getTextVal(MLWalletTransactionHistoryPage.objBank,"Bank"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objAmount, getTextVal(MLWalletTransactionHistoryPage.objAmount, "Amount"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objServiceFee, getTextVal(MLWalletTransactionHistoryPage.objServiceFee, "Service Fee"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objTotalCashIn, getTextVal(MLWalletTransactionHistoryPage.objTotalCashIn, "Total Cash In"));
			logger.info("TH_TC_17,Transaction History Cash In Transaction Details Validated");
			ExtentReporter.extentLoggerPass("TH_TC_17", "'TH_TC_17',Transaction History Cash In Transaction Details Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void transactionHistoryCashOutTransactionDetailsValidation_TH_TC_18() throws Exception {
		ExtentReporter.HeaderChildNode("Transaction History Cash Out Transaction Details Validation");
		mlWallet_TransactionHistory_TH_TC_01();
		scrollToFirstHorizontalScrollableElement("Cash Out");
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objCashOutTab, getTextVal(MLWalletTransactionHistoryPage.objCashOutTab,"Tab"));
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objFirstTransaction, "First Transaction");
		if (verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objTransactionDetails, "Page"))) {
			verifyElementPresent(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, "Reference Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objDate,getTextVal(MLWalletTransactionHistoryPage.objDate,"Date"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverName,getTextVal(MLWalletTransactionHistoryPage.objReceiverName,"Receiver Name"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverMobileNo, getTextVal(MLWalletTransactionHistoryPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objPaymentMethod, getTextVal(MLWalletTransactionHistoryPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionType, getTextVal(MLWalletTransactionHistoryPage.objTransactionType, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objBank,getTextVal(MLWalletTransactionHistoryPage.objBank,"Bank"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objAmount, getTextVal(MLWalletTransactionHistoryPage.objAmount, "Amount"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objServiceFee, getTextVal(MLWalletTransactionHistoryPage.objServiceFee, "Service Fee"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objTotalCashOut, getTextVal(MLWalletTransactionHistoryPage.objTotalCashOut, "Total Cash Out"));
			logger.info("TH_TC_18,Transaction History Cash Out Transaction Details Validated");
			ExtentReporter.extentLoggerPass("TH_TC_18", "'TH_TC_18',Transaction History Cash Out Transaction Details Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void transactionHistoryReceiveMoneyTransactionDetailsValidation_TH_TC_19() throws Exception {
		ExtentReporter.HeaderChildNode("Transaction History Receive Money Transaction Details Validation");
		mlWallet_TransactionHistory_TH_TC_01();
		scrollToFirstHorizontalScrollableElement("Receive Money");
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objReceiveMoneyTab, getTextVal(MLWalletTransactionHistoryPage.objReceiveMoneyTab, "Tab"));
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objFirstTransaction, "First Transaction");
		if (verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objTransactionDetails, "Page"))) {
			verifyElementPresent(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, "Reference Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objDate,getTextVal(MLWalletTransactionHistoryPage.objDate,"Date"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverName,getTextVal(MLWalletTransactionHistoryPage.objReceiverName,"Receiver Name"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverMobileNo, getTextVal(MLWalletTransactionHistoryPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objPaymentMethod, getTextVal(MLWalletTransactionHistoryPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objSenderName,getTextVal(MLWalletTransactionHistoryPage.objSenderName,"Sender Name"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objAmountReceived,getTextVal(MLWalletTransactionHistoryPage.objAmountReceived,"Amount Received"));
			logger.info("TH_TC_19,Transaction History Receive Money Transaction Details Validated");
			ExtentReporter.extentLoggerPass("TH_TC_19", "'TH_TC_19',Transaction History Receive Money Transaction Details Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void transactionHistoryMLShopTransactionDetailsValidation_TH_TC_20() throws Exception {
		ExtentReporter.HeaderChildNode("Transaction History ML Shop Transaction Details Validation");
		mlWallet_TransactionHistory_TH_TC_01();
		scrollToFirstHorizontalScrollableElement("ML Shop");
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objMlShopTab, getTextVal(MLWalletTransactionHistoryPage.objMlShopTab, "Tab"));
		verifyElementPresentAndClick(MLWalletTransactionHistoryPage.objFirstTransaction, "First Transaction");
		if (verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objTransactionDetails, "Page"))) {
			verifyElementPresent(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, "Reference Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objDate, getTextVal(MLWalletTransactionHistoryPage.objDate, "Date"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverName,getTextVal(MLWalletTransactionHistoryPage.objReceiverName,"Receiver Name"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverMobileNo, getTextVal(MLWalletTransactionHistoryPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objPaymentMethod, getTextVal(MLWalletTransactionHistoryPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objAmountReceived,getTextVal(MLWalletTransactionHistoryPage.objAmountReceived,"Amount Received"));
		}
	}



















//=================================== ML Wallet Shop Items ==========================================================//


	public void mlWallet_ShopItems_Generic_Steps() throws Exception {
//		 ExtentReporter.HeaderChildNode("Shop_Items");
		Utilities.click(MLWalletShopItemsPage.objShopItemsTab, "Shop Items Icon");
		Utilities.verifyElementPresentAndClick(MLWalletShopItemsPage.objMLShopPage, "ML Shop Page");
		Thread.sleep(10000);
		Utilities.Swipe("UP", 2);
		Utilities.click(MLWalletShopItemsPage.objItemMenu, "Rings Item");
		Utilities.click(MLWalletShopItemsPage.objSelectItem, Utilities.getTextVal(MLWalletShopItemsPage.objSelectItem, "Item"));
		Utilities.Swipe("up", 2);
		Utilities.click(MLWalletShopItemsPage.objAddToCartBtn, "Add to cart Button");
		Utilities.Swipe("down", 4);
		Utilities.click(MLWalletShopItemsPage.objHambergerMenu, "Hamburger Menu");
		Utilities.click(MLWalletShopItemsPage.objyourBagMenu, "Your Bag");
		Utilities.click(MLWalletShopItemsPage.objCheckBox, "Check Box");
		Utilities.click(MLWalletShopItemsPage.objCheckOutBtn, "Checkout Button");
		Utilities.click(MLWalletShopItemsPage.objEditAddress, "Edit Address Tab");
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objSelectBranchPage, Utilities.getTextVal(MLWalletShopItemsPage.objSelectBranchPage, "Page"));
		Utilities.click(MLWalletShopItemsPage.objInputFieldOne, "Select Branch Field 1");
		Utilities.click(MLWalletShopItemsPage.objBranchName, Utilities.getTextVal(MLWalletShopItemsPage.objBranchName, "Branch Name"));
		Utilities.click(MLWalletShopItemsPage.objInputFieldTwo, "Select Branch Field 2");
		Utilities.click(MLWalletShopItemsPage.objSubBranchName, Utilities.getTextVal(MLWalletShopItemsPage.objSubBranchName, "Branch Name"));
		Utilities.click(MLWalletShopItemsPage.objInputFieldThree, "Select Branch Field 3");
		Utilities.click(MLWalletShopItemsPage.objSubBranchNameTwo, Utilities.getTextVal(MLWalletShopItemsPage.objSubBranchNameTwo, "Branch Name"));
		Utilities.click(MLWalletShopItemsPage.objSaveBtn, "Save Button");
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objAddressSuccessfulMsg, Utilities.getTextVal(MLWalletShopItemsPage.objAddressSuccessfulMsg, "Message"));
		Utilities.click(MLWalletShopItemsPage.objOkBtn, "OK Button");
		Utilities.scrollToVertical("Place Order");
		Utilities.click(MLWalletShopItemsPage.objPlaceOrderBtn, "Place Order Button");

	}

	public void mlWallet_ShopItems_Successful_Purchase() throws Exception {
		ExtentReporter.HeaderChildNode("mlWalletShopItems_Successful_Purchase");
		mlWallet_ShopItems_Generic_Steps();
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objOtpPage, Utilities.getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		Utilities.click(MLWalletShopItemsPage.objOtpTextField, "Otp Text Field");
		Utilities.handleOtp(prop.getproperty("otp"));
		Utilities.click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		// code for successful purchase message validation
	}

	public void mlWallet_ShopItems_with_Insufficient_Balance() throws Exception {
		ExtentReporter.HeaderChildNode("mlWallet_ShopItems_with_Insufficient_Balance");
		mlWalletLogin(prop.getproperty("Buyer_Tier"));
		mlWallet_ShopItems_Generic_Steps();
		verifyElementPresent(MLWalletShopItemsPage.objOtpPage, Utilities.getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		click(MLWalletShopItemsPage.objOtpTextField, "Otp Text Field");
		handleOtp(prop.getproperty("OTP"));
		click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		String oOpsMsg = getText(MLWalletShopItemsPage.objInvalidOtpPopUp);
		String supplyFieldsMsg = getText(MLWalletShopItemsPage.objInvalidOtpPopUpMsg);
		logger.info(oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");
		ExtentReporter.extentLogger("", oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");
		logger.info("MLS_TC_02, Oops... Insufficient Balance. - Error message is validated ");
		ExtentReporter.extentLoggerPass("MLS_TC_02", "MLS_TC_02, Oops... Insufficient Balance. - Error message is validated");
		System.out.println("-----------------------------------------------------------");
	}

	public void mlWallet_ShopItems_with_Incorrect_Otp() throws Exception {
		ExtentReporter.HeaderChildNode("mlWallet_ShopItems_with_Incorrect_Otp");
		mlWallet_ShopItems_Generic_Steps();
		verifyElementPresent(MLWalletShopItemsPage.objOtpPage, getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		click(MLWalletShopItemsPage.objOtpTextField, "Otp Text Field");
		handleOtp(prop.getproperty("incorrectOtp"));
		click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		// Code to be written to validate incorrect otp msg
	}

	public void mlWallet_ShopItems_without_Input_Otp() throws Exception {
		ExtentReporter.HeaderChildNode("mlWallet_ShopItems_without_Input_Otp");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		mlWallet_ShopItems_Generic_Steps();
		verifyElementPresent(MLWalletShopItemsPage.objOtpPage, getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		String oOpsMsg = getText(MLWalletShopItemsPage.objInvalidOtpPopUp);
		String supplyFieldsMsg = getText(MLWalletShopItemsPage.objInvalidOtpPopUpMsg);
		logger.info(oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");
		ExtentReporter.extentLogger("", oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");

		logger.info("MLS_TC_04, Oops... Please supply all fields. - Error message is validated");
		ExtentReporter.extentLoggerPass("MLS_TC_04", "MLS_TC_04, Oops... Please supply all fields. - Error message is validated");
		System.out.println("-----------------------------------------------------------");
	}



//=========================================== Cash In Via Bank ================================================//
//======================= Generalized methods for Cash In Via Bank ===========================================//

	public void selectBankAndInputAmount(String sAmount) throws Exception {
		if (verifyElementPresent(MLWalletCashInBank.objCashIn, getTextVal(MLWalletCashInBank.objCashIn, "Icon"))) {
			click(MLWalletCashInBank.objCashIn, getTextVal(MLWalletCashInBank.objCashIn, "Icon"));
			click(MLWalletCashInBank.objMyBankAccount, getTextVal(MLWalletCashInBank.objMyBankAccount, "Button"));
			verifyElementPresent(MLWalletCashInBank.objSelectABank, getTextVal(MLWalletCashInBank.objSelectABank, "Page"));
			click(MLWalletCashInBank.objTestBankOnline, getTextVal(MLWalletCashInBank.objTestBankOnline, "Bank"));
			verifyElementPresent(MLWalletCashInBank.objDragonPay, getTextVal(MLWalletCashInBank.objDragonPay, "Page"));
			verifyElementPresent(MLWalletCashInBank.objBankCashIn, getTextVal(MLWalletCashInBank.objBankCashIn, "Text"));
			type(MLWalletCashInBank.objAmountEditField, sAmount, "Amount Text Field");
			click(MLWalletCashInBank.objNextBtn, getTextVal(MLWalletCashInBank.objNextBtn, "Button"));
			Thread.sleep(3000);
		}
	}
	public void dragonPayChargesMsgValidation() throws Exception {
		if (verifyElementPresent(MLWalletCashInBank.objDragonPayChargesMsg, getTextVal(MLWalletCashInBank.objDragonPayChargesMsg, "Message"))) {
			String sDragonPayChargesMsg = getText(MLWalletCashInBank.objDragonPayChargesMsg);
			String sExpectedDragonPayChargesMsg = "Dragon Pay charges a fee of 30 pesos for this transaction. Do you wish to continue with your transaction?";
			assertionValidation(sDragonPayChargesMsg, sExpectedDragonPayChargesMsg);
			click(MLWalletCashInBank.objContinueBtn, getTextVal(MLWalletCashInBank.objContinueBtn, "Button"));
		}
	}
	public void reviewTransactionValidation() throws Exception {
		verifyElementPresent(MLWalletCashInBank.objReviewTransaction,getTextVal(MLWalletCashInBank.objReviewTransaction,"Page"));
		Swipe("UP",1);
		if(verifyElementPresent(MLWalletCashInBank.objBankTransferCutOffTime,getTextVal(MLWalletCashInBank.objBankTransferCutOffTime,"Message"))){
			String sBankTransferTime = getText(MLWalletCashInBank.objBankTransferCutOffTime);
			String sExpectedBankTransferTime ="Bank transfers after 1:00PM are posted on the next banking day.";
			assertionValidation(sBankTransferTime,sExpectedBankTransferTime);
		}
		click(MLWalletCashInBank.objNextBtn,getTextVal(MLWalletCashInBank.objNextBtn,"Button"));
	}


	public void bankUserLogin(String sLoginId,String sPassword) throws Exception {
		explicitWaitVisible(MLWalletCashInBank.objReferenceNumberMsg,5);
		if(verifyElementPresent(MLWalletCashInBank.objReferenceNumberMsg,getTextVal(MLWalletCashInBank.objReferenceNumberMsg,"Reference Information"))){
			type(MLWalletCashInBank.objLoginIdTxtField,sLoginId,"Login Id Text Field");
			type(MLWalletCashInBank.objPasswordTxtField,sPassword,"Password Text Field");

		}
	}


//===================================================================================================================//

	public void cashInViaBank_CIBA_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		selectBankAndInputAmount("100");
		dragonPayChargesMsgValidation();
		reviewTransactionValidation();
		enterOTP(prop.getproperty("Valid_OTP"));
		enableLocation_PopUp();
		bankUserLogin(prop.getproperty("Valid_LoginId"), prop.getproperty("Valid_Password"));
		click(MLWalletCashInBank.objWebContinueBtn,"Continue Button");
		click(MLWalletCashInBank.objPayBtn,getTextVal(MLWalletCashInBank.objPayBtn,"Button"));
		verifyElementPresent(MLWalletCashInBank.objBankReferenceNumber,getTextVal(MLWalletCashInBank.objBankReferenceNumber,"Reference Number"));
		verifyElementPresent(MLWalletCashInBank.objStatus,getTextVal(MLWalletCashInBank.objStatus,"Status"));
		verifyElementPresent(MLWalletCashInBank.objMessage,getTextVal(MLWalletCashInBank.objMessage,"Message"));
		if(verifyElementPresent(MLWalletCashInBank.objSuccessMsg,getTextVal(MLWalletCashInBank.objSuccessMsg,"Message"))){
			logger.info("CIBA_TC_01, Cash In Via Bank validated");
			ExtentReporter.extentLoggerPass("CIBA_TC_01", "CIBA_TC_01, Cash In Via Bank validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

//	public void cashInViaBankInvalidBankDetails() throws Exception {
//		ExtentReporter.HeaderChildNode("Cash In Via Bank Invalid Bank Details");
//		mlWalletLogin(prop.getproperty("Branch_Verified"));
//		selectBankAndInputAmount("100");
//		dragonPayChargesMsgValidation();
//		reviewTransactionValidation();
//		enterMLPin();
//		enableLocation_PopUp();
//		bankUserLogin(prop.getproperty("InValid_LoginId"), prop.getproperty("Invalid_Password"));
//	}

	public void cashInViaBankMinimumTransactionLimit_CIBA_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Minimum Transaction Limit");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		selectBankAndInputAmount("20");
		if(verifyElementPresent(MLWalletCashInBank.objMinimumTransactionPopupMsg,getTextVal(MLWalletCashInBank.objMinimumTransactionPopupMsg,"Pop Message"))){
			String sMinimumTransactionPopupMsg = getText(MLWalletCashInBank.objMinimumTransactionPopupMsg);
			String sExpectedPopupMsg = "The supplied amount is less than the required minimum transaction limit";
			assertionValidation(sMinimumTransactionPopupMsg,sExpectedPopupMsg);
			logger.info("CIBA_TC_03, Minimum transaction limit pop up message is validated");
			ExtentReporter.extentLoggerPass("CIBA_TC_03", "CIBA_TC_03, Minimum transaction limit pop up message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankMaximumTransaction_CIBA_TC_04() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Maximum Transaction");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		selectBankAndInputAmount("60000");
		dragonPayChargesMsgValidation();
		reviewTransactionValidation();
		if (verifyElementPresent(MLWalletCashInBank.objMaxLimitErrorMsg, getTextVal(MLWalletCashInBank.objMaxLimitErrorMsg, "Error Message"))) {
			String sMaximumLimitErrorMsg = getText(MLWalletCashInBank.objMaxLimitErrorMsg);
			String sExpectedErrorMsg = "The maximum Bank Cash-in per transaction set for your verification level is P50,000.00. Please try again.";
			assertionValidation(sMaximumLimitErrorMsg, sExpectedErrorMsg);
			logger.info("CIBA_TC_04, The maximum send money per transaction - Error Message is validated");
			ExtentReporter.extentLoggerPass("CIBA_TC_04", "CIBA_TC_04, The maximum send money per transaction - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void cashInViaBankInvalidAmount_STW_TC_05() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Invalid Amount");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		selectBankAndInputAmount("0");
		if (verifyElementPresent(MLWalletCashInBank.objInvalidAmountMsg, getTextVal(MLWalletCashInBank.objInvalidAmountMsg, "Error Message"))) {
			String sInvalidAmountErrorMsg = getText(MLWalletCashInBank.objInvalidAmountMsg);
			String sExpectedErrorMsg = "The amount should not be less than 1";
			assertionValidation(sInvalidAmountErrorMsg, sExpectedErrorMsg);
			logger.info("STW_TC_05, The amount should not be less than 1 - Error Message is validated");
			ExtentReporter.extentLoggerPass("STW_TC_05", "STW_TC_05, The amount should not be less than 1 - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankNavigation_STW_TC_06() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Navigation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		verifyElementPresentAndClick(MLWalletCashInBank.objCashIn,getTextVal(MLWalletCashInBank.objCashIn,"Icon"));
		if(verifyElementPresent(MLWalletCashInBank.objCashIn,getTextVal(MLWalletCashInBank.objCashIn,"Page"))){
			logger.info("STW_TC_06, Navigated to Cash In Page Validated");
			ExtentReporter.extentLoggerPass("STW_TC_06", "STW_TC_06, Navigated to Cash In Page Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInUIValidation_STW_TC_07() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Page UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		verifyElementPresentAndClick(MLWalletCashInBank.objCashIn,getTextVal(MLWalletCashInBank.objCashIn,"Icon"));
		if(verifyElementPresent(MLWalletCashInBank.objCashIn,getTextVal(MLWalletCashInBank.objCashIn,"Page"))){
			verifyElementPresent(MLWalletCashInBank.objCashInOption,getTextVal(MLWalletCashInBank.objCashInOption,"Header"));
			verifyElementPresent(MLWalletCashInBank.objMyBankAccount,getTextVal(MLWalletCashInBank.objMyBankAccount,"Option"));
			verifyElementPresent(MLWalletCashInBank.objBranchName,getTextVal(MLWalletCashInBank.objBranchName,"Option"));
			logger.info("STW_TC_07, Cash In Page UI validated");
			ExtentReporter.extentLoggerPass("STW_TC_07", "STW_TC_07, Cash In Page UI validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInPageBackArrowBtnValidation_STW_TC_08() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Page Back Button Validation");
		cashInUIValidation_STW_TC_07();
		verifyElementPresentAndClick(MLWalletCashInBank.objCashInBackArrowBtn,"Cash In Back Button");
		explicitWaitVisible(MLWalletLoginPage.objAvailableBalance, 10);
		if (verifyElementPresent(MLWalletLoginPage.objAvailableBalance, getTextVal(MLWalletLoginPage.objAvailableBalance, "Text"))) {
			logger.info("STW_TC_08, Cash In Page Back Button validated");
			ExtentReporter.extentLoggerPass("STW_TC_08", "STW_TC_08, Cash In Page Back Button validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInSelectBankPageUIValidation_STW_TC_09() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Select Bank Page UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		verifyElementPresentAndClick(MLWalletCashInBank.objCashIn,getTextVal(MLWalletCashInBank.objCashIn,"Icon"));
		verifyElementPresentAndClick(MLWalletCashInBank.objMyBankAccount,getTextVal(MLWalletCashInBank.objMyBankAccount,"Option"));
		if(verifyElementPresent(MLWalletCashInBank.objSelectABank,getTextVal(MLWalletCashInBank.objSelectABank,"Header"))){
			verifyElementPresent(MLWalletCashInBank.objSearchBank,"Search Bank Input Field");
			if (verifyElementDisplayed(MLWalletCashInBank.objBanks)) {
				List<WebElement> values = findElements(MLWalletCashInBank.objBanks);
				for (int i = 0; i < values.size(); i++) {
					String savedRecipientName = values.get(i).getText();
					logger.info("Bank : " + savedRecipientName + " is displayed");
					ExtentReporter.extentLogger(" ", "Bank : " + savedRecipientName + " is displayed");
				}
			}
			logger.info("STW_TC_09, Cash In Select Bank Page UI validated");
			ExtentReporter.extentLoggerPass("STW_TC_09", "STW_TC_09, Cash In Select Bank Page UI validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void cashInViaBankSearchInvalidBank_STW_TC_10() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Invalid Bank");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		verifyElementPresentAndClick(MLWalletCashInBank.objCashIn,getTextVal(MLWalletCashInBank.objCashIn,"Icon"));
		verifyElementPresentAndClick(MLWalletCashInBank.objMyBankAccount,getTextVal(MLWalletCashInBank.objMyBankAccount,"Option"));
		if(verifyElementPresent(MLWalletCashInBank.objSelectABank,getTextVal(MLWalletCashInBank.objSelectABank,"Header"))) {
			type(MLWalletCashInBank.objSearchBank,prop.getproperty("Invalid_BankName"), "Search Bank Input Field");
			if(verifyElementPresent(MLWalletCashInBank.objNoRecentTransactionTxt,getTextVal(MLWalletCashInBank.objNoRecentTransactionTxt,"Text"))){
				logger.info("STW_TC_10, Cash In Select Bank Page UI validated");
				ExtentReporter.extentLoggerPass("STW_TC_10", "STW_TC_10, Cash In Select Bank Page UI validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void cashInViaBankSelectBankPageBackBtnValidation_STW_TC_11() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Select Bank Page BackArrow Button Validation");
		cashInViaBankSearchInvalidBank_STW_TC_10();
		verifyElementPresentAndClick(MLWalletCashInBank.objSelectBankBackBtn,"Select A Bank Page Back Button");
		if(verifyElementPresent(MLWalletCashInBank.objCashIn,getTextVal(MLWalletCashInBank.objCashIn,"Page"))){
			logger.info("STW_TC_11, Select Bank Page Back Button validated");
			ExtentReporter.extentLoggerPass("STW_TC_11", "STW_TC_11, Select Bank Page Back Button validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankDragonPayPageUIValidation_STW_TC_12() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Dragon Pay Page UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		verifyElementPresent(MLWalletCashInBank.objCashIn, getTextVal(MLWalletCashInBank.objCashIn, "Icon"));
		click(MLWalletCashInBank.objCashIn, getTextVal(MLWalletCashInBank.objCashIn, "Icon"));
		click(MLWalletCashInBank.objMyBankAccount, getTextVal(MLWalletCashInBank.objMyBankAccount, "Button"));
		verifyElementPresent(MLWalletCashInBank.objSelectABank, getTextVal(MLWalletCashInBank.objSelectABank, "Page"));
		click(MLWalletCashInBank.objTestBankOnline, getTextVal(MLWalletCashInBank.objTestBankOnline, "Bank"));
		if(verifyElementPresent(MLWalletCashInBank.objDragonPay, getTextVal(MLWalletCashInBank.objDragonPay, "Page"))) {
			verifyElementPresent(MLWalletCashInBank.objBankCashIn, getTextVal(MLWalletCashInBank.objBankCashIn, "Text"));
			verifyElementPresent(MLWalletCashInBank.objAmountEditField, "Amount Text Field");
			verifyElementPresent(MLWalletCashInBank.objNextBtn, getTextVal(MLWalletCashInBank.objNextBtn, "Button"));
			logger.info("STW_TC_12, Cash In Via Bank Dragon Pay Page UI validated");
			ExtentReporter.extentLoggerPass("STW_TC_12", "STW_TC_12, Cash In Via Bank Dragon Pay Page UI validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankDragonPayBackBtnValidation_STW_TC_13() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Dragon Pay Back Button Validation");
		cashInViaBankDragonPayPageUIValidation_STW_TC_12();
		verifyElementPresentAndClick(MLWalletCashInBank.objDragonPayBackBtn,"Dragon Pay Back Button");
		if(verifyElementPresent(MLWalletCashInBank.objSelectABank, getTextVal(MLWalletCashInBank.objSelectABank, "Page"))){
			logger.info("STW_TC_13, Cash In Via Bank Dragon Pay Back Button validated");
			ExtentReporter.extentLoggerPass("STW_TC_13", "STW_TC_13, Cash In Via Bank Dragon Pay Back Button validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void cashInViaBankReviewTransactionPageUIValidation_STW_TC_14() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Review Transaction Page UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		selectBankAndInputAmount("100");
		dragonPayChargesMsgValidation();
		if(verifyElementPresent(MLWalletCashInBank.objReviewTransaction,getTextVal(MLWalletCashInBank.objReviewTransaction,"Page"))) {
			verifyElementPresent(MLWalletCashInBank.objReceiverName,getTextVal(MLWalletCashInBank.objReceiverName,"Receiver's Name"));
			verifyElementPresent(MLWalletCashInBank.objBankName,getTextVal(MLWalletCashInBank.objBankName,"Bank Name"));
			verifyElementPresent(MLWalletCashInBank.objPrincipalAmount,getTextVal(MLWalletCashInBank.objPrincipalAmount,"Principal Amount"));
			verifyElementPresent(MLWalletCashInBank.objServiceFee,getTextVal(MLWalletCashInBank.objServiceFee,"Service Fee"));
			verifyElementPresent(MLWalletCashInBank.objEmail,getTextVal(MLWalletCashInBank.objEmail,"Email"));
			Swipe("UP", 1);
			verifyElementPresent(MLWalletCashInBank.objBankTransferCutOffTime, getTextVal(MLWalletCashInBank.objBankTransferCutOffTime, "Message"));
			verifyElementPresent(MLWalletCashInBank.objNextBtn, getTextVal(MLWalletCashInBank.objNextBtn, "Button"));
			logger.info("STW_TC_14, Cash In Via Bank Review Transaction Page UI validated");
			ExtentReporter.extentLoggerPass("STW_TC_14", "STW_TC_14, Cash In Via Bank Review Transaction Page UI validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankReviewTransactionBackBtnValidation_STW_TC_15() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Review Transaction Back Button Validation");
		cashInViaBankReviewTransactionPageUIValidation_STW_TC_14();
		verifyElementPresentAndClick(MLWalletCashInBank.objReviewTransactionBackBtn,"Review Transaction Back Button");
		if(verifyElementPresent(MLWalletCashInBank.objDragonPay,getTextVal(MLWalletCashInBank.objDragonPay,"Page"))){
			logger.info("STW_TC_15, Cash In Via Bank Review Transaction Back Button validated");
			ExtentReporter.extentLoggerPass("STW_TC_15", "STW_TC_15, Cash In Via Bank Review Transaction Back Button validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankPendingTransaction_CIBA_TC_17() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Pending Transaction");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		selectBankAndInputAmount("100");
		dragonPayChargesMsgValidation();
		reviewTransactionValidation();
		enterOTP(prop.getproperty("Valid_OTP"));
		enableLocation_PopUp();
		bankUserLogin(prop.getproperty("Valid_LoginId"), prop.getproperty("Valid_Password"));
		click(MLWalletCashInBank.objWebContinueBtn,"Continue Button");
		click(MLWalletCashInBank.objPayBtn,getTextVal(MLWalletCashInBank.objPayBtn,"Button"));
		verifyElementPresent(MLWalletCashInBank.objBankReferenceNumber,getTextVal(MLWalletCashInBank.objBankReferenceNumber,"Reference Number"));
		verifyElementPresent(MLWalletCashInBank.objStatus,getTextVal(MLWalletCashInBank.objStatus,"Status"));
		verifyElementPresent(MLWalletCashInBank.objMessage,getTextVal(MLWalletCashInBank.objMessage,"Message"));
		verifyElementPresentAndClick(MLWalletCashInBank.objCompleteTransactionBtn,getTextVal(MLWalletCashInBank.objCompleteTransactionBtn,"Button"));
		Swipe("DOWN",2);
		if(verifyElementPresent(MLWalletCashInBank.objCashIn,getTextVal(MLWalletCashInBank.objCashIn,"Transaction"))){
			verifyElementPresent(MLWalletCashInBank.objPendingStatus,getTextVal(MLWalletCashInBank.objPendingStatus,"Status"));
			String sStatus = getText(MLWalletCashInBank.objPendingStatus);
			String sExpectedStatus = "Pending";
			assertionValidation(sStatus,sExpectedStatus);
			logger.info("CIBA_TC_17, An entry in recent transaction for current Cash In should be present with status pending validated");
			ExtentReporter.extentLoggerPass("CIBA_TC_17", "CIBA_TC_17, An entry in recent transaction for current Cash In should be present with status pending validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankWithExistingPendingTransaction_CIBA_TC_20() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank With Existing Pending Transaction");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		verifyElementPresent(MLWalletTransactionHistoryPage.objRecentTransaction, getText(MLWalletTransactionHistoryPage.objRecentTransaction));
		Swipe("UP", 2);
		click(MLWalletTransactionHistoryPage.objSeeMoreBtn, "See More Button");
		scrollToFirstHorizontalScrollableElement("Cash In");
		click(MLWalletTransactionHistoryPage.objCashInTab, "Cash In");
		Thread.sleep(3000);
		scrollToVertical("Pending");
		if(verifyElementPresent(MLWalletCashInBank.objPending,getTextVal(MLWalletCashInBank.objPending,"Status"))){
			click(MLWalletCashInBank.objTransactionHistoryBackBtn,"Transaction History Back Button");
			Swipe("DOWN",1);
			selectBankAndInputAmount("100");
			dragonPayChargesMsgValidation();
			reviewTransactionValidation();
			enterOTP(prop.getproperty("Valid_OTP"));
			enableLocation_PopUp();
			bankUserLogin(prop.getproperty("Valid_LoginId"), prop.getproperty("Valid_Password"));
			click(MLWalletCashInBank.objWebContinueBtn,"Continue Button");
			click(MLWalletCashInBank.objPayBtn,getTextVal(MLWalletCashInBank.objPayBtn,"Button"));
			verifyElementPresent(MLWalletCashInBank.objBankReferenceNumber,getTextVal(MLWalletCashInBank.objBankReferenceNumber,"Reference Number"));
			verifyElementPresent(MLWalletCashInBank.objStatus,getTextVal(MLWalletCashInBank.objStatus,"Status"));
			verifyElementPresent(MLWalletCashInBank.objMessage,getTextVal(MLWalletCashInBank.objMessage,"Message"));
			verifyElementPresentAndClick(MLWalletCashInBank.objCompleteTransactionBtn,getTextVal(MLWalletCashInBank.objCompleteTransactionBtn,"Button"));
			Swipe("DOWN",2);
			Swipe("UP",1);
			if(verifyElementPresent(MLWalletCashInBank.objCashIn,getTextVal(MLWalletCashInBank.objCashIn,"Transaction"))){
				verifyElementPresent(MLWalletCashInBank.objPending,getTextVal(MLWalletCashInBank.objPending,"Status"));
				logger.info("CIBA_TC_20, Cash In Via Bank With Existing Pending Transaction Validated");
				ExtentReporter.extentLoggerPass("CIBA_TC_20", "CIBA_TC_20, Cash In Via Bank With Existing Pending Transaction validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void cancelButtonValidationInDragonPayPopUp_CIBA_TC_21() throws Exception {
		ExtentReporter.HeaderChildNode("Cancel Button Validation In Dragon Pay PopUp");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		selectBankAndInputAmount("100");
		verifyElementPresent(MLWalletCashInBank.objDragonPayChargesMsg, getTextVal(MLWalletCashInBank.objDragonPayChargesMsg, "Message"));
		String sDragonPayChargesMsg = getText(MLWalletCashInBank.objDragonPayChargesMsg);
		String sExpectedDragonPayChargesMsg = "Dragon Pay charges a fee of 30 pesos for this transaction. Do you wish to continue with your transaction?";
		assertionValidation(sDragonPayChargesMsg, sExpectedDragonPayChargesMsg);
		click(MLWalletCashInBank.objCancelBtn, getTextVal(MLWalletCashInBank.objCancelBtn, "Button"));
		if(verifyElementPresent(MLWalletCashInBank.objDragonPay, getTextVal(MLWalletCashInBank.objDragonPay, "Page"))){
			logger.info("CIBA_TC_21, Cancel Button Validated In Dragon Pay PopUp");
			ExtentReporter.extentLoggerPass("CIBA_TC_21", "CIBA_TC_21, Cancel Button Validated In Dragon Pay PopUp");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankInvalidAmountFieldValidation_CIBA_TC_23() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Invalid Amount Field Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		selectBankAndInputAmount("");
		if (Utilities.verifyElementPresent(MLWalletCashInBank.objInvalidAmountMsg, getTextVal(MLWalletCashInBank.objInvalidAmountMsg, "Error Message"))) {
			String sInvalidAmountErrorMsg = getText(MLWalletCashInBank.objInvalidAmountMsg);
			String sExpectedErrorMsg = "Amount is required";
			Utilities.assertionValidation(sInvalidAmountErrorMsg, sExpectedErrorMsg);
			logger.info("CIBA_TC_23, Amount is required - Error Message is validated");
			ExtentReporter.extentLoggerPass("CIBA_TC_23", "CIBA_TC_23, Amount is required - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankBuyerTierLevel_CIBA_TC_24() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Buyer Tier Level");
		mlWalletLogin(prop.getproperty("Buyer_Tier"));
		selectBankAndInputAmount("100");
		dragonPayChargesMsgValidation();
		reviewTransactionValidation();
		if (Utilities.verifyElementPresent(MLWalletCashInBank.objMaxLimitTxt, Utilities.getTextVal(MLWalletCashInBank.objMaxLimitTxt, "Text Message"))) {
			String sErrorMessage = Utilities.getText(MLWalletCashInBank.objMaxLimitTxt);
			String ExpectedTxt = "Bank Cash-in is not allowed for customers at this verification level. Please upgrade your account to use this service.";
			Utilities.assertionValidation(sErrorMessage, ExpectedTxt);
			verifyElementPresent(MLWalletCashInBank.objUpgradeNowBtn,getTextVal(MLWalletCashInBank.objUpgradeNowBtn,"Button"));
			logger.info("CIBA_TC_24, Branch Cash-In is not allowed for customers at this verification level. Error Message is Validated");
			ExtentReporter.extentLoggerPass("CIBA_TC_24", "CIBA_TC_24, Branch Cash-In is not allowed for customers at this verification level. Error Message is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankSemiVerifiedUserMaxLimit_CIBA_TC_27() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Maximum Limit");
		mlWalletLogin(prop.getproperty("Semi_Verified"));
		selectBankAndInputAmount("60000");
		dragonPayChargesMsgValidation();
		reviewTransactionValidation();
		waitTime(5000);
		if(verifyElementPresent(MLWalletCashInBank.objBankMaxLimitTxt,getTextVal(MLWalletCashInBank.objBankMaxLimitTxt,"Error Message"))) {
			String sErrorMsg = getText(MLWalletCashInBank.objBankMaxLimitTxt);
			String sExpectedErrorMsg = "The maximum Bank Cash-in per transaction set for your verification level is P50,000.00. Please try again.";
			assertionValidation(sErrorMsg, sExpectedErrorMsg);
			logger.info("CIBA_TC_27, To validate Maximum Limit of transaction");
			ExtentReporter.extentLoggerPass("CIBA_TC_27", "CIBA_TC_27, To validate Maximum Limit of transaction");
		}
	}

	public void cashInViaBankFullyVerifiedUserMaxLimit_CIBA_TC_28() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Maximum Limit");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		selectBankAndInputAmount("60000");
		dragonPayChargesMsgValidation();
		reviewTransactionValidation();
		waitTime(5000);
		if(verifyElementPresent(MLWalletCashInBank.objBankMaxLimitTxt,getTextVal(MLWalletCashInBank.objBankMaxLimitTxt,"Error Message"))) {
			String sErrorMsg = getText(MLWalletCashInBank.objBankMaxLimitTxt);
			String sExpectedErrorMsg = "The maximum Bank Cash-in per transaction set for your verification level is P50,000.00. Please try again.";
			assertionValidation(sErrorMsg, sExpectedErrorMsg);
			logger.info("CIBA_TC_28, To validate Maximum Limit of transaction");
			ExtentReporter.extentLoggerPass("CIBA_TC_28", "CIBA_TC_28, To validate Maximum Limit of transaction");
		}
	}

	public void cashInViaBankTransactionDetailsPageUIValidation_CIBA_TC_29() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Transaction Details Page UI Validation");
		cashInViaBank_CIBA_TC_01();
		verifyElementPresentAndClick(MLWalletCashInBank.objCompleteTransactionBtn,getTextVal(MLWalletCashInBank.objCompleteTransactionBtn,"Button"));
		Swipe("DOWN",2);
		Swipe("UP",2);
		verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
		click(MLWalletCashInBank.objCashInTransaction, getTextVal(MLWalletCashInBank.objCashInTransaction, "Transaction"));
		if (verifyElementPresent(MLWalletCashOutPage.objTransactionDetails, getTextVal(MLWalletCashOutPage.objTransactionDetails, "Page"))) {
			verifyElementPresent(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, getTextVal(MLWalletTransactionHistoryPage.objReferenceNumberInTransactionDetails, "Reference Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objDate,getTextVal(MLWalletTransactionHistoryPage.objDate,"Date"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objReceiverMobileNo, getTextVal(MLWalletTransactionHistoryPage.objReceiverMobileNo, "Receiver's Mobile Number"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objPaymentMethod, getTextVal(MLWalletTransactionHistoryPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objTransactionType, getTextVal(MLWalletTransactionHistoryPage.objTransactionType, "Payment Method"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objBank,getTextVal(MLWalletTransactionHistoryPage.objBank,"Bank"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objAmount, getTextVal(MLWalletTransactionHistoryPage.objAmount, "Amount"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objServiceFee, getTextVal(MLWalletTransactionHistoryPage.objServiceFee, "Service Fee"));
			verifyElementPresent(MLWalletTransactionHistoryPage.objTotalCashIn, getTextVal(MLWalletTransactionHistoryPage.objTotalCashIn, "Total Cash In"));
			logger.info("CIBA_TC_29, Cash In Via Bank Transaction Details Page UI Validated");
			ExtentReporter.extentLoggerPass("CIBA_TC_29", "CIBA_TC_29, Cash In Via Bank Transaction Details Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankDragonPayChagresPopUpValidation_CIBA_TC_32() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Dragon Pay charges popup Validation");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		selectBankAndInputAmount("100");
		if (verifyElementPresent(MLWalletCashInBank.objDragonPayChargesMsg, getTextVal(MLWalletCashInBank.objDragonPayChargesMsg, "Message"))) {
			String sDragonPayChargesMsg = getText(MLWalletCashInBank.objDragonPayChargesMsg);
			String sExpectedDragonPayChargesMsg = "Dragon Pay charges a fee of 30 pesos for this transaction. Do you wish to continue with your transaction?";
			assertionValidation(sDragonPayChargesMsg, sExpectedDragonPayChargesMsg);
			logger.info("CIBA_TC_32, Cash In Via Bank Dragon Pay charges popup Validated");
			ExtentReporter.extentLoggerPass("CIBA_TC_32", "CIBA_TC_32, Cash In Via Bank Dragon Pay charges popup Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}











//=============================================Pay Bills===============================================================//
//=============================Generalized Methods=====================================================//

	public void getBillers(By sWebElement){
		List<WebElement> list = Utilities.findElements(sWebElement);

		for(int i=0 ;i<list.size(); i++){
			String billers= list.get(i).getText();
			logger.info(billers+ " Biller is displayed");
		}
	}

	public void payBillsNavigation() throws Exception {
		verifyElementPresent(MLWalletPayBillsPage.objPayBills,getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		click(MLWalletPayBillsPage.objPayBills,getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
	}


	public void searchBiller() throws Exception {
		type(MLWalletPayBillsPage.objSearchBiller,prop.getproperty("Biller_Name"),"Search Biller");
		verifyElementPresent(MLWalletPayBillsPage.objMisBillsPayBiller,getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller,"Biller"));
		click(MLWalletPayBillsPage.objMisBillsPayBiller,getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller,"Biller"));
	}

	public void billerDetails(String sFirstName,String sLastName,String sMiddleName, String sAmount) throws Exception {
		if(verifyElementPresent(MLWalletPayBillsPage.objBillsPayInformation,getTextVal(MLWalletPayBillsPage.objBillsPayInformation,"Page"))){
			type(MLWalletPayBillsPage.objAccountNumberField, prop.getproperty("AccountNumber"),"Account Number Text Field");
			type(MLWalletPayBillsPage.objFirstNameField,sFirstName ,"First Name Text Field");
			type(MLWalletPayBillsPage.objMiddleNameField,sMiddleName ,"Middle Name Text Field");
			type(MLWalletPayBillsPage.objLastnameField,sLastName ,"Last Name Text Field");
			Swipe("UP",1);
			type(MLWalletPayBillsPage.objAmountField, sAmount,"Amount to Send Text Field");
			click(MLWalletPayBillsPage.objConfirmBtn,getTextVal(MLWalletPayBillsPage.objConfirmBtn,"Button"));
		}
	}

	public void addSelectedBiller() throws Exception {
		if (verifyElementPresent(MLWalletPayBillsPage.objAddSeectedBiller, "Edit Biller")) {
			click(MLWalletPayBillsPage.objAddSeectedBiller, "Edit Biller");
			click(MLWalletPayBillsPage.objBillerListSearchBiller,"Biller List Search Biller");
			type(MLWalletPayBillsPage.objBillerListSearchBiller, prop.getproperty("Biller_Name"), "Biller List Search Biller");
			explicitWaitVisible(MLWalletPayBillsPage.objMisBillsPayBiller,5);
			click(MLWalletPayBillsPage.objMisBillsPayBiller, getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller, "Biller"));
			click(MLWalletPayBillsPage.objMisBillsPayBiller, getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller, "Biller"));
		}
	}

	public void addBiller() throws Exception {
		if(verifyElementPresent(MLWalletPayBillsPage.objSavedBiller, getTextVal(MLWalletPayBillsPage.objSavedBiller, "Button"))) {
			click(MLWalletPayBillsPage.objSavedBiller, getTextVal(MLWalletPayBillsPage.objSavedBiller, "Button"));
			explicitWaitVisible(MLWalletPayBillsPage.objAddBiller,5);
			click(MLWalletPayBillsPage.objAddBiller, getTextVal(MLWalletPayBillsPage.objAddBiller, "Button"));
			addSelectedBiller();
			if (verifyElementPresent(MLWalletPayBillsPage.objAddBillers, getTextVal(MLWalletPayBillsPage.objAddBillers, "Page"))) {
				type(MLWalletPayBillsPage.objAddAccountNumber, prop.getproperty("AccountNumber"), "Account Number in Add Biller");
				type(MLWalletPayBillsPage.objAddFirstName, prop.getproperty("First_Name"), "First Name in Add Biller");
				type(MLWalletPayBillsPage.objAddMiddleName, prop.getproperty("Middle_Name"), "Middle Name in Add Biller");
				type(MLWalletPayBillsPage.objAddLastName, prop.getproperty("Last_Name"), "Last Name in Add Biller");
				type(MLWalletPayBillsPage.objAddNickName, prop.getproperty("Nick_Name"), "Nick Name in Add Biller");
				click(MLWalletPayBillsPage.objProceedBtn, getTextVal(MLWalletPayBillsPage.objProceedBtn, "button"));
			}
		}
	}

	public void selectAddedBiler() throws Exception {
		verifyElementPresentAndClick(MLWalletPayBillsPage.objSavedBiller,getTextVal(MLWalletPayBillsPage.objSavedBiller,"Button"));
		if(verifyElementPresent(MLWalletPayBillsPage.objSavedBillers,getTextVal(MLWalletPayBillsPage.objSavedBillers,"Page"))) {
			type(MLWalletPayBillsPage.objSearchBillerInSavedBillers, prop.getproperty("Last_Name"), "Search Recipient Text Field");
			verifyElementPresent(MLWalletPayBillsPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
			click(MLWalletPayBillsPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
		}
	}

//================================================================================================================//


	public void payBillsPageValidation_PB_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Pay Bills Page Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		payBillsNavigation();
		if(verifyElementPresent(MLWalletPayBillsPage.objSelectBiller,getTextVal(MLWalletPayBillsPage.objSelectBiller,"Page"))){
			verifyElementPresent(MLWalletPayBillsPage.objRecentTransactions,getTextVal(MLWalletPayBillsPage.objRecentTransactions,"Button"));
			verifyElementExist(MLWalletPayBillsPage.objBillers,getTextVal(MLWalletPayBillsPage.objBillers,"Text"));
			logger.info("PB_TC_01, Pay Bills Page validated");
			ExtentReporter.extentLoggerPass("PB_TC_01", "PB_TC_01, Pay Bills Page validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void billerCategories_PB_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Biller Categories");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		payBillsNavigation();
		click(MLWalletPayBillsPage.objCategories,getTextVal(MLWalletPayBillsPage.objCategories,"Button"));
		if(verifyElementPresent(MLWalletPayBillsPage.objBankingAndFinance,getTextVal(MLWalletPayBillsPage.objBankingAndFinance,"Button"))){
			click(MLWalletPayBillsPage.objBankingAndFinance,getTextVal(MLWalletPayBillsPage.objBankingAndFinance,"Biller Category"));
		}
		if(verifyElementPresent(MLWalletPayBillsPage.objCharityAndReligious,getTextVal(MLWalletPayBillsPage.objCharityAndReligious,"Biller Category"))){
			click(MLWalletPayBillsPage.objCharityAndReligious,getTextVal(MLWalletPayBillsPage.objCharityAndReligious,"Biller Category"));
			getBillers(MLWalletPayBillsPage.objCharityAndReligiousBillers);
			click(MLWalletPayBillsPage.objCharityAndReligious,getTextVal(MLWalletPayBillsPage.objCharityAndReligious,"Biller Category"));
		}
		Swipe("UP",1);
		if(verifyElementPresent(MLWalletPayBillsPage.objUtilities,getTextVal(MLWalletPayBillsPage.objUtilities,"Biller Category"))) {
			click(MLWalletPayBillsPage.objUtilities, getTextVal(MLWalletPayBillsPage.objUtilities, "Biller Category"));
			Swipe("UP", 1);
			getBillers(MLWalletPayBillsPage.objUtilitiesBillers);
			click(MLWalletPayBillsPage.objUtilities, getTextVal(MLWalletPayBillsPage.objUtilities, "Biller Category"));
		}
		if(verifyElementPresent(MLWalletPayBillsPage.objOthers,getTextVal(MLWalletPayBillsPage.objOthers,"Biller Category"))){
			click(MLWalletPayBillsPage.objOthers,getTextVal(MLWalletPayBillsPage.objOthers,"Biller Category"));
			Swipe("UP",1);
			getBillers(MLWalletPayBillsPage.objOthersBillers);
			click(MLWalletPayBillsPage.objOthers,getTextVal(MLWalletPayBillsPage.objOthers,"Biller Category"));
		}
		logger.info("PB_TC_02, Biller Categories validated");
		ExtentReporter.extentLoggerPass("PB_TC_02", "PB_TC_02, Biller Categories validated");
		System.out.println("-----------------------------------------------------------");
	}

	public void billersInAlphabeticalOrder_PB_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Billers In Alphabetical Order");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		payBillsNavigation();
		click(MLWalletPayBillsPage.objAlphabetical,getTextVal(MLWalletPayBillsPage.objAlphabetical,"Button"));
		swipeDownUntilElementVisible("SAGIP KAPAMILYA");
		swipeDownUntilElementVisible("ZYBITECH");
		logger.info("PB_TC_03, Billers swiped until Z Alphabet, and all the Billers are displayed in alphabetical order");
		ExtentReporter.extentLoggerPass("PB_TC_03", "PB_TC_03, Billers swiped until Z Alphabet, and all the Billers are displayed in alphabetical order");
		System.out.println("-----------------------------------------------------------");
	}

	public void selectBiller_PB_TC_04() throws Exception {
		ExtentReporter.HeaderChildNode("Select Biller");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		payBillsNavigation();
		click(MLWalletPayBillsPage.objAlphabetical,getTextVal(MLWalletPayBillsPage.objAlphabetical,"Button"));
		swipeDownUntilElementVisible("AIR ASIA BILLSPAYMENT");
		if(verifyElementPresent(MLWalletPayBillsPage.objAirAsia,getTextVal(MLWalletPayBillsPage.objAirAsia,"Biller"))){
			String sAirAsiaBillsPayment = getText(MLWalletPayBillsPage.objAirAsia);
			click(MLWalletPayBillsPage.objAirAsia,getTextVal(MLWalletPayBillsPage.objAirAsia,"Biller"));
			verifyElementPresent(MLWalletPayBillsPage.objBillsPayInformation,getTextVal(MLWalletPayBillsPage.objBillsPayInformation,"Page"));
			if(verifyElementPresent(MLWalletPayBillsPage.objBillerNameInBillsPayInformation,getTextVal(MLWalletPayBillsPage.objBillerNameInBillsPayInformation,"Biller Name"))){
				String sBillerInBillsPayInformation = getText(MLWalletPayBillsPage.objBillerNameInBillsPayInformation);
				assertionValidation(sAirAsiaBillsPayment,sBillerInBillsPayInformation);
				logger.info("PB_TC_04, Bills Pay Information page is displayed and Biller name is matched");
				ExtentReporter.extentLoggerPass("PB_TC_04", "PB_TC_04, Bills Pay Information page is displayed and Biller name is matched");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void searchBiller_PB_TC_05() throws Exception {
		ExtentReporter.HeaderChildNode("Search Biller");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		payBillsNavigation();
		Thread.sleep(5000);
		type(MLWalletPayBillsPage.objSearchBiller,prop.getproperty("Biller_Name"),"Search Biller");
		if(verifyElementPresent(MLWalletPayBillsPage.objMisBillsPayBiller,getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller,"Biller"))){
			String sMisBillsPayBiller = getText(MLWalletPayBillsPage.objMisBillsPayBiller);
			click(MLWalletPayBillsPage.objMisBillsPayBiller,getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller,"Biller"));
			verifyElementPresent(MLWalletPayBillsPage.objBillsPayInformation,getTextVal(MLWalletPayBillsPage.objBillsPayInformation,"Page"));
			if(verifyElementPresent(MLWalletPayBillsPage.objBillerNameInBillsPayInformation,getTextVal(MLWalletPayBillsPage.objBillerNameInBillsPayInformation,"Biller Name"))){
				String sBillerInBillsPayInformation = getText(MLWalletPayBillsPage.objBillerNameInBillsPayInformation);
				assertionValidation(sMisBillsPayBiller,sBillerInBillsPayInformation);
				logger.info("PB_TC_05, Bills Pay Information page is displayed and Biller name is MIS BILLSPAY123456");
				ExtentReporter.extentLoggerPass("PB_TC_05", "PB_TC_05, Bills Pay Information page is displayed and Biller name is MIS BILLSPAY123456");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void billingInformationInput_PB_TC_06() throws Exception {
		ExtentReporter.HeaderChildNode("Biller Information Input");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		payBillsNavigation();
		searchBiller();
		billerDetails(prop.getproperty("First_Name"),prop.getproperty("Middle_Name"),prop.getproperty("Last_Name"),"10");
		if(verifyElementPresent(MLWalletPayBillsPage.objConfirmDetails,getTextVal(MLWalletPayBillsPage.objConfirmDetails,"Page"))){
			verifyElementPresent(MLWalletPayBillsPage.objBillerName,getTextVal(MLWalletPayBillsPage.objBillerName,"Biller Name"));
			verifyElementPresent(MLWalletPayBillsPage.objAccountName,getTextVal(MLWalletPayBillsPage.objAccountName,"Account holder Name"));
			verifyElementPresent(MLWalletPayBillsPage.objAccountNumber,getTextVal(MLWalletPayBillsPage.objAccountNumber,"Account Number"));
			verifyElementPresent(MLWalletPayBillsPage.objPaymentMethod,getTextVal(MLWalletPayBillsPage.objPaymentMethod,"Payment Method"));
			logger.info("PB_TC_06, Confirm Details page displayed with transaction details is validated");
			ExtentReporter.extentLoggerPass("PB_TC_06", "PB_TC_06, Confirm Details page displayed with transaction details is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void payBillsWithValidInputs_PB_TC_07() throws Exception {
		ExtentReporter.HeaderChildNode("Pay Bills With Valid Inputs");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		payBillsNavigation();
		searchBiller();
		billerDetails(prop.getproperty("First_Name"), prop.getproperty("Middle_Name"), prop.getproperty("Last_Name"), "10");
		if(verifyElementPresent(MLWalletPayBillsPage.objConfirmDetails, getTextVal(MLWalletPayBillsPage.objConfirmDetails, "Page"))) {
			Swipe("UP",1);
			click(MLWalletPayBillsPage.objPayBtn,getTextVal(MLWalletPayBillsPage.objPayBtn,"Button"));
		}
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if(verifyElementPresent(MLWalletPayBillsPage.objSuccessPillPaymentMsg,getTextVal(MLWalletPayBillsPage.objSuccessPillPaymentMsg,"Message"))) {
			verifyElementPresent(MLWalletPayBillsPage.objAmountPaid, getTextVal(MLWalletPayBillsPage.objAmountPaid, "Amount"));
			verifyElementPresent(MLWalletPayBillsPage.objPaidDate, getTextVal(MLWalletPayBillsPage.objPaidDate, "Date"));
			verifyElementPresent(MLWalletPayBillsPage.objTransactionDetails, getTextVal(MLWalletPayBillsPage.objTransactionDetails, "Header"));
			verifyElementPresent(MLWalletPayBillsPage.objTransactionNumber, getTextVal(MLWalletPayBillsPage.objTransactionNumber, "Transaction Number"));
			String sTransactionNumber = getText(MLWalletPayBillsPage.objTransactionNumber);
			verifyElementPresent(MLWalletPayBillsPage.objBillerName, getTextVal(MLWalletPayBillsPage.objBillerName, "Biller Name"));
			verifyElementPresent(MLWalletPayBillsPage.objAccountName, getTextVal(MLWalletPayBillsPage.objAccountName, "Account Name"));
			verifyElementPresent(MLWalletPayBillsPage.objPaymentMethod, getTextVal(MLWalletPayBillsPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(MLWalletPayBillsPage.objAmountToPay, getTextVal(MLWalletPayBillsPage.objAmountToPay, "Amount"));
			verifyElementPresent(MLWalletPayBillsPage.objServiceFee, getTextVal(MLWalletPayBillsPage.objServiceFee, "Service Fee"));
			verifyElementPresent(MLWalletPayBillsPage.objTotalAmount, getTextVal(MLWalletPayBillsPage.objTotalAmount, "Total Amount"));
			verifyElementPresentAndClick(MLWalletPayBillsPage.objBackToHomeBtn, getTextVal(MLWalletPayBillsPage.objBackToHomeBtn, "Button"));
			waitTime(3000);
			Swipe("DOWN", 2);
			Swipe("UP", 1);
			verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			click(MLWalletHomePage.objPayBills, getTextVal(MLWalletHomePage.objPayBills, "Text"));
			if (verifyElementPresent(MLWalletCashOutPage.objTransactionDetails, getTextVal(MLWalletCashOutPage.objTransactionDetails, "Page"))) {
				String sReferenceNumberInCashOut = getText(MLWalletCashOutPage.objReferenceNumberInCashOut);
				System.out.println(sReferenceNumberInCashOut);
				assertionValidation(sReferenceNumberInCashOut, sTransactionNumber);
				logger.info("PB_TC_07, Bills Payment Successful and validated with Recent Transaction");
				ExtentReporter.extentLoggerPass("PB_TC_07", "PB_TC_07, Bills Payment Successful and validated with Recent Transaction");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void payBillsInRecentTransactions_PB_TC_08() throws Exception {
		ExtentReporter.HeaderChildNode("Pay Bills In Recent Transactions");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		Swipe("UP", 1);
		verifyElementPresent(MLWalletHomePage.objRecentTransactions, getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
		click(MLWalletHomePage.objPayBills, getTextVal(MLWalletHomePage.objPayBills, "Text"));
		if(verifyElementPresent(MLWalletPayBillsPage.objTransactionDetails,getTextVal(MLWalletPayBillsPage.objTransactionDetails,"Page"))){
			verifyElementPresent(MLWalletPayBillsPage.objReferenceNumber,getTextVal(MLWalletPayBillsPage.objReferenceNumber,"Reference Number"));
			verifyElementPresent(MLWalletPayBillsPage.objReceiverName,getTextVal(MLWalletPayBillsPage.objReceiverName,"Receiver Name"));
			verifyElementPresent(MLWalletPayBillsPage.objBillerName, getTextVal(MLWalletPayBillsPage.objBillerName, "Biller Name"));
			verifyElementPresent(MLWalletPayBillsPage.objReceiverMobNo, getTextVal(MLWalletPayBillsPage.objReceiverMobNo, "Receiver Mobile Number"));
			verifyElementPresent(MLWalletPayBillsPage.objPaymentMethod, getTextVal(MLWalletPayBillsPage.objPaymentMethod, "Payment Method"));
			verifyElementPresent(MLWalletPayBillsPage.objAmountToSend, getTextVal(MLWalletPayBillsPage.objAmountToSend, "Amount"));
			verifyElementPresent(MLWalletPayBillsPage.objServiceFee, getTextVal(MLWalletPayBillsPage.objServiceFee, "Service Fee"));
			verifyElementPresent(MLWalletPayBillsPage.objTotalAmount, getTextVal(MLWalletPayBillsPage.objTotalAmount, "Total Amount"));
			logger.info("PB_TC_08, Pay Bills In Recent Transactions Validated");
			ExtentReporter.extentLoggerPass("PB_TC_08", "PB_TC_08, Pay Bills In Recent Transactions Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void payBillsInsufficientBalance_PB_TC_09() throws Exception {
		ExtentReporter.HeaderChildNode("Pay Bills Insufficient Balance");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		payBillsNavigation();
		searchBiller();
		billerDetails(prop.getproperty("First_Name"), prop.getproperty("Middle_Name"), prop.getproperty("Last_Name"), "39000");
		if(verifyElementPresent(MLWalletPayBillsPage.objConfirmDetails, getTextVal(MLWalletPayBillsPage.objConfirmDetails, "Page"))) {
			Swipe("UP",1);
			click(MLWalletPayBillsPage.objPayBtn,getTextVal(MLWalletPayBillsPage.objPayBtn,"Button"));
			explicitWaitVisible(SendTransferPage.objInsufficientAmountMsg,5);
			if (verifyElementPresent(SendTransferPage.objInsufficientAmountMsg, getTextVal(SendTransferPage.objInsufficientAmountMsg, "Error Message"))) {
				String sInsufficientBalanceErrorMsg = getText(SendTransferPage.objInsufficientAmountMsg);
				String sExpectedErrorMsg = "There is insufficient balance to proceed with this transaction. Please try again.";
				assertionValidation(sInsufficientBalanceErrorMsg, sExpectedErrorMsg);
				logger.info("PB_TC_09, Insufficient Balance - Error Message is validated");
				ExtentReporter.extentLoggerPass("PB_TC_09", "PB_TC_09, Insufficient Balance - Error Message is validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void billingInformationInvalidInput_PB_TC_10() throws Exception {
		ExtentReporter.HeaderChildNode("Biller Information Invalid Input");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		payBillsNavigation();
		searchBiller();
		click(MLWalletPayBillsPage.objConfirmBtn,getTextVal(MLWalletPayBillsPage.objConfirmBtn,"Button"));
		if(verifyElementPresent(MLWalletPayBillsPage.objAccountNumberRequiredMsg,getTextVal(MLWalletPayBillsPage.objAccountNumberRequiredMsg,"Error Message"))){
			String sAccountNumberRequiredErrorMsg = getText(MLWalletPayBillsPage.objAccountNumberRequiredMsg);
			String sExceptedAccountNumberRequiredErrorMsg = "Account Number is required";
			assertionValidation(sAccountNumberRequiredErrorMsg,sExceptedAccountNumberRequiredErrorMsg);
		}
		if(verifyElementPresent(MLWalletPayBillsPage.objFirstNameRequiredMsg,getTextVal(MLWalletPayBillsPage.objFirstNameRequiredMsg,"Error Message"))){
			String sFirstNameRequiredErrorMsg = getText(MLWalletPayBillsPage.objFirstNameRequiredMsg);
			String sExceptedFirstNameRequiredErrorMsg = "First name is required";
			assertionValidation(sFirstNameRequiredErrorMsg,sExceptedFirstNameRequiredErrorMsg);
		}

		if(verifyElementPresent(MLWalletPayBillsPage.objLastNameRequiredMsg,getTextVal(MLWalletPayBillsPage.objLastNameRequiredMsg,"Error Message"))){
			String sLastNameRequiredErrorMsg = getText(MLWalletPayBillsPage.objLastNameRequiredMsg);
			String sExceptedLastNameRequiredErrorMsg = "Last name is required";
			assertionValidation(sLastNameRequiredErrorMsg,sExceptedLastNameRequiredErrorMsg);
		}

		billerDetails(prop.getproperty("Invalid_First_Name"),prop.getproperty("Invalid_Middle_Name"),prop.getproperty("Invalid_Last_Name"),"0.99");
		if(verifyElementPresent(MLWalletPayBillsPage.objInvalidFirstNameMsg,getTextVal(MLWalletPayBillsPage.objInvalidFirstNameMsg,"Error Message"))){
			String sInvalidFirstNameErrorMsg = getText(MLWalletPayBillsPage.objInvalidFirstNameMsg);
			String sExceptedFirstNameErrorMsg = "First name must only contain letters and spaces";
			assertionValidation(sInvalidFirstNameErrorMsg,sExceptedFirstNameErrorMsg);
		}
		if(verifyElementPresent(MLWalletPayBillsPage.objInvalidSecondNameMsg,getTextVal(MLWalletPayBillsPage.objInvalidSecondNameMsg,"Error Message"))){
			String sInvalidSecondNameErrorMsg = getText(MLWalletPayBillsPage.objInvalidSecondNameMsg);
			String sExceptedSecondNameErrorMsg = "Middle name must only contain letters and spaces";
			assertionValidation(sInvalidSecondNameErrorMsg,sExceptedSecondNameErrorMsg);
		}
		if(verifyElementPresent(MLWalletPayBillsPage.objInvalidLastName,getTextVal(MLWalletPayBillsPage.objInvalidLastName,"Error Message"))){
			String sInvalidThirdNameErrorMsg = getText(MLWalletPayBillsPage.objInvalidLastName);
			String sExceptedThirdNameErrorMsg = "Last name must only contain letters and spaces";
			assertionValidation(sInvalidThirdNameErrorMsg,sExceptedThirdNameErrorMsg);
		}
		billerDetails(prop.getproperty("First_Name"),prop.getproperty("Middle_Name"),prop.getproperty("Last_Name"),"0.99");
		Swipe("UP",1);

		if(verifyElementPresent(MLWalletPayBillsPage.objInvalidAmount,getTextVal(MLWalletPayBillsPage.objInvalidAmount,"Error Message"))){
			String sInvalidAmountErrorMsg = getText(MLWalletPayBillsPage.objInvalidAmount);
			String sExceptedAmountErrorMsg = "The amount should not be less than 1";
			assertionValidation(sInvalidAmountErrorMsg,sExceptedAmountErrorMsg);
		}

		logger.info("PB_TC_10, Invalid biller Information Error messages validated");
		ExtentReporter.extentLoggerPass("PB_TC_10", "PB_TC_10, Invalid biller Information Error messages validated");
		System.out.println("-----------------------------------------------------------");
	}

	public void addBillerToPayBills_PB_TC_12() throws Exception {
		ExtentReporter.HeaderChildNode("Add Biller To Pay Bills");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		payBillsNavigation();
		addBiller();
		click(MLWalletPayBillsPage.objOKBtn,getTextVal(MLWalletPayBillsPage.objOKBtn,"Button"));
		type(MLWalletPayBillsPage.objSearchSavedBiller, prop.getproperty("Last_Name"), "Search Biller Text Field");
		if (verifyElementPresent(MLWalletPayBillsPage.objSelectBillerInnSavedBiller(prop.getproperty("Last_Name")), getTextVal(MLWalletPayBillsPage.objSelectBillerInnSavedBiller(prop.getproperty("Last_Name")), "Recipient"))) {
			logger.info("PB_TC_12, The Added Biller is displayed in Saved Biller Page");
			ExtentReporter.extentLoggerPass("PB_TC_12", "PB_TC_12, The Added Biller is displayed in Saved Biller Page");
			System.out.println("-----------------------------------------------------------");
		}
	}
//
//	public void addBillerInvalidInputs_PB_TC_13() throws Exception {
//		ExtentReporter.HeaderChildNode("Add Biller Invalid Inputs");
//		mlWalletLogin(prop.getproperty("Branch_Verified"));
//		Utilities.verifyElementPresent(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
//		Utilities.click(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
//		Utilities.click(MLWalletPayBillsPage.objSavedBiller, Utilities.getTextVal(MLWalletPayBillsPage.objSavedBiller, "Button"));
//		Utilities.explicitWaitVisible(MLWalletPayBillsPage.objAddBiller,5);
//		Utilities.click(MLWalletPayBillsPage.objAddBiller, Utilities.getTextVal(MLWalletPayBillsPage.objAddBiller, "Button"));
//		addSelectedBiller();
//
//		type(MLWalletPayBillsPage.objAddAccountNumber,"ABC","Account Number Input Field");
//		click(MLWalletPayBillsPage.objProceedBtn,getTextVal(MLWalletPayBillsPage.objProceedBtn,"Button"));
//		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objAddAccountNumber,getTextVal(MLWalletPayBillsPage.objAddAccountNumber,"Error Message"))){
//			String sAccountNumberRequiredErrorMsg = Utilities.getText(MLWalletPayBillsPage.objAccountNumberRequiredMsg);
//			String sExceptedAccountNumberRequiredErrorMsg = "Account Number is required";
//			Utilities.assertionValidation(sAccountNumberRequiredErrorMsg,sExceptedAccountNumberRequiredErrorMsg);
//		}
//
//		click(MLWalletPayBillsPage.objProceedBtn,getTextVal(MLWalletPayBillsPage.objProceedBtn,"Button"));
//		type(MLWalletPayBillsPage.objAddAccountNumber,prop.getproperty("AccountNumber"),"Account Number Input Field");
//		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objFirstNameRequiredMsg,Utilities.getTextVal(MLWalletPayBillsPage.objFirstNameRequiredMsg,"Error Message"))){
//			String sFirstNameRequiredErrorMsg = Utilities.getText(MLWalletPayBillsPage.objFirstNameRequiredMsg);
//			String sExceptedFirstNameRequiredErrorMsg = "First name is required";
//			Utilities.assertionValidation(sFirstNameRequiredErrorMsg,sExceptedFirstNameRequiredErrorMsg);
//		}
//
//		click(MLWalletPayBillsPage.objProceedBtn,getTextVal(MLWalletPayBillsPage.objProceedBtn,"Button"));
//		type(MLWalletPayBillsPage.objAddFirstName,prop.getproperty("Invalid_First_Name"),"First Name Input Field");
//		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objLastNameRequiredMsg,Utilities.getTextVal(MLWalletPayBillsPage.objLastNameRequiredMsg,"Error Message"))){
//			String sLastNameRequiredErrorMsg = Utilities.getText(MLWalletPayBillsPage.objLastNameRequiredMsg);
//			String sExceptedLastNameRequiredErrorMsg = "Last name is required";
//			Utilities.assertionValidation(sLastNameRequiredErrorMsg,sExceptedLastNameRequiredErrorMsg);
//		}
//
//		click(MLWalletPayBillsPage.objProceedBtn,getTextVal(MLWalletPayBillsPage.objProceedBtn,"Button"));
//		type(MLWalletPayBillsPage.objAddLastName,prop.getproperty("Invalid_Last_Name"),"Last Name Input Field");
////		billerDetails(prop.getproperty("Invalid_First_Name"),prop.getproperty("Invalid_Middle_Name"),prop.getproperty("Invalid_Last_Name"),"0.99");
//
//
//		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objInvalidFirstNameMsg,Utilities.getTextVal(MLWalletPayBillsPage.objInvalidFirstNameMsg,"Error Message"))){
//			String sInvalidFirstNameErrorMsg = Utilities.getText(MLWalletPayBillsPage.objInvalidFirstNameMsg);
//			String sExceptedFirstNameErrorMsg = "First name must only contain letters and spaces";
//			Utilities.assertionValidation(sInvalidFirstNameErrorMsg,sExceptedFirstNameErrorMsg);
//		}
//		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objInvalidSecondNameMsg,Utilities.getTextVal(MLWalletPayBillsPage.objInvalidSecondNameMsg,"Error Message"))){
//			String sInvalidSecondNameErrorMsg = Utilities.getText(MLWalletPayBillsPage.objInvalidSecondNameMsg);
//			String sExceptedSecondNameErrorMsg = "Middle name must only contain letters and spaces";
//			Utilities.assertionValidation(sInvalidSecondNameErrorMsg,sExceptedSecondNameErrorMsg);
//		}
//		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objInvalidLastName,Utilities.getTextVal(MLWalletPayBillsPage.objInvalidLastName,"Error Message"))){
//			String sInvalidThirdNameErrorMsg = Utilities.getText(MLWalletPayBillsPage.objInvalidLastName);
//			String sExceptedThirdNameErrorMsg = "Last name must only contain letters and spaces";
//			Utilities.assertionValidation(sInvalidThirdNameErrorMsg,sExceptedThirdNameErrorMsg);
//		}

	public void editAddedBillerToPayBills_PB_TC_14() throws Exception {
		ExtentReporter.HeaderChildNode("Edit Added Biller to Pay bIlls");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		payBillsNavigation();
		selectAddedBiler();
		click(MLWalletPayBillsPage.objEditBtn,getTextVal(MLWalletPayBillsPage.objEditBtn,"Button"));

		clearField(MLWalletPayBillsPage.objEditRecipientLastName,"Last Name Input Field");
		type(MLWalletPayBillsPage.objEditRecipientLastName, prop.getproperty("Edited_Last_name"), "Last Name Text Field");
		click(MLWalletPayBillsPage.ObjSaveBtn, getTextVal(MLWalletPayBillsPage.ObjSaveBtn, "Button"));
		click(MLWalletPayBillsPage.objOKBtn,getTextVal(MLWalletPayBillsPage.objOKBtn,"Button"));
		type(MLWalletPayBillsPage.objSearchSavedBiller, prop.getproperty("Edited_Last_name"), "Search Recipient Text Field");
		if (verifyElementPresent(MLWalletPayBillsPage.objSelectLastName(prop.getproperty("Edited_Last_name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"), prop.getproperty("First_Name")), "Recipient"))) {
			logger.info("PB_TC_14, Successfully edited the Added Biller");
			ExtentReporter.extentLoggerPass("PB_TC_14", "PB_TC_14, Successfully edited the Added Biller");
			System.out.println("-----------------------------------------------------------");
		}

	}

	public void deleteAddedBillerPayBills_PB_TC_15() throws Exception {
		ExtentReporter.HeaderChildNode("Delete Added Biller Pay Bills");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		payBillsNavigation();
		selectAddedBiler();
		click(MLWalletPayBillsPage.objRemoveBtn,getTextVal(MLWalletPayBillsPage.objRemoveBtn,"Button"));
		if(verifyElementPresent(MLWalletPayBillsPage.objConfirmPopup,getTextVal(MLWalletPayBillsPage.objConfirmPopup,"Pop up"))){
			verifyElementPresentAndClick(MLWalletPayBillsPage.objConfirmBtn,getTextVal(MLWalletPayBillsPage.objConfirmBtn,"Button"));
		}
		if(verifyElementPresent(MLWalletPayBillsPage.objSavedBillers,getTextVal(MLWalletPayBillsPage.objSavedBillers,"Page"))){
			type(MLWalletPayBillsPage.objSearchBillerInSavedBillers,prop.getproperty("Edited_Last_name"),"Search saved biller input field");
			if (verifyElementNotPresent(MLWalletPayBillsPage.objSelectLastName(prop.getproperty("Edited_Last_name"), prop.getproperty("First_Name")),5)){
				logger.info("PB_TC_15, Successfully deleted the Added Biller");
				ExtentReporter.extentLoggerPass("PB_TC_15", "PB_TC_15, Successfully deleted the Added Biller");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

//================================ Phase 2=============================================================//

	public void payBillsUIValidation_PB_TC_16() throws Exception {
		ExtentReporter.HeaderChildNode("Pay Bills UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		payBillsNavigation();
		if(verifyElementPresent(MLWalletPayBillsPage.objSelectBiller,getTextVal(MLWalletPayBillsPage.objSelectBiller,"Page"))){
			verifyElementPresent(MLWalletPayBillsPage.objRecentTransactions,getTextVal(MLWalletPayBillsPage.objRecentTransactions,"Header"));
			verifyElementPresent(MLWalletPayBillsPage.objSavedBiller,getTextVal(MLWalletPayBillsPage.objSavedBiller,"Button"));
			verifyElementPresent(MLWalletPayBillsPage.objBillers,getTextVal(MLWalletPayBillsPage.objBillers,"Header"));
			verifyElementPresent(MLWalletPayBillsPage.objCategories,getTextVal(MLWalletPayBillsPage.objCategories,"Button"));
			verifyElementPresent(MLWalletPayBillsPage.objAlphabetical,getTextVal(MLWalletPayBillsPage.objAlphabetical,"Button"));
			verifyElementPresent(MLWalletPayBillsPage.objSearchBiller,"Search Biller Input Field");
			logger.info("PB_TC_16, Pay Bills UI Page Validated");
			ExtentReporter.extentLoggerPass("PB_TC_16", "PB_TC_16, Pay Bills UI Page Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void payBillsAddBillerPageUIValidation_PB_TC_18() throws Exception {
		ExtentReporter.HeaderChildNode("PayBills Add Biller Page UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		payBillsNavigation();
		verifyElementPresentAndClick(MLWalletPayBillsPage.objSavedBiller,getTextVal(MLWalletPayBillsPage.objSavedBiller,"Button"));
		verifyElementPresentAndClick(MLWalletPayBillsPage.objAddBiller,getTextVal(MLWalletPayBillsPage.objAddBiller,"Button"));
		if(verifyElementPresent(MLWalletPayBillsPage.objAddBillers,getTextVal(MLWalletPayBillsPage.objAddBillers,"Page"))){
			verifyElementPresent(MLWalletPayBillsPage.objBillerInformation,getTextVal(MLWalletPayBillsPage.objBillerInformation,"Header"));
			verifyElementPresent(MLWalletPayBillsPage.objBiller,getTextVal(MLWalletPayBillsPage.objBiller,"Edit Field"));
			verifyElementPresent(MLWalletPayBillsPage.objAddAccountNumber,"Account Number Input Field");
			verifyElementPresent(MLWalletPayBillsPage.objAddFirstName,"Account Holder First Name Input Field");
			verifyElementPresent(MLWalletPayBillsPage.objAddMiddleName,"Account Holder Middle Name Input Field");
			verifyElementPresent(MLWalletPayBillsPage.objAddLastName,"Account Holder Last Name Input Field");
			verifyElementPresent(MLWalletPayBillsPage.objAddNickName,"Nick Name Input Field");
			verifyElementPresent(MLWalletPayBillsPage.objProceedBtn,getTextVal(MLWalletPayBillsPage.objProceedBtn,"Button"));
			logger.info("PB_TC_18, PayBills Add Biller Page UI Validated");
			ExtentReporter.extentLoggerPass("PB_TC_18", "PB_TC_16, PayBills Add Biller Page UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void paybillsRecentTransaction_PB_TC_19() throws Exception {
		ExtentReporter.HeaderChildNode("Pay Bills Recent Transaction validation");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		payBillsNavigation();
		searchBiller();
		billerDetails(prop.getproperty("First_Name"), prop.getproperty("Middle_Name"), prop.getproperty("Last_Name"), "10");
		verifyElementPresent(MLWalletPayBillsPage.objConfirmDetails, getTextVal(MLWalletPayBillsPage.objConfirmDetails, "Page"));
		Swipe("UP",1);
		click(MLWalletPayBillsPage.objPayBtn,getTextVal(MLWalletPayBillsPage.objPayBtn,"Button"));
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		verifyElementPresent(MLWalletPayBillsPage.objTransactionDetails,getTextVal(MLWalletPayBillsPage.objTransactionDetails,"Page"));
		Swipe("UP",1);
		verifyElementPresentAndClick(MLWalletPayBillsPage.objNewTransactionBtn,getTextVal(MLWalletPayBillsPage.objNewTransactionBtn,"Button"));
		verifyElementPresentAndClick(MLWalletPayBillsPage.objRecentTransactionOne,"Recent Transaction");
		if(verifyElementPresent(MLWalletPayBillsPage.objBillsPayInformation,getTextVal(MLWalletPayBillsPage.objBillsPayInformation,"Page"))){
			verifyElementPresent(MLWalletPayBillsPage.objMisBillsPayBiller,getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller,"Biller"));
			logger.info("PB_TC_19, Pay Bills Recent Transaction Validated");
			ExtentReporter.extentLoggerPass("PB_TC_19", "PB_TC_19, Pay Bills Recent Transaction validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void payBillsSavedBilerUIValidation_PB_TC_20() throws Exception {
		ExtentReporter.HeaderChildNode("Pay Bills Saved Biler UI Validation");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		payBillsNavigation();
		selectAddedBiler();
		if(verifyElementPresent(MLWalletPayBillsPage.objAccountInfo,getTextVal(MLWalletPayBillsPage.objAccountInfo,"Page"))){
			verifyElementPresent(MLWalletPayBillsPage.objAddAccountNumber,getTextVal(MLWalletPayBillsPage.objAddAccountNumber,"Account Number"));
			verifyElementPresent(MLWalletPayBillsPage.objAddFirstName,getTextVal(MLWalletPayBillsPage.objAddFirstName,"First Name"));
			verifyElementPresent(MLWalletPayBillsPage.objAddMiddleName,getTextVal(MLWalletPayBillsPage.objAddMiddleName,"Middle Name"));
			verifyElementPresent(MLWalletPayBillsPage.objAddLastName,getTextVal(MLWalletPayBillsPage.objAddLastName,"Last Name"));
			verifyElementPresent(MLWalletPayBillsPage.objAddNickName,getTextVal(MLWalletPayBillsPage.objAddNickName,"Nick Name"));
			verifyElementPresent(MLWalletPayBillsPage.objEditBtn,getTextVal(MLWalletPayBillsPage.objEditBtn,"Button"));
			verifyElementPresent(MLWalletPayBillsPage.objRemoveBtn,getTextVal(MLWalletPayBillsPage.objRemoveBtn,"Button"));
			verifyElementPresent(MLWalletPayBillsPage.objSelectBiller,getTextVal(MLWalletPayBillsPage.objSelectBiller,"Button"));
			logger.info("PB_TC_20, Pay Bills Saved Biler UI Validated");
			ExtentReporter.extentLoggerPass("PB_TC_20", "PB_TC_20, Pay Bills Saved Biler UI validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void payBillsMaxBillsPaymentPerTransactionBuyTierUser_PB_TC_22() throws Exception {
		ExtentReporter.HeaderChildNode("Pay Bills Maximum Bills Payment Per Transaction for Buyer Tier Account");
		mlWalletLogin(prop.getproperty("Buyer_Tier"));
		payBillsNavigation();
		searchBiller();
		billerDetails(prop.getproperty("First_Name"), prop.getproperty("Middle_Name"), prop.getproperty("Last_Name"), "20000");
		verifyElementPresent(MLWalletPayBillsPage.objConfirmDetails, getTextVal(MLWalletPayBillsPage.objConfirmDetails, "Page"));
		Swipe("UP",1);
		click(MLWalletPayBillsPage.objPayBtn,getTextVal(MLWalletPayBillsPage.objPayBtn,"Button"));
		if(verifyElementPresent(MLWalletPayBillsPage.objMaxLimitErrorMessage,getTextVal(MLWalletPayBillsPage.objMaxLimitErrorMessage,"Error Message"))){
			String sMaxLimitErrorMessage = getText(MLWalletPayBillsPage.objMaxLimitErrorMessage);
			String sExpectedErrorMessage = "The maximum Bills Pay per transaction set for your verification level is P10,000.00. Please try again.";
			assertionValidation(sMaxLimitErrorMessage,sExpectedErrorMessage);
            verifyElementPresent(MLWalletPayBillsPage.objUpgradeNowBtn,getTextVal(MLWalletPayBillsPage.objUpgradeNowBtn,"Button"));
			logger.info("PB_TC_22, Pay Bills Maximum Bills Payment Per Transaction for Buyer Tier Account Validated");
			ExtentReporter.extentLoggerPass("PB_TC_22", "PB_TC_22, Pay Bills Maximum Bills Payment Per Transaction for Buyer Tier Account validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void payBillsMaxBillsPaymentPerTransactionSemiVerifiedTier_PB_TC_26() throws Exception {
		ExtentReporter.HeaderChildNode("Pay Bills Maximum Bills Payment Per Transaction for Semi Verified Tier Account");
		mlWalletLogin(prop.getproperty("Semi_Verified"));
		payBillsNavigation();
		searchBiller();
		billerDetails(prop.getproperty("First_Name"), prop.getproperty("Middle_Name"), prop.getproperty("Last_Name"), "30000");
		verifyElementPresent(MLWalletPayBillsPage.objConfirmDetails, getTextVal(MLWalletPayBillsPage.objConfirmDetails, "Page"));
		Swipe("UP",1);
		click(MLWalletPayBillsPage.objPayBtn,getTextVal(MLWalletPayBillsPage.objPayBtn,"Button"));
		if(verifyElementPresent(MLWalletPayBillsPage.objMaxLimitErrorMessage,getTextVal(MLWalletPayBillsPage.objMaxLimitErrorMessage,"Error Message"))){
			String sMaxLimitErrorMessage = getText(MLWalletPayBillsPage.objMaxLimitErrorMessage);
			String sExpectedErrorMessage = "The maximum Bills Pay per transaction set for your verification level is P20,000.00. Please try again.";
			assertionValidation(sMaxLimitErrorMessage,sExpectedErrorMessage);
			verifyElementPresent(MLWalletPayBillsPage.objUpgradeNowBtn,getTextVal(MLWalletPayBillsPage.objUpgradeNowBtn,"Button"));
			logger.info("PB_TC_26, Pay Bills Maximum Bills Payment Per Transaction for Semi Verified Tier Account Validated");
			ExtentReporter.extentLoggerPass("PB_TC_26", "PB_TC_26, Pay Bills Maximum Bills Payment Per Transaction for Semi Verified Tier Account validated");
			System.out.println("-----------------------------------------------------------");
		}
	}











//============================ Settings Module ============================================================//
//================================Generalized methods=======================================================//


	public void navigateToProfile() throws Exception {
		click(MLWalletSettinsPage.objProfileIcon, "Profile Icon");
		if (verifyElementPresent(MLWalletSettinsPage.objAccountDetails, "Account Details Page")) {
		} else {
			DriverManager.getAppiumDriver().navigate().back();
			click(MLWalletSettinsPage.objProfileIcon1, "Profile Icon");
		}
	}
//===========================================================================================================//
	public void accountDetailsValidation_SS_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Account Details validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		navigateToProfile();
		click(MLWalletSettinsPage.objAccountDetails, "Account Details");
		verifyElementPresent(MLWalletSettinsPage.objAccountDetails, getTextVal(MLWalletSettinsPage.objAccountDetails,"Name"));
		verifyElementPresent(MLWalletSettinsPage.objNameOfAccountHolder,getTextVal(MLWalletSettinsPage.objNameOfAccountHolder, "Name"));
		verifyElementPresent(MLWalletSettinsPage.objMailAddressOfAccountHolder,getTextVal(MLWalletSettinsPage.objMailAddressOfAccountHolder, "Mail Address"));
		verifyElementPresent(MLWalletSettinsPage.objMobileNoOfAccountHolder,getTextVal(MLWalletSettinsPage.objMobileNoOfAccountHolder, "Mobile Number"));
		logger.info("SS_TC_01, Account Details validated");
		ExtentReporter.extentLoggerPass("SS_TC_01", "SS_TC_01, Account Details validated");
		System.out.println("-----------------------------------------------------------");
	}
	public void invalidMLPinValidation_SS_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Invalid ML Pin Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		navigateToProfile();
		click(MLWalletSettinsPage.objChangeMLPin, "Change ML Pin");
		verifyElementPresent(MLWalletSettinsPage.objChangeMLPin, "Change ML Pin");

		handleMpin(prop.getproperty("wrongMpin"), "Entered Invalid ML PIN");
		logger.info(getTextVal(MLWalletSettinsPage.objInvalaidMpinPopUp, "Pop up"));
		ExtentReporter.extentLoggerPass("Invalid Mpin Pop up",getTextVal(MLWalletSettinsPage.objInvalaidMpinPopUp, "Pop up"));
		click(MLWalletSettinsPage.objOKBtn, "Ok Button");
		logger.info("SS_TC_03, Invalid ML PIN validated");
		ExtentReporter.extentLoggerPass("SS_TC_03", "SS_TC_03, Invalid ML PIN validated");
		System.out.println("-----------------------------------------------------------");
	}


	public void validMLPinValidation_SS_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Invalid ML Pin Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		navigateToProfile();
		click(MLWalletSettinsPage.objChangeMLPin, "Change ML Pin");
		verifyElementPresent(MLWalletSettinsPage.objChangeMLPin, "Change ML Pin");

		waitTime(2000);
		handleMpin(prop.getproperty("mPin"), "Entered");
		waitTime(2000);
		if (verifyElementPresent(MLWalletSettinsPage.objEnterNewMMLpinText,
				getTextVal(MLWalletSettinsPage.objEnterNewMMLpinText, "Page"))) {
			waitTime(2000);
			handleMpin(prop.getproperty("newMpin"), "New ML PIN");
			waitTime(2000);
			handleMpin(prop.getproperty("newMpin"), "Confirmed New ML ");
			logger.info(getTextVal(MLWalletSettinsPage.objMLPinChangedPopup, "Pop Up"));
			ExtentReporter.extentLoggerPass("ML Pin Changed Pop Up",
					getTextVal(MLWalletSettinsPage.objMLPinChangedPopup, "Pop Up"));
			click(MLWalletSettinsPage.objGotItBtn, "Got It Button");
		} else if (verifyElementPresent(MLWalletSettinsPage.objInvalaidMpinPopUp,
				getTextVal(MLWalletSettinsPage.objInvalaidMpinPopUp, "Pop Up"))) {
			click(MLWalletSettinsPage.objOKBtn, "OK Button");
			handleMpin(prop.getproperty("newMpin"), "New ML PIN");
			waitTime(2000);
			handleMpin(prop.getproperty("mPin"), "Confirmed New ML ");
			waitTime(2000);
			handleMpin(prop.getproperty("mPin"), "Confirmed New ML ");
			logger.info(getTextVal(MLWalletSettinsPage.objMLPinChangedPopup, "Pop Up"));
			ExtentReporter.extentLoggerPass("ML Pin Changed Pop Up",
					getTextVal(MLWalletSettinsPage.objMLPinChangedPopup, "Pop Up"));
			click(MLWalletSettinsPage.objGotItBtn, "Got It Button");
		}
		logger.info("'SS_TC_02' To validate ML PIN validated");
		ExtentReporter.extentLoggerPass("SS_TC_02", "'SS_TC_02' To validate ML PIN validated");

	}

	public void mlWalletSettingAccRecovery() throws Exception {
		ExtentReporter.HeaderChildNode("Invalid ML Pin Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		navigateToProfile();
		click(MLWalletSettinsPage.objAccountRecovery, "Account Recovery");
		verifyElementPresent(MLWalletSettinsPage.objMlWalletSupportPage,
				getTextVal(MLWalletSettinsPage.objMlWalletSupportPage, "Page"));

		click(MLWalletSettinsPage.objFullNameField, "First Name Field");
		type(MLWalletSettinsPage.objFullNameField, prop.getproperty("firstName"), "First Name Field");
		hideKeyboard();

		click(MLWalletSettinsPage.objRegisteredEmail, "Registered Email Field");
		type(MLWalletSettinsPage.objRegisteredEmail, prop.getproperty("eMailAddress"),
				"Registered Email Field");
		hideKeyboard();
		click(MLWalletSettinsPage.objMobileNumber, "Mobile Number Field");
		type(MLWalletSettinsPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Mobile Number Field");
		hideKeyboard();
		click(MLWalletSettinsPage.objNatureOfReqRadioBtn,
				getTextVal(MLWalletSettinsPage.objNatureOfReqRadioBtn, "Text"));
		scrollToVertical("Next");
		click(MLWalletSettinsPage.objNextBtn, "Next Button");
		verifyElementPresent(MLWalletSettinsPage.objStolenPage,
				getTextVal(MLWalletSettinsPage.objStolenPage, "Page"));

		click(MLWalletSettinsPage.objYourAnswer, "Lost/Stolen Mobile Number Field");
		type(MLWalletSettinsPage.objYourAnswer, prop.getproperty("Branch_Verified"),
				"Lost/Stolen Mobile Number Field");
		hideKeyboard();

		click(MLWalletSettinsPage.objNewMobNo, "New Mobile Number Field");
		type(MLWalletSettinsPage.objNewMobNo, prop.getproperty("newMobileNumber"), "New Mobile Number Field");
		hideKeyboard();

		click(MLWalletSettinsPage.objFacebookMessangerName, "Facebook Messenger Name Field");
		type(MLWalletSettinsPage.objFacebookMessangerName, prop.getproperty("messangerName"),
				"Facebook Messenger Name Field");
		hideKeyboard();

		scrollToVertical("Submit");
		click(MLWalletSettinsPage.objSumbitBtn, "Submit Button");
		verifyElementPresent(MLWalletSettinsPage.objReviewPage, "Review Page");
		String actualText = "Please allow us some time to review the details of your request. A customer service representative will contact you for updates and/or to get additional information.";
		String reviewExpectedText = getText(MLWalletSettinsPage.objReviewPage);
		assertionValidation(actualText, reviewExpectedText);
		logger.info("'SS_TC_05', To verify account recovery validated");
		ExtentReporter.extentLoggerPass("Account Recovery", "'SS_TC_05', To verify account recovery validated");

	}

	public static void handleMpin(String mPin, String validationText) throws Exception {
		for (int i = 0; i < mPin.length(); i++) {
			char ch = mPin.charAt(i);
			String ch1 = String.valueOf(ch);
			click(MLWalletSettinsPage.objEnterMpinVal(ch1),
					getTextVal(MLWalletSettinsPage.objEnterMpinVal(ch1), "MPIN"));
		}
		logger.info(validationText + " MPIN " + mPin + " Successfully");
		ExtentReporter.extentLogger("Enter MPIN", "" + validationText + " MPIN " + mPin + " Successfully");
	}



//=================================== Buy e - load ======================================================//
//==================================== Generalized methods ============================================//


	public void eLoad_generic(String typeOfAccount,String mobileNo, String status, int telcoOption) throws Exception
	{
		mlWalletLogin(typeOfAccount);
		click(MLWalletEloadPage.objEloadTab, "Buy eLoad");

		if(status.equals("true")) {
			verifyElementPresent(MLWalletEloadPage.objEloadtransactionPage, "eLoad Transaction Page");
			click(MLWalletEloadPage.telcoOptions(telcoOption),getTextVal(MLWalletEloadPage.telcoOptions(telcoOption), "Tab"));
		}
		click(MLWalletEloadPage.objMobileNoField, "Mobile Number Field");
		type(MLWalletEloadPage.objMobileNoField, mobileNo, "Mobile Number Field");
		hideKeyboard();
		click(MLWalletEloadPage.objNextBtn, "Next Button");
		click(MLWalletEloadPage.objNextBtn, "Next Button");
		enableLocation_PopUp();
	}
//===================================================================================================//



	public void buying_eLoad(String accountType,int promotab) throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad Validation");
		eLoad_generic(accountType,prop.getproperty("sunMobileNumber"), "true", promotab);
		verifyElementPresent(MLWalletEloadPage.objLoadSelectionPage, "Load Selection Page");
		click(MLWalletEloadPage.objPromoLoadTab, "Promo Load Tab");
		waitTime(5000);
		click(MLWalletEloadPage.objTransaction, getTextVal(MLWalletEloadPage.objTransaction, "Promo"));
		verifyElementPresent(MLWalletEloadPage.objContinuePromoPopUp, getTextVal(MLWalletEloadPage.objContinuePromoPopUp, "Pop Up"));
		click(MLWalletEloadPage.objConfirmBtn, "Confirm Button");
		waitTime(4000);
		verifyElementPresent(MLWalletEloadPage.objTransactionDetailsPage, getTextVal(MLWalletEloadPage.objTransactionDetailsPage, "Page"));
		verifyElementPresent(MLWalletEloadPage.objTypeOfPromoUsed, getTextVal(MLWalletEloadPage.objTypeOfPromoUsed, "Promo"));
		verifyElementPresent(MLWalletEloadPage.objMobileNumber, getTextVal(MLWalletEloadPage.objMobileNumber, "Mobile Number"));
		waitTime(2000);
		verifyElementPresent(MLWalletEloadPage.objAmountToSend, getTextVal(MLWalletEloadPage.objAmountToSend, "Amount to Send"));
		verifyElementPresent(MLWalletEloadPage.objServiceFee, getTextVal(MLWalletEloadPage.objServiceFee, "Service Fee"));
		verifyElementPresent(MLWalletEloadPage.objTotalAmount, getTextVal(MLWalletEloadPage.objTotalAmount, "Total Amount"));
		click(MLWalletEloadPage.objConfirmBtn, "Confirm Button");
		logger.info("'BE_TC_01', To verify buying eLoad validated ");
		ExtentReporter.extentLoggerPass("BE_TC_01", "'BE_TC_01', To verify buying eLoad validated ");
	}


	public void buying_eload_Invalid_Mob_Number() throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad using invalid mobile number");
		eLoad_generic(prop.getproperty("Fully_Verified"),prop.getproperty("inValidMobNumber"),"true", 3);
		verifyElementPresent(MLWalletEloadPage.objErrorMsg, getTextVal(MLWalletEloadPage.objErrorMsg, "Pop Up Message"));
		logger.info("'BE_TC_02', verification of buying eLoad using invalid mobile number input validated");
		ExtentReporter.extentLoggerPass("BE_TC_02", "'BE_TC_02', verification of buying eLoad using invalid mobile number input validated ");
	}

	public void buying_eload_without_input_Mob_Number() throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad without mobile number input");
		eLoad_generic(prop.getproperty("Fully_Verified"),"", "true", 3);
		verifyElementPresent(MLWalletEloadPage.objErrorMsg, getTextVal(MLWalletEloadPage.objErrorMsg, "Pop Up Message"));
		logger.info("'BE_TC_03', To verify buying eLoad without mobile number input validated");
		ExtentReporter.extentLoggerPass("BE_TC_03", "'BE_TC_03', To verify buying eLoad without mobile number input validated");
	}

	public void buying_eload_without_telecommunication_Selected() throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad without telecommunication selected");
		eLoad_generic(prop.getproperty("Fully_Verified"),"","false", 3);
		Utilities.waitTime(2000);
		Utilities.verifyElementPresent(MLWalletEloadPage.objErrorMsg, Utilities.getTextVal(MLWalletEloadPage.objErrorMsg, "Pop Up Message"));
		logger.info("'BE_TC_04', To verify buying eLoad without telecommunication selected validated");
		ExtentReporter.extentLoggerPass("BE_TC_04", "'BE_TC_04', To verify buying eLoad without telecommunication selected validated");
	}

	public void buying_eload_insufficient_balance(int  promotab) throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad with insufficient balance");
		buying_eLoad(prop.getproperty("Buyer_Tier"),promotab);
		Utilities.verifyElementPresent(MLWalletEloadPage.objInsufficientBalPopup, Utilities.getTextVal(MLWalletEloadPage.objInsufficientBalPopup, "Pop up"));
		logger.info("BE_TC_05, Insufficient Balance Error Message Validated");
		ExtentReporter.extentLoggerPass("BE_TC_05", "BE_TC_05, Insufficient Balance Error Message Validated");
	}

	public void buying_eload_dailyMaximum_Limit() throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad with daily maximum purchase limit");
		buying_eLoad(prop.getproperty("Branch_Verified"),3);
		handleMpin(prop.getproperty("MPin"), "Entered");
		Utilities.waitTime(2000);

	}

//=========================================== Cash In  Via Branch ================================//


	public void cashInViaBranch_Generic_Steps() throws Exception {
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashInViaBranch.objCashInMenu, "Cash In");
		Utilities.verifyElementPresent(MLWalletCashInViaBranch.objBranchName, "Cash In Options Page");
		Utilities.click(MLWalletCashInViaBranch.objBranchName, "ML Branch");
		Utilities.verifyElementPresent(MLWalletCashInViaBranch.objBranchCashIn, "Branch Cash In Page");
	}
		public void cashInViaBranchEnterAmount(String sAmount) throws Exception {
		Utilities.click(MLWalletCashInViaBranch.objAmountTextField, "Amount Text Field");
		Utilities.type(MLWalletCashInViaBranch.objAmountTextField, sAmount, "Amount Text Field");
		Utilities.hideKeyboard();
		Utilities.Swipe("up", 1);
		Utilities.click(MLWalletCashInViaBranch.objNextButton, "Next Button");
	}


	public void cashInviaBranch_ValidAmount_Scenario_CIBR_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("ML_Wallet_Cash_In_Via_Barnch_ValidAmount_Scenario");
		cashInViaBranch_Generic_Steps();
		cashInViaBranchEnterAmount("100");
		Utilities.waitTime(2000);
		Utilities.verifyElementPresent(MLWalletCashInViaBranch.objWarningPopup,
				Utilities.getTextVal(MLWalletCashInViaBranch.objWarningPopup, "Pop Up"));
		Utilities.click(MLWalletCashInViaBranch.objContinueButton, "Continue Button");
		enableLocation_PopUp();
		Utilities.getTextVal(MLWalletCashInViaBranch.objkptnId, "KPTN Id");
		Utilities.click(MLWalletCashInViaBranch.objCrossBtn, "Cross Button");
		logger.info("'CIBR_TC_01', To validate valid Amount in Cash In ML Branch ");
		ExtentReporter.extentLoggerPass("'CIBR_TC_01", "'CIBR_TC_01', To validate valid Amount in Cash In ML Branch ");
	}

	public void cashInViaBranchCancelTransactionScenario_CIBR_TC_02() throws Exception {
		 ExtentReporter.HeaderChildNode("ML Wallet Cash In Via Branch Cancel Transaction Scenario");
		cashInViaBranch_Generic_Steps();
		cashInViaBranchEnterAmount("100");
		Utilities.verifyElementPresent(MLWalletCashInViaBranch.objWarningPopup,Utilities.getTextVal(MLWalletCashInViaBranch.objWarningPopup, "Pop Up"));
		Utilities.click(MLWalletCashInViaBranch.objContinueButton, "Continue Button");
		enableLocation_PopUp();
		Utilities.getTextVal(MLWalletCashInViaBranch.objkptnId, "KPTN Id");
		Utilities.click(MLWalletCashInViaBranch.objCancelTransactionBtn, "Cancel Transaction");
		Utilities.verifyElementPresent(MLWalletCashInViaBranch.objCanceLTransactionPopup,Utilities.getTextVal(MLWalletCashInViaBranch.objCanceLTransactionPopup, "Pop up"));
		Utilities.click(MLWalletCashInViaBranch.objCancelBtn1, "Cancel Transaction");
		Utilities.getTextVal(MLWalletCashInViaBranch.objTransactionCancelSuccessfulMsg, "Message");
		Utilities.click(MLWalletCashInViaBranch.objCrossBtn, "Cross Button");
		logger.info("'CIBR_TC_02', To validate Cancel Transaction in Cash In ML Branch");
		ExtentReporter.extentLoggerPass("'CIBR_TC_02","'CIBR_TC_02', To validate Cancel Transaction in Cash In ML Branch");
	}

	public void cashInviaBranch_Invalid_Amount_CIBR_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("ML Wallet Cash In via Branch Invalid Amount");
		cashInViaBranch_Generic_Steps();
		cashInViaBranchEnterAmount("0");
		if (Utilities.verifyElementPresent(MLWalletCashInViaBranch.objInvalidAmountMsg, Utilities.getTextVal(MLWalletCashInViaBranch.objInvalidAmountMsg, "Error Message"))) {
			String sInvalidAmountErrorMsg = Utilities.getText(MLWalletCashInViaBranch.objInvalidAmountMsg);
			String sExpectedErrorMsg = "The amount should not be less than 1";
			Utilities.assertionValidation(sInvalidAmountErrorMsg, sExpectedErrorMsg);
			logger.info("'CIBR_TC_03', 'CIBR_TC_03' To validate Invalid Amount");
			ExtentReporter.extentLoggerPass("CIBR_TC_03", "'CIBR_TC_03', To validate Invalid Amount");
		}
	}


	public void cashInViaBranch_Maximum_Limit_Amount_CIBR_TC_04() throws Exception {
		ExtentReporter.HeaderChildNode("ML Wallet Cash In via Branch Maximum Limit Amount");
		cashInViaBranch_Generic_Steps();
		cashInViaBranchEnterAmount("50001");
		Utilities.click(MLWalletCashInViaBranch.objContinueButton, Utilities.getTextVal(MLWalletCashInViaBranch.objContinueButton, "Button"));
		Utilities.Swipe("UP", 2);
		Utilities.click(MLWalletCashInViaBranch.objNextButton, Utilities.getTextVal(MLWalletCashInViaBranch.objNextButton, "Button"));
		Thread.sleep(5000);
		if(Utilities.verifyElementPresent(MLWalletCashInViaBranch.objBankMaxLimitTxt,Utilities.getTextVal(MLWalletCashInViaBranch.objBankMaxLimitTxt,"Error Message"))) {
			String sErrorMsg = Utilities.getText(MLWalletCashInViaBranch.objBankMaxLimitTxt);
			String sExpectedErrorMsg = "The maximum Branch Cash-in per transaction set for your verification level is P50,000.00. Please try again.";
			Utilities.assertionValidation(sErrorMsg, sExpectedErrorMsg);
			logger.info("'CIBR_TC_04', 'CIBR_TC_04' To validate Maximum Limit of transaction");
			ExtentReporter.extentLoggerPass("CIBR_TC_04", "'CIBR_TC_04', To validate Maximum Limit of transaction");
		}
	}


	public void cashInViaBRanchInvalidAmount_CIBR_TC_05() throws Exception {
		ExtentReporter.HeaderChildNode("ML Wallet Cash In via Branch Invalid Amount");
		cashInViaBranch_Generic_Steps();
		cashInViaBranchEnterAmount("");
		if (Utilities.verifyElementPresent(MLWalletCashInViaBranch.objInvalidAmountMsg, getTextVal(MLWalletCashInViaBranch.objInvalidAmountMsg, "Error Message"))) {
			String sInvalidAmountErrorMsg = getText(MLWalletCashInViaBranch.objInvalidAmountMsg);
			String sExpectedErrorMsg = "Amount is required";
			Utilities.assertionValidation(sInvalidAmountErrorMsg, sExpectedErrorMsg);
			logger.info("CIBR_TC_05, Amount is required - Error Message is validated");
			ExtentReporter.extentLoggerPass("CIBR_TC_05", "CIBR_TC_05, Amount is required - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBranchUIValidation_CIBR_TC_06() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Branch UI Validation");
		cashInViaBranch_Generic_Steps();
		if(verifyElementPresent(MLWalletCashInViaBranch.objCashInMenu,getTextVal(MLWalletCashInViaBranch.objCashInMenu,"Page"))){
			verifyElementPresent(MLWalletCashInViaBranch.objBranchCashIn,getTextVal(MLWalletCashInViaBranch.objBranchCashIn,"Header"));
			verifyElementPresent(MLWalletCashInViaBranch.objUserName,getTextVal(MLWalletCashInViaBranch.objUserName,"User Name"));
			verifyElementPresent(MLWalletCashInViaBranch.objUserNumber,getTextVal(MLWalletCashInViaBranch.objUserNumber,"User Number"));
			verifyElementPresent(MLWalletCashInViaBranch.objAmountTextField,"Amount Input Field");
			 verifyElementPresent(MLWalletCashInViaBranch.objNextButton, "Next Button");
			logger.info("CIBR_TC_06, Cash In Via Branch UI validated");
			ExtentReporter.extentLoggerPass("CIBR_TC_06", "CIBR_TC_06, Cash In Via Branch UI validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBranchBackBtnValidation_CIBR_TC_07() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Branch Back Btn Validation");
		cashInViaBranchUIValidation_CIBR_TC_06();
		verifyElementPresentAndClick(MLWalletCashInViaBranch.objCashInBranchBackBtn,"Cash In Branch Back Button");
		if(verifyElementPresent(MLWalletCashInViaBranch.objCashInMenu,getTextVal(MLWalletCashInViaBranch.objCashInMenu,"Page"))){
			logger.info("CIBR_TC_07, Cash In Via Branch Back Btn validated");
			ExtentReporter.extentLoggerPass("CIBR_TC_07", "CIBR_TC_07, Cash In Via Branch Back Btn validated");
			System.out.println("-----------------------------------------------------------");
		}
	}






//============================== Log Out  ===============================================//


	public void logOutMinimizeAndRelaunch_Lout_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Log Out Minimize and relaunch the application");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		logger.info("Application Minimized for 5 Seconds");
		DriverManager.getAppiumDriver().runAppInBackground(Duration.ofSeconds(5));
		logger.info("Applicaton relaunched after 5 Seconds");
		if (verifyElementPresent(MLWalletLoginPage.objAvailableBalance, getTextVal(MLWalletLoginPage.objAvailableBalance, "Text"))) {
			logger.info("User should not be able to logout from the app");
			logger.info("Lout_TC_03, Log Out Minimize and relaunch the application validated");
			ExtentReporter.extentLoggerPass("Lout_TC_03", "Lout_TC_03, Log Out Minimize and relaunch the application validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void logOutAppKillAndRelaunch_Lout_TC_04() throws Exception {
		ExtentReporter.HeaderChildNode("Kill Application and Relaunch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		DriverManager.getAppiumDriver().closeApp();
		logger.info("Killed the Application");
		DriverManager.getAppiumDriver().launchApp();
		logger.info("Relaunch the Application");
		if(verifyElementPresent(MLWalletLoginPage.objLoginBtn,getTextVal(MLWalletLoginPage.objLoginBtn,"page"))){
			logger.info("Lout_TC_04, After Killing and relaunch the Application, Application got logged off");
			ExtentReporter.extentLoggerPass("Lout_TC_04", "Lout_TC_04, After Killing and relaunch the Application, Application got logged off");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void logOUtPopUpValidation_Lout_TC_05() throws Exception {
		ExtentReporter.HeaderChildNode("LogOut Popup Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		verifyElementPresentAndClick(MLWalletLogOutPage.objHamburgerMenu, "Hamburger Menu");
		click(MLWalletLogOutPage.objLogoutBtn, getTextVal(MLWalletLogOutPage.objLogoutBtn, "Button"));
		waitTime(3000);
		if(verifyElementPresent(MLWalletLogOutPage.objLogoOutPopupMsg,getTextVal(MLWalletLogOutPage.objLogoOutPopupMsg,"Pop up Message"))){
			String sLogOutPopupMsg = getText(MLWalletLogOutPage.objLogoOutPopupMsg);
			String sExpectedErrorMsg = "Are you sure you would like to logout?";
			assertionValidation(sLogOutPopupMsg,sExpectedErrorMsg);
			verifyElementPresent(MLWalletLogOutPage.objLogoutBtn,getTextVal(MLWalletLogOutPage.objLogoutBtn,"Button"));
			verifyElementPresent(MLWalletLogOutPage.objCancelBtn,getTextVal(MLWalletLogOutPage.objCancelBtn,"Button"));
			logger.info("Lout_TC_05, LogOut Popup validated");
			ExtentReporter.extentLoggerPass("Lout_TC_05", "Lout_TC_05, LogOut Popup validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void logOutPopUpCancelBtnValidation_Lout_TC_06() throws Exception {
		ExtentReporter.HeaderChildNode("LogOut PopUp Cancel Button Validation");
		logOUtPopUpValidation_Lout_TC_05();
		click(MLWalletLogOutPage.objCancelBtn,getTextVal(MLWalletLogOutPage.objCancelBtn,"Button"));
		if(verifyElementPresent(MLWalletLogOutPage.objHome,getTextVal(MLWalletLogOutPage.objHome,"Button"))){
			logger.info("Lout_TC_06, Popup disappeared after clicking on Cancel Button, LogOut PopUp Cancel Button validated");
			ExtentReporter.extentLoggerPass("Lout_TC_06", "Lout_TC_06, Popup disappeared after clicking on Cancel Button, LogOut PopUp Cancel Button validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void logOutPopUpLogOutBtnValidation_Lout_TC_07() throws Exception {
		ExtentReporter.HeaderChildNode("LogOut PopUp LogOut Btn Validation");
		logOUtPopUpValidation_Lout_TC_05();
		click(MLWalletLogOutPage.objLogoutBtn,getTextVal(MLWalletLogOutPage.objLogoutBtn,"Button"));
		if(verifyElementPresent(MLWalletLogOutPage.objChangeNumber,getTextVal(MLWalletLogOutPage.objChangeNumber,"Page"))){
			logger.info("Lout_TC_07, Popup disappeared after clicking on Cancel Button, LogOut PopUp Cancel Button validated");
			ExtentReporter.extentLoggerPass("Lout_TC_07", "Lout_TC_07, Popup disappeared after clicking on Cancel Button, LogOut PopUp Cancel Button validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void logoutChangeNumberUIValidation_Lout_TC_08() throws Exception {
		ExtentReporter.HeaderChildNode("Log Out Change Number UI page Validation");
		logOUtPopUpValidation_Lout_TC_05();
		click(MLWalletLogOutPage.objLogoutBtn,getTextVal(MLWalletLogOutPage.objLogoutBtn,"Button"));
		if(verifyElementPresent(MLWalletLogOutPage.objChangeNumber,getTextVal(MLWalletLogOutPage.objChangeNumber,"Page"))){
			verifyElementPresent(MLWalletLoginPage.objTroubleSigningIn,getTextVal(MLWalletLoginPage.objTroubleSigningIn,"Link"));
			verifyElementPresent(MLWalletLoginPage.objBranchLocator,getTextVal(MLWalletLoginPage.objBranchLocator,"Link"));
			verifyElementPresent(MLWalletLoginPage.objAppVersionChangeNumber,getTextVal(MLWalletLoginPage.objAppVersionChangeNumber,"App Version"));
			logger.info("Lout_TC_08, Log Out Change Number UI page validated");
			ExtentReporter.extentLoggerPass("Lout_TC_08", "Lout_TC_08, Log Out Change Number UI page validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void logInWithChangeNumber_Lout_TC_09() throws Exception {
		ExtentReporter.HeaderChildNode("Log In With Change Number");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		mlWalletLogout();
		click(MLWalletLogOutPage.objChangeNumber, getTextVal(MLWalletLogOutPage.objChangeNumber, "Link"));
		mlWalletLogin(prop.getproperty("Fully_verified"));
		if (verifyElementPresent(MLWalletLoginPage.objAvailableBalance, getTextVal(MLWalletLoginPage.objAvailableBalance, "Text"))) {
			logger.info("Lout_TC_09,Application Logged In Successfully with Change Number");
			ExtentReporter.extentLoggerPass("Lout_TC_09","Lout_TC_09, Application Logged In Successfully with Change Number");
			System.out.println("-----------------------------------------------------------");
		}
	}

//============================================== Branch Locator ===================================//

	public void branchLocatorNavigation() throws Exception {
		if(verifyElementPresentAndClick(MLWalletLoginPage.objBranchLocator,getTextVal(MLWalletLoginPage.objBranchLocator,"Button"))){
			enableLocation_PopUp();
			verifyElementPresent(MLWalletLoginPage.objBranchLocator,getTextVal(MLWalletLoginPage.objBranchLocator,"Page"));
			logger.info("Navigated to Branch Locator page");
		}else{
			logger.info("Not Navigated to Branch Locator Page");
		}
	}

	public void branchLocatorPageValidation() throws Exception {
		if(verifyElementPresent(MLWalletBranchLocator.objSearchBranch,getTextVal(MLWalletBranchLocator.objSearchBranch,"Header"))) {
			verifyElementPresent(MLWalletBranchLocator.obj24HoursOnly, getTextVal(MLWalletBranchLocator.obj24HoursOnly, "Option"));
			verifyElementPresent(MLWalletBranchLocator.objSearchBranchField, "Search Branch Input Field");
			verifyElementPresent(MLWalletBranchLocator.objLuzon, getTextVal(MLWalletBranchLocator.objLuzon, "Button"));
			click(MLWalletBranchLocator.objLuzon, getTextVal(MLWalletBranchLocator.objLuzon, "Button"));
			verifyElementPresent(MLWalletBranchLocator.objVisayas, getTextVal(MLWalletBranchLocator.objVisayas, "Button"));
			verifyElementPresent(MLWalletBranchLocator.objMindanao, getTextVal(MLWalletBranchLocator.objMindanao, "Button"));
			verifyElementPresent(MLWalletBranchLocator.objMLUS, getTextVal(MLWalletBranchLocator.objMLUS, "Button"));
			Swipe("UP",1);
			verifyElementPresent(MLWalletBranchLocator.objBranchesNearYou, getTextVal(MLWalletBranchLocator.objBranchesNearYou, "Map Header"));
		}
	}

//==========================================================================================================//

	public void branchLocatorNavigationFromMPinPage_BL_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Branch Locator Page Navigation from MPin Page and UI Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		mlWalletLogout();
		branchLocatorNavigation();
		if(verifyElementPresent(MLWalletLoginPage.objBranchLocator,getTextVal(MLWalletLoginPage.objBranchLocator,"Page"))){
			branchLocatorPageValidation();
			logger.info("BL_TC_01, Branch Locator Page Navigation from MPin Page and UI Validated");
			ExtentReporter.extentLoggerPass("BL_TC_01","BL_TC_01, Branch Locator Page Navigation from MPin Page and UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void branchLocatorNavigationFromLogInPage_BL_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Branch Locator page Navigation From Login Page and UI validation");
		branchLocatorNavigation();
		if(verifyElementPresent(MLWalletLoginPage.objBranchLocator,getTextVal(MLWalletLoginPage.objBranchLocator,"Page"))){
			branchLocatorPageValidation();
			logger.info("BL_TC_02, Branch Locator Page Navigation from Login Page and UI Validated");
			ExtentReporter.extentLoggerPass("BL_TC_02","BL_TC_02, Branch Locator Page Navigation from Login Page and UI Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void branchLocatorHamburgerFunctionality_BL_TC_05() throws Exception {
		ExtentReporter.HeaderChildNode("Branch Locator Hamburger Button Functionality");
		branchLocatorNavigation();
		verifyElementPresentAndClick(MLWalletBranchLocator.objBranchLocatorHamburgerMenu,"Hamburger Menu Button");
		if(verifyElementPresent(MLWalletBranchLocator.objHome,getTextVal(MLWalletBranchLocator.objHome,"Option"))){
			verifyElementPresent(MLWalletBranchLocator.objBranches,getTextVal(MLWalletBranchLocator.objBranches,"Option"));
			verifyElementPresent(MLWalletBranchLocator.objProductAndServices,getTextVal(MLWalletBranchLocator.objProductAndServices,"Option"));
			verifyElementPresent(MLWalletBranchLocator.objPromos,getTextVal(MLWalletBranchLocator.objPromos,"Option"));
			verifyElementPresent(MLWalletBranchLocator.objBlog,getTextVal(MLWalletBranchLocator.objBlog,"Option"));
			verifyElementPresent(MLWalletBranchLocator.objNewsLetters,getTextVal(MLWalletBranchLocator.objNewsLetters,"Option"));
			verifyElementPresent(MLWalletBranchLocator.objMLUSBtn,getTextVal(MLWalletBranchLocator.objMLUSBtn,"Option"));
			verifyElementPresent(MLWalletBranchLocator.objFAQ,getTextVal(MLWalletBranchLocator.objFAQ,"Option"));
			verifyElementPresent(MLWalletBranchLocator.objCareers,getTextVal(MLWalletBranchLocator.objCareers,"Option"));
			verifyElementPresent(MLWalletBranchLocator.objLogin,getTextVal(MLWalletBranchLocator.objLogin,"Option"));
			verifyElementPresent(MLWalletBranchLocator.objDownloadApp,getTextVal(MLWalletBranchLocator.objDownloadApp,"Option"));
			logger.info("BL_TC_05, Branch Locator Hamburger Button Functionality Validated");
			ExtentReporter.extentLoggerPass("BL_TC_05","BL_TC_05, Branch Locator Hamburger Button Functionality Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void branchLocatorBranchesButtonFunctionality_BL_TC_07() throws Exception {
		ExtentReporter.HeaderChildNode("Branch Locator Branches Button Functionality");
		branchLocatorNavigation();
		verifyElementPresentAndClick(MLWalletBranchLocator.objBranchLocatorHamburgerMenu,"Hamburger Menu Button");
		verifyElementPresentAndClick(MLWalletBranchLocator.objBranches,getTextVal(MLWalletBranchLocator.objBranches,"Option"));
		if(verifyElementPresent(MLWalletLoginPage.objBranchLocator,getTextVal(MLWalletLoginPage.objBranchLocator,"Page"))){
			branchLocatorPageValidation();
			logger.info("BL_TC_07, Branch Locator Branches Button Functionality validated");
			ExtentReporter.extentLoggerPass("BL_TC_07","BL_TC_07, Branch Locator Branches Button Functionality validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void branchLocatorPromosFunctionality_BL_TC_08() throws Exception {
		ExtentReporter.HeaderChildNode("Branch Locator Promos Button Functionality");
		branchLocatorNavigation();
		verifyElementPresentAndClick(MLWalletBranchLocator.objBranchLocatorHamburgerMenu,"Hamburger Menu Button");
		verifyElementPresentAndClick(MLWalletBranchLocator.objPromos,getTextVal(MLWalletBranchLocator.objPromos,"Option"));
		waitTime(10000);
		if(verifyElementPresent(MLWalletBranchLocator.objPromos,getTextVal(MLWalletBranchLocator.objPromos,"Page"))){
			logger.info("BL_TC_08, Branch Locator Promos Button Functionality validated and App Navigated to Promos Page");
			ExtentReporter.extentLoggerPass("BL_TC_08","BL_TC_08, Branch Locator Promos Button Functionality validated and App Navigated to Promos Page");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void branchLocatorBlogFunctionality_BL_TC_09() throws Exception {
		ExtentReporter.HeaderChildNode("Branch Locator Blog Functionality");
		branchLocatorNavigation();
		verifyElementPresentAndClick(MLWalletBranchLocator.objBranchLocatorHamburgerMenu,"Hamburger Menu Button");
		verifyElementPresentAndClick(MLWalletBranchLocator.objBlog,getTextVal(MLWalletBranchLocator.objBlog,"Option"));
		waitTime(10000);
		if(verifyElementPresent(MLWalletBranchLocator.objBlog,getTextVal(MLWalletBranchLocator.objBlog,"Page"))){
			logger.info("BL_TC_09, Branch Locator Blog Button Functionality validated and App Navigated to Blog Page");
			ExtentReporter.extentLoggerPass("BL_TC_09","BL_TC_09, Branch Locator Blog Button Functionality validated and App Navigated to Blog Page");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void branchLocatorNewsLettersFunctionality_BL_TC_10() throws Exception {
		ExtentReporter.HeaderChildNode("Branch Locator NewsLetter Functionality");
		branchLocatorNavigation();
		verifyElementPresentAndClick(MLWalletBranchLocator.objBranchLocatorHamburgerMenu,"Hamburger Menu Button");
		verifyElementPresentAndClick(MLWalletBranchLocator.objNewsLetters,getTextVal(MLWalletBranchLocator.objNewsLetters,"Option"));
		waitTime(10000);
		if(verifyElementPresent(MLWalletBranchLocator.objNewsLetters,getTextVal(MLWalletBranchLocator.objNewsLetters,"Page"))) {
			logger.info("BL_TC_10, Branch Locator NewsLetter Button Functionality validated and App Navigated to NewsLetter Page");
			ExtentReporter.extentLoggerPass("BL_TC_10", "BL_TC_10, Branch Locator NewsLetter Button Functionality validated and App Navigated to NewsLetter Page");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void branchLocatorFAQFunctionality_BL_TC_11() throws Exception {
		ExtentReporter.HeaderChildNode("Branch Locator FAQ Functionality");
		branchLocatorNavigation();
		verifyElementPresentAndClick(MLWalletBranchLocator.objBranchLocatorHamburgerMenu,"Hamburger Menu Button");
		verifyElementPresentAndClick(MLWalletBranchLocator.objFAQ,getTextVal(MLWalletBranchLocator.objFAQ,"Option"));
		waitTime(10000);
		if(verifyElementPresent(MLWalletBranchLocator.objFrequentlyAskedQuestions,getTextVal(MLWalletBranchLocator.objFrequentlyAskedQuestions,"Page"))) {
			logger.info("BL_TC_11, Branch Locator FAQ Button Functionality validated and App Navigated to Frequently Asked Questions Page");
			ExtentReporter.extentLoggerPass("BL_TC_11", "BL_TC_11, Branch Locator FAQ Button Functionality validated and App Navigated to Frequently Asked Questions Page");
			System.out.println("-----------------------------------------------------------");
		}
	}


//============================ Trouble signing in ========================================================//

	public void troubleSigningInNavigation() throws Exception {
		if(verifyElementPresentAndClick(MLWalletTroubleSigningInPage.objTroubleSigningIn,getTextVal(MLWalletTroubleSigningInPage.objTroubleSigningIn,"Link"))){
			verifyElementPresent(MLWalletLoginPage.objBranchLocator,getTextVal(MLWalletLoginPage.objBranchLocator,"Page"));
			logger.info("Navigated to Branch Locator page");
		}else{
		logger.info("Not Navigated to Branch Locator Page");
		}
	}














}







