package com.mlwallet.pages;

import org.openqa.selenium.By;

public class MLWalletCashInBank {

    public static By objCashIn = By.xpath("//*[@text='Cash In']");

    public static By objMyBankAccount = By.xpath("//*[@text='My Bank Account']");

    public static By objSelectABank = By.xpath("//*[@text='Select a Bank']");
    public static By objTestBankOnline = By.xpath("//*[@text='Test Bank Online']");

    public static By objDragonPay = By.xpath("//*[@text='Dragon Pay']");

    public static By objBankCashIn = By.xpath("//*[@text='Bank Cash In']");

    public static By objAmountEditField = By.xpath("//*[@text='PHP']/following-sibling::android.widget.EditText");

    public static By objNextBtn = By.xpath("//*[@text='Next']");

    public static By objServiceFeeMsg = By.xpath("(//*[@text='PHP']/parent::android.view.ViewGroup/following-sibling::android.widget.TextView)[2]");

    public static By objDragonPayChargesMsg = By.xpath("(//*[@text='Continue']/parent::android.view.ViewGroup/preceding-sibling::android.widget.TextView)[2]");

    public static By objContinueBtn = By.xpath("//*[@text='Continue']");

    public static By objReviewTransaction = By.xpath("//*[@text='Review Transaction']");

    public static By objBankTransferCutOffTime = By.xpath("//*[@text='BANK TRANSFER CUT-OFF TIME']/parent::android.view.ViewGroup/following-sibling::android.widget.TextView");

    public static By objReferenceNumberMsg = By.xpath("//*[@resource-id='ContentPlaceHolder1_mainMsg']");

    public static By objLoginIdTxtField = By.xpath("//*[@resource-id='ContentPlaceHolder1_userIdTextBox']");

    public static By objPasswordTxtField = By.xpath("//*[@resource-id='ContentPlaceHolder1_passwdTextBox']");

    public static By objWebContinueBtn = By.xpath("//*[@resource-id='ContentPlaceHolder1_getAccountsButton']");

    public static By objPayBtn = By.xpath("//*[@resource-id='ContentPlaceHolder1_transferButton']");

    public static By objBankReferenceNumber = By.xpath("//*[@resource-id='ContentPlaceHolder1_refnoTextBox']");

    public static By objStatus = By.xpath("//*[@resource-id='ContentPlaceHolder1_statusTextBox']");

    public static By objMessage = By.xpath("//*[@resource-id='ContentPlaceHolder1_msgTextBox']");

    public static By objSuccessMsg = By.xpath("//*[@resource-id='ContentPlaceHolder1_Label1']");

    public static By objMinimumTransactionPopupMsg = By.xpath("(//*[@text='Ok']/parent::android.view.ViewGroup/preceding-sibling::android.widget.TextView)[2]");


}
