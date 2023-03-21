package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletCashInViaBankScripts {

    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletCashInViaBankScripts.deviceName=deviceName;
        MLWalletCashInViaBankScripts.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }

//==================================================================================================//

    @Test(priority = 1)
    public void cashInViaBank() throws Exception {
        MLWalletBusinessLogic.cashInViaBank_CIBA_TC_01();
    }

    @Test(priority = 2)
    public void cashInViaBankMinimumTransactionLimit() throws Exception {
        MLWalletBusinessLogic.cashInViaBankMinimumTransactionLimit_CIBA_TC_03();
    }

    @Test(priority = 3)
    public void cashInViaBankMaximumTransaction() throws Exception {
        MLWalletBusinessLogic.cashInViaBankMaximumTransaction();
    }


}
