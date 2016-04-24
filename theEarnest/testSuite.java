package theEarnest;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class testSuite {

		jungleSocks mySocks=new jungleSocks();
		@BeforeTest
		public void setUpBrowser(){
			mySocks.openBrowser();
		}
		@Test(priority=0)
		public void priceCheck(){
			mySocks.verifyPrices(1, 0, 0, 0);
			mySocks.verifyPrices(0, 1, 0, 0);
			mySocks.verifyPrices(0, 0, 1, 0);
			mySocks.verifyPrices(0, 0, 0, 1);
		}
		@Test(priority=1)
		public void taxRateCheck(){
			mySocks.verifyTaxRate();
		}
		@Test(priority=2)
		public void summaryReport(){
			mySocks.finalReport();
		}
		@AfterTest
		public void setDownBrowser(){
			mySocks.closeBrowser();
		}
		
}
