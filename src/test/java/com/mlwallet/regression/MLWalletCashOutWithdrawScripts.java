package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.apache.poi.ss.formula.functions.T;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletCashOutWithdrawScripts {
    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletCashOutWithdrawScripts.deviceName=deviceName;
        MLWalletCashOutWithdrawScripts.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }

//============================================================================================================//


//    @Test(priority = 1)
//    public void cashOutWithdrawBank_WM_TC_01() throws Exception
//    {
//        MLWalletBusinessLogic.cashOutWithdrawBank_WM_TC_01("100");
//    }
//
//    @Test(priority = 2)
//    public void cashOutWithInvalidAccNumber_WM_TC_02() throws Exception
//    {
//        MLWalletBusinessLogic.cashOutWithInvalidAccNumber_WM_TC_02();
//    }
//
//    @Test(priority = 3)
//    public void cashOutWithdrawBankMaxAmount_WM_TC_03() throws Exception
//    {
//        MLWalletBusinessLogic.cashOutWithdrawBankMaxAmount_WM_TC_03("60000");
//    }
//
//    @Test(priority = 4)
//    public void cashOutWithdrawMinTransactionLimit_WM_TC_04() throws Exception
//    {
//        MLWalletBusinessLogic.cashOutWithdrawMinTransactionLimit_WM_TC_04("10");
//    }
//
//    @Test(priority = 5)
//    public void cashOutWithdrawBranch_WM_TC_05() throws Exception
//    {
//        MLWalletBusinessLogic.cashOutWithdrawBranch_WM_TC_05();
//    }
//
//    @Test(priority = 6)
//    public void cashOutMaxLimit_WM_TC_06() throws Exception
//    {
//        MLWalletBusinessLogic.cashOutMaxLimit_WM_TC_06();
//    }
//
//    @Test(priority = 7)
//    public void cashOutInsufficientBalance_WM_TC_07() throws Exception {
//        MLWalletBusinessLogic.cashOutInsufficientBalance_WM_TC_07();
//    }
//
//    @Test(priority = 8)
//    public void cashOutBuyerTierLevelAcc_WM_TC_08() throws Exception
//    {
//        MLWalletBusinessLogic.cashOutBuyerTierLevelAcc_WM_TC_08();
//    }

//=========================== Phase 2=================================================================//


    @Test(priority = 9)
    public void cashOutInvalidBank_WM_TC_10() throws Exception {
        MLWalletBusinessLogic.cashOutInvalidBank_WM_TC_10();
    }

    @Test(priority = 10)
    public void searchAndSelectBank_WM_TC_11() throws Exception {
        MLWalletBusinessLogic.searchAndSelectBank_WM_TC_11();
    }

    @Test(priority = 11)
    public void cashOutInvalidAmount_WM_TC_12() throws Exception {
       MLWalletBusinessLogic.cashOutInvalidAmount_WM_TC_12();
    }

    @Test(priority = 12)
    public void cashOutSaveRecipient_WM_TC_13() throws Exception {
        MLWalletBusinessLogic.cashOutSaveRecipient_WM_TC_13("100");
    }

    @Test(priority = 13)
    public void cashOutRecipientDuplicate_WM_TC_14() throws Exception {
        MLWalletBusinessLogic.cashOutRecipientDuplicate_WM_TC_14("100");
    }

    @Test(priority = 14)
    public void cashOutUIValidation_WM_TC_16() throws Exception {
        MLWalletBusinessLogic.cashOutUIValidation_WM_TC_16();
    }

    @Test(priority = 15)
    public void cashOutWithdrawBackBtnValidation_WM_TC_17() throws Exception {
        MLWalletBusinessLogic.cashOutWithdrawBackBtnValidation_WM_TC_17();
    }

    @Test(priority = 16)
    public void cashOutToBranchUIValidation_WM_TC_18() throws Exception {
        MLWalletBusinessLogic.cashOutToBranchUIValidation_WM_TC_18();
    }

    @Test(priority = 17)
    public void cashOutToBranchBackBtnValidation_WM_TC_19 () throws Exception {
        MLWalletBusinessLogic.cashOutToBranchBackBtnValidation_WM_TC_19();
    }

    @Test(priority = 18)
    public void cashOutOTPPageUIValidation_WM_TC_20() throws Exception {
        MLWalletBusinessLogic.cashOutOTPPageUIValidation_WM_TC_20("100");
    }

    @Test(priority = 19)
    public void cashOutOTPPageBackBtnValidation() throws Exception {
        MLWalletBusinessLogic.cashOutOTPPageBackBtnValidation_WM_TC_21("100");
    }


}
