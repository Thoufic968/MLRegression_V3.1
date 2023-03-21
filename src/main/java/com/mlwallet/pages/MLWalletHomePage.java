package com.mlwallet.pages;

import org.openqa.selenium.By;

public class MLWalletHomePage {

    public static By objRecentTransactions = By.xpath("(//*[@text='Recent Transactions'])");

    public static By objCashOutButton = By.xpath("(//*[@text='Cash Out'])[1]");

    public static By objKwartaPadala = By.xpath("(//*[@text='Kwarta Padala'])[1]");

    public static By objWalletToWallet = By.xpath("(//*[@text='Wallet to Wallet'])[1]");

    public static By objPayBills = By.xpath("(//*[@text='Posted']/parent::android.view.ViewGroup/following-sibling::android.widget.TextView)[1]");


}
