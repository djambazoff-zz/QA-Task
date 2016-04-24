package theEarnest;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class jungleSocks {
	static WebDriver driver;
	static ArrayList <String> errorRate = new ArrayList <String>(); // Error State collector
	static ArrayList <String> errorPrice = new ArrayList <String>(); // Error State collector
	
	
	public static  void verifyTaxRate(){
		String tempOption;
		int b = 1;        // State option iterator
		int size = 2;     //assigns the number of current State options available
		
		while(b<size){
			driver.get("https://jungle-socks.herokuapp.com/");//navigate to webpage
			
					//**** Read and Collect all State options available ****
			WebElement dropListOption = driver.findElement(By.name("state"));//find the drop list
			Select option = new Select(dropListOption);//Instantiate Select class and sends the drop list otpions
			List<WebElement> allOptions = option.getOptions();
			size = allOptions.size();//Update the size, in case corrections are made.
			tempOption = allOptions.get(b).getAttribute("Value");
			 
					//**** Add desired items ****
			driver.findElement(By.cssSelector("#line_item_quantity_zebra")).sendKeys("1");
			driver.findElement(By.cssSelector("#line_item_quantity_lion")).sendKeys("1");
			driver.findElement(By.cssSelector("#line_item_quantity_elephant")).sendKeys("1");
			driver.findElement(By.cssSelector("#line_item_quantity_giraffe")).sendKeys("1");
					
					//**** Choose State Option
			WebDriverWait wait = new WebDriverWait(driver, 10);//instantiate object of WebDriverWait class
			wait.until(ExpectedConditions.visibilityOf(allOptions.get(b)));//waiting for certain element to be visible
			allOptions.get(b).click(); // choose desired State Option
			driver.findElement(By.name("commit")).click();// click on submit
					    
			//************************  Verification  ********************************
			
					//**** SubTotal and Tax fields preparation for verification
			String sTotal = (driver.findElement(By.id("subtotal")).getText()).replaceAll("[$]", "");//extract/trim text value
			String Tax = (driver.findElement(By.id("taxes")).getText()).replaceAll("[$]", "");
			double subTotal = Double.parseDouble(sTotal);//convert extracted value to double
			double actualTax = Double.parseDouble(Tax);
					
					//**** Verification thru if statements
			if ("CA".equals(tempOption)){
				if(actualTax/subTotal == 0.08)
					System.out.println( tempOption + " tax rate is verified.");
				else{
					System.out.println( tempOption + " ATTENTION!! Tax rate is NOT verified.");
					errorRate.add(tempOption);//assign the name of the State where an error is present
				}
			}
			else if ("NY".equals(tempOption)){
				if(actualTax/subTotal == 0.06)
					System.out.println( tempOption + " tax rate is verified.");
				else{
					System.out.println( tempOption + " ATTENTION!! Tax rate is NOT verified.");
					errorRate.add(tempOption);
				}
			}
			else if ("MN".equals(tempOption)){
				if(actualTax/subTotal == 0.00)
					System.out.println( tempOption + " tax rate is verified.");
				else{
					System.out.println( tempOption + " ATTENTION!! Tax rate is NOT verified.");
					errorRate.add(tempOption);
				}
			}
			else {
				if(actualTax/subTotal == 0.05)
					System.out.println( tempOption + " tax rate is verified.");
				else{
					System.out.println( tempOption + " ATTENTION!! Tax rate is NOT verified.");
					errorRate.add(tempOption);
				}
			}
			b++;//increase iterator by 1 so next State Option would be chosen
		}
				// **** One statement report including all states where an error is present
		
	}
	
	public static void verifyPrices(int value1, int value2, int value3, int value4){
		driver.get("https://jungle-socks.herokuapp.com/");
		// Extract listed price of the items
		double zebraPrice=Double.parseDouble(driver.findElement(By.xpath("html/body/form/table[1]/tbody/tr[2]/td[2]")).getText());
		double lionPrice=Double.parseDouble(driver.findElement(By.xpath("html/body/form/table[1]/tbody/tr[3]/td[2]")).getText());
		double elephantPrice=Double.parseDouble(driver.findElement(By.xpath("html/body/form/table[1]/tbody/tr[4]/td[2]")).getText());
		double giraffePrice=Double.parseDouble(driver.findElement(By.xpath("html/body/form/table[1]/tbody/tr[5]/td[2]")).getText());
		
		//Add quantity for the purpose of testing
		driver.findElement(By.cssSelector("#line_item_quantity_zebra")).sendKeys(String.valueOf(value1));
		driver.findElement(By.cssSelector("#line_item_quantity_lion")).sendKeys(String.valueOf(value2));
		driver.findElement(By.cssSelector("#line_item_quantity_elephant")).sendKeys(String.valueOf(value3));
		driver.findElement(By.cssSelector("#line_item_quantity_giraffe")).sendKeys(String.valueOf(value4));
		
		// Select state and click checkout
		WebElement dropListOption = driver.findElement(By.name("state"));//find the drop list
		Select option = new Select(dropListOption);//Instantiate Select class and sends the drop list otpions
		List<WebElement> allOptions = option.getOptions();
		allOptions.get(3).click();
		driver.findElement(By.name("commit")).click();// click on submit
		
		//Extract output price from subTotal field
		String sTotal = (driver.findElement(By.id("subtotal")).getText()).replaceAll("[$]", "");
		Double subTotal=Double.parseDouble(sTotal);
		
		//verify price and output confirmation message
		if(value1==1){
			if( subTotal==zebraPrice)
				System.out.println("Zebra-Price is confirmed");
			else
				errorPrice.add("ATTENTION:Zebra-Price is NOT confirmed");
		}
		else if(value2==1)
			if( subTotal==lionPrice)
				System.out.println("Lion-Price is confirmed");
			else
				errorPrice.add("ATTENTION:Lion-Price is NOT confirmed");
				
		else if(value3==1)
			if( subTotal==elephantPrice)
				System.out.println("Elephant-Price is confirmed");
			else
				errorPrice.add("ATTENTION:Elephant-Price is NOT confirmed");
		else if(value4==1)
			if(subTotal==giraffePrice)
				System.out.println("Giraffe-Price is confirmed");
			else
				errorPrice.add("ATTENTION:Giraffe-Price is NOT confirmed");
		
	}
	public static  void openBrowser(){
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}
	public static  void closeBrowser(){
		driver.quit();
	}
	public static void finalReport(){
		System.out.println("All State tax rates are verified, except: - " +  errorRate);
		System.out.println("All price items are verified, except: - " +  errorPrice);
	}
	public static void main(String[] args) 	throws InterruptedException{
		
		openBrowser();
		verifyPrices(1,0,0,0);
		verifyPrices(0,1,0,0);
		verifyPrices(0,0,1,0);
		verifyPrices(0,0,0,1);
		verifyTaxRate();
		finalReport();
		closeBrowser();
}
}


