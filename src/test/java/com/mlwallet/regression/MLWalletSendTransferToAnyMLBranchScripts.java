package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletSendTransferToAnyMLBranchScripts {

    public static String deviceName;
    public static String portno;
    public static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName", "portno"})
    @BeforeMethod
    public void before(String deviceName, String portno) throws Exception {
        MLWalletSendTransferToAnyMLBranchScripts.deviceName = deviceName;
        MLWalletSendTransferToAnyMLBranchScripts.portno = portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet", deviceName, portno);
    }

//============================================================================================//


    @Test(priority = 1)
    public void sendMoneyToMLBranch() throws Exception {
        MLWalletBusinessLogic.sendMoneyToMLBranch_STB_TC_01();
    }

    @Test(priority = 2)
    public void sendMoneyAddRecipient() throws Exception {
        MLWalletBusinessLogic.sendMoneyAddRecipient_STB_TC_03();
    }

    @Test(priority = 3)
    public void sendMoneyContactDuplicate() throws Exception {
        MLWalletBusinessLogic.sendMoneyContactDuplicate_STB_TC_04();
    }

    @Test(priority = 4)
    public void sendMoneyToSavedRecipient() throws Exception {
        MLWalletBusinessLogic.sendMoneyToSavedRecipient_STB_TC_02();
    }

    @Test(priority = 5)
    public void sendMoneyEditRecipient() throws Exception {
        MLWalletBusinessLogic.sendMoneyEditRecipient_STB_TC_06();
    }

    @Test(priority = 6)
    public void sendMoneyDeleteRecipient() throws Exception {
        MLWalletBusinessLogic.sendMoneyDeleteRecipient_STB_TC_05();
    }

    @Test(priority = 7)
    public void sendMoneyInvalidAmount() throws Exception {
        MLWalletBusinessLogic.sendMoneyInvalidAmount_STB_TC_09("0");
    }

    @Test(priority = 8)
    public void sendMoneyInsufficientAmount() throws Exception {
        MLWalletBusinessLogic.sendMoneyInsufficientAmount_STB_TC_10();
    }

    @Test(priority = 9)
    public void sendMoneyMaximumAmount() throws Exception {
        MLWalletBusinessLogic.sendMoneyMaximumAmount_STB_TC_12();
    }

    @Test(priority = 10)
    public void sendMoneyRequiredDetails() throws Exception {
        MLWalletBusinessLogic.sendMoneyRequiredDetails_STB_TC_08();
    }

    @Test(priority = 11)
    public void sendMoneyInvalidDetails() throws Exception {
        MLWalletBusinessLogic.sendMoneyInvalidDetails_STB_TC_07();
    }


    //========================================================================================================//
    @Test(priority = 12)
    public void sendTransferUIValidation_STB_TC_13() throws Exception {
        MLWalletBusinessLogic.sendTransferUIValidation_STB_TC_13();
    }


    @Test(priority = 13)
    public void sendMoneyToBranchUIValidation_STB_TC_14() throws Exception {
        MLWalletBusinessLogic.sendMoneyToBranchUIValidation_STB_TC_14();
    }

    @Test(priority = 14)
    public void sendMoneyToBranchSaveRecipientPageUIValidation_STB_TC_15() throws Exception {
        MLWalletBusinessLogic.sendMoneyToBranchSaveRecipientPageUIValidation_STB_TC_15();
    }

    @Test(priority = 15)
    public void sendMoneyToBranchSuccessUIValidation_STB_TC_16() throws Exception {
        MLWalletBusinessLogic.sendMoneyToBranchSuccessUIValidation_STB_TC_16();
    }

    @Test(priority = 16)
    public void sendMoneyToBranchConfirmDetailsPageUIValidation_STB_TC_17() throws Exception {
        MLWalletBusinessLogic.sendMoneyToBranchConfirmDetailsPageUIValidation_STB_TC_17("100");
    }

    @Test(priority = 17)
    public void sendMoneyToBranchSelectPaymentMethodPageUIValidation_STB_TC_18() throws Exception {
        MLWalletBusinessLogic.sendMoneyToBranchSelectPaymentMethodPageUIValidation_STB_TC_18("100");
    }

    @Test(priority = 18)
    public void sendMoneyToBranchEnterAmountPageUIValidation_STB_TC_19() throws Exception {
        MLWalletBusinessLogic.sendMoneyToBranchEnterAmountPageUIValidation_STB_TC_19();
    }

    @Test(priority = 19)
    public void kwartaPadalaTransactionDetailsUIValidation_STB_TC_20() throws Exception {
        MLWalletBusinessLogic.kwartaPadalaTransactionDetailsUIValidation_STB_TC_20();
    }

    @Test(priority = 20)
    public void sendMoneyToBranchAddRecipientPageUIValidation_STB_TC_21() throws Exception {
        MLWalletBusinessLogic.sendMoneyToBranchAddRecipientPageUIValidation_STB_TC_21();
    }

}