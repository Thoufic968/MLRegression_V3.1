package com.mlwallet.pages;

import org.openqa.selenium.By;

public class MLWalletLogOutPage {

    public static By objHamburgerMenu = By.xpath("(//*[android.view.ViewGroup]/child::android.view.ViewGroup/child::android.widget.TextView)[3]");

    public static By objLogoutBtn = By.xpath("//*[@text='Logout']");

    public static By objTroubleSignIn = By.xpath("//*[@text='Trouble signing in?']");

    public static By objChangeNumber = By.xpath("//*[contains(@text,'Number')]");



}
