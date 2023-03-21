package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletSettingScripts {

    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletSettingScripts.deviceName=deviceName;
        MLWalletSettingScripts.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }
//=========================================================================================================//

    @Test(priority = 1)
    public void accountDetails() throws Exception {
        MLWalletBusinessLogic.accountDetailsValidation_SS_TC_01();
    }

    @Test(priority = 2)
    public void invalidMLPinValidation() throws Exception {
        MLWalletBusinessLogic.invalidMLPinValidation_SS_TC_03();
    }

    @Test(priority = 3)
    public void validMLPinValidation() throws Exception {
        MLWalletBusinessLogic.validMLPinValidation_SS_TC_02();
    }

    @Test(priority = 4)
    public void mlWalletSettingAccRecovery() throws Exception {
        MLWalletBusinessLogic.mlWalletSettingAccRecovery();
    }





}
