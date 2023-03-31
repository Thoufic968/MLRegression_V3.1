package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletCashInViaBranch {
    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletCashInViaBranch.deviceName=deviceName;
        MLWalletCashInViaBranch.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }

//======================================================================================================//


    //******************* Cash In Via Branch ====================================/

    @Test(priority = 1)
    public void cashInviaBranch_ValidAmount_Scenario_CIBR_TC_01() throws Exception
    {
        MLWalletBusinessLogic.cashInviaBranch_ValidAmount_Scenario_CIBR_TC_01();
    }

    @Test(priority = 2)
    public void cashInViaBranchCancelTransactionScenario_CIBR_TC_02() throws Exception
    {
        MLWalletBusinessLogic.cashInViaBranchCancelTransactionScenario_CIBR_TC_02();
    }

    @Test(priority = 3)
    public void cashInviaBranch_Invalid_Amount_CIBR_TC_03() throws Exception
    {
        MLWalletBusinessLogic.cashInviaBranch_Invalid_Amount_CIBR_TC_03();
    }

    @Test(priority = 4)
    public void cashInViaBranch_Maximum_Limit_Amount_CIBR_TC_04() throws Exception
    {
        MLWalletBusinessLogic.cashInViaBranch_Maximum_Limit_Amount_CIBR_TC_04();
    }


//============================================================================================================//


    @Test(priority = 5)
    public void cashInViaBRanchInvalidAmount_CIBR_TC_05() throws Exception {
        MLWalletBusinessLogic.cashInViaBRanchInvalidAmount_CIBR_TC_05();
    }

    @Test(priority = 6)
    public void cashInViaBranchUIValidation_CIBR_TC_06() throws Exception {
        MLWalletBusinessLogic.cashInViaBranchUIValidation_CIBR_TC_06();
    }

    @Test(priority = 7)
    public void cashInViaBranchBackBtnValidation_CIBR_TC_07() throws Exception {
        MLWalletBusinessLogic.cashInViaBranchBackBtnValidation_CIBR_TC_07();
    }


}
