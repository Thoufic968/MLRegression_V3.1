package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import com.mlwallet.pages.MLWalletPayBillsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletRegressionPayBillsScripts {

    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletRegressionPayBillsScripts.deviceName=deviceName;
        MLWalletRegressionPayBillsScripts.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }


//============================== Pay Bills Scripts==========================================================//


    @Test(priority = 1)
    public void payBillsValidation() throws Exception {
        MLWalletBusinessLogic.payBillsPageValidation_PB_TC_01();
    }

    @Test(priority = 2)
    public void billerCategoriesValidation() throws Exception {
        MLWalletBusinessLogic.billerCategories_PB_TC_02();
    }

    @Test(priority = 3)
    public void billersInAlphabeticalOrder() throws Exception {
        MLWalletBusinessLogic.billersInAlphabeticalOrder_PB_TC_03();
    }

    @Test(priority = 4)
    public void selectBiller() throws Exception {
        MLWalletBusinessLogic.selectBiller_PB_TC_04();
    }

    @Test(priority = 5)
    public void searchBiller() throws Exception {
        MLWalletBusinessLogic.searchBiller_PB_TC_05();
    }

    @Test(priority = 6)
    public void billingInformationInput() throws Exception {
        MLWalletBusinessLogic.billingInformationInput_PB_TC_06();
    }


   @Test(priority = 7)
    public void payBillsWithValidInputs_PB_TC_07() throws Exception {
        MLWalletBusinessLogic.payBillsWithValidInputs_PB_TC_07();
    }

    @Test(priority = 8)
    public void payBillsInRecentTransactions_PB_TC_08() throws Exception {
        MLWalletBusinessLogic.payBillsInRecentTransactions_PB_TC_08();
    }

    @Test(priority = 9)
    public void payBillsInsufficientBalance_PB_TC_09() throws Exception {
        MLWalletBusinessLogic.payBillsInsufficientBalance_PB_TC_09();
    }

    @Test(priority = 10)
    public void billingInformationInvalidInput_PB_TC_10() throws Exception {
        MLWalletBusinessLogic.billingInformationInvalidInput_PB_TC_10();
    }


   @Test(priority = 11)
    public void addBillerToPayBills_PB_TC_12() throws Exception {
        MLWalletBusinessLogic.addBillerToPayBills_PB_TC_12();
    }

//   @Test(priority = 12)
//   public void addBillerInvalidInputs() throws Exception {
//        MLWalletBusinessLogic.addBillerInvalidInputs_PB_TC_13();
//  }


    @Test(priority = 13)
    public void editAddedBillerToPayBills_PB_TC_14() throws Exception {
        MLWalletBusinessLogic.editAddedBillerToPayBills_PB_TC_14();
    }


    @Test(priority = 14)
    public void deleteAddedBillerPayBills() throws Exception {
        MLWalletBusinessLogic.deleteAddedBillerPayBills_PB_TC_15();
    }
}
