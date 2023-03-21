package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.business.mlwallet.MLWalletBusinessLogic.prop;

public class MLWalletBuyEload {

    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletBuyEload.deviceName=deviceName;
        MLWalletBuyEload.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }




    //===================================buy eload=======================================
    @Test(priority = 1)
    public void mlWallet_buying_eLoad_Scenario() throws Exception
    {
        MLWalletBusinessLogic.buying_eLoad(prop.getproperty("Fully_Verified"),4);

    }

    @Test(priority = 2)
    public void mlWallet_eLoad_InvalidMobNumber() throws Exception
    {
        MLWalletBusinessLogic.buying_eload_Invalid_Mob_Number();
    }

    @Test(priority = 3)
    public void mlwallet_eLoad_without_input_mobile_number() throws Exception
    {
        MLWalletBusinessLogic.buying_eload_without_input_Mob_Number();
    }

    @Test(priority = 4)
    public void mlwallet_eLoad_without_Telecommunication_Selected() throws Exception
    {
        MLWalletBusinessLogic.buying_eload_without_telecommunication_Selected();
    }

    @Test(priority = 5)
    public void mlWalet_insufficient_balance() throws Exception
    {
        MLWalletBusinessLogic.buying_eload_insufficient_balance(4);
    }

//    @Test(priority = 6)
//    public void mlWalet_daily_maximum_limit() throws Exception
//    {
//        MLWalletBusinessLogic.buying_eload_dailyMaximum_Limit();
//    }

}
