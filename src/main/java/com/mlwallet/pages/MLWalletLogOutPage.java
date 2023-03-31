package com.mlwallet.pages;

import org.openqa.selenium.By;

public class MLWalletLogOutPage {

    public static By objHamburgerMenu = By.xpath("(//*[android.view.ViewGroup]/child::android.view.ViewGroup/child::android.widget.TextView)[3]");
    public static By objLogoutBtn = By.xpath("//*[@text='Logout']");
    public static By objTroubleSignIn = By.xpath("//*[@text='Trouble signing in?']");
    public static By objChangeNumber = By.xpath("//*[contains(@text,'Number')]");
    public static By objLogoOutPopupMsg = By.xpath("//*[@text='Are you sure you would like to logout?']");
    public static By objCancelBtn = By.xpath("//*[@text='Cancel']");
    public static By objHome = By.xpath("//*[@text='Home']");

}
