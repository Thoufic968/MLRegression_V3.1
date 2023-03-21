package com.mlwallet.pages;

import org.openqa.selenium.By;

public class MLWalletEloadPage {

	public static By objEloadTab=By.xpath("//*[@text='Buy eLoad']");
	public static By objEloadtransactionPage=By.xpath("//*[@text='eLoad Transaction']");
	
	public static By telcoOptions(int indexTab)
	{
		return By.xpath("(//*[android.view.ViewGroup]/following-sibling::android.view.ViewGroup/(descendant::android.widget.ImageView)[1])["+indexTab+"]");
	}
	
//	public static By objTelecommunicationTab=By.xpath("//*[android.view.ViewGroup]/following-sibling::android.view.ViewGroup/(descendant::android.widget.ImageView)[4]");
	public static By objMobileNoField=By.xpath("//*[@class='android.widget.EditText']");
	public static By objNextBtn=By.xpath("//*[@text='Next']");
	public static By objLoadSelectionPage=By.xpath("//*[@text='Load Selection']");
	public static By objPromoLoadTab=By.xpath("//*[@text='Promo Load']");
	public static By objTransaction=By.xpath("(//*[android.view.ViewGroup]/following-sibling::android.view.ViewGroup/descendant::android.widget.TextView)[11]");
	public static By objContinuePromoPopUp=By.xpath("//*[@text='Would you like to continue with this promo?']");
	public static By objPromoName=By.xpath("(//*[android.view.ViewGroup]/child::android.view.ViewGroup/child::android.widget.TextView)[5]");
	public static By objConfirmBtn=By.xpath("//*[@text='Confirm']");
	public static By objTransactionDetailsPage=By.xpath("//*[@text='Transaction Details']");
	public static By objTypeOfPromoUsed=By.xpath("(//*[@resource-id='Type'])[2]");
	public static By objMobileNumber=By.xpath("(//*[@resource-id='Mobile Number'])[2]");
	public static By objAmountToSend=By.xpath("(//*[@resource-id='Amount to Send'])[2]");
	public static By objServiceFee=By.xpath("(//*[@resource-id='Service Fee'])[2]");
	public static By objTotalAmount=By.xpath("(//*[@resource-id='Total'])[2]");
	
	public static By objErrorMsg=By.xpath("//*[android.widget.EditText]/following-sibling::android.widget.TextView");
	
	public static By objInsufficientBalPopup=By.xpath("(//*[@resource-id='modal-confirm-button']/preceding-sibling::android.widget.TextView)[2]");
	public static By objOkBtn=By.xpath("//*[@text='Ok']");
	
	
	
	
	

}
